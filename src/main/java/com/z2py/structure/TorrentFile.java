package com.z2py.structure;
import org.eclipse.ecf.protocol.bittorrent.internal.encode.BEncodedDictionary;
import org.eclipse.ecf.protocol.bittorrent.internal.encode.Decode;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * The <code>TorrentFile</code> class is a representation of the information
 * stored within a <code>.torrent</code> file. Files can be set with the
 * {@link #setTargetFile(File)} method and then have its integrity checked
 * against the torrent's hash sum values {@link #validate()} method.
 */
public class TorrentFile {

    static MessageDigest shaDigest;

    private final String[] filenames;

    /**
     * An array of Strings that corresponds to the SHA-1 hash of each piece.
     */
    private final String[] pieces;

    private final long[] lengths;

    private final byte[] torrentData;

    private final ByteBuffer buffer;

    private final BEncodedDictionary dictionary;

    private final String tracker;

    private final String infoHash;

    private final String hexHash;

    private File file;

    private String name;

    private long total;

    private final int pieceLength;

    private final int numPieces;

    static {
        try {
            shaDigest = MessageDigest.getInstance("SHA-1"); //$NON-NLS-1$
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new <code>Torrent</code> to analyze the provided torrent
     * file.
     *
     * @param file
     *            the torrent file
     * @throws IllegalArgumentException
     *             If <code>file</code> is <code>null</code> or a directory
     * @throws IOException
     *             If an I/O error occurs whilst analyzing the torrent file
     */
    public TorrentFile(File file) throws IllegalArgumentException, IOException {
        if (file == null) {
            throw new IllegalArgumentException("The file cannot be null"); //$NON-NLS-1$
        } else if (file.isDirectory()) {
            throw new IllegalArgumentException("The provided file is a directory"); //$NON-NLS-1$
        }
        name = file.getName();
        if (name.endsWith(".torrent")) { //$NON-NLS-1$
            name = name.substring(0, name.length() - 8);
        }

        dictionary = Decode.bDecode(new FileInputStream(file));
        torrentData = dictionary.toString().getBytes("ISO-8859-1"); //$NON-NLS-1$
        tracker = (String) dictionary.get("announce"); //$NON-NLS-1$
        final BEncodedDictionary info = (BEncodedDictionary) dictionary.get("info"); //$NON-NLS-1$
        final List list = (List) info.get("files"); //$NON-NLS-1$
        String encoding = (String) dictionary.get("encoding");
        encoding = encoding == null ? "UTF-8" : encoding;

        if (list != null) {
            filenames = new String[list.size()];
            lengths = new long[filenames.length];
            total = 0;
            for (int i = 0; i < filenames.length; i++) {
                final BEncodedDictionary aDictionary = (BEncodedDictionary) list.get(i);
                lengths[i] = ((Long) aDictionary.get("length")).longValue(); //$NON-NLS-1$
                total += lengths[i];
                final List aList = (List) aDictionary.get("path"); //$NON-NLS-1$

                final StringBuffer buffer = new StringBuffer();
                synchronized (buffer) {
                    for (int j = 0; j < aList.size(); j++) {
                        String sname = new String(((String)aList.get(j)).getBytes("ISO-8859-1"),encoding);
                        buffer.append(sname).append(File.separator);
                    }
                }
                filenames[i] = buffer.toString();
            }
        } else {
            lengths = new long[] {((Long) info.get("length")).longValue()}; //$NON-NLS-1$
            total = lengths[0];
            filenames = new String[] {new String(((String) info.get("name")).getBytes("ISO-8859-1"),encoding)}; //$NON-NLS-1$
        }
        pieceLength = ((Long) info.get("piece length")).intValue(); //$NON-NLS-1$
        buffer = ByteBuffer.allocate(pieceLength);
        final String shaPieces = (String) info.get("pieces"); //$NON-NLS-1$
        pieces = new String[shaPieces.length() / 20];
        for (int i = 0; i < pieces.length; i++) {
            pieces[i] = shaPieces.substring(i * 20, i * 20 + 20);
        }
        numPieces = pieces.length;
        infoHash = new String(shaDigest.digest(info.toString().getBytes("ISO-8859-1")), "ISO-8859-1"); //$NON-NLS-1$ //$NON-NLS-2$
        final byte[] bytes = infoHash.getBytes("ISO-8859-1"); //$NON-NLS-1$
        final StringBuffer hash = new StringBuffer(40);
        for (int i = 0; i < bytes.length; i++) {
            if (-1 < bytes[i] && bytes[i] < 16) {
                hash.append('0');
            }
            hash.append(Integer.toHexString(0xff & bytes[i]));
        }
        hexHash = hash.toString();
    }

    private boolean hashCheckFile() throws FileNotFoundException, IOException {
        final int remainder = (int) (file.length() % pieceLength);
        int count = 0;
        final FileChannel channel = new FileInputStream(file).getChannel();
        while (channel.read(buffer) == pieceLength) {
            buffer.rewind();
            if (!pieces[count].equals(new String(shaDigest.digest(buffer.array()), "ISO-8859-1"))) { //$NON-NLS-1$
                return false;
            }
            count++;
        }
        buffer.rewind();
        shaDigest.update(buffer.array(), 0, remainder);
        return pieces[pieces.length - 1].equals(new String(shaDigest.digest(), "ISO-8859-1")); //$NON-NLS-1$
    }

    private boolean hashCheckFolder() throws FileNotFoundException, IOException {
        int read = 0;
        int count = 0;
        for (int i = 0; i < filenames.length; i++) {
            final File download = new File(file.getAbsolutePath(), filenames[i]);
            final FileChannel channel = new FileInputStream(download).getChannel();
            while ((read += channel.read(buffer)) == pieceLength) {
                buffer.rewind();
                if (!pieces[count].equals(new String(shaDigest.digest(buffer.array()), "ISO-8859-1"))) { //$NON-NLS-1$
                    return false;
                }
                count++;
                read = 0;
            }
        }
        buffer.rewind();
        shaDigest.update(buffer.array(), 0, read);
        return pieces[pieces.length - 1].equals(new String(shaDigest.digest(), "ISO-8859-1")); //$NON-NLS-1$
    }

    /**
     * Checks the integrity of the target file or folder as set by
     * {@link #setTargetFile(File)} to determine whether its contents pass all
     * of the hash checks.
     *
     * @return <code>true</code> if and only if every hash check has been
     *         passed, <code>false</code> otherwise
     * @throws FileNotFoundException
     *             If one of the files associated with the torrent could not be
     *             found
     * @throws IllegalStateException
     *             If the target file has not been set yet with
     *             {@link #setTargetFile(File)}
     * @throws IOException
     *             If an I/O error occurs while reading from the files
     */
    public boolean validate() throws IllegalStateException, IOException {
        if (file == null) {
            throw new IllegalStateException("The target file for this torrent has not yet been set"); //$NON-NLS-1$
        }
        return file.isDirectory() ? hashCheckFolder() : hashCheckFile();
    }

    /**
     * Sets the target file or folder that this torrent should download to or
     * look for the corresponding files in.
     *
     * @param file
     *            the target file or folder to use, this should be a file if the
     *            torrent is a single file torrent or a folder if it has
     *            multiple files
     * @throws IllegalArgumentException
     *             If <code>file</code> is null or if <code>file</code> is a
     *             directory when this torrent is only using a single file
     */
    public void setTargetFile(File file) throws IllegalArgumentException {
        if (file == null) {
            throw new IllegalArgumentException("The file cannot be null"); //$NON-NLS-1$
        } else if (filenames.length == 1 && file.isDirectory()) {
            throw new IllegalArgumentException("This torrent is downloading a file, the actual file should be set here and not a directory"); //$NON-NLS-1$
        }
        this.file = file;
    }

    /**
     * Returns the hash of the <code>info</code> dictionary specified by the
     * torrent's metainfo. This is primarily used for torrent identification
     * when sending messages to the tracker.
     *
     * @return the hash of the <code>info</code> dictionary within the
     *         torrent's metainfo, this will likely contain binary data and will
     *         not be human-readable as a result
     */
    public String getInfoHash() {
        return infoHash;
    }

    /**
     * Returns the hexadecimal representation of the hash returned from
     * {@link #getInfoHash()}. This string is forty characters long.
     *
     * @return the hexadecimal value of <code>getInfoHash()</code>
     */
    public String getHexHash() {
        return hexHash;
    }

    /**
     * Retrieve the specified lengths of the files contained within this
     * torrent. Every length contained within the returned array corresponds to
     * a file's name specified by {@link #getFilenames()}.
     *
     * @return an array of lengths for each of the files specified within the
     *         torrent's metainfo
     */
    public long[] getLengths() {
        return lengths;
    }

    /**
     * Returns the length of a piece.
     *
     * @return a piece's length
     */
    public int getPieceLength() {
        return pieceLength;
    }

    /**
     * Retrieves the URL of the tracker that's handling the requests for this
     * torrent.
     *
     * @return the tracker's URL
     */
    public String getTracker() {
        return tracker;
    }

    /**
     * Returns a string array that contains the SHA-1 hash of each of the pieces
     * defined by the torrent's metainfo.
     *
     * @return an array of strings with the SHA-1 hash for each piece of data
     */
    public String[] getPieces() {
        return pieces;
    }

    /**
     * Returns the number of pieces associated with this torrent.
     *
     * @return the number of pieces
     */
    public int getNumPieces() {
        return numPieces;
    }

    /**
     * Retrieves the names of all of the files' that is specified by this
     * <code>Torrent</code>. All of the files' lengths can be matched up
     * against the long value stored within the returned array from
     * {@link #getLengths()}.
     *
     * @return an array of names for all of the files specified by the metadata
     */
    public String[] getFilenames() {
        return filenames;
    }

    /**
     * Retrieves the name of this torrent file. This is dictated by whatever is
     * before the ending <code>.torrent</code> extension. If no such extension
     * exists, the entire file's name will be returned.
     *
     * @return the name of this torrent without the trailing
     *         <code>.torrent</code> extension, if present
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the file that has been set as the target file of this torrent per
     * {@link #setTargetFile(File)}.
     *
     * @return the target file for this torrent
     */
    public File getTargetFile() {
        return file;
    }

    /**
     * Returns whether this torrent is associated with multiple files or not.
     *
     * @return <code>true</code> if this torrent specifies multiple files,
     *         <code>false</code> otherwise
     */
    public boolean isMultiFile() {
        return lengths.length != 1;
    }

    /**
     * Retrieves the total length of all of the files specified within this
     * torrent. This is equivalent to the sum of all the lengths returned from
     * the array in {@link #getLengths()}.
     *
     * @return the combined length of all the files specified by this torrent
     */
    public long getTotalLength() {
        return total;
    }

    /**
     * Writes the contents of the file that was used to initialize this
     * <code>TorrentFile</code> onto the provided file.
     *
     * @param file
     *            the file to save to
     * @throws IOException
     *             If an I/O error occurs while trying to write to the file
     */
    public void save(File file) throws IOException {
        final FileOutputStream fos = new FileOutputStream(file);
        fos.write(torrentData);
        fos.flush();
        fos.close();
    }

    /**
     * Returns whether this <code>TorrentFile</code> is equal to the given
     * object. The two are the same if their info hash section of the contained
     * metainfo is the same. If <code>other</code> is not an instance of a
     * <code>TorrentFile</code>, <code>false</code> is returned.
     * @param other
     *
     * @return <code>true</code> if <code>other</code> is a
     *         <code>TorrentFile</code> and its info hash is the same as this
     *         one, <code>false</code> otherwise
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other instanceof TorrentFile) {
            return infoHash.equals(((TorrentFile) other).infoHash);
        } else {
            return false;
        }
    }

    /**
     * Returns the hash code of this <code>TorrentFile</code> based on its
     * info hash.
     *
     * @return the result of calling <code>hashCode()</code> on the returned
     *         string from {@link #getInfoHash()}
     */
    public int hashCode() {
        return infoHash.hashCode();
    }

}
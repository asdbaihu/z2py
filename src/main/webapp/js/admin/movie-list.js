/**
 * Created by Mr.Xu on 2017/4/8.
 */
$('.recommend').click(function(){
    $action = $(this).attr('action');
    $.ajax({
        type: "PUT",
        url: $action,
        dataType: "json",
        success: function(data){
            alert(data.msg);
            location.reload();
        }
    });
});
$('.btn-group .btn').click(function(){
    var ids = [];
    $('input:checkbox[name=m_id]:checked').each(function(){
        ids.push($(this).val());
    });
    if (ids.length <= 0){
        alert('没选中任何项！');
        return;
    }
    var action = $('.btn-group').attr('action');
    var data = {ids:ids.join(','),recommend:$(this).attr('value'), _method:'PUT'};
    $.ajax({
        type: "POST",
        url: action,
        data: data,
        dataType: "json",
        success: function(data){
            alert(data.msg);
            location.reload();
        }
    });
});
$('#check-ids').click(function(){
    $('input[name=m_id]').prop('checked', $(this).prop('checked'));
});
function ajax(id, bid, type) {
	$.ajax( {
		url : 'http://192.168.1.21:8080/nms/newsPaper/usersub.do',
		type : 'POST',
		data : {
			'id' : id,
			'bid' : bid,
			'type' : type
		},
		dataType : 'json',
		timeout : 10000,
		error : function() {
			alert('系统忙，请稍候再操作!');
		},
		success : function(result) {
			var json = result;
			if (json.result == "1") {
				if (type == "add") {
					$('#' + bid + '').val(" 取消订阅 ");
					document.getElementById(bid).name = 'del';
				} else {
					$('#' + bid + '').val(" 订阅 ");
					document.getElementById(bid).name = 'add';
				}
			}
		}
	});
}

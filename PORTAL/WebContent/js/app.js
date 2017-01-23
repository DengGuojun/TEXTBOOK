// ;(function(doc){
//     //给html根节点加上webps类名
//     function addRootTag(){
//         doc.documentElement.className += "webps";
//     }
//     //判断是否是webps=A这个cookie
//     if(!/(^|;\s?)webps=A/.test(document.cookie)){
//         var image = new Image();
//         image.src="data:image/webp;base64,UklGRkoAAABXRUJQVlA4WAoAAAAQAAAAAAAAAAAAQUxQSAwAAAARBxAR/Q9ERP8DAABWUDggGAAAABQBAJ0BKgEAAQAAAP4AAA3AAP7mtQAAAA==";
//         image.onload = function(){
//             if(image.width == 1){
//                 addRootTag();
//                 document.cookie = "webps=A;max-age=31536000;domain=172.16.21.16:8080";
//                 console.log(document.cookie);
//             }
//         };
//     }else{
//         addRootTag();
//         document.cookie = "webps=A;max-age=31536000;domain=172.16.21.16:8080";
//     }

// })(document);

$(function() {
	// 详情页 图片 切换
	$('.JS-imgSelect a').click(function() {
		var imgSrc = $(this).find('img').attr('src');
		$('.JS-imgDetail').attr('src', imgSrc);
	})

	// tab切换
	$('.JS-tab .tab-item').click(
			function() {
				var $self = $(this), index = $self.index();
				$self.addClass('selected').siblings().removeClass('selected');
				$('.tab-content .content-item').eq(index).removeClass('dn')
						.siblings().addClass('dn');
			})

})

// 图片切换
;(function() {
	var imgW = 82, mgr = 24, i = 0, showItem = 3;
	var len = $('.JS-imgSelect img').length;
	$('.JS-imgSelect .img-box').css('width', (imgW + mgr) * len + 'px');

	$('.btn-prev').click(
			function() {
				if (i === 0) {
				} else {
					i++;
					$('.btn-prev').addClass('clicked');
					$('.btn-next').removeClass('clicked');
					$('.JS-imgSelect .img-box').css('margin-left',
							(imgW + mgr) * i + 'px');
				}
			});

	$('.btn-next').click(
			function() {
				if (i === -len + showItem) {
				} else {
					i--;
					$('.btn-prev').removeClass('clicked');
					$('.btn-next').addClass('clicked');
					$('.JS-imgSelect .img-box').css('margin-left',
							(imgW + mgr) * i + 'px');
				}
			});
})();

function search() { // 进行拼接搜索
	var text = $("#fullTextSearch").val();
	if (text != "") {
		window.location.href = "/search/" + text + "/list-1-14.shtml";
	} else {
		// window.location.href="/list.shtml";
		return;
	}
}
function goPage(pageNum) {
	// 进行页码判断
	var tt = /^\d+$/g;
	if (!tt.test(pageNum)) {
		alert("请输入正确数字");
		return;
	}
	var url = window.location.href;
	var arr = url.split("-");
	arr[arr.length - 2] = pageNum;
	var res = "";
	var i = 0;
	for (i; i < arr.length - 1; i++) {
		res += arr[i] + "-";
	}
	res = res + arr[i];
	window.location.href = res;
}
function goInputPage(totalPage) {
	var page = $("#switchPage").val();
	if (page > totalPage) {
		page = totalPage;
	}
	goPage(page);
}

$('#textbookName').keydown(function(e) {
	if (e.keyCode == 13) {
		search();
	}
});

// 收藏
function AddFavorite() {
	var title = document.title;
	var url = location.href;
	if (window.sidebar) {
		window.sidebar.addPanel(title, url, "");
	} else if (document.all) {
		window.external.AddFavorite(url, title);
	} else {
		alert('您可以通过快捷键 ctrl + D 加入到收藏夹');
	}
}
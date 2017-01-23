var img = [];  //虚拟数据
var firstPage=1;
var nextPage=1;
var prePage=1;
var lastPage=1;
function hasClass(elm,className){ //检测classname
	var arr = elm.className.split(' '); 
	for(var i = 0; i < arr.length; i++){
		if(className == arr[i]){
 			return true
		}
	}
	return false;
};

function getBox(){
	var currentEditor = CKEDITOR.currentInstance.name; //获取当前弹窗
//	console.log(currentEditor);
	return '.cke_editor_'+currentEditor+'_dialog'; //拿到弹窗的class
}

function selectImg(){ //图片选中
	var imgSelect = $(getBox()+' .JS-imageBox .imgBox'),
		len  = imgSelect.length;
	for(var i = 0; i < len; i++){
		imgSelect[i].onclick = function(){
			for(var i = 0; i < len; i++){
				if(imgSelect[i] == this){
					var selectedItem = this.getElementsByClassName('checked')[0];
					var closeItem = this.getElementsByClassName('js-delbtn')[0];
					if(hasClass(this,"selected")){
						this.className = "fl imgBox";
						selectedItem.className = "checked dn";
						closeItem.className = "js-delbtn delbtn dn";
					}else{
						this.className = "fl imgBox selected";
						selectedItem.className = "checked";
						closeItem.className = "js-delbtn delbtn";
					}
				}
			}
		}
	}
};

function setImages(data){ //数据处理入口 读取图库数据
	//弹窗的editor的name
	var len = data.length;
	var html = '';
	for(var i = 0; i < len; i++){
		var template = '<div class="fl imgBox">'+
		'<div class="js-delbtn delbtn dn" onclick="delImg(this);"></div>'+
		'<div class="checked dn"></div>'+
		'<img class="imgSrc js-image" src='+ data[i] +'>'+
		'</div>';
		html += template; 
	}
	$(getBox()+' .JS-imageBox').html(html); //渲染
	return selectImg();
};

//跳转到第一个tab
function turnTab0(){
	setImages(img);
	var dialog = CKEDITOR.dialog.getCurrent(); 
	dialog.selectPage( 'imagesBox' );
}


//判断是哪一个tab然后执行什么操作 tab0: imageBox tab1: uploadImage
function tabController(tabName,fn){
	var selectedTabId = CKEDITOR.dialog.getCurrent().definition.dialog._.currentTabId;
	if( selectedTabId == tabName ){
		if(fn && typeof(fn) == 'function'){
			fn;
		}
	}
}

//模拟操作
function fn(){

	var img = $(getBox()+' .JS-imageBox .selected'),
		len = img.length,
		html = '';
	for(var i = 0; i < len; i++){
		var imgSrc = img[i].getElementsByClassName('js-image')[0].src;
		html += '<img src='+ imgSrc +' width=20% height=20% ><br/>';
	}

	console.log(CKEDITOR.currentInstance.name); 
	var currentIndex = CKEDITOR.currentInstance.name; //获取当前编辑器的name属性
	CKEDITOR.instances[currentIndex].insertHtml(html); //在当前的编辑器输出
};
function delImg(obj){
	var imgPath = obj.parentNode.lastChild.src;
	question = confirm("确实要删除所选图片吗");
	if (question !="0")
	{
		//从数据库中改状态为0
		$.ajax({
			type:'get',
			url : "/textbook/TextbookMaterialStatusModify.do?imgPath=" + imgPath,
			dataType : 'json',	
			success:function(data){
				
			} ,
			error : function(){
				alert("失败");
			}
	}); 
		obj.parentNode.remove();
	}
}
function mediaUpload() {
    var file_data = $(getBox()+' #mediafile')[0].files; // for multiple files
    for (var i = 0; i < file_data.length; i++) {
        var fd = new FormData();
        fd.append('file', file_data[i]);
        $.ajax({
        		url: '/textbook/TextbookMediaManage.do?uploadType=material',
            data: fd,
            contentType: false,
            processData: false,
            type: 'POST',
            success: function (data) {
                if(data.code == '1'){
                	img = eval(data.message);
                	prePage = data.content.prePageNumber;
                	nextPage = data.content.nextPageNumber;
                	lastPage = data.content.totalPageNumber;
                	turnTab0();
                	//
                }else{
                		alert("失败");
                		
                }
                
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown);
            }
            
        });  
       
    }
}
function mediaGoPage(num){
	if(num == 1){
		getImgData(firstPage,20);
	}else if(num == 2){
		//
		if(prePage < 1){
			prePage=1;
		}
		getImgData(prePage,20);
	}else if(num == 3){
		
		getImgData(nextPage,20);
	}else if(num == 4){
		getImgData(lastPage,20);
	}else if(num == 5){
		var intNum = document.getElementById("intNum").value;
		//进行页码判断
		var tt=/^\d+$/g;
		if(!tt.test(intNum)){
			alert("请输入正确数字");
			return;
		}
		if(intNum > lastPage){
			intNum = lastPage;
		}
		getImgData(intNum,20);
	}
	
	
}












































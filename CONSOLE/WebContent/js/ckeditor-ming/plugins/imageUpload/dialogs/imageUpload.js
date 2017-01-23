



CKEDITOR.dialog.add('imageUpload', function(editor){
	return {
		title: '图片上传',
		resizable: CKEDITOR.DIALOG_RESIZE_BOTH,
		minWidth: 600,
		minHeight: 400,
		contents: [{
			id: 'imagesBox',
			name: 'imagesBox',
			label: '在线图库',
			elements: [{
				type : 'vbox',
				align : 'right',
				children : [
							{
								type: 'html',
								html: '<div class="clearfix scrollBox JS-imageBox"></div>'
							},
							{
								type: 'html',
								html: '<div class="pageNum"><button onclick="mediaGoPage(1);">首页</button><button onclick="mediaGoPage(2);">上一页</button><button onclick="mediaGoPage(3);">下一页</button><button onclick="mediaGoPage(4);">尾页</button>&nbsp;&nbsp;<input type="text" class="intNum" id="intNum"/><button class="turn-btn" onclick="mediaGoPage(5)">跳转</button></div>'
							}
						],
					}]
				},
		{
			id: 'uploadImage',
			name: 'uploadImage',
			label: '本地上传',
			elements: [
				{
					type : 'vbox',
					align : 'right',
					children :
					[
						{
							id: 'tips',
							name: 'tips',
							type: 'html',
							html : '<p style="font-size: 14px;">支持jpg,png格式图片上传</p>'
						},
						{
							id: 'uploadInt',
							name: 'uploadInt',
							type : 'html',
							html: '<input type="file" class="uploadInt" id="mediafile"  name="mediafile" multiple="multiple">',
						},
						{
							type: 'button',
							label: '上传',
							onClick: function(){
								mediaUpload();
								
							}
						}
					]
				}
			]
		}],
		onOk: function(){
			tabController('imagesBox',fn());
		}, //弹出框的确定按钮
		onCancel: function(){
			
		},//弹出框的取消按钮
		onLoad: function(){
			getImgData(1,20);
		},//弹出框加载时
	}
});

//弹出的image属性框隐藏链接，高级，上传的tab
CKEDITOR.on('dialogDefinition', function(ev){
	var dialogName = ev.data.name;
	var dialogDefinition = ev.data.definition;
	var dialog = CKEDITOR.dialog.getCurrent(); 
	
	if(dialogName == "image"){
		dialogDefinition.removeContents('advanced');
		dialogDefinition.removeContents('Link');
		dialogDefinition.removeContents('Upload');
	}
});
function getImgData(pageNum,pageSize) {
	 $.ajax({
 	 url: '/textbook/TextbookImageSelect.do?pageNum='+pageNum+'&pageSize='+pageSize,
     contentType: false,
     processData: false,
     dataType : 'json',
     type: 'GET',
     success: function (data) {
         img = eval(data.message);
         prePage = data.content.prePageNumber;
     	 nextPage = data.content.nextPageNumber;
     	 lastPage = data.content.totalPageNumber;
         setImages(img);
         
     },
     error: function (XMLHttpRequest, textStatus, errorThrown) {
//         console.log(errorThrown);
     }
     
 });  
}
















CKEDITOR.plugins.add('imageUpload', {
	requiress: ['dialog'],
	init: function(editor){
		var pluginName = 'imageUpload';
		editor.addCommand('imageUpload', new CKEDITOR.dialogCommand('imageUpload'));
		editor.ui.addButton('imageUpload', {
			label: '图片上传',
			command: 'imageUpload',
			icon: this.path + 'images/imageUpload.png'
		});
		CKEDITOR.dialog.add('imageUpload', this.path + 'dialogs/imageUpload.js');
	}
});
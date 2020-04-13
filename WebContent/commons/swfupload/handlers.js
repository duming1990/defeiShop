/* Demo Note:  This demo uses a FileProgress class that handles the UI for displaying the file name and percent complete.
The FileProgress class is not part of SWFUpload.
 */

/* **********************
 Event Handlers
 These are my custom event handlers to make my
 web application behave the way I went when SWFUpload
 completes different tasks.  These aren't part of the SWFUpload
 package.  They are part of my application.  Without these none
 of the actions SWFUpload makes will show up in my application.
 ********************** */
function fileQueued(file) {
	try {
		
		var progress = new FileProgress(file,
				this.customSettings.progressTarget);
		progress.setStatus("Pending...");
		progress.toggleCancel(true, this);
	} catch (ex) {
		this.debug(ex);
	}

}

function fileQueueError(file, errorCode, message) {
	try {
		if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
//				alert("You have attempted to queue too many files.\n"
//						+ (message === 0 ? "You have reached the upload limit."
//								: "You may select "
//										+ (message > 1 ? "up to " + message
//												+ " files." : "one file.")));
			
			alert("你所上传的图片已经超过四张，不能在进行上传！");
			return;
		}

		var progress = new FileProgress(file,
				this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);
		
		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			progress.setStatus("图片大小不能超过2M！");
			this.debug("Error Code: File too big, File name: " + file.name
					+ ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			progress.setStatus("文件为空文件！");
			this.debug("Error Code: Zero byte file, File name: " + file.name
					+ ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			progress.setStatus("格式错误，上传格式只能为（bmp, gif, jpeg, jpg, png）!");
			this.debug("Error Code: Invalid File Type, File name: " + file.name
					+ ", File size: " + file.size + ", Message: " + message);
			break;
		default:
			if (file !== null) {
				progress.setStatus("加载入队列出错!");
			}
			this.debug("Error Code: " + errorCode + ", File name: " + file.name
					+ ", File size: " + file.size + ", Message: " + message);
			break;
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function fileDialogComplete(numFilesSelected, numFilesQueued) {
	try {
		if (numFilesSelected > 0) {
			document.getElementById(this.customSettings.cancelButtonId).disabled = false;
		}
		/* I want auto start the upload and I can do that here */
		//判断可以上传的个数是否小于 files_queued
		var can_upload = this.settings.can_upload;
		var stats = swfu.getStats();
		var files_queued = stats.files_queued;
		//alert(can_upload);
		//alert(files_queued);
		if(files_queued > can_upload){
			alert("你所上传的图片已经超过四张，不能在进行上传！");
			$(".progressWrapper").remove();
			//清除队列
			swfu.cancelQueue();
			return;
		}
		this.startUpload();
		
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadStart(file) {
	try {
		/*
		 * I don't want to do any file validation or anything, I'll just update
		 * the UI and return true to indicate that the upload should start. It's
		 * important to update the UI here because in Linux no uploadProgress
		 * events are called. The best we can do is say we are uploading.
		 */
		// $("#hasDocument").hide();
		// $(".progressWrapper").remove();
		
			var progress = new FileProgress(file,
					this.customSettings.progressTarget);
			// progress.setStatus("Uploading...");
			progress.setStatus("准备上传...");
			progress.toggleCancel(true, this);
			
			var ctx = SWFUpload.PATH.CTX;
			//上传开始后，设置按钮disabled
			var disabledImg = ctx + "/commons/swfupload/style/images/upload_disabled.png";
			swfu.setButtonDisabled(true);
			swfu.setButtonImageURL(disabledImg);
		
	} catch (ex) {
	}

	return true;
}

function uploadProgress(file, bytesLoaded, bytesTotal) {
	try {
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);

		var progress = new FileProgress(file,
				this.customSettings.progressTarget);
		progress.setProgress(percent);
		// progress.setStatus("Uploading...");
		progress.setStatus("文件上传中..." + "&nbsp;" + percent + "%");
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadSuccess(file, serverData) {
	try {
		var progress = new FileProgress(file,
				this.customSettings.progressTarget);
		progress.setComplete();
		createElementsToForm(file, serverData);
		// progress.setStatus("Complete.");
		progress.setStatus("文件上传成功.");
		// progress.toggleCancel(true, "deleteFile");
		progress.toggleCancel(false);
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadError(file, errorCode, message) {
	try {
		var progress = new FileProgress(file,
				this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);

		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			progress.setStatus("Upload Error: " + message);
			this.debug("Error Code: HTTP Error, File name: " + file.name
					+ ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
			progress.setStatus("Upload Failed.");
			this.debug("Error Code: Upload Failed, File name: " + file.name
					+ ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			progress.setStatus("Server (IO) Error");
			this.debug("Error Code: IO Error, File name: " + file.name
					+ ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
			progress.setStatus("Security Error");
			this.debug("Error Code: Security Error, File name: " + file.name
					+ ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			progress.setStatus("Upload limit exceeded.");
			this.debug("Error Code: Upload Limit Exceeded, File name: "
					+ file.name + ", File size: " + file.size + ", Message: "
					+ message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
			progress.setStatus("Failed Validation.  Upload skipped.");
			this.debug("Error Code: File Validation Failed, File name: "
					+ file.name + ", File size: " + file.size + ", Message: "
					+ message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			// If there aren't any files left (they were all cancelled) disable
			// the cancel button
			if (this.getStats().files_queued === 0) {
				document.getElementById(this.customSettings.cancelButtonId).disabled = true;
			}
			progress.setStatus("Cancelled");
			progress.setCancelled();
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			progress.setStatus("Stopped");
			break;
		default:
			progress.setStatus("Unhandled Error: " + errorCode);
			this.debug("Error Code: " + errorCode + ", File name: " + file.name
					+ ", File size: " + file.size + ", Message: " + message);
			break;
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadComplete(file) {
	if (this.getStats().files_queued === 0) {
		document.getElementById(this.customSettings.cancelButtonId).disabled = true;
	}
	//判断是否是5张不能在上传了
	  ImgCount(4);
	  //uploadCompleteAddUpload();

	   var can_upload = this.settings.can_upload;
	   can_upload--;
	   if(can_upload < 0){
		   can_upload = 0;
	   }
	   swfu.addSetting("can_upload",can_upload,4);
}


// This event comes from the Queue Plugin
function queueComplete(numFilesUploaded) {
	var status = document.getElementById("doc_path");
	// status.innerHTML = numFilesUploaded + " file" + (numFilesUploaded === 1 ?
	// "" : "s") + " uploaded.";
	if (status) {
		status.value = "File is Uploaded";
	}
}

/**
 * 在页面必要生成表单，方便后台保存数据。必须要引入jquery.js
 **/
function createElementsToForm(file, serverData) {
	if(!serverData){
		return null;
	}
	var ctx = SWFUpload.PATH.CTX;
	var divCreateElementsToForm = $("#divCreateElementsToForm");
	if (divCreateElementsToForm) {
		var real_url = ctx + "/" + serverData;
		var rel_url = serverData;
		
		
			$("#picUl li").each(function(index){
			 if(null == $(this).attr("data-status") || $(this).attr("data-status") == ''){
				 var primaryli = $(this).attr("id");
				 setImgView(primaryli,real_url,index,rel_url);
				 return false;
			 }
			});
		
	}
	
}

function setImgView(obj,path,index,rel_url){
	$("#"+obj).find(".preview").find("img").attr("src",path);
	$("#"+obj).find(".preview").find("img").attr("data_flag_img",1);
	$("#"+obj).attr("class","primary");
	$("#"+obj).attr("data-status",index+1);
	$("#"+obj).next().removeAttr("data-status");
	
	var html = '<div class="operate">';
	html += '<i class="toleft" onclick="toleft(\''+ obj +'\')">左移</i>';
	html += '<i class="toright" onclick="toright(\''+ obj +'\')">右移</i>';
	html += '<i class="del" onclick="deleteFileForNew(\'' + encodeURI(rel_url) + '\', \'' + obj + '\', '+ index +')">删除</i>';
	html += '<input value="' + rel_url + '" type="hidden" name="file_path"></input></div>';
	$("#"+obj).find(".preview").after(html);
	
	//判断是否是5张不能在上传了
	  ImgCount(4);
	
}



function toleft(obj){
	var objPrevId = $("#" + obj).prev().attr("id");
	if(null != objPrevId && objPrevId != ''){
	var a = $("#" + objPrevId).clone();
	$("#" + objPrevId).remove();
	$("#" + obj).after(a);
	liEvent();
	}
}

function toright(obj){
	var objNextId = $("#" + obj).next().attr("id");
	if(null != objNextId && objNextId != ''){
		var a = $("#" + obj).clone();
		$("#" + obj).remove();
		$("#" + objNextId).after(a);
		liEvent();
	}
}

function liEvent(){
	$("#picUl li").each(function(index){
		$(this).mouseover(function(){
			$(this).find(".operate").show();
		}).mouseout(function(){
			$(this).find(".operate").hide();
		});
	});
}

function ImgCount(count){
	var HasImgCount = 0;
	$("#picUl li").each(function(){
		if($(this).find("img").attr("data_flag_img") != null && $(this).find("img").attr("data_flag_img") != ''){
			HasImgCount++;
		}
	});
	var ctx = SWFUpload.PATH.CTX;
	if(HasImgCount == count){
		//设置按钮disabled
		var disabledImg = ctx + "/commons/swfupload/style/images/upload_disabled.png";
		swfu.setButtonDisabled(true);
		swfu.setButtonImageURL(disabledImg);
	}else{
		var button_image_url_ = ctx + "/commons/swfupload/style/images/upload.png";
		swfu.setButtonDisabled(false);
		swfu.setButtonImageURL(button_image_url_);
	}
}

function deleteFileForNew(file_path,obj,index){
	if (confirm("确定删除附件！")) {
		var delete_url = SWFUpload.PATH.DELETE_URL;
		var delete_method = SWFUpload.PATH.DELETE_METHOD;
		$("#"+obj).find(".preview").find("img").removeAttr("data_flag_img");
		$("#"+obj).find(".preview").find("img").attr("src",this.settings.no_image);
		$("#"+obj).find(".operate").remove();
		$("#"+obj).removeClass();
		$("#"+obj).removeAttr("data-status");
		$("#"+obj).next().attr("data-status",index+2);
		
		//修复其中删除出现的bug，删除就添加
	    var can_upload = this.settings.can_upload;
	    can_upload++;
	    swfu.addSetting("can_upload",can_upload,4);
		//删除防止提示已经上传太多
		var stats = swfu.getStats();
	    stats.successful_uploads--; 
	    if(stats.successful_uploads < 0){
	    	stats.successful_uploads = 0;
	    }
	    swfu.setStats(stats); 
	    
	  //判断是否是5张不能在上传了
	    ImgCount(4);
	    
	    var entity_id = this.settings.entity_id;
		$.post(delete_url, { method : delete_method, file_path : file_path,entity_id : entity_id}, function (result) {});
		
	}
}

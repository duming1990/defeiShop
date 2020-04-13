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
			alert("You have attempted to queue too many files.\n"
					+ (message === 0 ? "You have reached the upload limit."
							: "You may select "
									+ (message > 1 ? "up to " + message
											+ " files." : "one file.")));
			return;
		}

		var progress = new FileProgress(file,
				this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);

		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			progress.setStatus("File is too big.");
			this.debug("Error Code: File too big, File name: " + file.name
					+ ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			progress.setStatus("Cannot upload Zero Byte files.");
			this.debug("Error Code: Zero byte file, File name: " + file.name
					+ ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			progress.setStatus("Invalid File Type.");
			this.debug("Error Code: Invalid File Type, File name: " + file.name
					+ ", File size: " + file.size + ", Message: " + message);
			break;
		default:
			if (file !== null) {
				progress.setStatus("Unhandled Error");
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
	var real_url = ctx + "/" + serverData;
	var rel_url = serverData;
	$("#user_logo_img").attr("src",real_url);
	$("#user_logo").val(rel_url);
}

function deleteFile(entity_id, fileid, file_path){
	if (confirm("确定删除附件！")) {
		var delete_url = SWFUpload.PATH.DELETE_URL;
		var delete_method = SWFUpload.PATH.DELETE_METHOD;
		// alert(entityId);
		$("#" + fileid).parent().remove();
		//$(".progressWrapper").remove();
		//$("#doc_path").val("");
		$.post(delete_url, { method : delete_method, file_path : file_path, id : entity_id}, function (result) {});
	}
}

$(function(){
	$("#sendBtn").click(send_letter);
	$(".close").click(delete_msg);
});

function send_letter() {
	$("#sendModal").modal("hide");

	var toName = $("#recipient-name").val();
	var content = $("#message-text").val();
	$.post(
	    CONTEXT_PATH + "/letter/send",
	    {"toName":toName,"content":content},
	    function(data) {
	        data = $.parseJSON(data);
	        if(data.code == 0) {
	            $("#hintBody").text("Sent!");
	        } else {
	            $("#hintBody").text(data.msg);
	        }

	        $("#hintModal").modal("show");
            setTimeout(function(){
                $("#hintModal").modal("hide");
                location.reload();
            }, 2000);
	    }
	);
}


function delete_msg() {
	// TODO delete
	$(this).parents(".media").remove();
}
$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");

	// get tittle and content
	var title = $("#recipient-name").val();
	var content = $("#message-text").val();
	// Post
	$.post(
	    CONTEXT_PATH + "/discuss/add",
	    {"title":title,"content":content},
	    function(data) {
	        data = $.parseJSON(data);

	        $("#hintBody").text(data.msg);

            $("#hintModal").modal("show");

            setTimeout(function(){
                $("#hintModal").modal("hide");

                if(data.code == 0) {
                    window.location.reload();
                }
            }, 2000);
	    }
	);

}
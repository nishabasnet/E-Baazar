/**
 * 
 */
	

$( document ).ready(function() {
	

$("#message").hide();

$.urlParam = function(name){
	var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	if(results==null || results=='null' || results=='')
		return 0;
	return results[1] || 0;
}

if($.urlParam('message')!==0){
	$("#message").show();
	$("#messageData").replaceWith("<strong id='messageData'>"+$.urlParam('message').replace(/\+/g, ' ')+"</strong>");
	hideMessage();
}



function hideMessage(){

	 setTimeout(function() { $("#message").fadeOut(1500); }, 3000);

}

});
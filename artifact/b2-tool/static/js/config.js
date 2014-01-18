FileDiffForm = function(){
    var machine, scm;

    var pick = function() {
		machine = $("#machine").val();
		scm = $("#scm").val();
	};

    var post = function(){
        pick();
        $("#loading-div-background").show();
        $.ajax({
            url:"/config/results/",
            data:{machine:machine, scm:scm}
        }).done(displayResults);
    };

    var displayResults = function(response){
        $("#results").html(response);
        $("#loading-div-background").hide();
        tabs();
    };

    var tabs = function(){
        $('.expand-diff').click(function(e){
            $(this).siblings('.content-diff').slideToggle('slow');
            e.preventDefault();
        });
    };

    this.boot = function(){
       $("#fetch_diff").click(post);
       $("#loading-div-background").css({ opacity: 0.7 });

    };
};

$(document).ready(function() {
	new FileDiffForm().boot();
});

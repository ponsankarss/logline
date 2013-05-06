Settings = function(){
    var post = function(){
        var content = $('#settings-content').val();
        $("#loading-div-background").show();
        $.ajax({url : "settings/update/",type : "POST", data:{content:content}}).done(displayResults);
    };

    var displayResults = function(response){
        $("#results").html(response);
        $("#loading-div-background").hide();
    };

    this.boot = function(){
        $("#settings_update").click(post);
    };

};
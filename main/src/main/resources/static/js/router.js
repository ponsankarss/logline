Router = function(){
    var server, port, action;

    var post = function(){
      server = $("#server").val();
      port = $("#port").val();
      action = $(this).attr("id");
      $.ajax({
        type:"POST",
        url:"/router/update/",
        cache:false,
        data:{server:server, port:port, action:action},
        success: display
      });
    };

    var display = function(data){
      $("#results").html(data);
    };

    this.boot = function(){
      $("#run").click(post);
      $("#stop").click(post);
    };

};
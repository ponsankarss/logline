LinkHit = function(){
    var hitUrl = function(){
      var linkName = $(this).attr("url");
      $.ajax({
        type:"POST",
        url:"/link/hit/",
        cache:false,
        data:{link:linkName},
        success: display
      });
    };

    var display = function(data){
      var response=$(data);
      var element = response.find(".link");
      alert(element)
      $('#'+element).html(data);
    };

    this.boot = function(){
      $(".link-small-btn").click(hitUrl);
    };

};


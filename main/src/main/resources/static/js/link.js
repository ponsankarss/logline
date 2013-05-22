LinkHit = function(){

    var hitAllUrl = function(){
      var environment = $(this).siblings('span').text();
      var selector  = '.link-small-btn[env="'+environment+'"]';
      $(selector).each(function(){
         $(this).click();
      });
    };

    var hitUrl = function(){
      var linkName = $(this).attr("url");
      var environment = $(this).attr("env");

      $.ajax({
        type:"POST",
        url:"/link/hit/",
        cache:false,
        data:{link:linkName, env: environment},
        success: display
      });
    };

    var display = function(data){
      var response= $(data);
      var element = "#"+response.find(".link-name").text();
      var status = response.find(".link-code").text();

      $(element).html(status);
      if(status == 200)
        $(element).addClass('link-200')
      else
        $(element).addClass('link-not-200')
    };

    this.boot = function(){
      $(".link-small-btn").click(hitUrl);
      $(".link-main-btn").click(hitAllUrl);
    };

};


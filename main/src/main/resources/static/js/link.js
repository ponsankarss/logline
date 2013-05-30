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
      var allInputs = $(this).parent().siblings('td[class="link-params-col"]').find('input');

      var paramsMap = {};
      allInputs.each(function(){
        paramsMap[$(this).attr('name')] = $(this).val();
      });
      paramsMap['link']= linkName;
      paramsMap['env']= environment;

      $.ajax({
        type:"POST",
        url:"/link/hit/",
        cache:false,
        data:paramsMap,
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


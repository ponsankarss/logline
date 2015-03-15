LinkHit = function(){

    var hitAllUrl = function(){
      var environment = $(this).attr('environment');
      var selector  = '.link-small-btn[env="'+environment+'"]';
      $(selector).each(function(){
         $(this).click();
      });
    };

    var hitUrl = function(){
      var linkName = $(this).attr("url");
      var environment = $(this).attr("env");
      var allInputs = $(this).parent().siblings('td[class="link-params-col"]').find('input');
      var resultDiv = $(this).parent().siblings('td[class="link-hit-result-col"]').find('div');
      resultDiv.removeClass('link-200 link-not-200');

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

      var statusElement = "#"+response.find(".link-name").text();
      var responseElement = statusElement+"-response"
      var status = response.find(".link-code").text();
      var html =  response.find(".link-response").text();

      $(statusElement).html(status);
      if(status == 200)
        $(statusElement).addClass('link-200')
      else
        $(statusElement).addClass('link-not-200')

      $(responseElement).html(html);

    };

    var linkTabs = function(){
        $('.expand-link').click(function(e){
           $(this).siblings('.content-link').slideToggle('slow');
           e.preventDefault();
        });
    };

    var closeAllLinkTabs = function(){
        $('.content-link').slideToggle('fast');
    };

    this.boot = function(){
      $(".link-small-btn").click(hitUrl);
      $(".link-main-btn").click(hitAllUrl);
      linkTabs();
      closeAllLinkTabs();
    };

};


QueryHit = function(){

    var runAll = function(){
      var db = $(this).attr('db');
      var selector  = '.query-small-btn[db="'+db+'"]';
      $(selector).each(function(){
         $(this).click();
      });
    };

    var run = function(){
      var query = $(this).attr("query");
      var db = $(this).attr("db");

      var paramsMap = {};
      paramsMap['query']= query;
      paramsMap['db']= db;

      $.ajax({
        type:"POST",
        url:"/db/run/",
        cache:false,
        data:paramsMap,
        success: display
      });
    };

    var display = function(data){
      var response= $(data);
      var responseElement = "#"+response.find(".query-name").text()+"-response"
      var html =  response.find(".query-response").html();
      $(responseElement).html(html);
    };

    var queryTabs = function(){
        $('.expand-db').click(function(e){
           $(this).siblings('.content-db').slideToggle('slow');
           e.preventDefault();
        });
    };

    var closeAllQueryTabs = function(){
        $('.content-db').slideToggle('fast');
    };

    this.boot = function(){
      $(".query-small-btn").click(run);
      $(".query-main-btn").click(runAll);
      queryTabs();
      closeAllQueryTabs();
    };

};


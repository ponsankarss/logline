HomePage = function(){

      var setupNavigation = function(){
        $('a.logs').click(function(){
          $.ajax({url : "log-tool",type : "GET"}).done(updateToolBoxForLog);
        });
        $('a.settings').click(function(){
          $.ajax({url : "settings-view",type : "GET"}).done(updateToolBoxForSettings);
        });
        $('a.config').click(function(){
          $.ajax({url : "config-tool",type : "GET"}).done(updateToolBoxForDiff);
        });
        $('a.router').click(function(){
          $.ajax({url : "router-view",type : "GET"}).done(updateToolBoxForRouter);
        });
        $('a.webservice').click(function(){
          $.ajax({url : "webservice",type : "GET"}).done(updateToolBox);
        });
      };

      var updateToolBox = function(response){
        $('#tool_box').html(response);
      };

      var updateToolBoxForDiff = function(response){
        updateToolBox(response);
        new FileDiffForm().boot();
      };

      var updateToolBoxForSettings = function(response){
        updateToolBox(response);
        new Settings().boot();
      };

      var updateToolBoxForLog = function(response){
        updateToolBox(response);
        new LogForm().boot();
        new LogFetch().boot();
      };

      var updateToolBoxForRouter = function(response){
        updateToolBox(response);
        new Router().boot();
      };

      this.boot = function(){
        setupNavigation();
      };

};

$(document).ready(function() {
	new HomePage().boot();
});

HomePage = function(){

      var setupNavigation = function(){
        $('a.logs').click(function(){
          $.ajax({url : "log-tool",type : "GET"}).done(updateMainBoxForLog);
        });
        $('a.settings').click(function(){
          $.ajax({url : "settings-view",type : "GET"}).done(updateMainBoxForSettings);
        });
        $('a.config').click(function(){
          $.ajax({url : "config-tool",type : "GET"}).done(updateMainBoxForDiff);
        });
        $('a.router').click(function(){
          $.ajax({url : "router-view",type : "GET"}).done(updateMainBoxForRouter);
        });
      };

      var updateMainBox = function(response){
        $('#tool_box').html(response);
        $('#results').html('<p>Awaiting input...</p>');
      };

      var updateMainBoxForDiff = function(response){
        updateMainBox(response);
        new FileDiffForm().boot();
      };

      var updateMainBoxForSettings = function(response){
        updateMainBox(response);
        new Settings().boot();
      };

      var updateMainBoxForLog = function(response){
        updateMainBox(response);
        new LogForm().boot();
        new LogFetch().boot();
      };

      var updateMainBoxForRouter = function(response){
        updateMainBox(response);
        new Router().boot();
      };

      this.boot = function(){
        setupNavigation();
      };

};

$(document).ready(function() {
	new HomePage().boot();
});

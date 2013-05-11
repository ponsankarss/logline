LogForm = function(){
    var folder, keys, startDate, endDate,option;

    var pick = function() {
		folder = $("#folder").val();
		keys = $("#keys").val();
		startDate = $("#start_date").val();
		endDate = $("#end_date").val();
		option = $("#option").val();
	};

    var post = function(){
    $("#loading-div-background").show();
        pick();
        $.ajax({
        	url : "/log/search/",
        	data : {
        		folder : folder,
        		keys : keys,
        		startDate: startDate,
        		endDate: endDate,
        		option: option
        	},
        	type : "POST"
        }).done(displayResults);
    };

    var displayResults = function(response){
        $("#results").html(response);
        $("#loading-div-background").hide();
        new LogLight().boot(keys);
        new LogTabs().boot();
    };

    this.boot = function(){
        $("#analyse").click(post);
        $("#loading-div-background").css({ opacity: 0.7 });
        $("#start_date").datetimepicker();
        $("#end_date").datetimepicker();
    };
};
//-------------------------------------------------------------------------------------------------
LogTabs = function(){
    var threadTabs = function(){
       $('.expand').click(function(e){
          $(this).siblings('.content').slideToggle('slow');
          e.preventDefault();
        });
    };
    var errorTabs = function(){
       $('.expand-error').click(function(e){
          $(this).siblings('.content-error').slideToggle('slow');
          e.preventDefault();
       });
    };
    var xmlTabs = function(){
       $('.content-xml').each(function(e){
          $(this).before("<p class='expand-xml'><img src='/static/images/icon_xml.jpg'/></p>");
       });
       $('.expand-xml').click(function(e){
          $(this).next('.content-xml').slideToggle('slow');
          e.preventDefault();
       });
    };

    var closeAllErrorTabs = function(){
       $('.content-error').slideToggle('fast');
    };
    var closeAllXMLTabs = function(){
       $('.content-xml').slideToggle('fast');
    };

    this.boot = function(){
        $( "#tabs" ).tabs();
        threadTabs();
        errorTabs();
        xmlTabs();
        closeAllXMLTabs();
        closeAllErrorTabs();
    };
};
//-------------------------------------------------------------------------------------------------
LogLight = function(){
  var searchKeys;

  var specials = function(){
    var splits = searchKeys.split(",");
    for(var i=0, len=splits.length; i < len; i++) {
      splits[i] = splits[i].trim();
    }
    $("li.key-item").each(function(){
        var html = $(this).text();
        //xml
        var xmlMatch = html.match("XML");
        if(xmlMatch){
            $(this).css("color","#006400").css("padding-bottom", "10px").css("padding-top", "10px");
            $(this).wrap("<div class='content-xml'></div>");
            return;
        }
        //sql
        var dbMatch = html.match("Blue2Dao*");
         if(dbMatch){
            $(this).css("color","#104E8B");
            var schemaMatch = html.match("(PNASCO|PNASCMOD|T6QJDBA|NAXJRB1).[A-Z|_]+");
            if(schemaMatch){ html = html.replace(schemaMatch[0],"<span class='sccf'>"+schemaMatch[0]+"</span>")};
         }
        //jms message
        if(html.match("JMSSender|DispatchQSender|MessageDispatcher|jms/")){
            $(this).css("color","#1874CD");
        }
        //error
        if(html.match("\\[ERROR\\]")){
            $(this).css("color","maroon");
        }

        //user-id
        var userMatch = html.match("[A-Z0-9]{6,7}");
        if(userMatch){
           var keyNotMatch = splits.indexOf(userMatch[0])== -1;
           if(keyNotMatch && userMatch[0].match('[A-Z]+')&& userMatch[0].match('[0-9]*')&& !userMatch[0].match('SELECT|INSERT|UPDATE') ){
                html = html.replace(userMatch[0],"<span class='sccf'>"+userMatch[0]+"</span>");
           }
        }
        //sccf
        var sccfMatch = html.match("[0-9]{17}");
        if(sccfMatch){
          var keyNotMatch = splits.indexOf(sccfMatch[0])== -1;
          if(keyNotMatch){
                html = html.replace(sccfMatch[0],"<span class='sccf'>"+sccfMatch[0]+"</span>");
          }
        } else{
           //boid
           var boidMatch = html.match("780[12][\D]+|534[12]|542[12]|560[12]|690[12]|090[12]|580[12]|671[12]|701[12]|710[12]|430[12]|936[12]|134[12]|104[12]|834[12]|184[12]|273[12]|064[12]|308[12]|428[12]|104[12]|249[12]|456[12]|268[12]|053[12]|046[12]|720[12]");
           if(boidMatch){html = html.replace(boidMatch[0],"<span class='sccf'>"+boidMatch[0]+"</span>")};
        }
        //time-color
        var timeMatch = html.match("[0-9]{4}-[0-9]{2}-[0-9]{2}\\s*[0-9]{1,2}:[0-9]{2}:[0-9]{2},[0-9]{0,3}");
        if(timeMatch){html = html.replace(timeMatch[0],"<span class='time'>"+timeMatch[0]+"</span>")}
        else{
            timeMatch = html.match("[0-9]{2}\\s*(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s*[0-9]{4}\\s*[0-9]{1,2}:[0-9]{2}:[0-9]{2}");
            if(timeMatch){html = html.replace(timeMatch[0],"<span class='time'>"+timeMatch[0]+"</span>")}
        }
        //file
        var fileMatch = html.match("\\[.*?\\]");
        if(fileMatch){html = html.replace(fileMatch[0],"<span class='file'>"+fileMatch[0]+"</span>")};

        $(this).html(html);

        }//end of each function
    );// end of each li
  };

  var keysUp = function(){
    $("#results").highlight(searchKeys.split(","));
    $(".highlight").wrap("<a class='search' href='#'></a>");
  };

  this.boot = function(keys){
     searchKeys = keys;
     specials();
     keysUp();
  };
};
//-------------------------------------------------------------------------------------------------
LogFetch = function(){
    var machine, logFileNames;

    var browseLogs = function(){
        machine = $("#machine").val();
        $("#loading-div-background").show();
        $.ajax({
            	url : "/log/browse/",
            	data : {
            		machine : machine
            	},
            	type : "POST"
            }).done(displayResults);
    };

    var displayResults = function(response){
       $("#results").html(response);
       $("#loading-div-background").hide();
       $('#download_logs').click(downloadLogs);
    };

    var downloadLogs = function(){
        machine = $("#machine").val();
        logFileNames = "";
        $("#logFileNames option:selected").each(function () {
            logFileNames += $(this).text() + ",";
        });

        $("#loading-div-background").show();
        $.ajax({
            	url : "/log/download/",
            	data : {
            		machine : machine,
            		logFileNames:logFileNames
            	},
            	type : "POST"
            }).done(displayResults);
    };

    this.boot = function(){
        $('#browse_logs').click(browseLogs);
    };
};
//-------------------------------------------------------------------------------------------------

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
        $("#tail-log").hide();
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
        var dbMatch = html.match("\\bSELECT\\b|\\bINSERT\\b|\\bUPDATE\\b|\\bDELETE\\b");
        if(dbMatch){
           $(this).css("color","#104E8B");
        }
        //error
        if(html.match("\\[ERROR\\]")){
            $(this).css("color","maroon");
        }
        //file
        var fileMatch = html.match("\\[.*?\\]");
        if(fileMatch){
            html = html.replace(fileMatch[0],"<span class='file'>"+fileMatch[0]+"</span>")
        };

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
    var tailTabs = new LogTailTabs();

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
       $("#browse-logs-dialog").html(response);
       $("#browse-logs-dialog").dialog({height:"auto", width: 600, modal: true});
       $(".ui-dialog").show();
       $("#loading-div-background").hide();
       $('#download_logs').click(downloadLogs);
       $('#tail_logs').click(tailLogs);
    };

    var downloadLogs = function(){
        machine = $("#machine").val();
        logFileNames = "";
        $("#logFileNames option:selected").each(function () {
            logFileNames += $(this).text() + ",";
        });
        $(".ui-dialog").hide();
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

    var tailLogs = function(){
        machine = $("#machine").val();
        logFileNames = "";
        $("#logFileNames option:selected").each(function () {
            logFileNames += $(this).text() + ",";
        });
        $(".ui-dialog").hide();
        $("#loading-div-background").show();
         tailTabs. tellServerToStartTailing(machine, logFileNames);
    };

    this.boot = function(){
        $('#browse_logs').click(browseLogs);
        tailTabs.boot();
        tailTabs.hideAll();
    };
};
//-------------------------------------------------------------------------------------------------
LogTailTabs=  function(){

    var logTailers = {};

    this.add = function(machine, logFile){

    };

    this.remove = function(machine, logFiles){

    };

    this.tellServerToStartTailing = function(machine, logFileNames){
            $.ajax({
                        url : "/log/tail/start",
                        data : {
                        	machine : machine,
                        	logFileNames:logFileNames
                        },
                        type : "POST"
            }).done(showAll);
    };

    this.showAll = function(){
        $("#tail-log").show();
        $("#tail-tabs").show();
    };

    this.hideAll = function(){
        $("#tail-log").hide();
        $("#tail-tabs").hide();
     };

    this.boot = function(){
      $("#tail-tabs").tabs();
    };
};
//-------------------------------------------------------------------------------------------------
LogTailer = function(){
   var machine;
   var logFile;
   var tabId;
                                          l
   this.start = function(){
        $.ajax({
            url:"/log/tail/pull",
            type:"GET",
            data:{machine:machine, logFile:logFile}
        }).done(updateTab);
   };

   this.stop = function(){
           $.ajax({
               url:"/log/tail/stop",
               type:"GET" ,
               data:{machine:machine, logFile:logFile}
           }).done(closeTab);
      };

   var updateTab = function(response){
           $(tabId).append(response);
   };

   var closeTab = function(){
           $(tabId).remove();
   };

   this.boot = function(machineArg, logFileArg){
       machine = machineArg;
       logFile = logFileArg;
       tabId = "#"+machine+"-"+logFileArg;
   };
};
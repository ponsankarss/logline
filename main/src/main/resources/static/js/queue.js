QueueHit = function(){

    var linkTabs = function(){
        $('.expand-queue').click(function(e){
            $(this).siblings('.content-queue').slideToggle('slow');
            e.preventDefault();
        });
    };

    var closeAllLinkTabs = function(){
        $('.content-queue').slideToggle('fast');
    };

    var setupConnectLinks = function(){
        $('.queueMgr-connect').click(function(e){
            var queueMgr = $(this).attr("queueMgr");
            var responseElement = "#"+queueMgr;
            $(responseElement).html("<p style='color:orange;'>please wait..</p>");
            $(responseElement).slideToggle('slow');

            e.preventDefault();
            $.ajax({
               type:"GET",
               url:"/queue/connect",
               data:{queueMgr:queueMgr},
               cache:false,
               success: displayQueues
            });

        });
    };

    var setupBrowseLinks = function(){
       $('.queue-browse').click(function(e){
           var queueMgrName = $(this).attr("queueMgr");
           var queueName = $(this).attr("queue");
           e.preventDefault();
           $.ajax({
              type:"GET",
              url:"/queue/browse",
              data:{queueMgrName:queueMgrName,queueName:queueName},
              cache:false,
              success: displayQueueMsgs
           });
       });
    };

    var displayQueues = function(data){
        var response= $(data);
        var responseElement = "#"+response.attr("class").replace(/[!"#$%&'()*+,.\/:;<=>?@[\\\]^`{|}~]/g, "\\\\$&");
        $(responseElement).html(data);
        setupBrowseLinks();
    };

    var displayQueueMsgs = function(data){
        var response= $(data);
        var responseElement = "#"+response.attr("queue").replace(/[!"#$%&'()*+,.\/:;<=>?@[\\\]^`{|}~]/g, "\\\\$&");
        $("#queue-messages").dialog({height:"auto", width: 900, modal: true});
        $("#queue-messages").html(data);
    };

    this.boot = function(){
        linkTabs();
        closeAllLinkTabs();
        setupConnectLinks();
    };
};
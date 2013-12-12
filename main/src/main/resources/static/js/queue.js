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
            var queueMgr = $(this).siblings("div").attr("id");
            e.preventDefault();
            alert(queueMgr);
            $.ajax({
               type:"GET",
               url:"/queue/connect",
               data:{queueMgr:queueMgr},
               cache:false,
               success: displayQueues
            });

        });
    };

    var displayQueues = function(data){
        var response= $(data);
        var responseElement = "#"+response.attr("class");
        $(responseElement).html(data);
    };

    this.boot = function(){
        linkTabs();
        closeAllLinkTabs();
        setupConnectLinks();
    };
};
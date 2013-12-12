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

    this.boot = function(){
        linkTabs();
        closeAllLinkTabs();
    };
};
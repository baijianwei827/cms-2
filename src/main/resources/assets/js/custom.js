var custom = (function() {

    var updateActive = function(setId, removeId) {
        $(removeId).removeClass("active");
        $(setId).addClass("active");
    };

    return {
        updateActive : updateActive
    };
}());
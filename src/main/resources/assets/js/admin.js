var cmsAdmin = (function() {

    var token = 1337;
    var currentUser;

    $(document).ready(function() {
        cmsAdmin.getLoginTemplate();
        cmsAdmin.getSidebarTemplate();
    });

    $("#author-nav").click(function() {
        cmsAdmin.getAuthorTemplate();
    });

    $("#section-nav").click(function() {
        cmsAdmin.getSectionTemplate();
    });

    $("#article-nav").click(function() {
        cmsAdmin.getArticleTemplate();
    });

    $("#file-nav").click(function() {
        cmsAdmin.getFileTemplate();
    });
    
    $("#archive-nav").click(function() {
        cmsAdmin.getArchiveTemplate();
    });

    var getLoginTemplate = function(t) {
        getTemplate(t, "Admin Login", "#login-template");
        $("#login-form").validate({
            rules : {
                username : "required",
                password : "required"
            },
            submitHandler : function() {
                sendFormData("/auth", "POST", authenticate("#login-form"), function(data) {
                    setToken(data.token);
                    setCurrentUser(data.username);
                    getSidebarTemplate();
                    getAuthorTemplate(5000);
                });
            }
        });
    };

    var getAuthorTemplate = function(t) {
        if (verifyLoggedIn() === false) {
            return;
        }
        custom.updateActive("#author-nav", "#sub-nav li");
        getTemplate(t, "Authors", "#author-template", ".content-placeholder", undefined, 
                "/api/v1/authors", remoteJson, function() {
                    $("#author-table").tablesorter({
                        theme : 'bootstrap',
                        headerTemplate : '{content} {icon}',
                        widgets : [ "uitheme" ],
                        sortList : [ 0, 0 ]
                    });
                    getMetricTemplate("/api/v1/metrics", "#metric-template");
                });
    };

    var getAuthorCreateTemplate = function(t) {
        custom.updateActive("#author-nav", "#sub-nav li");
        getTemplate(t, "Authors", "#author-update-template");
        $("#author-form").validate({
            rules : {
                username : "required",
                email : {
                    required : true,
                    email : true
                },
                password : "required",
                "confirm-password" : {
                    required : true,
                    equalTo : "#password"
                }
            },
            submitHandler : function() {
                sendFormData("/api/v1/authors", "POST", getFormData("#author-form"), function(data) {
                    getMsgTemplate("#success-template", "Author was created");
                    getAuthorTemplate(5000);
                });
            }
        });
    };

    var getAuthorUpdateTemplate = function(t, path) {
        custom.updateActive("#author-nav", "#sub-nav li");
        getMetricTemplate("/api/v1/metrics/authors/" + path, "#metric-template");
        getTemplate(t, "Authors", "#author-update-template", ".content-placeholder", undefined,
                "/api/v1/authors/" + path, remoteJson, function() {
                    $("#author-form").validate({
                        rules : {
                            username : "required",
                            email : {
                                required : true,
                                email : true
                            },
                            password : "required",
                            "confirm-password" : {
                                required : true,
                                equalTo : "#password"
                            }
                        },
                        submitHandler : function() {
                            sendFormData("/api/v1/authors/" + path, "PUT", getFormData("#author-form"), 
                                    function(data) {
                                        getMsgTemplate("#success-template", "Author was updated");
                                        getAuthorTemplate(5000);
                                    });
                        }
                    });
                });
    };

    var getAuthorDeleteTemplate = function(path) {
        deleteResource('authors/' + path, getAuthorTemplate);
    };

    var getSectionTemplate = function(t) {
        if (verifyLoggedIn() === false) {
            return;
        }
        custom.updateActive("#section-nav", "#sub-nav li");
        getTemplate(t, "Sections", "#section-template", ".content-placeholder", undefined,
                "/api/v1/sections", remoteJson, function() {
                    $("#section-table").tablesorter({
                        theme : 'bootstrap',
                        headerTemplate : '{content} {icon}',
                        widgets : [ "uitheme" ],
                        sortList : [ 0, 0 ]
                    });
                    getMetricTemplate("/api/v1/metrics", "#metric-template");
                    $('[data-toggle="tooltip"]').tooltip();
                });
    };

    var getSectionCreateTemplate = function(t) {
        custom.updateActive("#section-nav", "#sub-nav li");
        getTemplate(t, "Sections", "#section-update-template");
        $("#section-form").validate({
            rules : {
                title : "required",
            },
            submitHandler : function() {
                sendFormData("/api/v1/sections", "POST", getFormData("#section-form"), function(data) {
                    getMsgTemplate("#success-template", "Section was created");
                    getSectionTemplate(5000);
                });
            }
        });
    };

    var getSectionUpdateTemplate = function(t, path) {
        custom.updateActive("#section-nav", "#sub-nav li");
        getMetricTemplate("/api/v1/metrics/sections/" + path, "#metric-template");
        getTemplate(t, "Sections", "#section-update-template", ".content-placeholder", undefined,
                "/api/v1/sections/" + path, remoteJson, function() {
                    $("#section-form").validate({
                        rules : {
                            title : "required",
                        },
                        submitHandler : function() {
                            sendFormData("/api/v1/sections/" + path, "PUT", getFormData("#section-form"),
                                    function(data) {
                                        getMsgTemplate("#success-template", "Section was updated");
                                        getSectionTemplate(5000);
                                    });
                        }
                    });
                });
    };

    var getSectionDeleteTemplate = function(path) {
        deleteResource('sections/' + path, getSectionTemplate);
    };

    var getArticleTemplate = function(t) {
        if (verifyLoggedIn() === false) {
            return;
        }
        custom.updateActive("#article-nav", "#sub-nav li");
        getTemplate(t, "Articles", "#article-template", ".content-placeholder", undefined,
                "/api/v1/articles", remoteJson, function() {
                    $("#article-table").tablesorter({
                        theme : 'bootstrap',
                        headerTemplate : '{content} {icon}',
                        widgets : [ "uitheme" ],
                        sortList : [ 0, 0 ]
                    });
                    getMetricTemplate("/api/v1/metrics", "#metric-template");
                    $('[data-toggle="tooltip"]').tooltip();
                });
    };

    var getArticleCreateTemplate = function(t) {
        custom.updateActive("#article-nav", "#sub-nav li");
        getTemplate(t, "Articles", "#article-update-template", ".content-placeholder", undefined,
                "/api/v1/articles/-1", remoteJson, function() {
                    $("#article-form").validate({
                        rules : {
                            title : "required",
                            content : "required",
                        },
                        submitHandler : function() {
                            sendFormData("/api/v1/articles", "POST", getFormData("#article-form"), function(data) {
                                getMsgTemplate("#success-template", "Article was created");
                                getArticleTemplate(5000);
                            });
                        }
                    });
                });
    };

    var getArticleUpdateTemplate = function(t, path) {
        custom.updateActive("#article-nav", "#sub-nav li");
        getMetricTemplate("/api/v1/metrics/articles/" + path, "#metric-template");
        getTemplate(t, "Articles", "#article-update-template", ".content-placeholder", undefined,
                "/api/v1/articles/" + path, remoteJson, function() {
                    $("#article-form").validate({
                        rules : {
                            title : "required",
                            content : "required",
                        },
                        submitHandler : function() {
                            sendFormData("/api/v1/articles/" + path, "PUT", getFormData("#article-form"),
                                    function(data) {
                                        getMsgTemplate("#success-template", "Article was updated");
                                        getArticleTemplate(5000);
                                    });
                        }
                    });
                });
    };

    var getArticleDeleteTemplate = function(path) {
        deleteResource('articles/' + path, getArticleTemplate);
    };

    var getFileTemplate = function(t) {
        if (verifyLoggedIn() === false) {
            return;
        }
        custom.updateActive("#file-nav", "#sub-nav li");
        getTemplate(t, "Files", "#file-template", ".content-placeholder", undefined,
                "/api/v1/files", remoteJson, function() {
                    $("#file-table").tablesorter({
                        theme : 'bootstrap',
                        headerTemplate : '{content} {icon}',
                        widgets : [ "uitheme" ],
                        sortList : [ 0, 0 ]
                    });
                    getMetricTemplate("/api/v1/metrics", "#metric-template");
                    $('[data-toggle="tooltip"]').tooltip();
                    updateDownloadLink();
                });
    };

    var getFileCreateTemplate = function(t) {
        custom.updateActive("#file-nav", "#sub-nav li");
        getTemplate(t, "Files", "#file-update-template", ".content-placeholder", undefined,
                "/api/v1/files/-1", remoteJson, function() {
                    $("#file-form").validate({
                        rules : {
                            filename : "required",
                            file : "required",
                        },
                        submitHandler : function() {
                            sendFormWithFileData("/api/v1/files", "POST", "#file-form", function(data) {
                                getMsgTemplate("#success-template", "File was created");
                                getFileTemplate(5000);
                            });
                        }
                    });
                });
    };

    var getFileUpdateTemplate = function(t, path) {
        custom.updateActive("#file-nav", "#sub-nav li");
        getTemplate(t, "Files", "#file-update-template",
                ".content-placeholder", undefined, "/api/v1/files/" + path,
                remoteJson, function() {
                    $("#file-form").validate({
                        rules : {
                            filename : "required",
                        },
                        submitHandler : function() {
                            sendFormData("/api/v1/files/" + path, "PUT", getFormData("#file-form"),
                                    function(data) {
                                        getMsgTemplate("#success-template", "File was updated");
                                        getFileTemplate(5000);
                                    });
                        }
                    });
                    updateDownloadLink();
                });
    };

    var getFileDeleteTemplate = function(path) {
        deleteResource('files/' + path, getFileTemplate);
    };
    
    var getArchiveTemplate = function(t) {
        if (verifyLoggedIn() === false) {
            return;
        }
        custom.updateActive("#archive-nav", "#sub-nav li");
        getTemplate(t, "Archives", "#archive-template", ".content-placeholder", undefined,
                "/api/v1/archives", remoteJson, function() {
                    $("#archive-table").tablesorter({
                        theme : 'bootstrap',
                        headerTemplate : '{content} {icon}',
                        widgets : [ "uitheme" ],
                        sortList : [ 0, 0 ]
                    });
                    getMetricTemplate("/api/v1/metrics", "#metric-template");
                    $('[data-toggle="tooltip"]').tooltip();
                });
    };

    var getTemplate = function(t, pageTitle, templateId, target, context, url, func, cb) {
        clearMsg(t);
        $("#page-title").text(pageTitle);
        var source = $(templateId).html();
        var template = Handlebars.compile(source);
        if (func === undefined) {
            var html = template(context);
            $(".content-placeholder").html(html);
        } else {
            func(url, template, target, cb);
        }
    };

    var getSidebarTemplate = function() {
        var user = getCurrentUser();
        var source = $("#sidebar-template").html();
        var template = Handlebars.compile(source);
        var context = {"username" : user};
        var html = template(context);
        $(".sidebar-placeholder").html(html);
    };

    var getMetricTemplate = function(url, templateId) {
        var source = $(templateId).html();
        var template = Handlebars.compile(source);
        remoteJson(url, template, ".metric-placeholder");
    };

    var getMsgTemplate = function(templateId, msg) {
        var source = $(templateId).html();
        var template = Handlebars.compile(source);
        var context = {"message" : msg};
        var html = template(context);
        $(".js-msg-placeholder").append(html);
    };

    var clearMsg = function(t) {
        if (t === undefined) {
            t = 0;
        }
        setTimeout(function() {
            $(".js-msg-placeholder").empty();
        }, t);
    };

    var checkResult = function(context, cb) {
        if (context.hasOwnProperty('error')) {
            getMsgTemplate("#error-template", context.error);
            return context;
        } else if (context.hasOwnProperty('success')) {
            getMsgTemplate("#success-template", context.success);
        }
        if (undefined !== cb) {
            cb(context);
        }
        // clear on success form?
        return context;
    };

    var authenticate = function(formId) {
        var formInputs = $(formId + ' :input').not(':input[type=button], :input[type=submit]');
        var obj = {};
        formInputs.each(function() {
            var cc = $.camelCase(this.id);
            if ($(this).is(':checkbox')) {
                obj[cc] = $(this).is(':checked');
            } else {
                obj[cc] = $(this).val();
            }
        });
        return obj;
    };

    var verifyLoggedIn = function() {
        if (getCurrentUser() === undefined) {
            getMsgTemplate("#error-template", "Please log in to continue");
            getLoginTemplate(5000);
            return false;
        }
        return true;
    };

    var updateDownloadLink = function() {
        var auth = getToken();
        $(".download").each(function(index) {
            var id = $(this).attr("href");
            $(this).attr("href", "/api/v1/files/" + id + "?" + $.param({"download" : true, "auth" : auth}));
        });
    };

    // TODO move auth into separate function
    var getFormData = function(formId) {
        var formInputs = $(formId + ' :input').not(':input[type=button], :input[type=submit]');
        var obj = {};
        var result = {};
        formInputs.each(function() {
            var cc = $.camelCase(this.id);
            if ($(this).is(':checkbox')) {
                obj[cc] = $(this).is(':checked');
            } else {
                obj[cc] = $(this).val();
            }
        });
        result.entity = obj;
        result.auth = getToken(); // add auth token
        return result;
    };

    var sendFormData = function(url, method, data, cb) {
        $.ajax({
            url : url,
            type : method,
            contentType : "application/json",
            data : JSON.stringify(data),
            dataType : "json"
        })
        .done(function(data) {
            checkResult(data, cb);
        })
        .fail(function(e) {
            getMsgTemplate("#error-template", "There was an issue communicating with the server.");
        });
    };

    // TODO make more generic
    var sendFormWithFileData = function(url, method, data, cb) {
        var auth = getToken();
        var selected = $("#articleId").val();
        var formData = new FormData($(data)[0]);
        formData.append("auth", auth);
        formData.append("articleId", selected);
        $.ajax({
            url : url,
            type : method,
            contentType : false,
            processData : false,
            cache : false,
            data : formData
            // dataType: "json"
        })
        .done(function(data) {
            checkResult(data, cb);
        })
        .fail(function(e) {
            getMsgTemplate("#error-template", "There was an issue communicating with the server.");
        });
    };

    var remoteJson = function(url, template, target, cb) {
        var obj = {};
        obj.auth = getToken();
        $.getJSON(url, obj, {  
        })
        .done(function(data) {
            var context = data;
            checkResult(context);
            var html = template(context);
            $(target).html(html);
            if (cb !== undefined) {
                cb();
            }
        })
        .fail(function(e) {
            getMsgTemplate("#error-template", "There was an issue communicating with the server.");
        });
    };

    var deleteResource = function(path, cb) {
        var auth = getToken();
        sendFormData("/api/v1/" + path + "?" + $.param({"auth" : auth}), "DELETE", undefined, function(data) {
            cb(5000);
        });
    };

    var getToken = function() {
        return token;
    };

    var setToken = function(data) {
        token = data;
    };

    var getCurrentUser = function() {
        return currentUser;
    };

    var setCurrentUser = function(data) {
        currentUser = data;
    };

    return {
        getLoginTemplate : getLoginTemplate,
        getAuthorTemplate : getAuthorTemplate,
        getAuthorCreateTemplate : getAuthorCreateTemplate,
        getAuthorUpdateTemplate : getAuthorUpdateTemplate,
        getAuthorDeleteTemplate : getAuthorDeleteTemplate,
        getSectionTemplate : getSectionTemplate,
        getSectionCreateTemplate : getSectionCreateTemplate,
        getSectionUpdateTemplate : getSectionUpdateTemplate,
        getSectionDeleteTemplate : getSectionDeleteTemplate,
        getArticleTemplate : getArticleTemplate,
        getArticleCreateTemplate : getArticleCreateTemplate,
        getArticleUpdateTemplate : getArticleUpdateTemplate,
        getArticleDeleteTemplate : getArticleDeleteTemplate,
        getFileTemplate : getFileTemplate,
        getFileCreateTemplate : getFileCreateTemplate,
        getFileUpdateTemplate : getFileUpdateTemplate,
        getFileDeleteTemplate : getFileDeleteTemplate,
        getArchiveTemplate : getArchiveTemplate,
        getTemplate : getTemplate,
        getSidebarTemplate : getSidebarTemplate,
        getMetricTemplate : getMetricTemplate,
        getMsgTemplate : getMsgTemplate,
        clearMsg : clearMsg,
        checkResult : checkResult,
        authenticate : authenticate,
        verifyLoggedIn : verifyLoggedIn,
        updateDownloadLink : updateDownloadLink,
        getFormData : getFormData,
        sendFormData : sendFormData,
        sendFormWithFileData : sendFormWithFileData,
        remoteJson : remoteJson,
        deleteResource : deleteResource,
        getToken : getToken,
        setToken : setToken,
        getCurrentUser : getCurrentUser,
        setCurrentUser : setCurrentUser
    };
}());
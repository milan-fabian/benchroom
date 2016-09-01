function showDialog(title, url) {
    $("#dialog").html("")
            .dialog({
                height: "auto",
                width: "auto",
                modal: true,
                resizable: false,
                position: 'center',
                title: title
            }).load(url, function () {
        $(this).dialog("option", "position", ['center', 'center']);
    });
}

//jquery.spring-friendly.min.js
!function(e){function n(e,t,r,o){var u;if(jQuery.isArray(t))jQuery.each(t,function(t,u){r||i.test(e)?o(e,u):n(e+"["+("object"==typeof u?t:"")+"]",u,r,o)});else if(r||"object"!==jQuery.type(t))o(e,t);else for(u in t)n(e+"."+u,t[u],r,o)}var t=/%20/g,i=/\[\]$/;e.param=function(e,i){var r,o=[],u=function(e,n){n=jQuery.isFunction(n)?n():null==n?"":n,o[o.length]=encodeURIComponent(e)+"="+encodeURIComponent(n)};if(void 0===i&&(i=jQuery.ajaxSettings&&jQuery.ajaxSettings.traditional),jQuery.isArray(e)||e.jquery&&!jQuery.isPlainObject(e))jQuery.each(e,function(){u(this.name,this.value)});else for(r in e)n(r,e[r],i,u);return o.join("&").replace(t,"+")}}(jQuery);
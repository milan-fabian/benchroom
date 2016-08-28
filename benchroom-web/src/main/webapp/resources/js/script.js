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

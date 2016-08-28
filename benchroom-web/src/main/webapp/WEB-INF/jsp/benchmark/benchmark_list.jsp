<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<script>
    $(function () {
        $("#softwareSelect").autocomplete({
            source: "<%=request.getContextPath()%><%=URL_BENCHMARK_SEARCH_SOFTWARE%>",
            minLength: 3,
            select: function (event, ui) {
                $("#versionSelect").prop('disabled', false).val("");
                $("#suiteSelect").prop('disabled', false).val("");
                $("#versionId").val("");
                $("#suiteId").val("");
                $("#softwareSelect").val(ui.item.label);
                $("#softwareId").val(ui.item.value);
                checkFilled();
                return false;
            },
            focus: function (event, ui) {
                $("#softwareSelect").val(ui.item.label);
                return false;
            }
        });

        $("#versionSelect").autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: "<%=request.getContextPath()%><%=URL_BENCHMARK_SEARCH_VERSION%>?software=" + $("#softwareId").val(),
                    data: {
                        term: request.term
                    },
                    success: function (data) {
                        response(data);
                    }
                });
            },
            minLength: 0,
            select: function (event, ui) {
                $("#versionSelect").val(ui.item.label);
                $("#versionId").val(ui.item.value);
                checkFilled();
                return false;
            },
            focus: function (event, ui) {
                $("#versionSelect").val(ui.item.label);
                return false;
            }
        });
        $("#suiteSelect").autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: "<%=request.getContextPath()%><%=URL_BENCHMARK_SEARCH_SUITE%>?software=" + $("#softwareId").val(),
                    data: {
                        term: request.term
                    },
                    success: function (data) {
                        response(data);
                    }
                });
            },
            minLength: 0,
            select: function (event, ui) {
                $("#suiteSelect").val(ui.item.label);
                $("#suiteId").val(ui.item.value);
                checkFilled();
                return false;
            },
            focus: function (event, ui) {
                $("#suiteSelect").val(ui.item.label);
                return false;
            }
        });

        $('#run').DataTable({
            ajax: function (data, callback, settings) {
                $.post('<%=request.getContextPath()%><%=URL_BENCHMARK_LIST%>?suite=' + $("#suiteId").val() + "&version=" + $("#versionId").val(), data, function (data) {
                    callback(data);
                });
            },
            dataSrc: "tableData",
            serverSide: true,
            columns: [
                {title: "When run", data: "whenStarted", render: $.fn.dataTable.render.moment('X', 'DD MMM YYYY')},
                {title: "Parameter", data: "benchmarkParameter.name"},
                {title: "System", data: "systemParameters"},
                {title: "Result", data: "results"}
            ]
        });
        $("#run_wrapper").hide();
    });

    function checkFilled() {
        var allFilled = $("#softwareSelect").val() != "" && $("#versionId").val() != "" && $("#suiteId").val() != "";
        $("#runButton").prop('disabled', !allFilled);
        if (!allFilled) {
            $("#runLink").html("");
            $("#run_wrapper").hide();
        } else {
            $("#run_wrapper").show();
            $("#run").DataTable().ajax.reload();
        }
    }

    function generateRunLink() {
        var link = window.location.href;
        link = "Run executor with parameters: <code>--server=" + link.substring(0, link.lastIndexOf("/")) + " --id=" + $("#versionId").val() + "-" + $("#suiteId").val() + "</code>";
        $("#runLink").html(link);
    }
</script>

<h2>Benchmark</h2>

<form>
    <table>
        <tr>
            <td>Software:</td>
            <td><input type="text" id="softwareSelect"/><input type="hidden" id="softwareId"/></td>
        </tr>
        <tr>
            <td>Version:</td>
            <td><input type="text" id="versionSelect" disabled/><input type="hidden" id="versionId"/></td>
        </tr>
        <tr>
            <td>Benchmark suite:</td>
            <td><input type="text" id="suiteSelect" disabled/><input type="hidden" id="suiteId"/></td>
        </tr>
    </table>
</form>

<button id="runButton" onclick="generateRunLink();" disabled>I wanna run it, show me parameters!</button>
<div id="runLink"></div>
<br><br>
<table id="run"></table>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>

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
                {title: "When run", data: "whenStarted", render: $.fn.dataTable.render.moment('X', 'DD MMM YYYY, HH:mm')},
                {title: "Parameter", data: "benchmarkParameters", sortable: false},
                {title: "System", data: "systemParameters", sortable: false},
                {title: "Result", data: "results", sortable: false},
                {sortable: false, render: function (data, type, row) {
                        return "<a href='javascript:void(0);' onclick='showDialog(\"Benchmark run\", \"<%=request.getContextPath()%><%=URL_BENCHMARK_DETAIL%>?run="
                                + row.id + "\");'>Details</a> "
                                + "<a href='<%=request.getContextPath()%><%=URL_BENCHMARK_COMPARE%>?run=" + row.id + "'>Compare</a>";
                    }
                }
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
        var suiteId = $("#suiteId").val();
        var minPriority = $("#minPriority").val();
        $("#runData").show();
        var link = window.location.href;
        link = "Run executor with parameters: <code>--id=" + $("#versionId").val() + "-" + suiteId + " --priority=" + minPriority + "</code>";
        $("#runLink").html(link);
        $("#parameterCobinations").load("<%=request.getContextPath()%><%=URL_BENCHMARK_COUNT_PARAMETERS%>?suite=" + suiteId + "&minPriority=" + minPriority);
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
<div id="runData" style="display:none">
    Min priority for parameters: <input type="number" min="1" max="255" value="100" id="minPriority" onchange="generateRunLink();" style="width: 50px;"/>
    Number of parameter combinations: <span id="parameterCobinations"></span>
    <div id="runLink"></div>
</div>
<br><br>
<table id="run"></table>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>

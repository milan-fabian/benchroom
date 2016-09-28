<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<h2>Benchmark executor</h2>

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

        function checkFilled() {

        }
    });
</script>

<h4>System parameters</h4>
<table>
    <c:forEach items="${executor.systemParameters}" var="parameter">
        <tr>
            <td><spring:message code="system.parameter.${parameter.key}"/>:</td>
            <td><b:printSystemParameter entry="${parameter}"/></td>
        </tr>
    </c:forEach>
</table>

<h4>Add job to run</h4>
<form method="post" action="<%=request.getContextPath()%><%=URL_EXECUTOR_ADD_JOB%>">
    <table>
        <tr>
            <td>Software:</td>
            <td><input type="text" id="softwareSelect"/><input type="hidden" id="softwareId"/></td>
        </tr>
        <tr>
            <td>Version:</td>
            <td><input type="text" id="versionSelect" disabled/><input type="hidden" id="versionId" name="version"/></td>
        </tr>
        <tr>
            <td>Benchmark suite:</td>
            <td><input type="text" id="suiteSelect" disabled/><input type="hidden" id="suiteId" name="suite"/></td>
        </tr>
        <tr>
            <td>Minimum priority:</td>
            <td><input type="number" value="1" min="1" max="255" name="minPriority"/>
        </tr>
        <tr>
            <td>Number of runs:</td>
            <td><input type="number" value="1" min="1" max="50" name="runs"/>
        </tr>
    </table>
    <input type="hidden" name="executor" value="${executor.id}"/>
    <input type="submit" value="Add"/>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>

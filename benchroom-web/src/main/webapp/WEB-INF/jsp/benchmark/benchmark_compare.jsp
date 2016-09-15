<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<script>
    function reloadGraph() {
        var data = $("#data").serialize();
        $("#graph").attr("data", "<%=request.getContextPath()%><%=URL_BENCHMARK_COMPARE_GRAPH%>?width=700&height=550&" + data);
    }

    function excelExport() {
        var iframe = $("<iframe style='display:none;'></iframe>").appendTo("body");
        var data = $("#data").serialize();
        iframe.attr("src", "<%=request.getContextPath()%><%=URL_BENCHMARK_COMPARE_EXCEL%>?" + data);
    }

    $(function () {
        reloadGraph();
    });
</script>

<h2>
    Benchmark run of <b><c:out value="${run.softwareVersion.softwareName}" /></b> (ver. <c:out value="${run.softwareVersion.name}"/>)
    for <b><c:out value="${suite.name}"/></b>
</h2>

<a href='javascript:void(0);' onclick='showDialog("Benchmark run", "<%=request.getContextPath()%><%=URL_BENCHMARK_DETAIL%>?run=${run.id}");'>Details</a>    
<a href='javascript:void(0);' onclick='excelExport();'>Export to excel</a>   
<br>
<form>
    <input type="hidden" name="run" value="${run.id}"/>
    <c:forEach items="${parameters}" var="parameter">
        <input type="checkbox" name="parameters" value="${parameter.id}" id="param-${parameter.id}" <c:if test="${choosenParameters.contains(parameter.id)}">checked</c:if>>
        <label for="param-${parameter.id}">${parameter.name}</label>
    </c:forEach>
    <input type="submit" value="Show"/>
</form>
<br>
<div style="display: inline-block;vertical-align: top">
    <form id="data">
        <table border="1" cellspacing="0" class="compareTable">
            <thead>
                <tr>
                    <th></th>
                        <c:forEach items="${run.benchmarkParameters}" var="parameter">
                        <th></th>
                        </c:forEach>
                        <c:forEach items="${run.results}" var="result">
                        <th colspan="2">
                            ${result.monitorName}
                            <input type="checkbox" name="monitors" value="${result.monitorId}" checked onchange="reloadGraph();"/>
                        </th>
                    </c:forEach>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${sameSystem}" var="run2">
                    <tr <c:if test="${run2.id == run.id}">style="background-color:#eee"</c:if>>
                            <td>
                                <input type="checkbox" name="runs" value="${run2.id}" checked onchange="reloadGraph();"/>
                            <c:if test="${run2.id != run.id}">
                                <a href="<%=request.getContextPath()%><%=URL_BENCHMARK_COMPARE%>?run=${run2.id}">&#x2750;</a>
                            </c:if>
                        </td>
                        <c:forEach items="${run2.benchmarkParameters}" var="parameter">
                            <td <c:if test="${run.benchmarkParameters.contains(parameter)}">style="font-weight: bold;"</c:if>>${parameter.name}</td>
                        </c:forEach>
                        <c:forEach items="${run2.results}" var="result" varStatus="i">
                            <td><b:printRunResult runResult="${result}"/></td>
                            <td><b:formatPercent percentage="${result.result / run.results[i.index].result}"/></td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </form>
</div>

<div style="display: inline-block;vertical-align: top">
    <object id="graph" type="image/svg+xml" >
    </object>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
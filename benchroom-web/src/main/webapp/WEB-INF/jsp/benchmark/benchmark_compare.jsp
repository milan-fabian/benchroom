<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<h2>
    Benchmark run of <b><c:out value="${run.softwareVersion.softwareName}" /></b> (ver. <c:out value="${run.softwareVersion.name}"/>)
    for <b><c:out value="${suite.name}"/></b>
</h2>

<a href='javascript:void(0);' onclick='showDialog("Benchmark run", "<%=request.getContextPath()%><%=URL_BENCHMARK_DETAIL%>?run=${run.id}");'>Details</a>    

<table border="1" cellspacing="0" cellpadding="2">
    <thead>
        <tr>
            <th></th>
                <c:forEach items="${run.benchmarkParameters}" var="parameter">
                <th></th>
                </c:forEach>
                <c:forEach items="${run.results}" var="result">
                <th colspan="2">${result.monitorName}</th>
                </c:forEach>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${sameSystem}" var="run2">
            <tr>
                <td>
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

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
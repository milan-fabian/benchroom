<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<p>
    Benchmark run of software <b><c:out value="${run.softwareVersion.softwareName}" /></b> (version <c:out value="${run.softwareVersion.name}"/>)
    for suite TODO with parameters <b><c:out value="${run.benchmarkParameter.name}"/></b>
</p>

<h4>Results:</h4>
<table>
    <c:forEach items="${run.results}" var="result">
        <tr>
            <td>${result.monitorName}</td>
            <td>${result.result}</td>
        </tr>
    </c:forEach>
</table>

<h4>Parameters of the system where benchmark run</h4>
<table>
    <c:forEach items="${run.systemParameters}" var="parameter">
        <tr>
            <td>${parameter.key}</td>
            <td>${parameter.value}</td>
        </tr>
    </c:forEach>
</table>

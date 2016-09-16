<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/benchroom.tld" prefix="b"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<p>
    Benchmark run of software <b><c:out value="${run.softwareVersion.softwareName}" /></b> (version <c:out value="${run.softwareVersion.name}"/>)
    <br>for suite <b><c:out value="${suite.name}"/></b>, which was run on ${run.whenStarted}.
</p>

<div><b>Run with parameters:</b> ${cmd}</div>
<table>
    <c:forEach items="${run.benchmarkParameters}" var="parameter">
        <tr>
            <td><c:out value="${parameter.name}"/></td>
        </tr>
    </c:forEach>
</table>

<h4>Results:</h4>
<table>
    <c:forEach items="${run.results}" var="result">
        <tr>
            <td><c:out value="${result.monitorName}"/>:</td>
            <td><b:printRunResult runResult="${result}"/></td>
        </tr>
    </c:forEach>
</table>

<h4>Parameters of the system where benchmark run:</h4>
<table>
    <c:forEach items="${run.systemInfo.parameters}" var="parameter">
        <tr>
            <td><spring:message code="system.parameter.${parameter.key}"/>:</td>
            <td><b:printSystemParameter entry="${parameter}"/></td>
        </tr>
    </c:forEach>
</table>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<c:choose>
    <c:when test="${not empty monitor.id}">
        <h2>Edit benchmark monitor <c:out value="${monitor.name}"/> for suite <c:out value="${suite.name}"/></h2>
    </c:when>
    <c:otherwise>
        <h2>Add new benchmark monitor for suite <c:out value="${suite.name}"/></h2>
    </c:otherwise>
</c:choose>

<form:form method="post" commandName="monitor">
    <form:hidden path="id"/>
    <table>
        <tr>
            <td>Name:</td>
            <td><form:input path="name"/></td>
            <td><form:errors path="name"/></td>
        </tr>
        <tr>
            <td>Type:</td>
            <td><form:select path="type" items="<%=sk.mimac.benchroom.api.enums.MonitorType.values()%>"/></td>
            <td><form:errors path="type"/></td>
        </tr>

        <tr>
            <td>Action:</td>
            <td><form:input path="action"/></td>
            <td><form:errors path="action"/></td>
        </tr>
    </table>
    <input type="submit" value="Save"/>
</form:form>

<a href="<%=request.getContextPath()%><%=URL_BENCHMARK_MONITOR%>?suite=${suite.id}">Back</a>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>

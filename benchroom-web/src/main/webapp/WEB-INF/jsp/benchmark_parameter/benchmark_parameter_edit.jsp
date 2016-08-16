<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<c:choose>
    <c:when test="${not empty parameter.id}">
        <h2>Edit benchmark parameter <c:out value="${parameter.name}"/> for suite <c:out value="${suite.name}"/> (software <c:out value="${suite.softwareName}"/>)</h2>
    </c:when>
    <c:otherwise>
        <h2>Add new parameter for benchmark suite <c:out value="${suite.name}"/> (software <c:out value="${suite.softwareName}"/>)</h2>
    </c:otherwise>
</c:choose>

<form:form method="post" commandName="parameter">
    <form:hidden path="id"/>
    <table>
        <tr>
            <td>Name:</td>
            <td><form:input path="name"/></td>
            <td><form:errors path="name"/></td>
        </tr>
        <tr>
            <td>Command line arguments:</td>
            <td><form:input path="commandLineArguments"/></td>
            <td><form:errors path="commandLineArguments"/></td>
        </tr>
        <tr>
            <td>Command line input:</td>
            <td><form:input path="commandLineInput"/></td>
            <td><form:errors path="commandLineInput"/></td>
        </tr>
    </table>
    <input type="submit" value="Save"/>
</form:form>

<a href="<%=request.getContextPath()%><%=URL_BENCHMARK_PARAMETER%>?suite=${suite.id}">Back</a>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>

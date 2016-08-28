<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<c:choose>
    <c:when test="${not empty suite.id}">
        <h2>Edit benchmark suite <c:out value="${suite.name}"/> for software <c:out value="${software.name}"/></h2>
    </c:when>
    <c:otherwise>
        <h2>Add new benchmark suite for software <c:out value="${software.name}"/></h2>
    </c:otherwise>
</c:choose>

<form:form method="post" commandName="suite">
    <form:hidden path="id"/>
    <table>
        <tr>
            <td>Name:</td>
            <td><form:input path="name"/></td>
            <td><form:errors path="name"/></td>
        </tr>
        <tr>
            <td>Setup data:</td>
            <td><form:textarea path="setupScript" cols="60"/></td>
            <td><form:errors path="setupScript"/></td>
        </tr>
        <tr>
            <td>Cleanup data:</td>
            <td><form:textarea path="cleanupScript" cols="60"/></td>
            <td><form:errors path="cleanupScript"/></td>
        </tr>
    </table>
    <input type="submit" value="Save"/>
</form:form>

<a href="<%=request.getContextPath()%><%=URL_BENCHMARK_SUITE%>?software=${software.id}">Back</a>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>

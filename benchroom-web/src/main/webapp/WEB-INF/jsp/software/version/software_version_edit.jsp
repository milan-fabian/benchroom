<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<c:choose>
    <c:when test="${not empty version.id}">
        <h1>Edit version <c:out value="${version.name}"/> for software <c:out value="${software.name}"/></h1>
    </c:when>
    <c:otherwise>
        <h1>Add new version for software <c:out value="${software.name}"/></h1>
    </c:otherwise>
</c:choose>

<form:form method="post" commandName="version">
    <form:hidden path="id"/>
    <table>
        <tr>
            <td>Name:</td>
            <td><form:input path="name"/></td>
            <td><form:errors path="name"/></td>
        </tr>
        <tr>
            <td>Release date:</td>
            <td><form:input path="releaseDate"/></td>
            <td><form:errors path="releaseDate"/></td>
        </tr>
    </table>
    <input type="submit" value="Save"/>
</form:form>

<a href="<%=request.getContextPath()%><%=URL_SOFTWARE_VERSION%>?software=${software.id}">Back</a>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>

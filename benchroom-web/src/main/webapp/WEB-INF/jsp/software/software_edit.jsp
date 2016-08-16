<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<c:choose>
    <c:when test="${not empty software.id}">
        <h2>Edit software <c:out value="${software.name}"/></h2>
    </c:when>
    <c:otherwise>
        <h2>Add new software</h2>
    </c:otherwise>
</c:choose>

<form:form method="post" commandName="software">
    <form:hidden path="id"/>
    <table>
        <tr>
            <td>Name:</td>
            <td><form:input path="name"/></td>
            <td><form:errors path="name"/></td>
        </tr>
    </table>
    <input type="submit" value="Save"/>
</form:form>

<a href="<%=request.getContextPath()%><%=URL_SOFTWARE%>">Back</a>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>

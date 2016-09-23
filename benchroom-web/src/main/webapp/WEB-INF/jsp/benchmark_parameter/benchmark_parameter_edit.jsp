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
            <td>Position:</td>
            <td>
                <form:select path="position">
                    <c:forEach items="${suite.parameterNames}" var="parameterName" varStatus="i">
                        <form:option value="${i.index}" label="${parameterName}"/>
                    </c:forEach>
                </form:select>
            </td>
            <td><form:errors path="position"/></td>
        </tr>
        <tr>
            <td>Priority:</td>
            <td><form:input path="priority" type="number" min="1" max="255"/></td>
            <td><form:errors path="priority"/></td>
        </tr>
        <tr>
            <td>Command line arguments:</td>
            <td><form:textarea path="commandLineArguments" cols="80" rows="4"/></td>
            <td><form:errors path="commandLineArguments"/></td>
        </tr>
        <tr>
            <td>Setup script:</td>
            <td><form:textarea path="setupScript" cols="80" rows="4"/></td>
            <td><form:errors path="setupScript"/></td>
        </tr>
        <tr>
            <td>Cleanup script:</td>
            <td><form:textarea path="cleanupScript" cols="80" rows="4"/></td>
            <td><form:errors path="cleanupScript"/></td>
        </tr>
    </table>
    <input type="submit" value="Save"/>
</form:form>

<a href="<%=request.getContextPath()%><%=URL_BENCHMARK_PARAMETER%>?suite=${suite.id}">Back</a>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>

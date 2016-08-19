<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<c:choose>
    <c:when test="${not empty script.id}">
        <h2>Edit script for software <c:out value="${version.softwareName}"/>, version <c:out value="${version.name}"/></h2>
    </c:when>
    <c:otherwise>
        <h2>Add new script for software <c:out value="${version.softwareName}"/>, version <c:out value="${version.name}"/></h2>
    </c:otherwise>
</c:choose>

<form:form method="post" commandName="script">
    <form:hidden path="id"/>
    <table>
        <tr>
            <td>Type:</td>
            <td><form:select path="type" items="<%=sk.mimac.benchroom.api.enums.ScriptType.values()%>"/></td>
            <td><form:errors path="type"/></td>
        </tr>
        <tr>
            <td>Platforms:</td>
            <td><form:checkboxes path="supportedPlatforms" items="<%=sk.mimac.benchroom.api.enums.Platform.values()%>"/></td>
            <td><form:errors path="supportedPlatforms"/></td>
        </tr>
        <tr>
            <td>Script data:</td>
            <td><form:textarea path="scriptData"/></td>
            <td><form:errors path="scriptData"/></td>
        </tr>
    </table>
    <input type="submit" value="Save"/>
</form:form>

<a href="<%=request.getContextPath()%><%=URL_SCRIPT%>?version=${version.id}">Back</a>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>

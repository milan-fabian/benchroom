<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<h2>Benchmark executors</h2>

<table id="suite" cellspacing="0">
    <tr>
        <th>System parameters</th>
    </tr>
    <c:forEach items="${executors}" var="executor">
        <tr>
            <td><c:out value="${executor.systemParameters}"/></td>
            <td><a href="<%=request.getContextPath()%><%=URL_EXECUTOR_DETAIL%>?executor=${executor.id}">Detail</a></td>
        </tr>
    </c:forEach>
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>

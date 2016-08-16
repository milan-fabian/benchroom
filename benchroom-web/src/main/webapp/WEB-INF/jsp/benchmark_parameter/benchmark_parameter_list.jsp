<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<script>
    $(document).ready(function () {
        $('#parameter').DataTable({
            ajax: '<%=request.getContextPath()%><%=URL_BENCHMARK_PARAMETER_LIST%>?suite=${suite.id}',
            dataSrc: "tableData",
            serverSide: true,
            columns: [
                {title: "Name", data: "name"},
                {title: "Command line arguments", data: "commandLineArguments"},
                {sortable: false, render: function (data, type, row) {
                        return "<a href='<%=request.getContextPath()%><%=URL_BENCHMARK_PARAMETER_EDIT%>?parameter=" + row.id + "&suite=${suite.id}'>Edit</a>";
                    }
                }
            ]
        });
    });
</script>

<h2>Benchmark parameter for <c:out value="${suite.name}"/> (software <c:out value="${suite.softwareName}"/>)</h2>

<a href="<%=request.getContextPath()%><%=URL_BENCHMARK_PARAMETER_EDIT%>?suite=${suite.id}">Add new parameter</a>
<table id="parameter" cellspacing="0">
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>

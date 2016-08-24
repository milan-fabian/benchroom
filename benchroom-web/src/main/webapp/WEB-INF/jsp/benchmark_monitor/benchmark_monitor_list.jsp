<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<script>
    $(document).ready(function () {
        $('#monitor').DataTable({
            ajax: '<%=request.getContextPath()%><%=URL_BENCHMARK_MONITOR_LIST%>?suite=${suite.id}',
                        dataSrc: "tableData",
                        serverSide: true,
                        columns: [
                            {title: "Name", data: "name"},
                            {title: "Type", data: "type"},
                            {sortable: false, render: function (data, type, row) {
                                    return "<a href='<%=request.getContextPath()%><%=URL_BENCHMARK_MONITOR_EDIT%>?monitor=" + row.id + "&suite=${suite.id}'>Edit</a>";
                                }
                            }
                        ]
                    });
                });
</script>

<h2>Benchmark monitors for suite <c:out value="${suite.name}"/></h2>

<a href="<%=request.getContextPath()%><%=URL_BENCHMARK_MONITOR_EDIT%>?suite=${suite.id}">Add new monitor</a>
<table id="monitor" cellspacing="0">
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>

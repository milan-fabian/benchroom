<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<script>
    $(document).ready(function () {
        $('#suite').DataTable({
            ajax: '<%=request.getContextPath()%><%=URL_BENCHMARK_SUITE_LIST%>?software=${software.id}',
                        dataSrc: "tableData",
                        serverSide: true,
                        columns: [
                            {title: "Name", data: "name"},
                            {sortable: false, render: function (data, type, row) {
                                    return "<a href='<%=request.getContextPath()%><%=URL_BENCHMARK_PARAMETER%>?suite=" + row.id + "'>Parameters</a> "
                                            + "<a href='<%=request.getContextPath()%><%=URL_BENCHMARK_MONITOR%>?suite=" + row.id + "'>Monitors</a> "
                                            + "<a href='<%=request.getContextPath()%><%=URL_BENCHMARK_SUITE_EDIT%>?suite=" + row.id + "&software=${software.id}'>Edit</a>";
                                }
                            }
                        ]
                    });
                });
</script>

<h2>Benchmark suites for <c:out value="${software.name}"/></h2>

<a href="<%=request.getContextPath()%><%=URL_BENCHMARK_SUITE_EDIT%>?software=${software.id}">Add new suite</a>
<table id="suite" cellspacing="0">
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>

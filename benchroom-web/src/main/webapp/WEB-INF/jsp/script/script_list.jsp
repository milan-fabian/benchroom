<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<script>
    $(document).ready(function () {
        $('#script').DataTable({
            ajax: '<%=request.getContextPath()%><%=URL_SCRIPT_LIST%>?version=${version.id}',
                        dataSrc: "tableData",
                        serverSide: true,
                        columns: [
                            {title: "Type", data: "type"},
                            {title: "Platforms", data: "supportedPlatforms"},
                            {sortable: false, render: function (data, type, row) {
                                    return "<a href='<%=request.getContextPath()%><%=URL_SCRIPT_EDIT%>?script=" + row.id + "&version=${version.id}'>Edit</a>";
                                }
                            }
                        ]
                    });
                });
</script>

<h2>Scripts for software <c:out value="${version.softwareName}"/>, version <c:out value="${version.name}"/></h2>

<a href="<%=request.getContextPath()%><%=URL_SCRIPT_EDIT%>?version=${version.id}">Add new script</a>
<table id="script" cellspacing="0">
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>

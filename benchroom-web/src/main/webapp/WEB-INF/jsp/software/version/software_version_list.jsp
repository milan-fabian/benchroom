<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<script>
    $(document).ready(function () {
        $('#version').DataTable({
            ajax: '<%=request.getContextPath()%><%=URL_SOFTWARE_VERSION_LIST%>?software=${software.id}',
                        dataSrc: "tableData",
                        serverSide: true,
                        columns: [
                            {title: "Name", data: "name"},
                            {title: "Release date", data: "releaseDate"},
                            {sortable: false, render: function (data, type, row) {
                                    return "<a href='<%=request.getContextPath()%><%=URL_SCRIPT%>?version=" + row.id + "'>Scripts</a> "
                                            + "<a href='<%=request.getContextPath()%><%=URL_SOFTWARE_VERSION_EDIT%>?version=" + row.id + "&software=${software.id}'>Edit</a>";
                                }
                            }
                        ]
                    });
                });
</script>

<h2>Software versions for <c:out value="${software.name}"/></h2>

<a href="<%=request.getContextPath()%><%=URL_SOFTWARE_VERSION_EDIT%>?software=${software.id}">Add new version</a>
<table id="version" cellspacing="0" width="100%">
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>

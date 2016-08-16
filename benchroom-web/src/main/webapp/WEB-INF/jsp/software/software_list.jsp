<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<script>
    $(document).ready(function () {
        var table = $('#software').DataTable({
            ajax: '<%=request.getContextPath()%><%=URL_SOFTWARE_LIST%>',
            dataSrc: "tableData",
            serverSide: true,
            columns: [
                {title: "Name", data: "name"},
                {sortable: false, render: function (data, type, row) {
                        return "<a href='<%=request.getContextPath()%><%=URL_SOFTWARE_VERSION%>?software=" + row.id + "'>Versions</a> "
                                + "<a href='<%=request.getContextPath()%><%=URL_SOFTWARE_EDIT%>?software=" + row.id + "'>Edit</a>";
                    }
                }
            ]
        });

        $('#software tbody').on('click', 'button', function () {
            var data = table.row($(this).parents('tr')).data();
            alert(data[0] + "'s salary is: " + data[ 5 ]);
        });
    });
</script>

<h2>Software</h2>

<a href="<%=request.getContextPath()%><%=URL_SOFTWARE_EDIT%>">Add new software</a>
<table id="software" cellspacing="0" width="100%">
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<script>
    function reloadGraph() {
        var count = $("input[name=monitors]:checked").length;
        if (count === 1 || count === 2) {
            var formData = $("#data").serialize();
            $.get("<%=request.getContextPath()%><%=URL_BENCHMARK_COMPARE_GRAPH%>?" + formData, function (data) {
                showGraph(data, count);
            });
        }
    }

    var chart;
    function showGraph(data, count) {
        var monitors = $("input[name=monitors]:checked");
        var ctx = document.getElementById("chart");
        if (chart) {
            chart.destroy();
        }
        if (count === 2) {
            chart = new Chart(ctx, {
                type: 'bubble',
                data: {
                    datasets: data
                },
                options: {
                    animation: false,
                    scales: {
                        xAxes: [{
                                scaleLabel: {
                                    display: true,
                                    labelString: $("label[for=" + monitors[0].id + "]").html()
                                }
                            }],
                        yAxes: [{
                                scaleLabel: {
                                    display: true,
                                    labelString: $("label[for=" + monitors[1].id + "]").html()
                                }
                            }]
                    },
                    tooltips: {
                        callbacks: {
                            label: function (tooltipItem, data) {
                                var dataPoint = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                                return dataPoint.title;
                            }
                        }
                    }
                }
            });
        } else {
            chart = new Chart(ctx, {
                type: 'horizontalBar',
                data: {
                    labels: data.labels,
                    datasets: [{
                            label: $("label[for=" + monitors[0].id + "]").html(),
                            backgroundColor: "rgba(54, 162, 235, 0.9)",
                            data: data.values
                        }]
                },
                options: {
                    animation: false,
                    scales: {
                        xAxes: [{
                                ticks: {beginAtZero: true}
                            }],
                        yAxes: [{
                                ticks: {beginAtZero: true}
                            }]
                    }
                }
            });
        }
    }

    function excelExport() {
        var iframe = $("<iframe style='display:none;'></iframe>").appendTo("body");
        var data = $("#data").serialize();
        iframe.attr("src", "<%=request.getContextPath()%><%=URL_BENCHMARK_COMPARE_EXCEL%>?" + data);
    }


</script>

<h2>
    Benchmark run of <b><c:out value="${run.softwareVersion.softwareName}" /></b> (ver. <c:out value="${run.softwareVersion.name}"/>)
    for <b><c:out value="${suite.name}"/></b>
</h2>

<a href='javascript:void(0);' onclick='showDialog("Benchmark run", "<%=request.getContextPath()%><%=URL_BENCHMARK_DETAIL%>?run=${run.id}");'>Details</a>    
<a href='javascript:void(0);' onclick='excelExport();'>Export to Excel</a>   
<br>
<form>
    <input type="hidden" name="run" value="${run.id}"/>
    <c:set var="previousParam" value="${parameters[0]}"/>
    <c:forEach items="${parameters}" var="parameter">
        <c:if test="${parameter.position != previousParam.position}">
            <br>
        </c:if>
        <input type="checkbox" name="parameters" value="${parameter.id}" id="param-${parameter.id}" <c:if test="${choosenParameters.contains(parameter.id)}">checked</c:if>>
        <label for="param-${parameter.id}">${parameter.name}</label>
        <c:set var="previousParam" value="${parameter}"/>
    </c:forEach>
    <input type="submit" value="Show"/>
</form>
<br>
<div style="display: inline-block;vertical-align: top">
    <form id="data">
        <table border="1" cellspacing="0" class="compareTable">
            <thead>
                <tr>
                    <th></th>
                        <c:forEach items="${run.benchmarkParameters}" var="parameter">
                        <th></th>
                        </c:forEach>
                        <c:forEach items="${run.results}" var="result">
                        <th colspan="2">
                            <label for="monitor-${result.monitorId}">${result.monitorName}</label>
                            <input type="checkbox" name="monitors" value="${result.monitorId}" checked onchange="reloadGraph();" id="monitor-${result.monitorId}"/>
                        </th>
                    </c:forEach>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${sameSystem}" var="run2">
                    <tr <c:if test="${run2.id == run.id}">style="background-color:#eee"</c:if>>
                            <td>
                                <input type="checkbox" name="runs" value="${run2.id}" checked onchange="reloadGraph();"/>
                            <c:if test="${run2.id != run.id}">
                                <a href="<%=request.getContextPath()%><%=URL_BENCHMARK_COMPARE%>?run=${run2.id}">&#x2750;</a>
                            </c:if>
                        </td>
                        <c:forEach items="${run2.benchmarkParameters}" var="parameter">
                            <td <c:if test="${run.benchmarkParameters.contains(parameter)}">style="font-weight: bold;"</c:if>>${parameter.name}</td>
                        </c:forEach>
                        <c:forEach items="${run2.results}" var="result" varStatus="i">
                            <td><b:printRunResult runResult="${result}"/></td>
                            <td><b:formatPercent percentage="${result.result / run.results[i.index].result}"/></td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>
        <div>Group on graph by parameter position:
            <c:forEach begin="0" end="${suite.parameterPositions - 1}" step="1" varStatus="i">
                <input type="radio" name="groupBy" value="${i.index}" id="groupBy-${i.index}"/><label for="groupBy-${i.index}">${i.index}</label>
            </c:forEach>
        </div>
    </form>
</div>

<canvas id="chart" width="400" height="180" style="margin: 10px; max-width: 95%"></canvas>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="image/svg+xml" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/benchroom.tld" prefix="b"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:formatNumber  var="runSpacing" value="${height / sameSystem.size()}" maxFractionDigits="0"/>
<c:set var="resultSpacing" value="${height / (sameSystem.size() * (run.results.size() + 1))}"/>
<c:set var="colors" value="${['#f1595f', '#79c36a', '#599ad3', '#f9a65a', '#9e66ab', '#cd7058', '#727272', '#d77fb3']}"/>

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="${width}" height="${height}" onload="init(evt)">
    <script xlink:href="http://d3js.org/d3.v3.min.js"></script>
    <script>
        function wrap(text, width) {
            text.each(function () {
                var text = d3.select(this);
                var words = text.text().split(/\s+/).reverse();
                var word;
                var line = [];
                var lineNumber = 0;
                var y = text.attr("y");
                words.pop();
                var tspan = text.text(null);
                while (word = words.pop()) {
                    line.push(word);
                    tspan.text(line.join(" "));
                    if (tspan.node().getComputedTextLength() > width) {
                        line.pop();
                        tspan.text(line.join(" "));
                        line = [word];
                        tspan = text.append("tspan").attr("x", 0).attr("y", y).attr("dy", ++lineNumber * 14 + "px");
                    }
                }
                var newY = +y + ${runSpacing / 2} - ((lineNumber * 14) / 2);
                text.attr("y", newY);
                tspan.attr("y", newY);
            });
        }

        function init(evt) {
            if (window.svgDocument == null) {
                svgDocument = evt.target.ownerDocument;
            }
            d3.selectAll(".yAxis").call(wrap, 200);
        }
        function showTooltip(evt, tooltip) {
            var tooltip = svgDocument.getElementById(tooltip);
            tooltip.setAttributeNS(null, "x", evt.clientX + 12);
            tooltip.setAttributeNS(null, "y", evt.clientY + 10);
            tooltip.setAttributeNS(null, "visibility", "visible");
        }

        function hideTooltip(tooltip) {
            svgDocument.getElementById(tooltip).setAttributeNS(null, "visibility", "hidden");
        }
    </script>

    <line x1="200" y1="0" x2="200" y2="${height}" style="stroke:black;stroke-width:2"/>
    <c:forEach items="${sameSystem}" var="run2" varStatus="i">

        <text class="yAxis" x="0" y="${i.index * runSpacing + 5}" width="200" height="${runSpacing - 4}" dy="0">
            <c:forEach items="${run2.benchmarkParameters}" var="parameter">
                ${parameter.name}, 
            </c:forEach>
        </text>

        <c:forEach items="${run2.results}" var="result" varStatus="y">
            <c:set var="id" value="graph-${i.index}-${y.index}"/>
            <rect id="${id}" style="fill:${colors[y.index]};" x="201" y="${i.index * runSpacing + (y.index + 0.5) * resultSpacing}" 
                  height="${resultSpacing}" width="${result.result * (width - 201) / maxResults[y.index]}" 
                  onmousemove="showTooltip(evt, '${id}-tooltip')" onmouseout="hideTooltip('${id}-tooltip')"/>
        </c:forEach> 
    </c:forEach> 

    <c:forEach items="${sameSystem}" var="run2" varStatus="i">
        <c:forEach items="${run2.results}" var="result" varStatus="y">
            <c:set var="id" value="graph-${i.index}-${y.index}"/>
            <text id="${id}-tooltip" width="400" height="150" visibility="hidden">
                ${result.monitorName}: <b:printRunResult runResult="${result}"/>
            </text>
        </c:forEach>
    </c:forEach> 
</svg>
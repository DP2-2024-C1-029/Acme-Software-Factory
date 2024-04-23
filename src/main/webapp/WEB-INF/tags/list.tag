<%--
- list.tag
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@tag import="
	java.util.Collection,
	java.util.ArrayList,
	java.util.Map,
	acme.client.helpers.JspHelper"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<%@attribute name="navigable" required="false" type="java.lang.Boolean"%>

<jstl:if test="${navigable == null}">
	<jstl:set var="navigable" value="${true}"/>
</jstl:if>

<%
	Collection<Map<String, Object>> dataTableColumns;
	String mapName;

	dataTableColumns = new ArrayList<Map<String, Object>>();
	request.setAttribute("$data$table$columns", dataTableColumns);
%>

<table id="list" class="table table-striped table-condensed table-hover nowrap w-100">
	<jsp:doBody var="body"/>
	<thead>
		<tr>
			<th class="control"><%-- HINT: this is a placeholder for the (+) button --%></th>
			<jstl:set var="aoColumns" value="'aoColumns': [ {'bSortable': false}"/>			
			<jstl:forEach var="column" items="${$data$table$columns}">				
				<jstl:choose>
					<jstl:when test="${column.type == 'data'}"> 
						<th style="width: ${column.width}">
							<jstl:if test="${column.code != null}">
								<acme:message code="${column.code}"/>
							</jstl:if>
							<jstl:set var="aoColumns" value="${aoColumns}, {'bSortable': ${column.sortable}, 'bSearchable': true, 'bVisible': true}"/>
						</th>
					</jstl:when>
					<jstl:when test="${column.type == 'payload'}">
						<th>														
							<jstl:set var="aoColumns" value="${aoColumns}, {'bSortable': false, 'bSearchable': true, 'bVisible': false}"/>
						</th>
					</jstl:when>					
				</jstl:choose>
			</jstl:forEach>
			<jstl:set var="aoColumns" value="${aoColumns} ]"/>
		</tr>
	</thead>
	<tbody>
		<jstl:if test="${$number$data >= 1}">			
			<jstl:forEach var="index" begin="${0}" end="${$number$data - 1}">
				<jstl:set var="index_id" value="id[${index}]"/>				
				<tr data-item-id="${requestScope[index_id]}">
					<td class="control"><%-- Placeholder for details button --%></td>
					<jstl:forEach var="column" items="${$data$table$columns}">
						<jstl:choose>
							<jstl:when test="${column.type == 'data'}">
								<jstl:set var="path" value="${column.path}"/> 
								<jstl:set var="format" value="${column.format}"/>
								<jstl:set var="index_path" value="${path}[${index}]"/>
								<jstl:set var="dataSort" value="${JspHelper.computeDataSort(requestScope[index_path])}"/>	
								<jstl:set var="dataText" value="${JspHelper.computeDataText(requestScope[index_path], format)}"/>
							 	<td ${dataSort}>
							 		<acme:print value="${dataText}"/>							
								</td>
							</jstl:when>
							<jstl:when test="${column.type == 'payload'}">
								<jstl:set var="path" value="${column.path}"/> 
								<jstl:set var="format" value="${column.format}"/>
								<jstl:set var="index_path" value="${path}[${index}]"/>
								<jstl:set var="dataText" value="${JspHelper.computeDataText(requestScope[index_path], format)}"/>
								<td>
									<acme:print value="${dataText}"/>							
								</td>
							</jstl:when>
						</jstl:choose>
					</jstl:forEach>
				</tr>
			</jstl:forEach>
		</jstl:if>
	</tbody>
</table>

<acme:return/>

<acme:message var="$i18n$File$Uri" code="default.datatables.language"/>
<jstl:set var="language" value="'language': { 'url': getAbsoluteUrl('${$i18n$File$Uri}') }"/>

<script type="text/javascript">
	$(document).ready(function() {
		var table;

		// HINT: the "retrieve" option prevents a reinitialisation exception that may happen 
		// HINT+ due to his event firing twice due to some unknown asynchrony problems.
		table = $("#list").dataTable({
				  "retrieve": true,   
				 "stateSave": true,
			  	"lengthMenu": [5, 10, 25, 50, 75, 100],
			  	"pageLength": 5,
			  	"pagingType": "numbers", 			   
			  	"responsive": { "details": { type: "column" } },
			  	"dom": "<'row'<'col'f><'col'i>><'row'<'col'tr>><'row'<'col'l><'col'p>>",
				   	 "order": [ [1, "asc"] ],					        
			<jstl:out value="${aoColumns}" escapeXml="false"/>,
			<jstl:out value="${language}" escapeXml="false"/>
	    });
		
		<jstl:if test="${navigable}">
		    $("#list tbody").on("click", "td", function () {
		    	var id, row, column, singleColumn;
		    	
		    	row = $(this).parent().parent().children().index($(this).parent());
		    	column = $(this).parent().children().index($(this));
		    	singleColumn = $(this).parent().children().length == 1;
		    			    	
		    	if (singleColumn || column >= 1) {
			    	id = $(this).parent().attr("data-item-id")
			    	if (typeof(id) != 'undefined') {	    		
			    		pushReturnUrl("<%=JspHelper.getRequestUrl(request)%>");
			    		redirect("show?id={0}".format(id));
			    	}
		    	}
	    	});
		</jstl:if>
	});	
</script>

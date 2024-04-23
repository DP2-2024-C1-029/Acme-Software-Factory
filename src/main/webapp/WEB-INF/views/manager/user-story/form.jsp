<%--
- form.jsp
-
- Copyright (C) 2024-2024 Ismael Gata Dorado.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<jstl:if test="${published != null && published == true}">
		<div style="color: green; text-align: center; text-decoration: blink;">
			<acme:message code="manager.userstory.form.label.published"/>
		</div>
	</jstl:if>
	<acme:input-textbox code="manager.userstory.form.label.title" path="title" readonly="${published == true}"/>
	<acme:input-textarea code="manager.userstory.form.label.description" path="description" readonly="${published == true}"/>
	<acme:input-integer code="manager.userstory.form.label.estimatedCost" path="estimatedCost" readonly="${published == true}"/>
	<acme:input-textarea code="manager.userstory.form.label.acceptanceCriteria" path="acceptanceCriteria" readonly="${published == true}"/>
	<acme:input-select code="manager.userstory.form.label.priority" path="priority" choices="${priorities}" readonly="${published == true}"/>
	<acme:input-url code="manager.userstory.form.label.link" path="link" readonly="${published == true}"/>

	
	<jstl:if test="${published != null}">
		<acme:show-errors path="published"/>
	</jstl:if>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && published == false}">
			<acme:submit code="manager.project.form.button.publish" action="/manager/user-story/publish"/>
			<acme:submit code="manager.userstory.form.button.update" action="/manager/user-story/update"/>
			<acme:submit code="manager.userstory.form.button.delete" action="/manager/user-story/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.userstory.form.button.create" action="/manager/user-story/create"/>
		</jstl:when>		
	</jstl:choose>	
</acme:form>

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
	
	<acme:input-textbox code="manager.project.form.label.code" path="code" readonly="${published == true}"/>
	<acme:input-textbox code="manager.project.form.label.title" path="title" readonly="${published == true}"/>
	<acme:input-textarea code="manager.project.form.label.abstractText" path="abstractText" readonly="${published == true}"/>
	<acme:input-checkbox code="manager.project.form.label.indication" path="indication" readonly="${published == true}"/>
	<acme:input-money code="manager.project.form.label.cost" path="cost" readonly="${published == true}"/>
	<acme:input-url code="manager.project.form.label.link" path="link" readonly="${published == true}"/>
	
	<jstl:if test="${published != null}">
		<acme:show-errors path="published"/>
	</jstl:if>
	
	<jstl:if test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:button code="manager.project.form.button.showUserStory" action="/manager/project-user-story/list-by-project?id=${id}"/>
	</jstl:if>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && published == false}">
			<acme:submit code="manager.project.form.button.publish" action="/manager/project/publish"/>
			<acme:submit code="manager.project.form.button.update" action="/manager/project/update"/>
			<acme:submit code="manager.project.form.button.delete" action="/manager/project/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.project.form.button.create" action="/manager/project/create"/>
		</jstl:when>
				
	</jstl:choose>
</acme:form>

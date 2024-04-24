<%--
- form.jsp
-
- Copyright (C) 2024-2024 Ismael Gata Dorado.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<%@taglib prefix="acmesf" tagdir="/WEB-INF/tags"%>


<acmesf:list navigable="false">
	<acmesf:list-column code="manager.project-user-story.list.label.title" path="userStory.title" width="25%"/>
	<acmesf:list-column code="manager.project-user-story.list.label.estimatedCost" path="userStory.estimatedCost" width="25%"/>
	<acmesf:list-column code="manager.project-user-story.list.label.priority" path="userStory.priority" width="25%"/>
	<acmesf:list-column code="manager.project-user-story.list.label.draftMode" path="published" width="25%"/>
</acmesf:list>

<acme:form>
	<acmesf:list navigable="false">
		<acmesf:list-check code="manager.project-user-story.list.label.check" path="check" width="10%"/>
		<acmesf:list-column code="manager.project-user-story.list.label.title" path="userStory.title" width="22%"/>
		<acmesf:list-column code="manager.project-user-story.list.label.estimatedCost" path="userStory.estimatedCost" width="22%"/>
		<acmesf:list-column code="manager.project-user-story.list.label.priority" path="userStory.priority" width="22%"/>
		<acmesf:list-column code="manager.project-user-story.list.label.draftMode" path="published" width="22%"/>
	</acmesf:list>
	
	<jstl:if test="${_command == 'list-by-project'}">
		<acme:button code="manager.project-user-story.list.button.add" action="/manager/project-user-story/add"/>
	</jstl:if>	
</acme:form>



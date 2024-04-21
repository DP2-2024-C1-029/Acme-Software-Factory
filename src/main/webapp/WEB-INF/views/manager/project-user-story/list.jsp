<%--
- form.jsp
-
- Copyright (C) 2024-2024 Ismael Gata Dorado.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<acme:list>
	<acme:list-column code="manager.project-user-story.list.label.title" path="userStory.title" width="10%"/>
	<acme:list-column code="manager.project-user-story.list.label.description" path="userStory.description" width="10%"/>
	<acme:list-column code="manager.project-user-story.list.label.estimatedCost" path="userStory.estimatedCost" width="40%"/>
	<acme:list-column code="manager.project-user-story.list.label.acceptanceCriteria" path="userStory.acceptanceCriteria" width="10%"/>
	<acme:list-column code="manager.project-user-story.list.label.priority" path="userStory.priority" width="10%"/>
	<acme:list-column code="manager.project-user-story.list.label.link" path="userStory.link" width="10%"/>
	<acme:list-column code="manager.project-user-story.list.label.draftMode" path="userStory.draftMode" width="10%"/>
		
</acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="manager.project-user-story.list.button.add" action="/manager/project-user-story/add"/>
</jstl:if>	
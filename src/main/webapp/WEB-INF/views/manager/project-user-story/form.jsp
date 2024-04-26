<%--
- form.jsp
-
- Copyright (C) 2024-2024 Ismael Gata Dorado.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="manager.project-user-story.form.label.codeProject" path="project.code" readonly="true"/>
	<acme:input-textbox code="manager.project-user-story.form.label.titleUserStory" path="userStory.title" readonly="true"/>

	<jstl:choose>
		<jstl:when test="${addUH == null && published != null && published == false}">
			<acme:submit code="manager.project-user-story.form.button.remove" action="/manager/project-user-story/delete"/>
		</jstl:when>
		<jstl:when test="${addUH == true && published != null && published == false}">
			<acme:hidden-data path="projectId"/>
			<acme:hidden-data path="userStoryId"/>
			<acme:submit code="manager.project-user-story.form.button.add" action="/manager/project-user-story/add"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>



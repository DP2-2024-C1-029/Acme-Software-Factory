<%--
- form.jsp
-
- Copyright (C) 2024-2024 Ismael Gata Dorado.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.project-user-story.list.label.codeProject" path="code" width="20%"/>
	<acme:list-column code="manager.project-user-story.list.label.title" path="title" width="20%"/>
	<acme:list-column code="manager.project-user-story.list.label.estimatedCost" path="estimatedCost" width="20%"/>
	<acme:list-column code="manager.project-user-story.list.label.priority" path="priority" width="20%"/>
	<acme:list-column code="manager.project-user-story.list.label.draftMode" path="published" width="20%"/>
</acme:list> 

<div style="float: right">
	<acme:button code="default.label.return" action="##"/>
</div>
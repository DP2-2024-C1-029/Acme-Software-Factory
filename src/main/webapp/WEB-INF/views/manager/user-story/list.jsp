<%--
- form.jsp
-
- Copyright (C) 2024-2024 Ismael Gata Dorado.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.userstory.list.label.title" path="title" width="20%"/>
	<acme:list-column code="manager.userstory.list.label.estimatedCost" path="estimatedCost" width="20%"/>
	<acme:list-column code="manager.userstory.list.label.priority" path="priority" width="20%"/>
	<acme:list-column code="manager.userstory.list.label.draftMode" path="published" width="20%"/>
</acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="manager.userstory.list.button.create" action="/manager/user-story/create"/>
</jstl:if>	

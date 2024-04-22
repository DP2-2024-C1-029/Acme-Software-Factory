<%--
- form.jsp
-
- Copyright (C) 2024-2024 Ismael Gata Dorado.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.project.list.label.code" path="code" width="10%"/>
	<acme:list-column code="manager.project.list.label.title" path="title" width="10%"/>
	<acme:list-column code="manager.project.list.label.abstractText" path="abstractText" width="40%"/>
	<acme:list-column code="manager.project.list.label.indication" path="indication" width="10%"/>
	<acme:list-column code="manager.project.list.label.cost" path="cost" width="10%"/>
	<acme:list-column code="manager.project.list.label.link" path="link" width="10%"/>
	<acme:list-column code="manager.project.list.label.draftMode" path="published" width="10%"/>
		
</acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="manager.project.list.button.create" action="/manager/project/create"/>
</jstl:if>	
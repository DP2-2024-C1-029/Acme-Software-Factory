<%--
- form.jsp
-
- Copyright (C) 2024-2024 Ismael Gata Dorado.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.claim.list.label.code" path="code" width="10%"/>
	<acme:list-column code="authenticated.claim.list.label.instantiationMoment" path="instantiationMoment" width="10%"/>
	<acme:list-column code="authenticated.claim.list.label.heading" path="heading" width="10%"/>
	<acme:list-column code="authenticated.claim.list.label.description" path="description" width="40%"/>
	<acme:list-column code="authenticated.claim.list.label.department" path="department" width="10%"/>
	<acme:list-column code="authenticated.claim.list.label.emailAddress" path="emailAddress" width="10%"/>
	<acme:list-column code="authenticated.claim.list.label.link" path="link" width="10%"/>	
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="authenticated.claim.list.button.create" action="/authenticated/claim/publish"/>
</jstl:if>	
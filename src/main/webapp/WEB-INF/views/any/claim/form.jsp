<%--
- form.jsp
-
- Copyright (C) 2024-2024 Ismael Gata Dorado.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.claim.form.label.code" path="code"/>
	<acme:input-textbox code="authenticated.claim.form.label.heading" path="heading"/>
	<acme:input-textarea code="authenticated.claim.form.label.description" path="description"/>
	<acme:input-textbox code="authenticated.claim.form.label.department" path="department"/>
	<acme:input-email code="authenticated.claim.form.label.emailAddress" path="emailAddress"/>
	<acme:input-url code="authenticated.claim.form.label.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'publish' }">
			<acme:input-checkbox code="authenticated.claim.form.label.confirmation" path="confirmation"/>
			<acme:submit code="authenticated.claim.form.button.create" action="/any/claim/publish"/>
		</jstl:when>
				
	</jstl:choose>
</acme:form>

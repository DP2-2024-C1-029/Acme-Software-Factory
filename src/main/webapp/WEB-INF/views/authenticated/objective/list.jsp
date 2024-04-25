<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.objective.list.label.title" path="title" width="20%"/>
	<acme:list-column code="authenticated.objective.list.label.priority" path="priority" width="30%"/>
	<acme:list-column code="authenticated.objective.list.label.instantiation-moment" path="instantiationMoment" width="30%"/>	
	<acme:list-column code="authenticated.objective.list.label.is-critical" path="isCritical" width="20%"/>
</acme:list>

<acme:check-access test="hasRole('Administrator')">
	<acme:button code="administrator.objective.form.button.create" action="/administrator/objective/create"/>
</acme:check-access>

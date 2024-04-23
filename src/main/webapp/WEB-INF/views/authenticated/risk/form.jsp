<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.risk.form.label.reference" path="reference"/>
	<acme:input-moment code="authenticated.risk.form.label.identificationDate" path="identificationDate"/>
	<acme:input-double code="authenticated.risk.form.label.impact" path="impact"/>
	<acme:input-double code="authenticated.risk.form.label.probability" path="probability"/>
	<acme:input-textarea code="authenticated.risk.form.label.description" path="description"/>
	<acme:input-url code="authenticated.risk.form.label.link" path="link"/>
	<acme:input-double code="authenticated.risk.form.label.riskValue" path="riskValue"/>
	
	<acme:submit test="${_command == 'create'}" code="authenticated.risk.form.button.create" action="/authenticated/risk/create"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.risk.form.button.update" action="/authenticated/risk/update"/>
</acme:form>
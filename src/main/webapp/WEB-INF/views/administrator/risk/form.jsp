<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.risk.form.label.reference" path="reference" />
	<acme:input-moment code="administrator.risk.form.label.identificationDate" path="identificationDate" />
	<acme:input-integer code="administrator.risk.form.label.impact" path="impact" />
	<acme:input-double code="administrator.risk.form.label.probability"	path="probability" />
	<acme:input-textarea code="administrator.risk.form.label.description" path="description" />
	<acme:input-url code="administrator.risk.form.label.link" path="link" />
	<acme:input-double code="administrator.risk.form.label.riskValue" path="riskValue" readonly="true" />

	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="administrator.risk.form.button.update" action="/administrator/risk/update" />
			<acme:submit code="administrator.risk.form.button.delete" action="/administrator/risk/delete" />
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="administrator.risk.form.button.create" action="/administrator/risk/create" />
		</jstl:when>
	</jstl:choose>
</acme:form>

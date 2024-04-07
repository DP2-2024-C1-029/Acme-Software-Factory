<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.codeAudit.form.label.reference" path="reference"/>
	<acme:input-moment code="auditor.codeAudit.form.label.identificationDate" path="identificationDate"/>
	<acme:input-double code="auditor.codeAudit.form.label.impact" path="impact"/>
	<acme:input-double code="auditor.codeAudit.form.label.probability" path="probability"/>
	<acme:input-textarea code="auditor.codeAudit.form.label.description" path="description"/>
	<acme:input-url code="auditor.codeAudit.form.label.link" path="link"/>
	<acme:input-double code="auditor.codeAudit.form.label.value" path="value"/>
	
	<acme:submit test="${_command == 'create'}" code="auditor.codeAudit.form.button.create" action="/auditor/codeAudit/create"/>
	<acme:submit test="${_command == 'update'}" code="auditor.codeAudit.form.button.update" action="/auditor/codeAudit/update"/>
</acme:form>

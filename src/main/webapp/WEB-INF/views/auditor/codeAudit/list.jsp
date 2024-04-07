<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.codeAudit.list.label.reference" path="reference" width="20%"/>
	<acme:list-column code="auditor.codeAudit.list.label.identificationDate" path="identificationDate" width="20%"/>
	<acme:list-column code="auditor.codeAudit.list.label.impact" path="impact" width="20%"/>
	<acme:list-column code="auditor.codeAudit.list.label.probability" path="probability" width="20%"/>
	<acme:list-column code="auditor.codeAudit.list.label.value" path="value" width="20%"/>
</acme:list>

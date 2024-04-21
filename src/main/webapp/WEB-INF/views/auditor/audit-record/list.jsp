<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.auditRecord.list.label.code" path="code" width="20%"/>
	<acme:list-column code="auditor.auditRecord.list.label.mark" path="mark" width="20%"/>
	<acme:list-column code="auditor.auditRecord.list.label.startPeriod" path="startPeriod" width="30%"/>
	<acme:list-column code="auditor.auditRecord.list.label.endPeriod" path="endPeriod" width="30%"/>
</acme:list>

<acme:button code="auditor.auditRecord.list.button.create" action="/auditor/code-audit/create"/>


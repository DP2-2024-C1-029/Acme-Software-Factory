<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.auditRecord.list.label.code" path="code" width="25%"/>
	<acme:list-column code="auditor.auditRecord.list.label.mark" path="mark" width="20%"/>
	<acme:list-column code="auditor.auditRecord.list.label.startPeriod" path="startPeriod" width="25%"/>
	<acme:list-column code="auditor.auditRecord.list.label.endPeriod" path="endPeriod" width="25%"/>
	<acme:list-column code="auditor.auditRecord.list.label.published" path="published" width="5%"/>
	
</acme:list>

<acme:button test="${showCreate}" code="auditor.auditRecord.list.button.create" action="/auditor/audit-record/create?masterId=${masterId}"/>



<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.auditRecord.form.label.code" path="code"/>
	<acme:input-moment code="auditor.auditRecord.form.label.startPeriod" path="startPeriod"/>
	<acme:input-moment code="auditor.auditRecord.form.label.endPeriod" path="endPeriod"/>
	<acme:input-select code="auditor.auditRecord.form.label.mark" path="mark" choices="${marks}"/>
	<acme:input-textbox code="auditor.auditRecord.form.label.codeAudit" path="codeAudit" readonly="true"/>
	<acme:input-checkbox code="auditor.auditRecord.form.label.draftMode" path="draftMode" readonly="true"/>
	
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="auditor.auditRecord.form.button.update" action="/auditor/audit-record/update"/>
			<acme:submit code="auditor.auditRecord.form.button.delete" action="/auditor/audit-record/delete"/>
			<acme:submit code="auditor.auditRecord.form.button.publish" action="/auditor/audit-record/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.auditRecord.form.button.create" action="/auditor/audit-record/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>

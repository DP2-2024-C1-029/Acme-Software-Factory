<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.codeAudit.form.label.code" path="code"/>
	<acme:input-moment code="auditor.codeAudit.form.label.executionDate" path="executionDate"/>
	<acme:input-select code="auditor.codeAudit.form.label.type" path="type" choices="${types}"/>
	<acme:input-textarea code="auditor.codeAudit.form.label.correctiveActions" path="correctiveActions"/>
	<acme:input-url code="auditor.codeAudit.form.label.link" path="link"/>
		<acme:input-select code="auditor.codeAudit.form.label.project" path="project" choices="${projects}"/>
	<acme:input-textbox code="auditor.codeAudit.form.label.mark" path="mark" readonly="true" placeholder="---"/>
	<acme:input-checkbox code="auditor.codeAudit.form.label.draftMode" path="draftMode" readonly="true"/>
	
	
	<jstl:choose>	 
		<jstl:when test="${draftMode == false}">
			<acme:button code="auditor.codeAudit.form.button.auditRecords" action="/auditor/audit-record/list?masterId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:button code="auditor.codeAudit.form.button.auditRecords" action="/auditor/audit-record/list?masterId=${id}"/>
			<acme:submit code="auditor.codeAudit.form.button.update" action="/auditor/code-audit/update"/>
			<acme:submit code="auditor.codeAudit.form.button.delete" action="/auditor/code-audit/delete"/>
			<acme:submit code="auditor.codeAudit.form.button.publish" action="/auditor/code-audit/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.codeAudit.form.button.create" action="/auditor/code-audit/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>

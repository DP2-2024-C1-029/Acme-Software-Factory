<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.codeAudit.form.label.code" path="code"/>
	<acme:input-moment code="auditor.codeAudit.form.label.executionDate" path="executionDate"/>
	<acme:input-double code="auditor.codeAudit.form.label.type" path="type"/>
	<acme:input-textarea code="auditor.codeAudit.form.label.correctiveActions" path="correctiveActions"/>
	<acme:input-url code="auditor.codeAudit.form.label.link" path="link"/>
	<acme:input-textbox code="auditor.codeAudit.form.label.mark" path="mark"/>
	
	
	<acme:submit test="${_command == 'create'}" code="auditor.codeAudit.form.button.create" action="/auditor/codeAudit/create"/>
	<acme:submit test="${_command == 'update'}" code="auditor.codeAudit.form.button.update" action="/auditor/codeAudit/update"/>
	
	<jstl:choose>	 
		<jstl:when test="${false}">
			<acme:button code="employer.job.form.button.duties" action="/employer/duty/list?masterId=${id}"/>			
		</jstl:when>
		<jstl:when test="${true}">
			<acme:button code="employer.job.form.button.duties" action="/employer/duty/list?masterId=${id}"/>
			<acme:submit code="employer.job.form.button.update" action="/employer/job/update"/>
			<acme:submit code="employer.job.form.button.delete" action="/employer/job/delete"/>
			<acme:submit code="employer.job.form.button.publish" action="/employer/job/publish"/>
		</jstl:when>
		<jstl:when test="${true}">
			<acme:submit code="employer.job.form.button.create" action="/employer/job/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>

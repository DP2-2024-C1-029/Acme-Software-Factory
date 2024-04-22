<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="client.progress-log.form.label.code" path="recordId" />
	<acme:input-double code="client.progress-log.form.label.completeness" path="completeness" />
	<acme:input-textbox code="client.progress-log.form.label.comment" path="comment" />
	<acme:input-moment code="client.progress-log.form.label.registrationMoment" path="registrationMoment" />
	<acme:input-textbox code="client.progress-log.form.label.responsiblePerson" path="responsiblePerson" />
	

	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')&& draftMode == true }">
			<acme:submit code="client.progress-log.form.button.update" action="/client/progress-logs/update" />
			<acme:submit code="client.progress-log.form.button.delete" action="/client/progress-logs/delete" />
			<acme:submit code="client.progress-log.form.button.publish" action="/client/progress-logs/publish" />
			
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="client.progress-log.form.button.create" action="/client/progress-logs/create?contractId=${contractId}" />
		</jstl:when>
	</jstl:choose>
</acme:form>
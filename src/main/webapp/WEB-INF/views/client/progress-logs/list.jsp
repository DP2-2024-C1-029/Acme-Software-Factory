<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>

	<acme:list-column code="client.progress-log.list.label.recordId" path="recordId" width="10%" />
	<acme:list-column code="client.progress-log.list.label.completeness" path="completeness" width="10%" />
	<acme:list-column code="client.progress-log.list.label.comment" path="comment" />
	<acme:list-column code="client.progress-log.list.label.registrationMoment" path="registrationMoment" width="10%" />
	<acme:list-column code="client.progress-log.list.label.responsiblePerson" path="responsiblePerson" width="10%" />

		
</acme:list>
<acme:button code="client.progress-log.form.button.create" action="/client/progress-logs/create?contractId=${contractId}"/>
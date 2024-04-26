<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>

	<acme:list-column code="client.progress-logs.list.label.recordId" path="recordId" width="20%" />
	<acme:list-column code="client.progress-logs.list.label.completeness" path="completeness" width="10%" />
	<acme:list-column code="client.progress-logs.list.label.comment" path="comment" width="40" />
	<acme:list-column code="client.progress-logs.list.label.registrationMoment" path="registrationMoment" width="20%" />
	<acme:list-column code="client.progress-logs.list.label.draftMode" path="draftMode" width="10%" />

		
</acme:list>
<acme:button code="client.progress-logs.form.button.create" action="/client/progress-logs/create?contractId=${contractId}"/>
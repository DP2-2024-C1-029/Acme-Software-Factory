<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="auditor.dashboard.form.title.general-indicators" />
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row"><acme:message
				code="auditor.dashboard.form.label.totalStaticCodeAudits" /></th>
		<td><acme:print value="${totalStaticCodeAudits}" /></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="auditor.dashboard.form.label.totalDynamicCodeAudits" /></th>
		<td><acme:print value="${totalDynamicCodeAudits}" /></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="auditor.dashboard.form.label.averageAuditRecords" /></th>
		<td><acme:print value="${averageAuditRecords}" /></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="auditor.dashboard.form.label.deviationAuditRecords" /></th>
		<td><acme:print value="${deviationAuditRecords}" /></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="auditor.dashboard.form.label.minimunAuditRecords" /></th>
		<td><acme:print value="${minimunAuditRecords}" /></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="auditor.dashboard.form.label.maximunAuditRecords" /></th>
		<td><acme:print value="${maximunAuditRecords}" /></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="auditor.dashboard.form.label.averageDurationAuditRecords" /></th>
		<td><acme:print value="${averageDurationAuditRecords}h" /></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="auditor.dashboard.form.label.deviationDurationAuditRecords" />
		</th>
		<td><acme:print value="${deviationDurationAuditRecords}h" /></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="auditor.dashboard.form.label.minimunDurationAuditRecords" /></th>
		<td><acme:print value="${minimunDurationAuditRecords}h" /></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="auditor.dashboard.form.label.maximunDurationAuditRecords" /></th>
		<td><acme:print value="${maximunDurationAuditRecords}h" /></td>
	</tr>
</table>
<acme:return />
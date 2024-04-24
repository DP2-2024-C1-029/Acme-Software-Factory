<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="developer.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.total-training-module-with-update-moment"/>
		</th>
		<td>
			<acme:print value="${totalTrainingModuleWithUpdateMoment}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.total-training-session-with-link"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfTrainingSessionsWithLink}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.average-time-training-module"/>
		</th>
		<td>
			<acme:print value="${averageTrainingModuleTime}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="developer.dashboard.form.label.deviation-time-training-module"/>
		</th>
		<td>
		<acme:print value="${deviationTrainingModuleTime}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="developer.dashboard.form.label.minimum-time-training-module"/>
		</th>
		<td>
		<acme:print value="${minimumTrainingModuleTime}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="developer.dashboard.form.label.maximum-time-training-module"/>
		</th>
		<td>
		<acme:print value="${maximumTrainingModuleTime}"/>
		</td>
	</tr>
</table>
<acme:return/>
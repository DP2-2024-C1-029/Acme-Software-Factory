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
			<jstl:choose>
			    <jstl:when test="${totalTrainingModuleWithUpdateMoment == 0}">
			        <acme:print value="N/A"/>
			    </jstl:when>
			    <jstl:otherwise>
			        <acme:print value="${totalTrainingModuleWithUpdateMoment}"/>
			    </jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.total-training-session-with-link"/>
		</th>
		<td>
			<jstl:choose>
			    <jstl:when test="${totalNumberOfTrainingSessionsWithLink == 0}">
			        <acme:print value="N/A"/>
			    </jstl:when>
			    <jstl:otherwise>
			        <acme:print value="${totalNumberOfTrainingSessionsWithLink}"/>
			    </jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.average-time-training-module"/>
		</th>
		<td>
			<jstl:choose>
			    <jstl:when test="${averageTrainingModuleTime == null}">
			        <acme:print value="N/A"/>
			    </jstl:when>
			    <jstl:otherwise>
			        <acme:print value="${averageTrainingModuleTime}"/>
			    </jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="developer.dashboard.form.label.deviation-time-training-module"/>
		</th>
		<td>
			<jstl:choose>
			    <jstl:when test="${deviationTrainingModuleTime == null || deviationTrainingModuleTime == 0.00}">
			        <acme:print value="N/A"/>
			    </jstl:when>
			    <jstl:otherwise>
			        <acme:print value="${deviationTrainingModuleTime}"/>
			    </jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="developer.dashboard.form.label.minimum-time-training-module"/>
		</th>
		<td>
			<jstl:choose>
			    <jstl:when test="${minimumTrainingModuleTime == 0.00}">
			        <acme:print value="N/A"/>
			    </jstl:when>
			    <jstl:otherwise>
			        <acme:print value="${minimumTrainingModuleTime}"/>
			    </jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="developer.dashboard.form.label.maximum-time-training-module"/>
		</th>
		<td>
			<jstl:choose>
			    <jstl:when test="${maximumTrainingModuleTime == 0.00}">
			        <acme:print value="N/A"/>
			    </jstl:when>
			    <jstl:otherwise>
			        <acme:print value="${maximumTrainingModuleTime}"/>
			    </jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>
</table>
<acme:return/>
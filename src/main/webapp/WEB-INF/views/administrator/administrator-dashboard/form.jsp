<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="developer.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.totalNumberOfPrincipalsWithAdministrator"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfPrincipalsWithAdministrator}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.totalNumberOfPrincipalsWithManager"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfPrincipalsWithManager}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.totalNumberOfPrincipalsWithDeveloper"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfPrincipalsWithDeveloper}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.totalNumberOfPrincipalsWithSponsor"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfPrincipalsWithSponsor}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.totalNumberOfPrincipalsWithAuditor"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfPrincipalsWithAuditor}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.totalNumberOfPrincipalsWithClient"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfPrincipalsWithClient}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.ratioOfNoticesWithEmailAndLink"/>
		</th>
		<td>
			<acme:print value="${ratioOfNoticesWithEmailAndLink}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="administrator.dashboard.form.label.ratioOfCriticalObjectives"/>
		</th>
		<td>
			<acme:print value="${ratioOfCriticalObjectives}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="administrator.dashboard.form.label.ratioOfNonCriticalObjectives"/>
		</th>
		<td>
			<acme:print value="${ratioOfNonCriticalObjectives}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.averageValueInTheRisks"/>
		</th>
		<td>
			<acme:print value="${averageValueInTheRisks}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="administrator.dashboard.form.label.deviationValueInTheRisks"/>
		</th>
		<td>
			<jstl:choose>
			    <jstl:when test="${deviationValueInTheRisks == null || deviationValueInTheRisks == 0.00}">
			        <acme:print value="N/A"/>
			    </jstl:when>
			    <jstl:otherwise>
			        <acme:print value="${deviationValueInTheRisks}"/>
			    </jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="administrator.dashboard.form.label.minValueInTheRisks"/>
		</th>
		<td>
			<acme:print value="${minValueInTheRisks}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="administrator.dashboard.form.label.maxValueInTheRisks"/>
		</th>
		<td>
			<acme:print value="${maxValueInTheRisks}"/>
		</td>
	</tr>
</table>
<acme:return/>
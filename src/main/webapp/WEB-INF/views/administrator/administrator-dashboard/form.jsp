<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="developer.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.averageValueInTheRisks"/>
		</th>
		<td>
			<jstl:choose>
			    <jstl:when test="${averageValueInTheRisks = NaN}">
			        <acme:print value="N/A"/>
			    </jstl:when>
			    <jstl:otherwise>
			        <acme:print value="${averageValueInTheRisks}"/>
			    </jstl:otherwise>
			</jstl:choose>
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
			<jstl:choose>
			    <jstl:when test="${minValueInTheRisks = NaN}">
			        <acme:print value="N/A"/>
			    </jstl:when>
			    <jstl:otherwise>
			        <acme:print value="${minValueInTheRisks}"/>
			    </jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="administrator.dashboard.form.label.maxValueInTheRisks"/>
		</th>
		<td>
			<jstl:choose>
			    <jstl:when test="${maxValueInTheRisks = NaN}">
			        <acme:print value="N/A"/>
			    </jstl:when>
			    <jstl:otherwise>
			        <acme:print value="${maxValueInTheRisks}"/>
			    </jstl:otherwise>
			</jstl:choose>
		</td>
	</tr>
</table>
<acme:return/>
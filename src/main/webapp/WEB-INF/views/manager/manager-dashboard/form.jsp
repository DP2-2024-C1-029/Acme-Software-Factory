<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<div style="color: green; text-align: center; text-decoration: blink;">
	<acme:message code="manager.dashboard.form.label.information" arguments="${currencySystem}"/>
</div>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.total-user-story-must"/>
		</th>
		<td>
			<acme:print value="${totalUserStoryMust}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.total-user-story-should"/>
		</th>
		<td>
			<acme:print value="${totalUserStoryShould}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.total-user-story-could"/>
		</th>
		<td>
			<acme:print value="${totalUserStoryCould}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.total-user-story-wont"/>
		</th>
		<td>
			<acme:print value="${totalUserStoryWont}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.average-estimated-cost"/>
		</th>
		<td>
			<jstl:choose>
				<jstl:when test="${averageEstimatedCost != null}">
					<acme:print value="${averageEstimatedCost}"/>
				</jstl:when>
				<jstl:when test="${averageEstimatedCost == null}">
					<acme:print value="-"/>
				</jstl:when>
			</jstl:choose>
		</td>
	</tr>		
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.deviation-estimated-cost"/>
		</th>
		<td>
			<jstl:choose>
				<jstl:when test="${deviationEstimatedCost != null}">
					<acme:print value="${deviationEstimatedCost}"/>
				</jstl:when>
				<jstl:when test="${deviationEstimatedCost == null}">
					<acme:print value="-"/>
				</jstl:when>
			</jstl:choose>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.minimum-estimated-cost"/>
		</th>
		<td>
			<acme:print value="${minimumEstimatedCost}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.maximum-estimated-cost"/>
		</th>
		<td>
			<acme:print value="${maximumEstimatedCost}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.average-cost-project"/>
		</th>
		<td>
			<jstl:choose>
				<jstl:when test="${averageCostProject != null}">
					<acme:print value="${averageCostProject}"/>  <acme:print value="${currencySystem[0]}"/>
				</jstl:when>
				<jstl:when test="${averageCostProject == null}">
					<acme:print value="-"/>
				</jstl:when>
			</jstl:choose>	
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.deviation-cost-project"/>
		</th>
		<td>
			<jstl:choose>
				<jstl:when test="${deviationCostProject != null}">
					<acme:print value="${deviationCostProject}"/> <acme:print value="${currencySystem[0]}"/>
				</jstl:when>
				<jstl:when test="${deviationCostProject == null}">
					<acme:print value="-"/>
				</jstl:when>
			</jstl:choose>			
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.minimum-cost-project"/>
		</th>
		<td>
			<acme:print value="${minimumCostProject} ${currencySystem[0]}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.maximum-cost-project"/>
		</th>
		<td>
			<acme:print value="${maximumCostProject} ${currencySystem[0]}"/>
		</td>
	</tr>	
</table>

<acme:return/>


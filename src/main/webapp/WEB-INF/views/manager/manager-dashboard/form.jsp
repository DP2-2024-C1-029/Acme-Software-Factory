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

<div style="text-align: center; font-weight: bold; font-size: 18px;">
	<acme:message code="manager.dashboard.form.label.hu.statistics"/>
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
</table>

<jstl:forEach var="currency" items="${acceptedCurrency}">

	<div style="text-align: center; font-weight: bold; font-size: 18px;">
		<acme:message code="manager.dashboard.form.label.project.statistics"/> ${currency}	
	</div>
	
    <table class="table table-sm">
        <tr>
            <th scope="row">
                <acme:message code="manager.dashboard.form.label.average-cost-project"/>
            </th>
            <td>
            	<jstl:choose>
					<jstl:when test="${averageCostProject[currency] != null}">
						<acme:print value="${averageCostProject[currency]}"/>  <acme:print value="${currency}"/>
					</jstl:when>
					<jstl:when test="${averageCostProject[currency] == null}">
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
					<jstl:when test="${deviationCostProject[currency] != null}">
						<acme:print value="${deviationCostProject[currency]}"/>  <acme:print value="${currency}"/>
					</jstl:when>
					<jstl:when test="${deviationCostProject[currency] == null}">
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
                <acme:print value="${minimumCostProject[currency]}"/> <acme:print value="${currency}"/>
            </td>
        </tr>   
        <tr>
            <th scope="row">
                <acme:message code="manager.dashboard.form.label.maximum-cost-project"/>
            </th>
            <td>
                <acme:print value="${maximumCostProject[currency]}"/> <acme:print value="${currency}"/>
            </td>
        </tr>
    </table>
</jstl:forEach>

<acme:return/>


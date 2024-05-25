<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="client.dashboard.form.title.budget-factors"/>
</h2>

<jstl:forEach var="currency" items="${acceptedCurrency}">

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.min-budget"/>
		</th>
		<td>
			<acme:print value="${minimumBudget[currency]}"/> <acme:print value="${currency}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.max-budget"/>
		</th>
		<td>
			<acme:print value="${maximumBudget[currency]}"/> <acme:print value="${currency}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.average"/>
		</th>
		<td>
            <jstl:choose>
				<jstl:when test="${averageBudget[currency] != null}">
					<acme:print value="${averageBudget[currency]}"/>  <acme:print value="${currency}"/>
				</jstl:when>
				<jstl:when test="${averageBudget[currency] == null}">
					<acme:print value="-"/>
				</jstl:when>
			</jstl:choose>	
       </td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.dashboard.form.label.deviation"/>
		</th>
		<td>
            <jstl:choose>
				<jstl:when test="${deviationBudget[currency] != null}">
					<acme:print value="${deviationBudget[currency]}"/>  <acme:print value="${currency}"/>
				</jstl:when>
				<jstl:when test="${deviationBudget[currency] == null}">
					<acme:print value="-"/>
				</jstl:when>
			</jstl:choose>
      </td>
	</tr>
</table>
</jstl:forEach>


<h2>
	<acme:message code="client.dashboard.form.title.logs-rate"/>
</h2>

<div>
	<canvas id="canvas"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"[0:25)%", "[25:50)%", "[50:75)%", "[0:100]%"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${numberLogsWithCompletenessBelow25}"/>, 
						<jstl:out value="${numberLogsWithCompletenessBetween25And50}"/>, 
						<jstl:out value="${numberLogsWithCompletenessBetween50And75}"/>,
						<jstl:out value="${numberLogsWithCompletenessAbove75}"/>
					]
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 10.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("canvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>

<acme:return/>
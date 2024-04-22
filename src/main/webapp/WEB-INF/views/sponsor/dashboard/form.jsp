<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="sponsor.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.total-invoices-with-tax-lower-to-21"/>
		</th>
		<td>
			<acme:print value="${totalInvoicesWithTaxLowerTo21}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.total-sponsorships-with-link"/>
		</th>
		<td>
			<acme:print value="${totalSponsorshipsWithLink}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.average-amount-sponsorships"/>
		</th>
		<td>
			<acme:print value="${averageAmountSponsorships}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="sponsor.dashboard.form.label.deviation-amount-sponsorships"/>
		</th>
		<td>
		<acme:print value="${deviationAmountSponsorships}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="sponsor.dashboard.form.label.minimum-amount-sponsorships"/>
		</th>
		<td>
		<acme:print value="${minimumAmountSponsorships}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="sponsor.dashboard.form.label.maximum-amount-sponsorships"/>
		</th>
		<td>
		<acme:print value="${maximumAmountSponsorships}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="sponsor.dashboard.form.label.average-quantity-invoices"/>
		</th>
		<td>
		<acme:print value="${averageQuantityInvoices}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="sponsor.dashboard.form.label.deviation-quantity-invoices"/>
		</th>
		<td>
		<acme:print value="${deviationQuantityInvoices}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="sponsor.dashboard.form.label.minimum-quantity-invoices"/>
		</th>
		<td>
		<acme:print value="${minimumQuantityInvoices}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
		<acme:message code="sponsor.dashboard.form.label.maximum-quantity-invoices"/>
		</th>
		<td>
		<acme:print value="${maximumQuantityInvoices}"/>
		</td>
	</tr>
</table>
<acme:return/>
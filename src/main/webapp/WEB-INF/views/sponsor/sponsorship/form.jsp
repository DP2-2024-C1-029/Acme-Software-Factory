<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="sponsor.sponsorship.form.label.code" path="code"/>
	<acme:input-moment code="sponsor.sponsorship.form.label.moment" path="moment"/>
	<acme:input-money code="sponsor.sponsorship.form.label.amount" path="amount"/>
	<acme:input-select code="sponsor.sponsorship.form.label.types" path="type" choices="${types}"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.email" path="email"/>
	<acme:input-url code="sponsor.sponsorship.form.label.link" path="link"/>
	<acme:input-moment code="sponsor.sponsorship.form.label.initialExecutionPeriod" path="initialExecutionPeriod"/>
	<acme:input-moment code="sponsor.sponsorship.form.label.endingExecutionPeriod" path="endingExecutionPeriod"/>
	<acme:input-select code="sponsor.sponsorship.form.label.projects" path="project" choices="${projects}"/>
	
	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && isPublished == true}">
			<acme:button code="sponsor.sponsorship.form.button.invoices" action="/sponsor/invoice/list?masterId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && isPublished == false}">
			<acme:button code="sponsor.sponsorship.form.button.invoices" action="/sponsor/invoice/list?masterId=${id}"/>
			<acme:submit code="sponsor.sponsorship.form.button.delete" action="/sponsor/sponsorship/delete"/>
			<acme:submit code="sponsor.sponsorship.form.button.publish" action="/sponsor/sponsorship/publish"/>
			<acme:submit code="sponsor.sponsorship.form.button.update" action="/sponsor/sponsorship/update"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="sponsor.sponsorship.form.button.create" action="/sponsor/sponsorship/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
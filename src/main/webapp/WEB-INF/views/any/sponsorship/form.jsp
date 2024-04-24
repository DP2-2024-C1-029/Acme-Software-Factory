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
</acme:form>
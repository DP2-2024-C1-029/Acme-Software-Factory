<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.objective.form.label.title" path="title"/>
	<acme:input-textarea code="administrator.objective.form.label.description" path="description"/>
	<acme:input-moment code="administrator.objective.form.label.instantiation-moment" path="instantiationMoment"/>
	<acme:input-select code="administrator.objective.form.label.priority" path="priority" choices="${priorities}"/>
	<acme:input-checkbox code="administrator.objective.form.label.is-critical" path="isCritical"/>
	<acme:input-moment code="administrator.objective.form.label.initial-execution-period" path="initialExecutionPeriod"/>
	<acme:input-moment code="administrator.objective.form.label.ending-execution-period" path="endingExecutionPeriod"/>
	<acme:input-url code="administrator.objective.form.label.link" path="link"/>
	
	<acme:input-checkbox code="administrator.objective.form.label.confirmation" path="confirmation"/>
	<acme:submit code="administrator.objective.form.button.create" action="/administrator/objective/create"/>
</acme:form>
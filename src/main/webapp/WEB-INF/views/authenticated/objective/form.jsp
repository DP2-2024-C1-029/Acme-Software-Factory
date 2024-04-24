<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.objective.form.label.title" path="title"/>
	<acme:input-textarea code="authenticated.objective.form.label.description" path="description"/>
	<acme:input-moment code="authenticated.objective.form.label.instantiation-moment" path="instantiationMoment" readonly="true"/>
	<acme:input-select code="authenticated.objective.form.label.priority" path="priority" choices="${priorities}"/>
	<acme:input-checkbox code="authenticated.objective.form.label.is-critical" path="isCritical"/>
	<acme:input-moment code="authenticated.objective.form.label.initial-execution-period" path="initialExecutionPeriod"/>
	<acme:input-moment code="authenticated.objective.form.label.ending-execution-period" path="endingExecutionPeriod"/>
	<acme:input-url code="authenticated.objective.form.label.link" path="link"/>
</acme:form>
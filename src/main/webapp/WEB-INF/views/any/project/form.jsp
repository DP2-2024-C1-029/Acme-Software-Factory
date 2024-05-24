<%--
- form.jsp
-
- Copyright (C) 2024-2024 Ismael Gata Dorado.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.project.form.label.code" path="code" readonly="${published == true}"/>
	<acme:input-textbox code="any.project.form.label.title" path="title" readonly="${published == true}"/>
	<acme:input-textarea code="any.project.form.label.abstractText" path="abstractText" readonly="${published == true}"/>
	<acme:input-checkbox code="any.project.form.label.indication" path="indication" readonly="${published == true}"/>
	<acme:input-money code="any.project.form.label.cost" path="cost" readonly="${published == true}"/>
	<jstl:if test="${acme:anyOf(_command, 'show')}">
		<acme:input-money code="any.project.form.label.internationalised-cost" path="internationalisedCost" readonly="true"/>
	</jstl:if>
	<acme:input-url code="any.project.form.label.link" path="link" readonly="${published == true}"/>

</acme:form>

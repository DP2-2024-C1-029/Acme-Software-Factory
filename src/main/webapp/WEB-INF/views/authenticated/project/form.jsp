<%--
- form.jsp
-
- Copyright (C) 2024-2024 Ismael Gata Dorado.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<%@taglib prefix="acmesf" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="authenticated.project.form.label.code" path="code" readonly="${published == true}"/>
	<acme:input-textbox code="authenticated.project.form.label.title" path="title" readonly="${published == true}"/>
	<acme:input-textarea code="authenticated.project.form.label.abstractText" path="abstractText" readonly="${published == true}"/>
	<acme:input-checkbox code="authenticated.project.form.label.indication" path="indication" readonly="${published == true}"/>
	<acme:input-money code="authenticated.project.form.label.cost" path="cost" readonly="${published == true}"/>
	<acme:input-url code="authenticated.project.form.label.link" path="link" readonly="${published == true}"/>

</acme:form>

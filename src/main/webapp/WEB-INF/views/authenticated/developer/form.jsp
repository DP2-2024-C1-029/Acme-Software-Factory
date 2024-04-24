
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<acme:form>
	<acme:input-textbox code="authenticated.developer.form.label.degree" path="degree"/>
	<acme:input-textarea code="authenticated.developer.form.label.specialisation" path="specialisation"/>
	<acme:input-textarea code="authenticated.developer.form.label.listOfSkills" path="listOfSkills"/>
	<acme:input-textbox code="authenticated.developer.form.label.link" path="link"/>
	<acme:input-textbox code="authenticated.developer.form.label.email" path="email"/>

	<acme:submit test="${_command == 'create'}" code="authenticated.develover.form.button.create" action="/authenticated/developer/create"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.developer.form.button.update" action="/authenticated/developer/update"/>
</acme:form>
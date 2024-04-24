<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.banner.form.label.currency" path="currency"/>
	<acme:input-textbox code="administrator.banner.form.label.acceptedCurrencies" path="acceptedCurrencies"/>
	
	<acme:submit code="administrator.banner.form.button.update" action="/administrator/configuration/update"/>
</acme:form>
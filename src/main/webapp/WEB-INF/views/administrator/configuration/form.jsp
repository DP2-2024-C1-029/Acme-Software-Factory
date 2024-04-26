<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.configuration.form.label.currency" path="currency"/>
	<acme:input-textbox code="administrator.configuration.form.label.acceptedCurrencies" path="acceptedCurrencies"/>
	<acme:input-textarea code="administrator.configuration.form.label.currentCurrencies" path="currentCurrencies" readonly="true"/>
	<acme:input-textarea code="administrator.configuration.form.label.currenciesFromAPI" path="currenciesFromAPI" readonly="true"/>
	
	<acme:submit code="administrator.configuration.form.button.update" action="/administrator/configuration/update"/>
</acme:form>
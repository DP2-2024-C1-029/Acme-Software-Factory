<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="client.contract.list.label.code" path="code" width="10%"/>
	<acme:list-column code="client.contract.list.label.draftMode" path="draftMode" width="10%"/>
	<acme:list-column code="client.contract.list.label.project" path="project" width="10%"/>
	<acme:list-column code="client.contract.list.label.budget" path="budget" width="10%"/>			
		
</acme:list>
<acme:button code="client.contract.form.button.create" action="/client/contract/create"/>
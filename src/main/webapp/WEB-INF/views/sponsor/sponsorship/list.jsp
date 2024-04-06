<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.sponsorship.list.label.code" path="code" width="20%"/>
	<acme:list-column code="sponsor.sponsorship.list.label.amount" path="amount" width="40%"/>
	<acme:list-column code="sponsor.sponsorship.list.label.type" path="type" width="40%"/>	
</acme:list>

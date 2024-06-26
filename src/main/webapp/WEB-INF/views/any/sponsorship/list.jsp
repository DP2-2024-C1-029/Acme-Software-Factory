<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.sponsorship.list.label.code" path="code" width="20%"/>
	<acme:list-column code="sponsor.sponsorship.list.label.amount" path="amount" width="30%"/>
	<acme:list-column code="sponsor.sponsorship.list.label.type" path="type" width="25%"/>
	<acme:list-column code="sponsor.sponsorship.list.label.projects" path="project" width="25%"/>
</acme:list>
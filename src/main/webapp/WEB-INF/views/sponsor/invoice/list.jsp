<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.invoice.list.label.code" path="code" width="20%"/>	
	<acme:list-column code="sponsor.invoice.list.label.dueDate" path="dueDate" width="30%"/>
	<acme:list-column code="sponsor.invoice.list.label.totalAmount" path="totalAmount" width="30%"/>
	<acme:list-column code="sponsor.invoice.list.label.isPublished" path="isPublished" width="20%"/>
</acme:list>

<acme:button test="${showCreate}" code="sponsor.invoice.list.button.create" action="/sponsor/invoice/create?masterId=${masterId}"/>


<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="developer.trainingmodule.list.label.code" path="code" width="20%"/>
	<acme:list-column code="developer.trainingmodule.list.label.details" path="type" width="40%"/>	
	<acme:list-column code="developer.trainingmodule.list.label." path="amount" width="40%"/>
</acme:list>


<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="developer.trainingmodule.list.label.code" path="code" width="20%"/>
	<acme:list-column code="developer.trainingmodule.list.label.details" path="details" width="40%"/>	
	<acme:list-column code="developer.trainingmodule.list.label.difficultyLevels" path="difficultyLevel" width="40%"/>
	<acme:list-column code="developer.trainingmodule.list.label.draftMode" path="draftMode"/>
	
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="developer.trainingmodule.list.button.create" action="/developer/training-module/create"/>
</jstl:if>	
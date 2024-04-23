

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="developer.trainingsession.list.label.code" path="code" width="20%"/>
	<acme:list-column code="developer.trainingsession.list.label.location" path="location" width="40%"/>	
	<acme:list-column code="developer.trainingsession.list.label.project.contactEmail" path="contactEmail" width="40%"/>
	<acme:list-column code="developer.trainingsession.list.label.draftMode" path="draftMode"/>
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="developer.trainingsession.list.button.create" action="/developer/training-session/create?masterId=${masterId}"/>
</jstl:if>	
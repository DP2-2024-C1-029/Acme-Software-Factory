<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="developer.trainingsession.list.label.code" path="code"/>
	<acme:input-moment code="developer.trainingsession.list.label.startTime" path="startTime"/>
	<acme:input-moment code="developer.trainingsession.list.label.endTime" path="endTime"/>
	<acme:input-textbox code="developer.trainingsession.list.label.location" path="location"/>
	<acme:input-textbox code="developer.trainingsession.list.label.instructor" path="instructor"/>
	<acme:input-email code="developer.trainingsession.list.label.contactEmail" path="contactEmail"/>
	<acme:input-url code="developer.trainingsession.list.label.furtherInformationLink" path="furtherInformationLink"/>	
	
	<jstl:choose>	 
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.trainingsession.form.button.create" action="/developer/training-session/create?masterId=${masterId}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')&& draftMode == true}">
			<acme:submit code="developer.trainingsession.form.button.delete" action="/developer/training-session/delete"/>
			<acme:submit code="developer.trainingsession.form.button.update" action="/developer/training-session/update"/>
			<acme:submit code="developer.trainingsession.form.button.publish" action="/developer/training-session/publish"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
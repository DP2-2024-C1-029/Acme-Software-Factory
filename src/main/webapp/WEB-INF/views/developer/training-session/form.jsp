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
	<acme:input-select code="developer.trainingsession.list.label.trainingModules" path="trainingModule" choices="${trainingModules}"/>
	
	
	<jstl:choose>	 
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.trainingsession.form.button.create" action="/developer/training-sessio/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="developer.trainingsession.form.button.delete" action="/developer/training-sessio/delete"/>
			<acme:submit code="developer.trainingsession.form.button.update" action="/developer/training-sessio/update"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
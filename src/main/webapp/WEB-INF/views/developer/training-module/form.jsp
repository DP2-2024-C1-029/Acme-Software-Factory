<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="developer.trainingmodule.list.label.creationMoment" path="creationMoment"/>
	<acme:input-textbox code="developer.trainingmodule.list.label.details" path="details"/>
	<acme:input-textbox code="developer.trainingmodule.list.label.code" path="code"/>
	<acme:input-select code="developer.trainingmodule.list.label.difficultyLevels" path="difficultyLevel" choices="${difficultyLevels}"/>
	<acme:input-moment code="developer.trainingmodule.list.label.updateMoment" path="updateMoment"/>
	<acme:input-integer code="developer.trainingmodule.list.label.estimatedTotalTime" path="estimatedTotalTime"/>
	<acme:input-url code="developer.trainingmodule.list.label.link" path="link"/>
	<acme:input-select code="developer.trainingmodule.list.label.projects" path="project" choices="${projects}"/>
	
	
	<jstl:choose>	 
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.trainingmodule.form.button.create" action="/developer/training-module/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="developer.trainingmodule.form.button.delete" action="/developer/training-module/delete"/>
			<acme:submit code="developer.trainingmodule.form.button.update" action="/developer/training-module/update"/>
			<acme:button code="master.menu.developer.all-trainingsession" action="/developer/training-session/list?masterId=${id}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
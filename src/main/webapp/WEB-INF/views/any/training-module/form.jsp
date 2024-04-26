<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="developer.trainingmodule.list.label.creationMoment" readonly="true" path="creationMoment"/>
	<acme:input-textbox code="developer.trainingmodule.list.label.details" path="details"/>
	<acme:input-textbox code="developer.trainingmodule.list.label.code" path="code"/>
	<acme:input-select code="developer.trainingmodule.list.label.difficultyLevels" path="difficultyLevel" choices="${difficultyLevels}"/>
	<acme:input-moment code="developer.trainingmodule.list.label.updateMoment" readonly="true" path="updateMoment"/>
	<acme:input-integer code="developer.trainingmodule.list.label.estimatedTotalTime" placeholder="100" path="estimatedTotalTime"/>
	<acme:input-url code="developer.trainingmodule.list.label.link" path="link"/>
	
	<acme:input-select code="developer.trainingmodule.list.label.projects" path="project" choices="${projects}"/>
</acme:form>
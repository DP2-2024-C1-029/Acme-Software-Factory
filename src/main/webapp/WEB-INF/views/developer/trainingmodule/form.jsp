<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="sponsor.sponsorship.form.label.creationMoment" path="creationMoment"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.details" path="details"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.code" path="code"/>
	<acme:input-select code="sponsor.sponsorship.form.label.difficultyLevels" path="difficultyLevel" choices="${difficultyLevels}"/>
	<acme:input-email code="sponsor.sponsorship.form.label.updateMoment" path="updateMoment"/>
	<acme:input-integer code="sponsor.sponsorship.form.label.estimatedTotalTime" path="estimatedTotalTime"/>
	<acme:input-url code="sponsor.sponsorship.form.label.link" path="link"/>
</acme:form>
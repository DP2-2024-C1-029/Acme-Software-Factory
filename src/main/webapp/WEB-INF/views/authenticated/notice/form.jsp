<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.notice.form.label.title" path="title"/>
	<acme:input-textbox code="authenticated.notice.form.label.author" path="author" readonly="true"/>
	<acme:input-textarea code="authenticated.notice.form.label.message" path="message"/>
	<acme:input-email code="authenticated.notice.form.label.email" path="email"/>
	<acme:input-url code="authenticated.notice.form.label.link" path="link"/>
	<acme:input-moment code="authenticated.notice.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'publish' }">
			<acme:input-checkbox code="authenticated.notice.form.label.confirmation" path="confirmation"/>
			<acme:submit code="authenticated.notice.list.button.create" action="/authenticated/notice/publish"/>
		</jstl:when>
				
	</jstl:choose>
</acme:form>
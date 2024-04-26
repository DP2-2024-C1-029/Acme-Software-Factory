<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.notice.form.label.title" path="title" width="20%"/>
	<acme:list-column code="authenticated.notice.form.label.author" path="author" width="20%"/>
	<acme:list-column code="authenticated.notice.form.label.message" path="message" width="60%"/>
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="authenticated.notice.form.button.create" action="/authenticated/notice/publish"/>
</jstl:if>	

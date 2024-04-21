<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.banner.list.label.slogan" path="slogan" width="20%"/>
	<acme:list-column code="administrator.banner.list.label.picture" path="picture" width="20%"/>
	<acme:list-column code="administrator.banner.list.label.displayStartMoment" path="displayStartMoment" width="30%"/>
	<acme:list-column code="administrator.banner.list.label.displayEndMoment" path="displayEndMoment" width="30%"/>	
</acme:list>

<%-- <acme:button code="administrator.banner.form.button.create" action="/administrator/banner/create"/> --%>
<%--
- menu.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.favourite-link" action="http://www.example.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.josporhue" action="https://www.samsung.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.ismgator" action="https://www.ted.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.davgodfer" action="https://www.youtube.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-juagombor" action="https://www.informatica.us.es/"/>
			<acme:menu-suboption code="master.menu.anonymous.jaivarcac" action="https://planefinder.net/"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-suboption code="master.menu.administrator.list-risks" action="/administrator/risk/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-initial" action="/administrator/system/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-sample" action="/administrator/system/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-down" action="/administrator/system/shut-down"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.provider" access="hasRole('Provider')">
			<acme:menu-suboption code="master.menu.provider.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.consumer" access="hasRole('Consumer')">
			<acme:menu-suboption code="master.menu.consumer.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.auditor" access="hasRole('Auditor')">
			<acme:menu-suboption code="master.menu.auditor.list-code-audit" action="/auditor/code-audit/list"/>
			<acme:menu-suboption code="master.menu.auditor.dashboard" action="/auditor/dashboard/show"/>
		</acme:menu-option>
			
		<acme:menu-option code="master.menu.manager" access="hasRole('Manager')">			
			<acme:menu-suboption code="master.menu.manager.my-projects" action="/manager/project/list-mine"/>
			<acme:menu-suboption code="master.menu.manager.my-user-stories" action="/manager/user-story/list-mine"/>
			<acme:menu-suboption code="master.menu.manager.dashboard" action="/manager/manager-dashboard/show"/>			
		</acme:menu-option>
  
    	<acme:menu-option code="master.menu.sponsorship" access="hasRole('Sponsor')">
			<acme:menu-suboption code="master.menu.sponsor.sponsorship.list-all" action="/sponsor/sponsorship/list"/>
			<acme:menu-suboption code="master.menu.sponsor.sponsorship.list-mine" action="/sponsor/sponsorship/list-mine"/>
			<acme:menu-suboption code="master.menu.sponsor.dashboard.show" action="/sponsor/dashboard/show"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.authenticated.claim" action="/authenticated/claim/list"/>
			<acme:menu-suboption code="master.menu.authenticated.objective.list" action="/authenticated/objective/list"/>
			<acme:menu-suboption code="master.menu.authenticated.list-risks" action="/authenticated/risk/list"/>
			<acme:menu-suboption code="master.menu.administrator.banner.list" action="/administrator/banner/list" access="hasRole('Administrator')"/>
		</acme:menu-option>
		
	</acme:menu-left>
	
	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/anonymous/system/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-provider" action="/authenticated/provider/create" access="!hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.provider" action="/authenticated/provider/update" access="hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.become-consumer" action="/authenticated/consumer/create" access="!hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.consumer" action="/authenticated/consumer/update" access="hasRole('Consumer')"/>

			<acme:menu-suboption code="master.menu.user-account.become-auditor" action="/authenticated/auditor/create" access="!hasRole('Auditor')"/>
			<acme:menu-suboption code="master.menu.user-account.auditor" action="/authenticated/auditor/update" access="hasRole('Auditor')"/>

			<acme:menu-suboption code="master.menu.user-account.become-sponsor" action="/authenticated/sponsor/create" access="!hasRole('Sponsor')"/>
			<acme:menu-suboption code="master.menu.user-account.sponsor" action="/authenticated/sponsor/update" access="hasRole('Sponsor')"/>

		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out" action="/authenticated/system/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>


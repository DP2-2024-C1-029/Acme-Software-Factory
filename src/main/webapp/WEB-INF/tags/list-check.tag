<%--
- list-column.tag
-
- Copyright (C) 2024 Ismael Gata Dorado.
--%>

<%@tag body-content="empty" import="
	java.util.Map, 
	java.util.LinkedHashMap, 
	acme.client.helpers.JspHelper"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<%@attribute name="path" required="true" type="java.lang.String"%>
<%@attribute name="code" required="true" type="java.lang.String"%>
<%@attribute name="width" required="false" type="java.lang.String"%>

<jstl:if test="${width == null}">
	<jstl:set var="width" value="inherited"/>
</jstl:if>

<div class="form-group">
	<div class="form-check">
		<input id="${path}" name="${path}" type="checkbox" class="form-check-input"/>  
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {		
		$("#${path}\\\\$proxy").on("change", function(e) {
			if ($(this).prop('checked'))
				$(this).next().val("true");
			else
				$(this).next().val("false");
		});
	})
</script>



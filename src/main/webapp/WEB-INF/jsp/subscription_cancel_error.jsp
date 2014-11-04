<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="application/xml"%>
<result> 
	<success>false</success> 
	<c:choose>
		<c:when test="${subscription}">
			<message>Account cancellation failed for ${subscription.compagny} by ${subscription.firstName} ${subscription.lastName}</message>
			<accountIdentifier>${subscription.id}</accountIdentifier>
		</c:when>
		<c:otherwise>
			    <message>Account cancellation failed</message>
		</c:otherwise>
</c:choose> </result>

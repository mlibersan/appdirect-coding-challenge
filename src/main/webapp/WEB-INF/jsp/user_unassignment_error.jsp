<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="application/xml"%>
<result> 
	<success>false</success> 
	<c:choose>
		<c:when test="${user}">
			<message>User unassigment failed for ${user.firstName} ${user.lastName}</message>
			<accountIdentifier>${user.id}</accountIdentifier>
		</c:when>
		<c:otherwise>
			    <message>User unassigment failed</message>
		</c:otherwise>
</c:choose> </result>

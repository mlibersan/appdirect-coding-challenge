<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="application/xml" %>
<result>
    <success>true</success>
    <message>Account cancellation successful for ${subscription.compagny} by ${subscription.firstName} ${subscription.lastName}</message>
    <accountIdentifier>${subscription.id}</accountIdentifier>
</result>

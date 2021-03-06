<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
<title>Martin Libersan AppDirect Coding Challenge</title>

<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">

<!--
      IMPORTANT:
      This is Heroku specific styling. Remove to customize.
    -->
<link href="http://heroku.github.com/template-app-bootstrap/heroku.css" rel="stylesheet">
<!-- /// -->

</head>

<body>

	<div class="container">
		<div class="row">
			<div class="span12">
				<div class="navbar navbar-fixed-top">
					<div class="navbar-inner">
						<div class="container">
							<a href="/" class="brand">AppDirect Coding Challenge</a> <a href="/" class="brand" id="heroku">by <strong>Martin Libersan</strong></a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="span8 offset2">
				<div class="page-header">
					<h4>User and Subscription Management</h4>
				</div>
			</div>
			<div class="row">&nbsp;</div>
			<div class="row">
				<div class="span8 offset4">
					<c:url value="/list" var="viewUserURL">
					</c:url>
					<a href="${viewUserURL}">View users</a>
				</div>
			</div>
			<div class="row">
				<div class="span8 offset4">
					<c:url value="/create" var="addUserURL">
					</c:url>
					<a href="${addUserURL}">Add user</a>
				</div>
			</div>
			<div class="row">&nbsp;</div>
			<div class="row">
				<div class="span8 offset4">
					<c:url value="/subscription/order" var="orderSubscriptionURL">
						<c:param name="endpointUrl">https://www.appdirect.com/rest/api/events/dummyOrder</c:param>
					</c:url>
					<a href="${orderSubscriptionURL}">Test order subscription</a>
				</div>
			</div>
			<div class="row">
				<div class="span8 offset4">
					<c:url value="/subscription/cancel" var="cancelSubscriptionURL">
						<c:param name="endpointUrl">https://www.appdirect.com/rest/api/events/dummyCancel</c:param>
					</c:url>
					<a href="${cancelSubscriptionURL}">Test cancel subscription</a>
				</div>
			</div>
			<div class="row">
				<div class="span8 offset4">
					<c:url value="/user/assignment" var="userAssignmentURL">
						<c:param name="endpointUrl">https://www.appdirect.com/rest/api/events/dummyAssign</c:param>
					</c:url>
					<a href="${userAssignmentURL}">Test user assignment</a>
				</div>
			</div>
			<div class="row">
				<div class="span8 offset4">
					<c:url value="/user/unassignment" var="userUnassignmentURL">
						<c:param name="endpointUrl">https://www.appdirect.com/rest/api/events/dummyUnassign</c:param>
					</c:url>
					<a href="${userUnassignmentURL}">Test user unassignment</a>
				</div>
			</div>
			<div class="row">&nbsp;</div>
			<div class="row">
				<div class="span8 offset4">
					<c:url value="/j_spring_security_logout" var="logoutURL" />
					<a href="${logoutURL}"> Logout</a>
				</div>
			</div>

		</div>
	</div>
</body>
</html>

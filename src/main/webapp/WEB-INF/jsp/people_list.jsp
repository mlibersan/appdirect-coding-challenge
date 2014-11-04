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
					<h4>User List</h4>
				</div>
			</div>
		</div>
		<div class="row">
				<c:if test="${!empty peopleList}">
					<table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>Name</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${peopleList}" var="person">
								<tr>
									<td>
										<table class="">
											<tbody>
												<tr>
													<td>Open Id :</td>
													<td>${person.openId}</td>
												</tr>
												<tr>
													<td>First Name :</td>
													<td>${person.firstName}</td>
												</tr>
												<tr>
													<td>Last Name :</td>
													<td>${person.lastName}</td>
												</tr>
											</tbody>
										</table>
									</td>
									<td>
										<c:url value="/edit" var="editUserURL">
										</c:url>
										<form action="${editUserURL}" method="post">
											<input type="hidden" name="personOpenId" value="${person.openId}" />
											<input type="submit" class="btn btn-danger btn-mini" value="Edit" />
										</form>
									</td>
									<td>
										<c:url value="/xml/${person.id}" var="xmlUserURL">
										</c:url>
										<form action="${xmlUserURL}" method="post">
											<input type="submit" class="btn btn-danger btn-mini" value="View XML" />
										</form>
									</td>
									<td>
										<c:url value="/delete/${person.id}" var="deleteUserURL">
										</c:url>
										<form action="${deleteUserURL}" method="post">
											<input type="submit" class="btn btn-danger btn-mini" value="Delete" />
										</form>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
		</div>
	</div>

</body>
</html>

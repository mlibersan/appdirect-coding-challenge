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
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a href="/" class="brand">AppDirect Coding Challenge</a> <a href="/" class="brand" id="heroku">by <strong>Martin Libersan</strong></a>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="span8 offset2">
			<div class="page-header">
				<h1>User Originating XML Request</h1>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="row">
			<pre>
				<c:out value="${formattedXml}" escapeXml="true" />
		     </pre>
		</div>
	</div>

</body>
</html>

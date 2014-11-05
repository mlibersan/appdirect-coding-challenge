<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login-box {
	width: 600px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
</style>
</head>
<body onload='document.loginForm.username.focus();'>

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a href="/" class="brand">AppDirect Coding Challenge</a> <a href="/" class="brand" id="heroku">by <strong>Martin Libersan</strong></a>
			</div>
		</div>
	</div>

	<div id="login-box">

		<h3>Login with OpenId URL</h3>

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>

		<form name='loginForm' action="<c:url value='j_spring_openid_security_check' />" method='POST'>

			<table>
				<tr>
					<td>${openIdUrl}<input id="openid_identifier" type='hidden' name='openid_identifier' value='${openIdUrl}' maxlength="100" size="100"></td>
				</tr>
				<tr>
					<td colspan='2'>&nbsp;</td>
				</tr>
				<tr>
					<td colspan='2'><input name="submit" type="submit" value="Sign On" /></td>
				</tr>
			</table>

		</form>
	</div>

</body>
</html>
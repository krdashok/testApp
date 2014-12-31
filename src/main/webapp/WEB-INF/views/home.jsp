<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Welcome to Test uploader
</h1>

	<div>
		<h3>Choose File to Upload in Server</h3>
		<form action="/core/uploadservice.do" method="post" enctype="multipart/form-data">
			<input type="file" name="ufile" /> <input type="submit"
				value="upload" />
			<input type="hidden" name="action" value="upload"/>
		</form>
	</div>
	<br></br>
	<div>
		<h3>Enter image ID to get an image</h3>
		<form action="/core/uploadservice.do" method="post"
			enctype="multipart/form-data">
			<input type="text" name="id" /> <input type="submit" value="View" />
			<input type="hidden" name="action" value="dowload"/>
		</form>
	</div>


	<P>  The time on the server is ${serverTime}. </P>
</body>
</html>

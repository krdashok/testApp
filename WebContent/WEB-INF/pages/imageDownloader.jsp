<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Image downloader</title>
</head>
<body>
	<div>
		<h3>Choose File to Upload in Server</h3>
		<form action="imageUploader" method="get">
			<input type="file" name="ufile" /> 
			<input type="submit" value="upload" />
		</form>
	</div>

<br></br>
	<div>
		<h3>Enter image ID to get an image</h3>
		<form action="imageUploader" method="get" enctype="multipart/form-data">
			<input type="text" name="id" /> 
			<input type="submit" value="View" />
		</form>
	</div>

</body>
</html>
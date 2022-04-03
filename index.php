<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>botted</title>
	<meta name="description" content="botted app">
	<meta name="author" content="">
	<link rel="icon" href="/images/favicon.ico">
	<link rel="icon" href="/images/favicon.svg" type="image/svg+xml">
	<link rel="apple-touch-icon" href="/images/apple-touch-icon.png">
	<link rel="stylesheet" href="/css/stylesheet.css">
</head>
<body>
	<div class="top"><img src="/images/botted.png" width="140px"></div>
	<div class="content"><img src="/images/bot.png" width="200px" align="center"></div>
	<form action="/action.php" id="form">
		<input type="text" id="uname" name="uname" placeholder="Username"><br><br>
		<input type="submit" value="Submit">
	</form>
	<?php
		$output = passthru("python botted.py poopy");
		echo $output;
	?>
</body>
</html>

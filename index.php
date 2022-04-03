<?php
$output = "";
if(isset($_POST['submit'])){ //check if form was submitted
  $input = $_POST['uname']; //get input text
  $output = passthru("python botted.py " . $input);
}    
?>
<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>botted</title>
	<meta name="description" content="botted app">
	<meta name="author" content="">
	<link rel="apple-touch-icon" sizes="180x180" href="/images/favicon/apple-touch-icon.png">
	<link rel="icon" type="image/png" sizes="32x32" href="/images/favicon/favicon-32x32.png">
	<link rel="icon" type="image/png" sizes="16x16" href="/images/favicon/favicon-16x16.png">
	<link rel="manifest" href="/images/favicon/site.webmanifest">
	<link rel="mask-icon" href="/images/favicon/safari-pinned-tab.svg" color="#5bbad5">
	<meta name="msapplication-TileColor" content="#da532c">
	<meta name="theme-color" content="#ffffff">
	<link rel="stylesheet" href="/css/stylesheet.css">
</head>
<body>
	<div class="top"><img src="/images/botted.png" width="140px"></div>
	<div class="content"><img src="/images/bot.png" width="200px" align="center">
	<form action="" id="form" method="post">
		<input type="text" id="uname" name="uname" placeholder="Username"><br><br>
		<input type="submit" name="submit" value="Submit">
	</form>
		<div class="result">
	<?php print nl2br($output); ?>
		</div>
	</div>
</body>
</html>

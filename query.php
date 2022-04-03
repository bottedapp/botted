<?php
$output = "";
if(isset($_POST['username'])){ //check if form was submitted
  $input = $_POST['username']; //get input text
  $output = passthru("python botted.py " . $input);
}    
echo $output;
?>

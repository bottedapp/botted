<?php
$output = "";
if(isset($_POST['submit'])){ //check if form was submitted
  $input = $_POST['uname']; //get input text
  $output = passthru("python botted.py " . $input);
}    
echo $output;
?>

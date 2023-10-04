<!DOCTYPE html>
<html lang="en">
<?php include 'head.txt';?>
<body>
<?php include 'header.txt';?>
    <main>
    	<div class="container">
    		<h2>Welcome to eatyourway.</h2>
    	</div>
    	<div class="container">
    		<h3>We help <i>you</i> find the right food for <i>you</i>.</h3>
    	</div>
		<div class="container"> 
					<?php
			if (isset($_GET['errMsg'])) {
				echo("<div id=\"errMsg\" class=\"container\">");
    			echo("<h3>".$_GET['errMsg']."</h3>");
				echo("</div>");
			}
			?>

			<form name="signup" method="post" action="register_driver.php">
				<div class="container-grid">
					<label for="email">Enter Email Address:</label>
					<input type="email" placeholder="johndoe@gmail.com" name="email" />
					<br />
					<label for="username">Please Choose a Username:</label>
					<input type="text" placeholder="johndoe123" name="username" />
					<br />
					<label for="password">Choose a Password:</label>
					<input type="password" name="password" placeholder="(minimum 8 characters)"/>
					<br />
					<input type="submit" placeholder="enter password" value="Sign Up" />
				</div>
			</form>
		</div>
	</main>
<?php include 'footer.txt';?> 
</body>
</html>
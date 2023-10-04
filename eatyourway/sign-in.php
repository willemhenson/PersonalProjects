<!DOCTYPE html>
<html lang="en">
<?php include 'head.txt';?>
<body>
<?php include 'header.txt';?>
    <main>
    	<div class="container">
    		<h2>Welcome to eatyourway.</h2>
    		<br>
    		<h3>Sign in to continue.</h3>
    	</div>
    	<div class="container">
    		<p>Don't have an account? <a href="register.php">Create an account.</a>
    		<br>
    		It takes less than a minute.
    		</p>
    	</div>
		<div class="container"> 
			<?php
			if (isset($_GET['errMsg'])) {
				echo("<div id=\"errMsg\" class=\"container\">");
    			echo("<h3>".$_GET['errMsg']."</h3>");
				echo("</div>");
			}
			?>
			<form name="login" method="post" action="sign-in_driver.php">
			    <div class="container-grid">
            		<label for="username">Username:</label>
            		<input type="text" name="username" placeholder="enter username"/>
            		<br />
            		<label for="password">Password:</label>
            		<input type="password" placeholder="enter password" name="password"/>
            		<br />
            		<input type="submit" value="Login" />
            	</div>
    		</form>
		</div>
	</main>
<?php include 'footer.txt';?> 
</body>
</html>
<?php
if(!isset($_COOKIE['username'])) { // If the user is not logged in redirect to sign in
  header('Location: ./sign-in.php');
} ?>

<!DOCTYPE html>
<html lang="en">
<?php include 'head.txt';?>
<body>
<?php include 'header.txt';?>
    <main>
    	<a href=# onclick="history.back()"><img src="images/arrow.png" id="arrow"></a>
    	<div class="container" id="title">
    	</div>
    	<center>
		<div class="container" id="preferences"> 
		</div>
		<br>
		</center>
		<div class="container"> 
			<button onclick="location.href='scan(search).php'">start scanning</button>
			<button onclick="location.href='scan(change).php'">change your preferences</button>
			<button onclick="signOut()">sign out</button>
		</div>
		<script type="text/javascript">
			function signOut() {
				location.href='sign-out_driver.php';
			}
		  
		  let user_data = getPreferences();

			document.getElementById("title").innerHTML = "<h2>Welcome "+getUsername()+", here are your preferences:</h2>";

			my_str = '<table>';
			for (const [key, value] of Object.entries(user_data)) {
				my_str += '<tr><td>'+key+'</td><td>'+value+'</td></tr>';
			}
			my_str += '</table.'
			document.getElementById("preferences").innerHTML = my_str;
		</script>
	</main>
<?php include 'footer.txt';?> 
</body>
</html>
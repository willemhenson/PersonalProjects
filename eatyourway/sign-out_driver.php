<?php 
session_start();
$_SESSION = array(); 
echo $_SESSION['username'];?>
<script>
document.cookie="username=;expires=Thu, 01 Jan 1970 00:00:00 UTC;";
				document.cookie="stored=;expires=Thu, 01 Jan 1970 00:00:00 UTC;";
				console.log("successfully signed out");
location.href='sign-in.php?errMsg=Successfully%20signed%20out';
</script>
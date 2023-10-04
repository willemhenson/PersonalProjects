<!DOCTYPE html>
<html lang="en">
<?php include 'head.txt';?>
<body>
<?php include 'header.txt';?>
    <main>
    	<a href=# onclick="history.back()"><img src="images/arrow.png" id="arrow"></a>
    	<div class="container">
    		<h2>Scan Result</h2>
    	</div>
		<div class="container" id="tickcross">
		</div>
		<center>
		<div class="card" style="background-color: #333333;">
			<p id="results text"></p>
		</div>
		</center>
<?php include 'db_credits.txt';?>
		<div class="container" id="info redirect">
		</div>
	</main>
	<script type="text/javascript">
		let product_data = JSON.parse(localStorage.getItem("product data"));
		let satisfied = JSON.parse(localStorage.getItem("satisfied"));
		if (satisfied["status"] == "yes") {
			document.getElementById("tickcross").innerHTML = '<img src="images/tick-circle.png" class="contact_pic" id="result-image">';
			document.getElementById("results text").innerHTML = 'Nice! This Product is Compatible.';
		}
		else if (satisfied["status"] == "no") {
			document.getElementById("tickcross").innerHTML = '<img src="images/cross.png" class="contact_pic" id="result-image"><br><br>';
			document.getElementById("results text").innerHTML = 'Sorry, This Product Is Not Compatible.';
			document.getElementById("results text").innerHTML += '<br><br>'
			document.getElementById("results text").innerHTML += satisfied["message"];
		}
		else if (satisfied["status"] == "unknown") {
			document.getElementById("tickcross").innerHTML = '<img src="images/question.png" class="contact_pic" id="result-image"><br><br>';
			document.getElementById("results text").innerHTML = 'Sorry, There is Data Missing for This Product on a Filter You Have Selected.';
			document.getElementById("results text").innerHTML += '<br><br>'
			document.getElementById("results text").innerHTML += satisfied["message"];
		}
		document.getElementById("info redirect").outerHTML = '<button onclick=location.href="info.php?barcode=' + product_data["barcode"] + '">See Product Info</button>';
	</script>
<?php include 'footer.txt';?>
</body>
</html>
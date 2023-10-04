<!DOCTYPE html>
<html lang="en">
<?php include 'head.txt';?>
<body>
<?php include 'header.txt';?>
	<script type="text/javascript" src="js/barcode-api.js"></script>
	<main>
		<div class="container">
			<a href=# onclick="history.back()"><img src="images/arrow.png" id="arrow"></a>
			<img src="images/barcode-graphic.png" class="pic" id="barcode_manual" alt="jcomp on Freepik">
			<div class="container">
				<h2>Enter the barcode manually</h2>
			</div>
		<div class="container">
			<h3>Enter the barcode below to find all the information you need.</h3>
		</div>
		<div class="container_barcode">
			<input type="text" name="barcode" , id="inp">
			<br><br>
		<div class="container">
			<center><p id="result-error"></p></center>
		</div>
			<button onclick=barcodeEntered()>enter</button>
			<script type="text/javascript">
				function barcodeEntered() {
					// This is called when a barcode is manually entered
					var barcode = document.getElementById("inp").value;
					processBarcode(barcode);
				}
			</script>
		</div>
	</main>

	<!-- This javascript manages the barcode scanner -->
	<script type="text/javascript" src="https://unpkg.com/@zxing/library@latest/umd/index.min.js"></script>
<?php include 'footer.txt' ?>
</body>
</html>
<!DOCTYPE html>
<html lang="en">
<?php include 'head.txt';?>
<body>
<?php include 'header.txt';?>
	<script type="text/javascript" src="js/scanner.js"></script>
    <script type="text/javascript" src="js/barcode-api.js"></script>
	<main>
		<a href=# onclick="history.back()"><img src="images/arrow.png" id="arrow"></a>
		<div class="container">
			<h2>Please Scan Product</h2>
		</div>
		<div class="container">
			<video id="video" width="50%"></video>
			<div id="sourceSelectPanel" style="visibility: hidden;">
				<label for="sourceSelect">Change video source:</label>
				<select id="sourceSelect" style="max-width:400px"></select>
			</div>
			<center>
				<a href="#" onclick="swapCamera()"><img src="images/camera.png" class="logo_small"></a>
			</center>
		</div>
		<div class="container">
			<center><p id="result-error"></p></center>
		</div>
		<div class="container">
			<!--<input type="text" name="barcode" , id="inp"> -->
			<button onclick="location.href='scan(search-manual).php'">enter manually</button>
		</div>
		<div class="container" id="info">
		</div>
	</main>

	<!-- This javascript manages the barcode scanner -->
	<script type="text/javascript">
		function barcodeScanned(barcode) {
			// This is called whenever a barcode is barcode is Scanned 
			// Currently a placeholder in case we treat scanning and entering differently
			processBarcode(barcode);
		}

		function scanError(err) {
			alert(err);
		}
	</script>
	<script type="text/javascript" src="https://unpkg.com/@zxing/library@latest/umd/index.min.js"></script>
<?php include 'footer.txt' ?>
</body>
</html>
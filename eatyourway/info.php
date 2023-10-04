<!DOCTYPE html>
<html lang="en">
<?php include 'head.txt';?>
<body>
<?php include 'header.txt';?>
	<script type="text/javascript" src="js/barcode-api.js"></script>
    <main>
    	<a href=# onclick="history.back()"><img src="images/arrow.png" id="arrow"></a>
<?php include 'db_credits.txt';?>
    	<center>
		<div class="card" style="background-color: #333333;" id="info-card">
			<h3 id="name">Loading data...</h3>
			<div class="board">
			 <div class="card" id="image"></div>
			 <div class="card" id="barcode">Product Barcode:<br></div>
			 <div class="card" id="brands">Brands:<br></div>
			 <div class="card" id="quantity">Quantity: <br></div>
			 <div class="card" id="categories">Categories:<br></div>
			 <div class="card" id="ingredients">Ingredients:<br></div>
			 <div class="card" id="nutrition">Nutrition:<br></div>
			 <div class="card" id="has palm">Contains Palm Oil?<br></div>
			 <div class="card" id="is vegetarian">Is Vegetarian?<br></div>
			 <div class="card" id="is vegan">Is Vegan?<br></div>
			 <div class="card" id="co2/g">Co2/g Produced:<br></div>
			 <div class="card" id="ecograde">Environemental Impact Grade:<br></div>
			 <div class="card" id="has plastic">Contains Single-use Plastics?<br></div>
			 <div class="card" id="nova">Wholefood Grade:<br></div>
			 <div class="card" id="nutrigrade">Health Grade:<br></div>
			</div>
		</div>
		</center>
	</main>
	<script type="text/javascript">

		function writeHTML(data) {
			data = nullToUnknown(data);
			document.getElementById("name").innerHTML = "General Information About " + data["name"];
			let ids = ["barcode","brands","ingredients","nutrition","has palm","is vegetarian","is vegan","categories","co2/g","ecograde","has plastic","nova","nutrigrade","quantity","ingredients"];
			document.getElementById("image").innerHTML = '<img src="' + data["image url"] + '">'
			for (i=0;i<ids.length;i++) {
				document.getElementById(ids[i]).innerHTML += data[ids[i]];
			}
		}
		
		<?php echo("let barcode = ".$_GET["barcode"].";\n"); ?>
        fetch('https://world.openfoodfacts.org/api/v0/product/' + barcode + '.json').then((response) => response.json()).then((data) => writeHTML(extractApiJSON(data["product"])));

	
	</script>
<?php include 'footer.txt';?> 
</body>
</html>
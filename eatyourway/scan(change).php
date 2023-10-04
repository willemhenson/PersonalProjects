<?php
if(!isset($_COOKIE['username'])) { // If the user is not logged in redirect to sign in
  header('Location: ./sign-in.php');
} ?>

<!DOCTYPE html>
<html lang="en">
<?php include 'head.txt';?>
<body>
<?php include 'header.txt';?>
<script type="text/javascript">
	// Populate the preferences fields with the data stored in the cookie
	function populateFields() {
		//SEED///////////////
/*	    let user_data = {"exclude brands":           ["Mars","Nestlé"],
	                     "exclude ingredients":      ["corn","gluten","milk"],
	                     "exclude plastics":         true,
	                     "exclude palm":             true,
	                     "only vegetarian":          false,
	                     "only vegan":               true,
	                     "min nutrigrade":           "E",
	                     "min ecograde":             "A",
	                     "max nova":                 4,
	                     "max co2/g":                2.8};*/
		
		let user_data = getPreferences();

	    let brands = user_data["exclude brands"];
	    for (i=0;i<brands.length;i++) {
	    	document.getElementById("brands").value+=brands[i];
	    	if (i < brands.length-1) {
	    		document.getElementById("brands").value+="*";
	    	}
	    }

	    let ingredients = user_data["exclude ingredients"];
	    for (i=0;i<ingredients.length;i++) {
	    	document.getElementById("ingredients").value+=ingredients[i];
	    	if (i < ingredients.length-1) {
	    		document.getElementById("ingredients").value+="*";
	    	}
	    }

	    if (user_data["exclude plastics"] == true) document.getElementById("plastics").checked="checked";
	    if (user_data["exclude palm"] == true) document.getElementById("palm").checked="checked";
	    if (user_data["only vegetarian"] == true) document.getElementById("vegetarian").checked="checked";
	    if (user_data["only vegan"] == true) document.getElementById("vegan").checked="checked";

	    let nutrigrade = user_data["min nutrigrade"];
	    let grade = "abcde".indexOf(nutrigrade.toLowerCase());
	    document.getElementById("nutrigrade").value = grade;
	    let ecograde = user_data["min ecograde"];
	    let grade1 = "abcde".indexOf(ecograde.toLowerCase());
	    document.getElementById("ecograde").value = grade1;
	    
	    let nova = user_data["max nova"];
	    document.getElementById("nova").value = nova;

	    let co2 = user_data["max co2/g"];
	    document.getElementById("co2/g").value = co2;

}

	function updateLabels() {
		document.getElementById("nutrigrade-label").innerHTML = "ABCDE"[document.getElementById("nutrigrade").value];
		document.getElementById("ecograde-label").innerHTML = "ABCDE"[document.getElementById("ecograde").value];
		document.getElementById("nutrigrade-label").innerHTML = "ABCDE"[document.getElementById("nutrigrade").value];
		document.getElementById("nova-label").innerHTML = document.getElementById("nova").value;
		document.getElementById("co2-label").innerHTML = document.getElementById("co2/g").value;
	}
	</script>
    <main>
    	<div class="container">
    		<h2>Please modify your preferences below.</h2>
			<?php
			if (isset($_GET['errMsg'])) {
				echo("<div id=\"errMsg\" class=\"container\">");
    			echo("<h3>".$_GET['errMsg']."</h3>");
				echo("</div>");
			}
			?>
    	</div>
		<div class="container">
			<h3>
			<form action="update_preferences.php" method="post">
				<label for="brands">Excluded Brands:</label>
				<input type="text" id="brands" name="brands" placeholder="Mars*Nestle"><br><br>
				
				<label for="ingredients">Excluded Ingredients:</label>
				<input type="text" id="ingredients" name="ingredients" placeholder="Peanuts*Gluten"><br><br>

				<label for="plastics">Exclude Plastics?</label>
				<input type="checkbox" id="plastics" name="plastics"><br>
				
				<label for="palm">Exclude Palm Oil Products?</label>
				<input type="checkbox" id="palm" name="palm"><br>
				
				<label for="vegetarian">Only Vegetarian?</label>
				<input type="checkbox" id="vegetarian" name="vegetarian"><br>
				
				<label for="vegan">Only Vegan?</label>
				<input type="checkbox" id="vegan" name="vegan"><br>

				Min Nutrigrade:<br>
				<label for="nutrigrade" id="nutrigrade-label"></label>
				<input type="range" id="nutrigrade" name="nutrigrade" min="0" max="4" step="1"><br>

				Min Ecograde:<br>
				<label for="ecograde" id="ecograde-label"></label>
				<input type="range" id="ecograde" name="ecograde" min="0" max="4" step="1"><br>

				Max Nova Group:<br>
				<label for="nova" id="nova-label"></label>
				<input type="range" id="nova" name="nova" min="1" max="4" step="1"><br>

				Max Co2/g:<br>
				<label for="co2/g" id="co2-label"></label>
				<input type="range" id="co2/g" name="co2/g" min="0" max="1000" step="1"><br>

			  	
			  	<button type="submit">submit</button>
			</form>
			<a href="explain.php"><i>what does each filter mean?</i></a>
		</h3>
		</div>
	</main>
	<script type="text/javascript">populateFields();window.setInterval(updateLabels, 100)</script>
<?php include 'footer.txt';?> 
</body>
</html>
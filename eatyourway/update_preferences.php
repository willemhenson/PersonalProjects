<?php
session_start();
if(isset($_SESSION['username'])){
        $servername = "dbhost.cs.man.ac.uk";
        $username = "z02829ah";
        $password = "Z4CNyTijfjP9NB5";
        $dbname = "2022_comp10120_m8";

        // Create connection
        $conn = new mysqli($servername, $username, $password, $dbname);
        // Check connection
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }


        // Update the preferences stored in the database
        $sql = "UPDATE users SET dietary_preferences = ? WHERE username = ?";
        $stmt = $conn->prepare($sql);

        // Format brands and other fields
        $brands = $_POST['brands'];
        str_replace("/[^a-zA-Z-_\s]/g","",$brands);
        str_replace("/\s+/g", "*",$brands);
        $brands_arr = explode('*',$brands);
        $ingredients = $_POST['ingredients'];
        str_replace("/[^a-zA-Z-_\s]/g","",$ingredients);
        str_replace("/\s+/g", "*",$ingredients);
        $ingredients_arr=explode('*',$ingredients);

        $stored = array();

        $stored["exclude brands"] = $brands_arr;
        $stored["exclude ingredients"] = $ingredients_arr;

        if(isset($_POST['plastics'])){
            $stored["exclude plastic"] = true;
        }else{
            $stored["exclude plastic"] = false;
        }
        if(isset($_POST['palm'])){
            $stored["exclude palm"] = true;
        }else{
            $stored["exclude palm"] = false;
        }
        if(isset($_POST['vegetarian'])){
            $stored["only vegetarian"] = true;
        }else{
            $stored["only vegetarian"] = false;
        }
        if(isset($_POST['vegan'])){
            $stored["only vegan"] = true;
        }else{
            $stored["only vegan"] = false;
        }

        $stored['min nutrigrade'] = "ABCDE"[$_POST['nutrigrade']];
        $stored['min ecograde'] = "ABCDE"[$_POST['ecograde']];
        $stored['max nova'] = $_POST['nova'];
        $stored['max co2/g'] = $_POST['co2/g'];


        $stored=json_encode($stored);
        setcookie('stored', $stored); 
            
        $stmt->bind_param("ss", $stored, $_SESSION['username']);
        $stmt->execute();
        header("Location: scan(start).php");

}else{
    header("Location: sign-in.php?errMsg=Error%20-%20Please%20sign%20in.");
}
?>
<?php
// Connect to the database
$db = new mysqli("dbhost.cs.man.ac.uk", "z02829ah", "Z4CNyTijfjP9NB5", "2022_comp10120_m8");

// Get the value of the query parameter
$q = $_GET['q'];

// Prepare the SQL statement
$sql = "SELECT preference FROM preferences WHERE preference LIKE ?";
$stmt = $db->prepare($sql);

// Bind the parameters
$stmt->bind_param("s", $q.'%');

// Execute the statement
$stmt->execute();

// Get the results
$stmt->bind_result($preferences);
$suggestions = array();
while ($stmt->fetch()) {
    array_push($suggestions, $preferences);
}

// Close the statement
$stmt->close();

// Close the connection
$db->close();

// Return the suggestions
header('Content-Type: application/json');
echo json_encode($suggestions);
?>
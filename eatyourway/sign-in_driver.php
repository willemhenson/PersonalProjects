<?php
function userLogin(){

$servername = "dbhost.cs.man.ac.uk";
$username = "z02829ah";
$password = "Z4CNyTijfjP9NB5";
$dbname = "2022_comp10120_m8";

if (isset($_POST['username']) && isset($_POST['password'])) {
    // Create connection
    $conn = new mysqli($servername, $username, $password, $dbname);
    // Check connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    // Check if password correct
    $password_correct = false;
    $sql = "SELECT * FROM users WHERE username = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $_POST['username']);
    $stmt->execute();
    $result = $stmt->get_result();

    $row = $result->fetch_assoc();

    // Check if row is empty
    if (empty($row)) {
        header("Location: sign-in.php?errMsg=Error%20-%20Username%20is%20incorrect");
    }else{

        if (password_verify($_POST['password'], $row['password'])) {
            $password_correct = true;
        }
            
        // If username and password are correct, log in the user
        if ($password_correct) {
            // Log in the user
            echo('success - user logged in');
            session_start();
            $_SESSION['username']=$_POST['username'];
            setcookie('username', $_POST['username']);
            if (empty($row['dietary_preferences'])){
                header("Location: scan(change).php?errMsg=No%20preferences%20currently%20selected");
            } else {
                setcookie('stored', $row['dietary_preferences']);
                header("Location: index.php");
            }
        } else {
            header("Location: sign-in.php?errMsg=Error%20-%20Password%20is%20incorrect");
        }
    }
}
};

userLogin();
?>
<?php
function userSignup(){
    $servername = "dbhost.cs.man.ac.uk";
    $username = "z02829ah";
    $password = "Z4CNyTijfjP9NB5";
    $dbname = "2022_comp10120_m8";

    if (isset($_POST['email']) && isset($_POST['username']) && isset($_POST['password'])) {
        // Validate email
        $email_regex = "/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/";
        if (!preg_match($email_regex, $_POST['email'])) {
            header("Location: sign-in.php?errMsg=Error%20-%20Email%20is%20invalid");
            exit;
        }

        // Create connection
        $conn = new mysqli($servername, $username, $password, $dbname);
        // Check connection
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }

        // Check if username and/or email already exists
        $username_exists = false;
        $email_exists = false;
        $sql = "SELECT * FROM users WHERE username = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("s", $_POST['username']);
        $stmt->execute();
        $result = $stmt->get_result();
        if ($result->num_rows > 0) {
            $username_exists = true;
        }
        $sql = "SELECT * FROM users WHERE email = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("s", $_POST['email']);
        $stmt->execute();
        $result = $stmt->get_result();
        if ($result->num_rows > 0) {
            $email_exists = true;
        }
    
        // If username and/or email already exists, log in the user
        // Otherwise, create a new record
        if ($username_exists || $email_exists) {
            // Redirect to sign in page
            header("Location: sign-in.php?errMsg=Error%20-%20Username%20already%20exists");
        }
        elseif (strlen($_POST['password']) < 8) {
            // pw not long enough
            header("Location: register.php?errMsg=Error%20-%20Password%20not%20secure%20enough");  
        }
        else {
            $username = $_POST['username'];
            $email = $_POST['email'];
            $password = password_hash($_POST['password'], PASSWORD_DEFAULT);

            // Prepare the SQL statement
            $sql = "INSERT INTO users (email, username, password) 
                    VALUES ('$email', '$username', '$password')";

            if ($conn->query($sql) === TRUE) {
                // We redirect to sign-in_driver and post username and password (only possible with js form)
                echo "New record created successfully";
                echo '<form id="sign-in" action="./sign-in_driver.php" method="post">';
                echo '<input type="hidden" name="username" value="' . $_POST['username'] . '">';
                echo '<input type="hidden" name="password" value="' . $_POST['password'] . '">';
                echo '</form>';
                echo '<script type="text/javascript">';
                echo 'document.getElementById("sign-in").submit();';
                echo '</script>';
            } else {
                echo "Error: " . $sql . "<br>" . $conn->error;
            }
        }

        // Close the connection
        $conn->close();
    }
};

userSignup();

?>
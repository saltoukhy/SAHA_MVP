<?php
    $con = mysqli_connect("localhost", "id615709_saltoukhy", "Ahlan8996", "id615709_saha");
   
    if (isset($_POST)) {
        if (isset($_POST["password"])){
            $password = $_POST['password'];
        }
    }

if (isset($_POST)) {
        if (isset($_POST["email"])){
            $email = $_POST['email'];
        }
    }
    
    $statement = mysqli_prepare($con, "SELECT user_id, first_name, last_name FROM user WHERE email = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $email, $password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $user_id, $first_name, $last_name);

    $response = array();
    $response["success"] = false;  

while(mysqli_stmt_fetch($statement)){
        $response["success"] = true; 
$response["user_id"] = $user_id; 
        $response["first_name"] = $first_name;
        $response["last_name"] = $last_name;
    }
mysqli_close($con);

    
    echo json_encode($response);
?>
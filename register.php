<?php
    $con = mysqli_connect("localhost", "id615709_saltoukhy", "Ahlan8996", "id615709_saha");
    
    if (isset($_POST)) {
      if (isset($_POST["first_name"])){
        $first_name = $_POST['first_name'];
      }
    }
    if (isset($_POST)) {
      if (isset($_POST["last_name"])){
        $last_name = $_POST['last_name'];
      }
    }
    if (isset($_POST)) {
      if (isset($_POST["email"])){
        $email = $_POST['email'];
      }
    }
    if (isset($_POST)) {
      if (isset($_POST["password"])){
        $password = $_POST['password'];
      }
    }

    if (isset($_POST)) {
      if (isset($_POST["age"])){
        $age = $_POST['age'];
      }
    }

    if (isset($_POST)) {
      if (isset($_POST["phone"])){
        $phone = $_POST['phone'];
      }
    }

    if (isset($_POST)) {
      if (isset($_POST["country"])){
        $country = $_POST['country'];
      }
    }

    if (isset($_POST)) {
      if (isset($_POST["status"])){
        $status = $_POST['status'];
      }
    }
    $check = mysqli_prepare($con, "SELECT count(*) FROM user WHERE email = ?");
    mysqli_stmt_bind_param($check, "s", $email);
    mysqli_stmt_execute($check);
    
    mysqli_stmt_store_result($check);
    mysqli_stmt_bind_result($check, $count);
    $response = array();
    while(mysqli_stmt_fetch($check)){
            $response["count"] = $count;}
    if ($response["count"] >= 1) {
      $response["success"] = false;
    }   
    else {
      $statement = mysqli_prepare($con, "INSERT INTO user (first_name, last_name, email, password, age, phone, country, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
      mysqli_stmt_bind_param($statement, 'ssssssss', $first_name, $last_name, $email, $password, $age, $phone, $country, $status);
      mysqli_stmt_execute($statement);
      
      $response["success"] = true;  
      
      echo json_encode($response);
    }
?>
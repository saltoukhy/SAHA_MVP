<?php
    $con = mysqli_connect("localhost", "id615709_saltoukhy", "Ahlan8996", "id615709_saha");
    
if (isset($_POST)) {
  if (isset($_POST["user_id"])){
    $user_id = $_POST['user_id'];
  }
}
if (isset($_POST)) {
  if (isset($_POST["description"])){
    $description = $_POST['description'];
  }
}
    
    $statement = mysqli_prepare($con, "INSERT INTO question (user_id, question) VALUES (?, ?)");
    mysqli_stmt_bind_param($statement, 'ss', $user_id, $description);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
?>
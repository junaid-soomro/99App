<?php
$username = $_POST["username"];
$password = $_POST["password"];
include("dbConnect.php");
$response = array();
$response["success"] = false;
$response["status"]="INVALID";
$result = mysqli_query($conn, "SELECT * FROM `user` WHERE `username` = '$username' AND `password` = '$password'");
$affected = mysqli_affected_rows($conn);
if ($affected > 0) {
  #USER DETAILS MATCH
    $response["success"] = true;
    while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
        $response["ID"]=$row['user_id'];
        $response["Name"]=$row['name'];
        $response["national_id"]=$row['national_id'];
        $response["Phone"]=$row['phone'];
        $response["Address"]=$row['address'];
		$response["Username"]=$row['username'];
		$response["image"]=$row["image"];
    }

    $response["type"]="user";
}
else{
$result2 = mysqli_query($conn, "SELECT * FROM `employee` WHERE `e_username` = '$username' AND `e_password` = '$password'");
$affected2 = mysqli_affected_rows($conn);
if ($affected2 > 0) {
  #Employee DETAILS MATCH
    $response["success"] = true;
    while ($row = mysqli_fetch_array($result2, MYSQLI_ASSOC)) {
        $response["ID"]=$row['e_id'];
        $response["Name"]=$row['e_name'];
        $response["Phone"]=$row['phone'];
		$response["Username"]=$row['username'];
    }
    $response["Address"]="null";
    $response["type"]="employee";
    }
}

  $userCheck = mysqli_query($conn, "SELECT * FROM `user` WHERE `e_username` = '$username'");
  $userAffected = mysqli_affected_rows($conn);
  if($userAffected>0){
    $response["status"]="PASSWORD";
  }else{
   $userCheck2 = mysqli_query($conn, "SELECT * FROM `employee` WHERE `e_username` = '$username'");
  $userAffected2 = mysqli_affected_rows($conn);
  if($userAffected2>0){
    $response["status"]="PASSWORD";
  }   
      
  }

echo json_encode($response);
mysqli_close($conn);
exit();
?>
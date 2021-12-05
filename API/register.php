<?php
include("dbConnect.php");
if(isset($_POST["user_id"])){
   
    $response["status"]=false;
    $user_id = $_POST["user_id"];
    $arrayy = $_POST["diseases"];
    $disease = json_decode($arrayy,true);
    $blood_group = $_POST["blood_group"];
    $other_detail = $_POST["other_details"];
    
    foreach($disease as $item){
        
        mysqli_query($conn,"INSERT INTO `medical_history` (`user_id`,`diseases`,`other_details`,`blood_group`)VALUES('$user_id','$item','$other_detail','$blood_group')");
    }
    if(mysqli_affected_rows($conn)>0){
        
        $response["status"]=true;
    }
    
    
}
else{
$password = $_POST["password"];
$username = $_POST["username"];
$name = $_POST["name"];
$phone = $_POST["phone"];
$address = $_POST["address"];
$national_id = $_POST["national_id"];
$image = $_POST["image"];
$response["username"]=false;
$response=array();
$response["success"]=false;
$check=mysqli_query($conn,"SELECT * FROM `user` WHERE `username`='$username'");
$affected=mysqli_affected_rows($conn);
if($affected>0){
  $response["username"]="true";
}
else{
  
  $result=mysqli_query($conn,"INSERT INTO `user` (`name`, `password`, `username`,`phone`,`address`,`national_id`,`image`) VALUES ('$name', '$password', '$username','$phone','$address','$national_id','$image')");
  
  $response["success"]=true;    
    
    $query = mysqli_query($conn,"SELECT `user_id` FROM `user` WHERE `username`='$username'");
    
    while($row = mysqli_fetch_array($query,MYSQLI_ASSOC)){
        
        $response["user_id"] = $row["user_id"];
    }
    $reponse["username"]=false;
    
}
}
echo json_encode($response);#encoding RESPONSE into a JSON and returning.
mysqli_close($conn);
exit();
?>
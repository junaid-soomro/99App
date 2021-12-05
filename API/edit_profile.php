<?php
$id = $_POST["ID"];
$name = $_POST["Name"];
$national_id = $_POST["national_id"];
$address = $_POST["address"];
$phone = $_POST["phone"];
$username = $_POST["Username"];

include("dbConnect.php");
$response["success"]=false;
$response["PASSWORD"]=false;

if(isset($_POST["password"])){

$password = $_POST["password"];
$new_password = $_POST["new_password"];

$getID=mysqli_query($conn,"SELECT `password` FROM `user` WHERE `user_id` = $id");

while($row = mysqli_fetch_array($getID,MYSQLI_ASSOC)){
    
    $real_pass=$row['password'];
        
}


if($real_pass==$password){
    if(isset($_POST["image"])){
        $image = $_POST["image"];
        $query = mysqli_query($conn,"UPDATE `user` SET `name` = '$name' ,`national_id` = '$national_id',`password`='$new_password',`username`='$username',`phone`='$phone',`address`='$address',`image`='$image' WHERE `user_id`='$id'");
        $check2 = mysqli_affected_rows($conn);
	
	if($check2 > 0){
	$response["success"]=true;
	}
    }
    else{
            $query = mysqli_query($conn,"UPDATE `user` SET `name` = '$name' ,`national_id` = '$national_id',`password`='$new_password',`username`='$username',`phone`='$phone',`address`='$address' WHERE `user_id`='$id'");
        $check2 = mysqli_affected_rows($conn);
	
	if($check2 > 0){
	$response["success"]=true;
	}
    }
    
	}
else{
    
    $response["PASSWORD"]=true;
    
	}
}
else{
			    if(isset($_POST["image"])){
			        $image = $_POST["image"];
                $result3 = mysqli_query($conn,"UPDATE `user` SET `name` = '$name' ,`national_id` = '$national_id',`username`='$username',`phone`='$phone',`address`='$address',`image`='$image' WHERE `user_id`='$id'");
    $check2 = mysqli_affected_rows($conn);
	
	if($check2 > 0){
	$response["success"]=true;
	}
                    }else{
                                        $result3 = mysqli_query($conn,"UPDATE `user` SET `name` = '$name' ,`national_id` = '$national_id',`username`='$username',`phone`='$phone',`address`='$address' WHERE `user_id`='$id'");
                                        $check2 = mysqli_affected_rows($conn);
	
	if($check2 > 0){
	$response["success"]=true;
	}                   
                    }
			    
	
}

echo json_encode($response);
mysqli_close($conn);
exit();
?>
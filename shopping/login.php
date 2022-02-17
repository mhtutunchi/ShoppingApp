<?php

include 'connect.php';


if($_POST){
	
$phone=@$_POST['phone'] ?? '';
$password=@$_POST['password'] ?? '';
	
$response = [];


$userQuery = $connection->prepare("SELECT * FROM users WHERE phone = ? ");	
$userQuery->execute(array($phone));
$query= $userQuery->fetch();

if($userQuery->rowCount()==0){
	
	$response['status'] = false;
	$response['message'] = "اکانت با این شماره وجود ندارد";

	
}else{
	
	$passwordDB = $query['password'];
	
	if(strcmp(md5($password),$passwordDB)===0){
		
	$response['status'] = true;
	$response['message'] = "خوش آمدید";
	$response['data'] = [
	'id' => $query['id'],
	'email' => $query['email'],
	'phone' => $query['phone']
	];
	
	}else{
		
		$response['status'] = false;
		$response['message'] = "Password Wrong";
		
	}
	
}


$json = json_encode($response,JSON_PRETTY_PRINT);
	
echo $json;	
	
}

?>
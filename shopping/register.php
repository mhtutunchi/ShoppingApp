<?php

include 'connect.php';


if($_POST){

$email = filter_input(INPUT_POST , 'email' , FILTER_SANITIZE_STRING);	
$phone =filter_input(INPUT_POST , 'phone' , FILTER_SANITIZE_STRING);
$password=filter_input(INPUT_POST , 'password' , FILTER_SANITIZE_STRING);
	
$response = [];


$userQuery = $connection->prepare("SELECT * FROM users WHERE phone = ? ");	
$userQuery->execute(array($phone));
//$query= $userQuery->fetch();

if($userQuery->rowCount()!=0){
	
	$response['status'] = false;
	$response['message'] = "این شماره قبلا ثبت شده است";
	
}else{
	
	$insertAccount = 'INSERT INTO users (email , phone, password) VALUES (:email , :phone , :password)';
	$statment = $connection->prepare($insertAccount);
	
	try{
		
		$statment->execute([
		':email' => $email,
		':phone' => $phone,
		':password' => md5($password)
		]);
		
		
		$response['status'] = true;
	    $response['message'] = "ثبت نام با موفقیت انجام شد";
		$response['data'] = [
		'id' => $query['id'],
		'email' => $email,
		'phone' => $phone
		];
		
	}catch(Exception $e){
		die($e->getMessage());
	}

}

$json = json_encode($response , JSON_PRETTY_PRINT);

echo $json;

}

?>
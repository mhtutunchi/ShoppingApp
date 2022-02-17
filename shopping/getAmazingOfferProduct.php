<?php

include 'connect.php';

$query = " SELECT * FROM product WHERE off = 1 LIMIT 5 ";
$stmt = $connection->prepare($query);
$stmt->execute();

$product = array();

while($row = $stmt -> fetch(PDO::FETCH_ASSOC)){
	
	$rec = array();
	
	$value_off = $row['value_off'];
	$price = $row['price'];
	$totaloffprice = $price * $value_off / 100;
	$finalprice = $price - $totaloffprice;
	$rec ['offprice'] = $finalprice;
	
	if($value_off==0){
		
		$totaloffprice = 0;
		
	}
	
	$rec['id'] = $row['id'];
	$rec['category_id'] = $row['category_id'];
	$rec['name'] = $row['name'];
	$rec['link_img'] = $row['link_img'];
	$rec['price'] = $row['price'];
	$rec['value_off'] = $row['value_off'];
	$rec['brand'] = $row['brand'];
	$product[] = $rec;	
}

echo json_encode($product);


?>
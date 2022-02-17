<?php

include 'connect.php';

$id = @$_POST["id"];


$query = " SELECT * FROM category WHERE id =:id ";
$stmt = $connection->prepare($query);
$stmt ->bindParam(":id" , $id);
$stmt->execute();


while($row = $stmt -> fetch(PDO::FETCH_ASSOC)){
	
	$rec ["id"] = $row ["id"];
	
}

$query2 = " SELECT * FROM product WHERE new = 1 AND category_id =:id ";
$stmt2 = $connection->prepare($query2);
$stmt2 ->bindParam(":id" , $rec ["id"]);
$stmt2 ->execute();

$product = array();

while($row2 = $stmt2 -> fetch(PDO::FETCH_ASSOC)){
	
	$rec2 = array();
	

	$rec2['id'] = $row2['id'];
	$rec2['category_id'] = $row2['category_id'];
	$rec2['name'] = $row2['name'];
	$rec2['link_img'] = $row2['link_img'];
	$rec2['price'] = $row2['price'];
	$rec2['brand'] = $row2['brand'];
	$product[] = $rec2;	
}

echo json_encode($product);


?>
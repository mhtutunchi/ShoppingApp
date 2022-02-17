<?php

include 'connect.php';

$query = " SELECT * FROM brand ";
$stmt = $connection->prepare($query);
$stmt->execute();

$slider = array();

while($row = $stmt -> fetch(PDO::FETCH_ASSOC)){
	
	$banner = array();
	$banner['id'] = $row['id'];
	$banner['name'] = $row['name'];
	$banner['link_img'] = $row['link_img'];
	$slider[] = $banner;
		
}

echo json_encode($slider);

?>
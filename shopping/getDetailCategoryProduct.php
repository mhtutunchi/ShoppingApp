<?php
include "connect.php";

$query="SELECT * FROM product WHERE detail_category_id = 2 LIMIT 5";
$stmt=$connection->prepare($query);
$stmt->execute();

$product=array();
while($row=$stmt->fetch(PDO::FETCH_ASSOC))
{
  $record=array();

  $record["id"]=$row["id"];
  $record["category_id"]=$row["category_id"];
  $record["name"]=$row["name"];
  $record["link_img"]=$row["link_img"];
  $record["price"]=$row["price"];
  $product[]=$record;

}
echo JSON_encode($product);
?>
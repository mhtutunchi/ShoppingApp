<?php
include "connect.php";

$query="SELECT * FROM intro ";
$stmt=$connection->prepare($query);
$stmt->execute();

$product=array();
while($row=$stmt->fetch(PDO::FETCH_ASSOC))
{
  $record=array();

  $record["id"]=$row["id"];
  $record["link_lottie"]=$row["link_lottie"];
  $record["description"]=$row["description"];
  $product[]=$record;

}
echo JSON_encode($product);
?>
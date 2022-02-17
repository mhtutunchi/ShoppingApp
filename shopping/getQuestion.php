<?php
include "connect.php";

$query="SELECT * FROM question ";
$stmt=$connection->prepare($query);
$stmt->execute();

$product=array();
while($row=$stmt->fetch(PDO::FETCH_ASSOC))
{
  $record=array();

  $record["id"]=$row["id"];
  $record["title"]=$row["title"];
  $record["description"]=$row["description"];
  $product[]=$record;

}
echo JSON_encode($product);
?>
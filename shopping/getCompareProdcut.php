<?php

include "connect.php";

$id = @$_POST["category_id"];
 //$id=1;
$query = "SELECT * FROM category WHERE id=:id";
$stmt =$connection ->prepare($query);
$stmt ->bindParam(":id",$id);
$stmt ->execute();

while ($row = $stmt ->fetch(PDO::FETCH_ASSOC)) {

  $record["id"] = $row["id"];
  // code...
}

$query2 ="SELECT * FROM product WHERE  category_id =:id";
$stmt2 =$connection ->prepare($query2);
$stmt2 ->bindParam(":id",$record["id"]);
$stmt2 ->execute();
$product=array();
while($row2 = $stmt2->fetch(PDO::FETCH_ASSOC)){

  $record=array();
  $record["id"]=$row2["id"];
  $record["category_id"]=$row2["category_id"];
  $record["name"]=$row2["name"];
  $record["link_img"]=$row2["link_img"];
  $record["price"]=$row2["price"];
  $product[]=$record;
}


 echo JSON_encode($product);

 ?>

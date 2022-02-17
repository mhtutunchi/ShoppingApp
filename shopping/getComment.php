<?php

include "connect.php";

$id = @$_POST["id"];
 //$id=1;
$query = "SELECT * FROM product WHERE id=:id";
$stmt =$connection ->prepare($query);
$stmt ->bindParam(":id",$id);
$stmt ->execute();

while ($row = $stmt ->fetch(PDO::FETCH_ASSOC)) {

  $record["id"] = $row["id"];
  // code...
}

$query2 ="SELECT * FROM comment WHERE  id_product =:id AND confirmation = 1";
$stmt2 =$connection ->prepare($query2);
$stmt2 ->bindParam(":id",$record["id"]);
$stmt2 ->execute();
$product=array();
while($row2 = $stmt2->fetch(PDO::FETCH_ASSOC)){

  $record=array();
  $record["id"]=$row2["id"];
  $record["id_product"]=$row2["id_product"];
  $record["user_email"]=$row2["user_email"];
  $record["description"]=$row2["description"];
  $record["date"]=$row2["date"];
  $record["rating"]=$row2["rating"];
  $record["positive"]=$row2["positive"];
  $record["negative"]=$row2["negative"];
  $record["confirmation"]=$row2["confirmation"];
  $record["offer"]=$row2["offer"];
  $record["title"]=$row2["title"];
  $product[]=$record;
}


 echo JSON_encode($product);

 ?>

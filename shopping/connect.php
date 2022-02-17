<?php

$servername = "localhost";
$username = "root";
$password = "";

try {
  $connection = new PDO("mysql:host=$servername;dbname=shopping", $username, $password);
  // set the PDO error mode to exception
  $connection->exec("SET character_set_connection 'utf8' ");
  $connection->exec("SET NAMES 'utf8' ");
  
  $connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
  //echo "Connected successfully";
} catch(PDOException $e) {
  //echo "Connection failed: " . $e->getMessage();
}
?>
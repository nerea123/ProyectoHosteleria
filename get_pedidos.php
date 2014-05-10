<?php
   // include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all products from products table
$result = mysql_query("SELECT tiquet.IDT, tiquet.IDM, IDL, DESCRIPCION, CANTIDAD 
						FROM tiquet 
						LEFT JOIN linea ON linea.IDT=tiquet.IDT 
						LEFT JOIN productos on linea.IDP=productos.IDP 
						where ENVIADO = 1 and PREPARADO=0") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["products"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["IDT"] = $row["IDT"];
		$product["IDM"] = $row["IDM"];
		$product["IDL"] = $row["IDL"];
        $product["DESCRIPCION"] = $row["DESCRIPCION"];
        $product["CANTIDAD"] = $row["CANTIDAD"];
        
                // push single product into final response array
        array_push($response["products"], $product);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";

    // echo no users JSON
    echo json_encode($response);
}
?>
   
?>
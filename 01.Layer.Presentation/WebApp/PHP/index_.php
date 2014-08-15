<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

	<title>Validar Usuario</title>
	
</head>

<body>

	<?php


	$data = file_get_contents("http://50.62.138.62/wcfEvalCuadra/Service1.svc/validarusuario/Admin,admin");
	$array = json_decode($data, true);

	//print_r($array);

	$resultado = $array['GetValidarUsuarioResult'];
	//echo $resultado;

	$final = explode("|", $resultado);

	echo '<h1>' . $final[0] . '</h1>';
	echo '<h2>' . $final[1] . '</h2>';

	?>

</body>
</html>

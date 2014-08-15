<?php
	ob_start();
	$tienda = $_GET['id_tienda'];
	
	if (!$tienda) {
		header("Location: mapas.php");
		die();
	}
	
	require_once 'includes/classConexion.php';

	$usuario = new mysql();
		
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

	<title>Tienda no. <?php echo $tienda; ?></title>
	<?php include('includes/setup.php'); ?>
	
</head>

<body>

	<div id="header">
		<h2 id="app">Tiendas</h2>
		<a href="index.php" id="regresar">Regresa al Mapa</a>
	</div>

	<div id="informacion-tienda">
		<div class="wrap">
			<?php $usuario->listarUbicacion($tienda); ?>
		</div>
	</div>
	
	<div id="el-qr">
		<div class="wrap">
			<div id="qrcode">

			</div>
			<h3 class="fs">Localiza la tienda en <span>Foursquare</span>. <strong>Escanea el QR Code</strong></h3>
		</div>
	</div>
	
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="js/jquery.qrcode-0.9.4.min.js" type="text/javascript" charset="utf-8"></script>
	
	<?php
	
		print '<script type="text/javascript" charset="utf-8">
			$("#qrcode").qrcode({
			  "width": 100,
			  "height": 100,
			  "color": "#3a3",
			  "text": "https://foursquare.com/explore?mode=url&q='.$tienda.'"
			});
		</script>';
	
	?>

</body>
</html>

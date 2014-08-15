<?php
	error_reporting(0);
	
	require_once 'includes/classConexion.php';

	$usuario = new mysql();
	$marcadores = $usuario->listar_ubicaciones();


?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

	<title>Mapa de Tiendas Cuadra</title>
	<?php include('includes/setup.php'); ?>
	
</head>

<body>
	
	<div id="header">
		<h2 id="app">Tiendas</h2>
		<a href="javascript:history.go(0)" id="regresar">Recargar</a>
	</div>

	<?php

		$cadena = "[";
		$infowindow = "[";

		for ($row = 0; $row < count($marcadores); $row++)
		{
			for ($i = 0; $i < count($row) ; $i++) { 
				$cadena .= "['".$marcadores[$row][$i]."',";
				$cadena .= $marcadores[$row][$i+1].",";
				$cadena .= $marcadores[$row][$i+2]."]";

				$infowindow .= "['".$marcadores[$row][$i+3]."']";
			}

			if ($row < count($marcadores) - 1) {
				$cadena .= ",";
				$infowindow .= ",";
			}

		}

		$cadena .= "]";
		$infowindow .= "]";

	?>


	<div id="map_wrapper">
	    <div id="map_canvas" class="mapping"></div>
	</div>

	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

	<script>

	jQuery(function($) {
	    // Asynchronously Load the map API 
	    var script = document.createElement('script');
	    script.src = "http://maps.googleapis.com/maps/api/js?sensor=false&callback=initialize";
	    document.body.appendChild(script);
	});

	function initialize() {
	    var map;
	    var bounds = new google.maps.LatLngBounds();
	    var mapOptions = {
	        mapTypeId: 'roadmap',
			zoom: 3,
			scrollwheel: false,
			navigationControl: false,
		    mapTypeControl: false,
		    scaleControl: false
	    };

	    // Display a map on the page
	    map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
	    map.setTilt(45);

	    // Multiple Markers



	    var markers = <?php echo $cadena; ?>;

	    // Info Window Content
	    var infoWindowContent = <?php echo $infowindow; ?>;

	    // Display multiple markers on a map
	    var infoWindow = new google.maps.InfoWindow(), marker, i;
		var marImg = "img/markers/marker";

	    // Loop through our array of markers & place each one on the map  
	    for( i = 0; i < markers.length; i++ ) {
	        var position = new google.maps.LatLng(markers[i][1], markers[i][2]);
	        bounds.extend(position);
	        marker = new google.maps.Marker({
	            position: position,
	            map: map,
	            title: markers[i][0],
				icon: new google.maps.MarkerImage(marImg + (i+1) + '.png')
	        });

	        // Allow each marker to have an info window    
	        google.maps.event.addListener(marker, 'click', (function(marker, i) {
	            return function() {
	                infoWindow.setContent(infoWindowContent[i][0]);
	                infoWindow.open(map, marker);
	            }
	        })(marker, i));

	        // Automatically center the map fitting all markers on the screen
	        map.fitBounds(bounds);
	    }

	    // Override our map zoom level once our fitBounds function runs (Make sure it only runs once)
	    var boundsListener = google.maps.event.addListener((map), 'bounds_changed', function(event) {
	        this.setZoom(13);
	        google.maps.event.removeListener(boundsListener);
	    });

	}

	</script>

</body>
</html>

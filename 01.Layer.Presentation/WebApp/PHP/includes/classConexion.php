<?
class mysql
    {
    private $host="localhost";
    private $user="root";
    private $clave="root";
    private $bd="tiendas";
    private $conexion;  //Se almacenará el apuntador a la conexion
 

	//Conexión a MySQL
	public function conectar()
	{
		$this->conexion=mysql_connect($this->host,$this->user,$this->clave);
		mysql_select_db($this->bd,$this->conexion);
	}

	//Función que regresa todas las ubicaciones 
	public function listar_ubicaciones()
	{
		$consulta="select * from ubicaciones";
		$this->conectar();
		$resultado = mysql_query($consulta);
		$this->cerrar();

		$resultSet = array();
		
		//Regresa los datos de las ubicaciones
		//Esta información aparece en el infoWindow generado en Javascript
		while($result = mysql_fetch_array($resultado))
		{
			//Manda llamar al WCF
			$resultadoconsulta = $this->llamarWCF($result['id_tienda']);
			//print_r($resultadoconsulta);

			$sincolor = substr($resultadoconsulta[3], 0, -10);
			$sincolor1 = substr($resultadoconsulta[6], 0, -10);
			$sincolor2 = substr($resultadoconsulta[9], 0, -10);

			$str = '<table><tr><td colspan="2"><h2>'.$result['nombre']."</h2></td></tr>";
			$str .= '<tr><td colspan="2"><a href="tienda.php?id_tienda='.$result['id_tienda'].'&tienda='.$result['nombre'].'">Más Información</a></td></tr>';

			$str .= '<tr><td colspan="2"><h3>'.$resultadoconsulta[1].'</h3></td></tr>';
			$str .= "<tr><td>Ponderación</td><td>Calificación</td></tr>";
			$str .= "<tr><td>".$resultadoconsulta[2]."</td><td>".$sincolor."</td></tr>";

			$str .= '<tr><td colspan="2"><h3>'.$resultadoconsulta[4].'</h3></td></tr>';
			$str .= "<tr><td>Ponderación</td><td>Calificación</td></tr>";
			$str .= "<tr><td>".$resultadoconsulta[5]."</td><td>".$sincolor1."</td></tr>";

			$str .= '<tr><td colspan="2"><h3>'.$resultadoconsulta[7].'</h3></td></tr>';
			$str .= "<tr><td>Ponderación</td><td>Calificación</td></tr>";
			$str .= "<tr><td>".$resultadoconsulta[8]."</td><td>".$sincolor2."</td></tr>";

			$str .= "</table>";

			$resultSet[] = array($result['nombre'],$result['latitud'],$result['longitud'],$str);

		}

		return $resultSet;

	}

	//Regresa las ponderaciones y calificaciones de la encuesta
	public function llamarWCF($tienda)
	{
		$servicio = "http://50.62.138.62/wcfEvalCuadra/Service1.svc/Califica/48,4," . $tienda;
		
		$data = file_get_contents($servicio);
		$array = json_decode($data, true);

		//print_r($array);

		$result = $array['GetCalifResult'];
		//echo $resultado;

		$final = explode("|", $result);

		foreach($final as $element) {
			$encuesta[] = $element;
		}

		return $encuesta;
	}
 
	//Regresa la información de una ubicación
	public function listarUbicacion($tienda)
	{

		$consulta = "SELECT * FROM ubicaciones WHERE id_tienda = " . $tienda;
		$this->conectar();
		$resultado = mysql_query($consulta);
		$this->cerrar();


		while ($r = mysql_fetch_array($resultado))
		{
			echo '<h2>Tienda no. ' . $r['id_tienda'] . '</h2>';
			echo '<h1>' . $r['nombre'] . '</h1>';
			echo '<p>' . $r['direccion'] . '</p>';
		}

	}
 
	public function cerrar ()
	{
	@mysql_close($this->conexion);
	}
}

?>
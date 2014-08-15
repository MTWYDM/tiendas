

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class CalificacionActivity extends Activity{
	String mRowTienda="";
	String mRowZona="";
	String mRowEncuesta="";
	String mRowMetodo="";
	TextView txtEstatus;
	ArrayList<Lista_entrada> datos;
	private ListView lista; 

	@Override
 public void onCreate(Bundle savedInstanceState) {
		   /** Primer Metodo que se llama cuando la Actividad es creada */
		super.onCreate(savedInstanceState);
		txtEstatus = new TextView(this);
		setContentView(txtEstatus);
		datos = new ArrayList<Lista_entrada>();  
		try{
			Bundle extras = getIntent().getExtras();
			if(extras != null )
			{	
				mRowMetodo = extras.getString("metodo");
 	    		mRowTienda = extras.getString("tienda");
 	    		mRowZona = extras.getString("zona");
 	    		mRowEncuesta = extras.getString("encuesta");
			}
        }catch(Exception err){
 			Toast.makeText(getApplicationContext(),err.toString() , Toast.LENGTH_LONG).show();
        }
		Consultar_Encuesta();
	}
	private String[] Separa_Atributos(String metodo,String str){
		str=str.replace(metodo, "");
		str=str.replace("\"","").replace(":","").replace("{","").replace("}", "").replace("\\u000a","°").replace("|", "°");
		return str.split("°");
	}
	
	public void Consultar_Encuesta()
	{
		try{
			txtEstatus.setText("Pulsando GET....");
			DefaultHttpClient httpClient=new DefaultHttpClient();
			HttpGet request= new HttpGet(mRowMetodo + mRowEncuesta + "," + mRowZona + "," + mRowTienda);
			request.setHeader("Accept","application/json");
			request.setHeader("Content-type","application/json");
			HttpResponse response=httpClient.execute(request);
			HttpEntity responseEntity=response.getEntity();
			char[] buffer = new char[(int)responseEntity.getContentLength()];
			InputStream stream=responseEntity.getContent();
			InputStreamReader reader=new InputStreamReader(stream);
			reader.read(buffer);
			stream.close();
			String str=new String(buffer);
			if(str.length()>25)
			{
				setContentView(R.layout.listado);
				String arr[]=Separa_Atributos("GetCalifResult",str);
				for(int i=0;i<arr.length-2;)
				 {
					datos.add(new Lista_entrada(android.R.drawable.ic_menu_camera, arr[i+1].toString(),arr[i+2].toString() + "  " + arr[i+3].toString()));
				 	i=i+4;
				 }
				lista = (ListView) findViewById(R.id.ListView_listado);
				lista.setAdapter(new Lista_adaptador(this, R.layout.entrada, datos){
				@Override
				public void onEntrada(Object entrada, View view) {
			        if (entrada != null) {
			            TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior); 
			            if (texto_superior_entrada != null) 
			            	texto_superior_entrada.setText(((Lista_entrada) entrada).get_textoEncima()); 

			            TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior); 
			            if (texto_inferior_entrada != null)
			            	texto_inferior_entrada.setText(((Lista_entrada) entrada).get_textoDebajo()); 

			            ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen); 
			            if (imagen_entrada != null)
			            	imagen_entrada.setImageResource(((Lista_entrada) entrada).get_idImagen());
			        }
				}
			});
			}
			else{
			txtEstatus.setText("No se encontraron registros(" + str + ")");
			}
		}catch(Throwable t){
			txtEstatus.setText("Request failed: " + t.toString());
		}
		
	}

}

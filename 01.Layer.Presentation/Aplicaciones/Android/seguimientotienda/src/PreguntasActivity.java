

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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class PreguntasActivity extends Activity{
	   /** Called when the activity is first created. */
	String mRowId="";
	String mRowNombre="";
	TextView txtEstatus;
	ArrayList<Lista_entrada> datos;
	private ListView lista; 

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textview = new TextView(this);
        txtEstatus = new TextView(this);
        setContentView(textview);
        setContentView(txtEstatus);
    	setContentView(R.layout.listado);
    	datos = new ArrayList<Lista_entrada>();  
        
        try{
            Bundle extras = getIntent().getExtras();
            if(extras != null )
    	    {	mRowNombre = extras.getString("tienda_nombre");
    	    	mRowId = extras.getString("tienda_id");
    	    }
            textview.setText(mRowId + " " + mRowNombre);
           }catch(Exception err){
    			Toast.makeText(getApplicationContext(),err.toString() , Toast.LENGTH_LONG).show();
           }
        Consultar_Encuenta();
    }
    
	private String[] Separa_Atributos(String metodo,String str){
		str=str.replace(metodo, "");
		str=str.replace("\"","").replace(":","").replace("{","").replace("}", "").replace("\\u000a","°").replace("|", "°");
		return str.split("°");
	}
	
	public void Consultar_Encuenta()
	{
		try{
			txtEstatus.setText("Pulsando GET....");
			DefaultHttpClient httpClient=new DefaultHttpClient();
			HttpGet request= new HttpGet("http://50.62.138.62/wcfEvalCuadra/Service1.svc/Preguntas/3");
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
			String arr[]=Separa_Atributos("GetPregXEncResult",str);
			for(int i=0;i<arr.length-2;)
				 {
					datos.add(new Lista_entrada(android.R.drawable.ic_menu_camera, arr[i+2].toString(),""));
				 	i=i+5;
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
		}catch(Throwable t){
			txtEstatus.setText("Request failed: " + t.toString());
		}
	}

}



import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
//import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TiendaList extends ListActivity {
	private static final int DIALOG_FILTER = 0;
	private static final int DIALOG_MANTENIMIENTO=1;
	private static final int DIALOG_DELETE = 2;
	private static final int DIALOG_ORDER = 3;

	private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    
    private static final int INSERT_ID = Menu.FIRST;
    private static final int FILTER_ID = Menu.FIRST + 1;
    private static final int SHOW_ID = Menu.FIRST + 2;
    private static final int EDIT_ID = Menu.FIRST + 3;
    private static final int DELETE_ID = Menu.FIRST + 4;
    
    private static final int ORDER_ID = Menu.FIRST + 5;
    private static final int ORDER_NUM = Menu.FIRST + 6;

    
	long mRowId;
	
    Animation fadein;
    Animation fadeout;
    int colorText;
    //RowAdapter datos;
    private ListView lista; 
    
    int lastFilter = -1;
    int auxLastFilter = -1;
    String orderList = null;
    boolean typeOrderReverse = false;
    boolean alphabeticalOrder = true;
    boolean priorityOrder = true;
    String _sTabla="moneda";
    String _sID="_id";
    String _sOrden="nombre asc";
    String _sOrdenASC="concepto asc";
    String _sOrdenDESC="concepto desc";
    
    String _sOrden2ASC="valor asc";
    String _sOrden2DESC="valor desc";
    final CharSequence[] itemsABC = {"Mostrar","Editar", "Eliminar"};
    final CharSequence[] itemsORD = {"Nombre","Valor"};
   
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
          case DIALOG_MANTENIMIENTO:
	        return new AlertDialog.Builder(this)
	        .setIcon(R.drawable.alert_dialog_icon)
	        .setTitle(R.string.alert_dialog_text_mantenimiento)
	        .setItems(itemsABC,new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	            switch(whichButton+3) {
	            	case SHOW_ID:
	            		Toast.makeText(getApplicationContext(), "" + '\n' + "", Toast.LENGTH_LONG).show();break;
	            	case EDIT_ID:editarRegistro();break;
	            	case DELETE_ID:showDialog(DIALOG_DELETE);break;
	            }
	            setResult(RESULT_OK);
	        }})
	       .create();
        }
        return null;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.listado);
                        
        fadein = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fadeout = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        
        try {
        } catch (Exception e) {
			e.printStackTrace();
		}
		fillData(lastFilter);
    }
    private void fillData(int registro_id) {
    	    	
    	ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();  

        datos.add(new Lista_entrada(R.drawable.alert_dialog_icon, "", ""));
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID, 0, R.string.menu_insert)
        	.setIcon(android.R.drawable.ic_menu_add)
            .setAlphabeticShortcut('N');
        menu.add(0, FILTER_ID, 0,  R.string.menu_filter)
        	.setIcon(android.R.drawable.ic_menu_view)
            .setAlphabeticShortcut('F');;
        menu.add(0, ORDER_ID, 0,  R.string.menu_order)
      	.setIcon(android.R.drawable.ic_menu_sort_alphabetically)
        .setAlphabeticShortcut('O');
        return true;
    }
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
        case INSERT_ID: //agregarRegistro();
            return true;
        case FILTER_ID: showDialog(DIALOG_FILTER);
            return true;
        case ORDER_ID: showDialog(DIALOG_ORDER);
            return true;     
        	
        }
        return super.onMenuItemSelected(featureId, item);
    }
    private void editarRegistro() {
		Intent i = new Intent();//this, MonedaEdit.class);
		//i.putExtra(TableDBart.KEY_ID, currentEntity.getId());
		startActivityForResult(i, ACTIVITY_EDIT);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mRowId = position;
		showDialog(DIALOG_MANTENIMIENTO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData(lastFilter);
    }
}
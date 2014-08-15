


import android.app.TabActivity;
import android.content.Intent;
//import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
//import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.TabHost;

public class MainTabWidget extends TabActivity {
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.main);
        
        Resources res = getResources();     
        TabHost tabHost = getTabHost();     
        TabHost.TabSpec spec;     
        Intent intent;  
        intent = new Intent().setClass(this, CuentasActivity.class);    
        spec = tabHost.newTabSpec("tiendas").setIndicator("Tiendas",                      
        		res.getDrawable(android.R.drawable.ic_menu_agenda)).setContent(intent);    
        tabHost.addTab(spec);       
        intent = new Intent().setClass(this, HerramientasActivity.class);    
        spec = tabHost.newTabSpec("herramientas").setIndicator("Herramientas",                      
        		res.getDrawable(android.R.drawable. ic_menu_manage )).setContent(intent);    
        tabHost.addTab(spec);      
        intent = new Intent().setClass(this, EvaluacionActivity.class);    
        spec = tabHost.newTabSpec("evaluacion").setIndicator("Evaluacion",                      
        		res.getDrawable(android.R.drawable.ic_menu_myplaces)).setContent(intent);    
        tabHost.addTab(spec);    
        intent = new Intent().setClass(this, GraficasActivity.class);    
        spec = tabHost.newTabSpec("home").setIndicator("Home",                      
        		res.getDrawable(android.R.drawable.ic_dialog_dialer)).setContent(intent);    
        tabHost.addTab(spec);
        tabHost.setCurrentTab(2);
    }


}
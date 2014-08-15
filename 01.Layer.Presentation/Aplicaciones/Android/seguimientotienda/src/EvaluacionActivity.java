

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;

public class EvaluacionActivity extends Activity implements Runnable{
	private static final int DIALOG_ERROR = 1;
	private static final int ACTIVITY_EDIT=1;
	 
	private static final int SAVE_ID = Menu.FIRST + 0;
	private static final int CLEAN_ID = Menu.FIRST + 1;
	private static final int BACK_ID = Menu.FIRST + 2;

	private EditText mMetodoText;
    private EditText mTiendaText;
    private EditText mZonaText;
    private EditText mEncuestaText;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.leerevaluacion);
       
        mMetodoText = (EditText) findViewById(R.id.txtmetodo);
        mTiendaText = (EditText) findViewById(R.id.txttienda);
        mZonaText = (EditText) findViewById(R.id.txtzona);
        mEncuestaText = (EditText) findViewById(R.id.txtnumencuesta);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, SAVE_ID, 0,  R.string.save)
        	.setIcon(android.R.drawable.ic_menu_save)
            .setAlphabeticShortcut('E');
        menu.add(0, CLEAN_ID, 0,  R.string.clean)
    		.setIcon(android.R.drawable.ic_menu_recent_history)
    		.setAlphabeticShortcut('D');
        menu.add(0, BACK_ID, 0,  R.string.back)
			.setIcon(android.R.drawable.ic_menu_revert)
			.setAlphabeticShortcut('B');
        return true;
    }
    
    public void consultarevaluacion_click(View view){
    	Intent i = new Intent(this, CalificacionActivity.class);
		i.putExtra("metodo", mMetodoText.getText().toString());
		i.putExtra("encuesta", mEncuestaText.getText().toString());
		i.putExtra("zona", mZonaText.getText().toString());
		i.putExtra("tienda", mTiendaText.getText().toString());
		startActivityForResult(i, ACTIVITY_EDIT);
    	
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
        case SAVE_ID:
        	if (mTiendaText.getText().toString().equals("") ) {
        		showDialog(DIALOG_ERROR);
        	} else {
                setResult(RESULT_OK);
                finish();
        	}
            return true;
        case CLEAN_ID:
        	mMetodoText.setText("");
        	mTiendaText.setText("");
        	mZonaText.setText("");
        	mEncuestaText.setText("");
        	return true;
        case BACK_ID:
        	setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    private void saveState() {
    }
	public void run() {
	}
}

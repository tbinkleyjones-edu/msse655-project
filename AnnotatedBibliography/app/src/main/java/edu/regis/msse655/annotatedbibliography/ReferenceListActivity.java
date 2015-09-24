package edu.regis.msse655.annotatedbibliography;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * The primary or main Activity for the application.
 *
 * The Activity display a list of Reference objects (via a child ReferenceListFragment).
 *
 * The Activity also handles a request to open a bib file.
 */
public class ReferenceListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_list);

        // TODO: add support for bib file import
        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction()) || Intent.ACTION_EDIT.equals(intent.getAction())) {
            Toast.makeText(getApplicationContext(), "BibTex .bib file import is not implemented!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reference_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * onClick handler for the Hide action bar menu item.
     * @param item
     */
    public void onClickHide(MenuItem item) {
        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }
}

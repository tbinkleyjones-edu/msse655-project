package edu.regis.msse655.annotatedbibliography;

import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import edu.regis.msse655.annotatedbibliography.model.ReferenceFilter;

/**
 * The primary or main Activity for the application.
 * <p/>
 * The Activity display a list of Reference objects (via a child ReferenceListActivityFragment).
 * <p/>
 * The Activity also handles a request to open a bib file.
 */
public class ReferenceListActivity extends AppCompatActivity {

    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ReferenceListActivityFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_list);

        // TODO: add support for bib file import
        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction()) || Intent.ACTION_EDIT.equals(intent.getAction())) {
            Toast.makeText(getApplicationContext(), "BibTex .bib file import is not implemented!", Toast.LENGTH_LONG).show();
        }

        // load the string displayed in the navigation drawer
        drawerListViewItems = getResources().getStringArray(R.array.items);

        // create a adapter for the list view defined in the navigation drawer layout
        drawerListView = (ListView) findViewById(R.id.left_drawer);
        drawerListView.setAdapter(
                new ArrayAdapter<String>(this, R.layout.drawer_listview_item, drawerListViewItems));

        // set navigation drawer click event handlers
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());

        // add a toggle button to the drawer layout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.drawer_open, R.string.drawer_close);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        actionBarDrawerToggle.syncState();

        // remember the list fragment for use by the click handler
        listFragment = (ReferenceListActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

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

        // this block neables the nav drawer
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch (position) {
                case 0: // All
                    listFragment.filterList(ReferenceFilter.ALL);
                    break;
                case 1: // Recent
                    listFragment.filterList(ReferenceFilter.RECENT);
                    break;
                case 2: // Favorites
                    listFragment.filterList(ReferenceFilter.FAVORITES);
                    break;
                default:
                    // Create a TOAST message
                    Toast.makeText(ReferenceListActivity.this,
                            ((TextView) view).getText(),
                            Toast.LENGTH_LONG).show();
            }

            // Close the nav drawer
            drawerLayout.closeDrawer(drawerListView);
        }
    }
}

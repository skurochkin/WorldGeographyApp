package com.ninjawebzen.worldgeographyapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AbsListView.MultiChoiceModeListener {

    private ArrayList<String> countries;
    private ArrayList<String> selected;

    private ArrayAdapter<String> adapter;
    private ListView listView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selected = new ArrayList<String>();
        countries = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.countries_array)));
        countries.addAll(Arrays.asList(getResources().getStringArray(R.array.ficticious_countries)));

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, countries);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }else if(id == R.id.action_refresh) {
            countries.clear();
            countries.addAll(Arrays.asList(getResources().getStringArray(R.array.countries_array)));
            countries.addAll(Arrays.asList(getResources().getStringArray(R.array.ficticious_countries)));
            adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        if(checked){
            selected.add(adapter.getItem(position));
        }else{
            selected.remove(adapter.getItem(position));
        }
        mode.setTitle(String.valueOf(selected.size()));
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cab, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_delete:
                for(String country : selected){
                    countries.remove(country);
                }
                adapter.notifyDataSetChanged();
                mode.finish();
                return true;
            default:
                return false;
        }

    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

        Toast.makeText(this, "Removed " + selected.size() + " items", Toast.LENGTH_SHORT).show();
        selected.clear();
    }
}

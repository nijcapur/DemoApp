package com.example.nijkap01.demoapp;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class TrackActivity extends Activity
         {

    // This is the Adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;
    ArrayList<Tracker> listTracker=new ArrayList<Tracker>(90);
    // These are the Contacts rows that we will retrieve
    static final String[] PROJECTION = new String[] {ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME};

    // This is the select criteria
    static final String SELECTION = "((" +
            ContactsContract.Data.DISPLAY_NAME + " NOTNULL) AND (" +
            ContactsContract.Data.DISPLAY_NAME + " != '' ))";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracklist);
        final ListView listview = (ListView) findViewById(R.id.list);
        Tracker tracker1=new Tracker();
        Tracker tracker2=new Tracker();
        listTracker=new ArrayList<>(90);

        tracker1.trackerName = "Tracker1";
        tracker2.trackerName = "Tracker2";
        String[] values = new String[] {tracker1.trackerName , tracker2.trackerName };
        listTracker.add(0,tracker1);
        listTracker.add(1,tracker2);
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                final Iterator<Tracker> it = listTracker.iterator();

                while (it.hasNext()) {
                    Tracker tracker = it.next();

                    if (tracker.trackerName.equals(item)) {
                        Intent i = new Intent(TrackActivity.this, LoadTrackerMap.class);
                        i.putExtra("tracker", item);
                        startActivity(i);
                        break;
                    }
                }



            }

        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        // Create a progress bar to display while the list loads
        //ProgressBar progressBar = new ProgressBar(this);
        //progressBar.setLayoutParams(new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT,
          //      DrawerLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        //progressBar.setIndeterminate(true);
        //getListView().setEmptyView(progressBar);


        // For the cursor adapter, specify which columns go into which views
        //String[] fromColumns = {ContactsContract.Data.DISPLAY_NAME};
        //int[] toViews = {android.R.id.text1}; // The TextView in simple_list_item_1

        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        //mAdapter = new SimpleCursorAdapter(this,
          //      android.R.layout.simple_list_item_1, null,
            //    fromColumns, toViews, 0);
        //setListAdapter(mAdapter);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
     //   getLoaderManager().initLoader(0, null, this);
    }
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}

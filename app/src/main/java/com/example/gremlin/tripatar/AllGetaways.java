package com.example.gremlin.tripatar;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gremlin.dto.Data;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AllGetaways extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private void renderAddGetAway() {
        Intent intent = new Intent(this, AddGetaway.class);
        startActivity(intent);
    }

    public ArrayList<Data> getData() {
        ParseObject testObject = new ParseObject("getaways");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("getaways");
        query.orderByAscending("traveldate");
        ArrayList<Data> dataList = new ArrayList<Data>();
        try{
            List<ParseObject> objList = query.find();
            System.out.println(objList.size());
            //Intent intent = new Intent(this,ListViewDetails.class);
            Bundle bundle = new Bundle();

            for(ParseObject obj : objList){

                Data d =  new Data();
                d.setObjectId((String)obj.getObjectId());
                d.setDescription((String) obj.get("description"));
                d.setTitle((String) obj.get("title"));
                Number duration = (Number)obj.get("duration");
                int days = (Integer)duration/(60*24);
                int min = (Integer)duration - (days*60*24);
                d.setDuration(days + " Days"+" "+min+" Min");
                d.setDate((Date) obj.get("traveldate"));
                d.setPrice(((Number)obj.get("price")).toString());
                ParseFile fileObject = (ParseFile)obj.get("cover");
                d.setCoverFile((File)fileObject.getFile());
                dataList.add(d);
            }
            //intent.putExtra("data",dataList);
            //startActivity(intent);
        }catch(Exception e){
            e.printStackTrace();
        }

        return dataList;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("All Getaways", "=======================");

        setContentView(R.layout.activity_all_getaways);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renderAddGetAway();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Resources res = getResources();
        final ArrayList<Data> dataList = getData();

        CustomUsersAdapter adapter = new CustomUsersAdapter(this, dataList);
        // Attach the adapter to a ListView
        ListView listView = (ListView)findViewById(R.id.lvUsers);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String objId = dataList.get(position).getObjectId();

                ParseObject testObject = new ParseObject("getaways");
                ParseQuery<ParseObject> query = ParseQuery.getQuery("getaways").whereEqualTo("objectId", objId);
                List<ParseObject> objList = null;
                try {
                    objList = query.find();
                } catch (Exception e) {
                }


                Data d = new Data();
                d.setObjectId((String) objList.get(0).getObjectId());
                d.setDescription((String) objList.get(0).get("description"));
                d.setTitle((String) objList.get(0).get("title"));
                Number duration = (Number) objList.get(0).get("duration");
                int days = (Integer) duration / (60 * 24);
                int min = (Integer) duration - (days * 60 * 24);
                d.setDuration(days + " Days" + " " + min + " Min");
                d.setDate((Date) objList.get(0).get("traveldate"));
                d.setPrice(((Number) objList.get(0).get("price")).toString());
                ParseFile fileObject = (ParseFile) objList.get(0).get("cover");
                try {
                    d.setCoverFile((File) fileObject.getFile());
                } catch (Exception e) {
                }
                Intent intent = new Intent(AllGetaways.this, Navigation.class);
                intent.putExtra("getawayData", d);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.all_getaways, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class CustomUsersAdapter extends ArrayAdapter<Data> {
        public CustomUsersAdapter(Context context, ArrayList<Data> dataList) {
            super(context, 0, dataList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Data data = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_data, parent, false);
            }
            // Lookup view for data population
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            TextView tvDuration = (TextView) convertView.findViewById(R.id.tvDuration);
            TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            // Populate the data into the template view using the data object
            tvTitle.setText(data.getTitle());
            tvDuration.setText(data.getDuration());
            tvPrice.setText("$ " + data.getPrice());
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
            tvDate.setText(sdf.format(data.getDate()));
            ImageView mImageView = (ImageView) convertView.findViewById(R.id.imageView);
            Bitmap myBitmap = BitmapFactory.decodeFile(data.getCoverFile().getAbsolutePath());
            mImageView.setImageBitmap(myBitmap);

            // Return the completed view to render on screen
            return convertView;
        }
    }
}

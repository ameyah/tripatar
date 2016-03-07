package com.example.gremlin.tripatar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gremlin.dto.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Intent intent = getIntent();
        final ArrayList<Data> dataList = (ArrayList<Data>) intent.getSerializableExtra("data");
        CustomUsersAdapter adapter = new CustomUsersAdapter(this, dataList);
        // Attach the adapter to a ListView
        ListView listView = (ListView)findViewById(R.id.lvUsers);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(MainScreen.this, Navigation.class);
                String message = "abc";
                intent.putExtra("hello", message);
                startActivity(intent);
            }
        });
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

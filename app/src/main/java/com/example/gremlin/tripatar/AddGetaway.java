package com.example.gremlin.tripatar;

    import android.app.ActionBar;
        import android.app.AlertDialog;
        import android.app.DatePickerDialog;
        import android.app.Dialog;
        import android.app.DialogFragment;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.CursorLoader;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.database.Cursor;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.Environment;
        import android.provider.MediaStore;
        import android.support.design.widget.TextInputLayout;
        import android.support.v4.app.NavUtils;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.InputType;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.CompoundButton;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Spinner;
        import android.widget.Switch;
        import android.widget.Toast;
        import android.widget.ToggleButton;

        import com.parse.ParseFile;
        import com.parse.ParseObject;
        import com.parse.SaveCallback;
        import com.parse.ParseException;

        import java.io.ByteArrayOutputStream;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Locale;

public class AddGetaway extends AppCompatActivity {

    private EditText travelDateExt;
    private DatePickerDialog travelDatePickerDialog;
    private static int RESULT_LOAD_IMG = 1;
    public static final int SELECT_FILE = 1 ;
    public static final int REQUEST_CAMERA = 0;
    public ProgressDialog pd;
    String imgDecodableString;
    Switch toggle;
    Spinner spinner;
    Calendar travelDate;
    ParseFile cover;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // BEGIN_INCLUDE (inflate_set_custom_view)
        // Inflate a "Done/Cancel" custom action bar view.
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View customActionBarView = inflater.inflate(
                R.layout.actionbar_custom_view_done_cancel, null);
        customActionBarView.findViewById(R.id.actionbar_done).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // "Done"
                        saveGetaway();
                    }
                });
        customActionBarView.findViewById(R.id.actionbar_cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // "Cancel"
                        finish();
                    }
                });

        // Show the custom action bar view and hide the normal Home icon and title.
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();



        actionBar.setDisplayOptions(
                android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM,
                android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM | android.support.v7.app.ActionBar.DISPLAY_SHOW_HOME
                        | android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(customActionBarView,
                new android.support.v7.app.ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        // END_INCLUDE (inflate_set_custom_view)

        setContentView(R.layout.activity_add_getaway);

        setDurationDropdown();
        setToggleButton();
        setDateTimeField();
    }

    private void refresh() {
        Intent in = new Intent(this, AllGetaways.class);
        startActivity(in);
    }

    private void showProgressBar() {
        pd = new ProgressDialog(this);
        pd.setTitle("Saving the Getaway !");
        pd.setMessage("Please wait....");
        pd.setCancelable(false);
        pd.show();
    }

    private void setDurationDropdown( ) {
        spinner = (Spinner) findViewById(R.id.slctDuration);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.duration_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void setToggleButton() {
        toggle = (Switch) findViewById(R.id.slctIsPaid);

        final TextInputLayout input_layout_price = (TextInputLayout) findViewById(R.id.input_layout_price);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    input_layout_price.setVisibility(View.VISIBLE);
                } else {
                    input_layout_price.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setDateTimeField() {

        travelDateExt = (EditText) findViewById(R.id.txtTravelDate);

        travelDateExt.setInputType(InputType.TYPE_NULL);

        travelDateExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTravelDateClick();
            }
        });

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        Calendar newCalendar = Calendar.getInstance();
        travelDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                travelDate = Calendar.getInstance();
                travelDate.set(year, monthOfYear, dayOfMonth);
                travelDateExt.setText(dateFormatter.format(travelDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void onTravelDateClick() {
        travelDatePickerDialog.show();
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void loadImageFromGallery(View view) {
        selectImage();
    }


    public void SaveParseObject(ParseObject getaway) {
        getaway.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    pd.dismiss();
                    //finish();
                    refresh();
                } else {
                    pd.dismiss();
                    showAlert("Error", "Error creating the getaway. Retry");
                }
            }
        });
    }

    public void saveGetaway() {

        final ParseObject getaway = new ParseObject("getaways");
        EditText title = (EditText) findViewById(R.id.txtTitle);
        EditText description = (EditText) findViewById(R.id.txtDescription);

        if(title.getText().toString().length() == 0) {
            showAlert("Required", "Please give title");
            return;
        } else if (description.getText().toString().length() == 0) {
            showAlert("Required", "Please give description");
            return;
        }

        getaway.put("title",title.getText().toString());
        getaway.put("description", description.getText().toString());

        if(travelDate == null) {
            showAlert("Required", "Please select travel date");
            return;
        }
        getaway.put("traveldate", travelDate.getTime());

        boolean isPaid = toggle.isChecked();

        getaway.put("ispaid", isPaid);


        if(isPaid) {
            EditText price = ((EditText) findViewById(R.id.txtPrice));
            if(title.getText().toString().length() == 0) {
                showAlert("Required", "Please enter price");
                return;
            }
            Integer inPrice = Integer.parseInt(price.getText().toString());
            getaway.put("price", inPrice);
        } else {
            getaway.put("price", 0);
        }

        int spinner_pos = spinner.getSelectedItemPosition();
        String[] size_values = getResources().getStringArray(R.array.duration_values);
        int duration = Integer.valueOf(size_values[spinner_pos]);

        getaway.put("duration", duration);

        showProgressBar();

        if(cover == null) {
            showAlert("Required", "Please select cover image");
            return;
        } else {
            cover.saveInBackground(new SaveCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        getaway.put("cover", cover);
                        SaveParseObject(getaway);
                    } else {
                        pd.dismiss();
                        showAlert("Error", "Error uploading file");
                    }
                }
            });
        }
    }

    public void showAlert(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(AddGetaway.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imgView = (ImageView) findViewById(R.id.imgView);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");

                cover = new ParseFile(destination.getName(), bytes.toByteArray());

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imgView.setImageBitmap(thumbnail);
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                imgView.setImageBitmap(bm);

                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();

                cover = new ParseFile(System.currentTimeMillis() + ".png", image);
            }
        }
    }
}

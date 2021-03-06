package grocery.vajaralabs.com.grocery;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class AddPill extends AppCompatActivity {
    private ImageView imf_set_image;

    private Button btn_submit;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    //    private ImageView ivImage;
    private String userChoosenTask;
    private File destination;
    private EditText edt_date;
    private EditText edt_time, edt_purpose;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Button btn_ack;
    private ProgressDialog progressBar;
    private EditText edt_Prescription, edt_pill_name;
    private EditText edt_no_pills;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_reminder);
        progressBar = new ProgressDialog(this);
        imf_set_image = (ImageView) findViewById(R.id.img_photo);
//        edt_title = (EditText) findViewById(R.id.edt_title);
//        edt_content = (EditText) findViewById(R.id.edt_content);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        edt_date = (EditText) findViewById(R.id.edt_date);
        edt_time = (EditText) findViewById(R.id.edt_time);
        edt_purpose = (EditText) findViewById(R.id.edt_purpose);
        edt_no_pills = (EditText) findViewById(R.id.edt_no_pills);
        edt_Prescription = (EditText) findViewById(R.id.edt_Prescription);
        edt_pill_name = (EditText) findViewById(R.id.edt_pill_name);


        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPill.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                edt_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
        edt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddPill.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                edt_time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        imf_set_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Appointment.this, ""+getIntent().getStringExtra("pid"), Toast.LENGTH_SHORT).show();

                if (!edt_date.getText().toString().equals("")) {
                    if (destination != null) {
                        if (!edt_Prescription.getText().toString().equals("")) {
                            progressBar = new ProgressDialog(AddPill.this);
                            new UploadFileToServer().execute();
                        } else {
                            Toast.makeText(AddPill.this, "choose date and time ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddPill.this, "choose image to post", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(AddPill.this, "fill the required fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddPill.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(AddPill.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == AddPill.this.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        System.out.println("destination" + destination);

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

//        imf_set_image.setImageBitmap(thumbnail);

//        Bitmap b = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//        imf_set_image.setImageBitmap(Bitmap.createScaledBitmap(thumbnail, 600, 600, false));

        imf_set_image.setImageBitmap(thumbnail);

//        progressBar = new ProgressDialog(getActivity());
//        new UploadFileToServer().execute();
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(AddPill.this.getContentResolver(), data.getData());


                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");

                System.out.println("destination_gallery" + destination);

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

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        imf_set_image.setImageBitmap(bm);
    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            super.onPreExecute();
            progressBar.show();
            progressBar.setCancelable(false);
            progressBar.setMessage("Loading...");

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
//            progressBar.setVisibility(View.VISIBLE);
//
//            // updating progress bar value
//            progressBar.setProgress(progress[0]);

            // updating percentage value
//            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://vajralabs.com/Taxi/add_pill.php");

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
//                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

//                File sourceFile = new File(destination);

                // Adding file data to http body
                final SharedPreferences prfs = AddPill.this.getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);

                entity.addPart("image", new FileBody(destination));

                // Extra parameters if you want to pass to server
                entity.addPart("pill",
                        new StringBody(edt_pill_name.getText().toString()));
                entity.addPart("purpose", new StringBody(edt_purpose.getText().toString()));
                entity.addPart("date", new StringBody(edt_date.getText().toString()));
                entity.addPart("number", new StringBody(edt_no_pills.getText().toString()));
                entity.addPart("pid", new StringBody(getIntent().getStringExtra("pid")));
                entity.addPart("prescrption", new StringBody(edt_Prescription.getText().toString()));


//                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

//            Log.e(TAG, "Response from server: " + result);

            System.out.println("Response from server: " + result);

            progressBar.dismiss();
            // showing the server response in an alert dialog
            finish();
            Toast.makeText(getApplicationContext(), "Successfully Sent.", Toast.LENGTH_SHORT).show();


////            showAlert("sent Successfully.");
//            edt_date.getText().clear();
//            edt_time.getText().clear();
//            edt_problem.getText().clear();
////            edt_content.getText().clear();
//            imf_set_image.setImageDrawable(null);
////            edt_title.requestFocus();

        }

    }
}

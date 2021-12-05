package com.example.dark.a99app.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dark.a99app.EditProfile;
import com.example.dark.a99app.Login;
import com.example.dark.a99app.R;
import com.example.dark.a99app.SessionManager.SessionManager;
import com.example.dark.a99app.Singletons.RequestQueues;
import com.example.dark.a99app.Volley.helpRequest;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class UserDashboard extends AppCompatActivity {

    FirebaseStorage storage;
    StorageReference storageReference;

    CheckBox ambulance,police,fire,traffic,med_access;

    ImageView logoff,edit_prof,voice;
    TextInputLayout comment;

    Button help,history;

    MediaRecorder mRecorder;
    String mFileName=null;

    Place place;

    String latitude,longitude;

    String randoimID;

    ArrayList<String> dept_id = new ArrayList<>();

    String COMMENT,time;
    int PLACE_PICKER_REQUEST = 1;

    String access ="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        initialize();
        history = (Button)findViewById(R.id.history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDashboard.this,History.class));
                finish();
            }
        });
        work();
    }


    private void work() {

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch(event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                randoimID = UUID.randomUUID().toString();
                                mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
                                mFileName += "/"+randoimID+".3gp";

                                startRecording();
                                Toast.makeText(UserDashboard.this, "Recording started.", Toast.LENGTH_SHORT).show();
                                return true;
                            case MotionEvent.ACTION_UP:
                                stopRecording();
                                Toast.makeText(UserDashboard.this, "Recording stopped.", Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return false;
                    }
                });
            }
        });



        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    startActivityForResult(builder.build(UserDashboard.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void helprequest() {

        time = Calendar.getInstance().getTime().toString();

        if(comment.getEditText().getText().toString()!=""){
            COMMENT = comment.getEditText().getText().toString();
        }

        if(!ambulance.isChecked() && !fire.isChecked() && !police.isChecked() && !traffic.isChecked()){
            Toast.makeText(this, "لم يتم تحديد جهه", Toast.LENGTH_SHORT).show();
            return;
        }

        if(med_access.isChecked()){
            access = "1";
        }

        if(ambulance.isChecked()){
            dept_id.add("2");
        }
        if(fire.isChecked()){
            dept_id.add("1");
        }
        if(police.isChecked()){
            dept_id.add("3");
        }
        if(traffic.isChecked()){
            dept_id.add("4");
        }

       final ProgressDialog progressDialog = new ProgressDialog(UserDashboard.this);
        progressDialog.setTitle("الرجاء الانتظار");
        progressDialog.setMessage("طلب المساعده قيد التنفيذ");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if(mFileName!=null){

            storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        StorageReference ref = storageReference.child("voice_notes/"+UUID.randomUUID().toString());
        Uri uri = Uri.fromFile(new File(mFileName));
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String url = taskSnapshot.getDownloadUrl().toString();
                helpRequest request = new helpRequest(new SessionManager(UserDashboard.this).getId(), dept_id, latitude, longitude, COMMENT, url, time,access
                        , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            if(new JSONObject(response).getBoolean("status")){

                                Toast.makeText(UserDashboard.this, "تم ارسال طلبكم", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(UserDashboard.this,UserDashboard.class));
                            }else{
                                Toast.makeText(UserDashboard.this, "تعذر ارسال الطلب.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            Log.d("Exception", e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(UserDashboard.this, "Volley error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueues.getInstance(UserDashboard.this).addToRequestQue(request);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(UserDashboard.this, "firebase exception"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });}else{

            helpRequest request = new helpRequest(new SessionManager(UserDashboard.this).getId(), dept_id, latitude, longitude, COMMENT, "none", time,access
                    , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        progressDialog.dismiss();
                        if(new JSONObject(response).getBoolean("status")){
                            Toast.makeText(UserDashboard.this, "تم ارسال طلبكم", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(UserDashboard.this,UserDashboard.class));
                        }else{
                            Toast.makeText(UserDashboard.this, "تعذر ارسال الطلب.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        Log.d("Exception", e.getMessage());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(UserDashboard.this, "Volley error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueues.getInstance(UserDashboard.this).addToRequestQue(request);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == PLACE_PICKER_REQUEST) {
                if(resultCode == RESULT_OK) {
                    place = PlacePicker.getPlace(this, data);

                    latitude = String.valueOf(place.getLatLng().latitude);
                    longitude = String.valueOf(place.getLatLng().longitude);
                    helprequest();
                }
        }
    }

    private void startRecording() {
        try {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(mFileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            mRecorder.prepare();
        }
         catch (Exception e) {
            Log.e("RecordException", "prepare() failed "+e.getMessage());
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    private void initialize() {

        med_access = (CheckBox)findViewById(R.id.med_access);
        traffic = (CheckBox)findViewById(R.id.traffic);
        ambulance = (CheckBox)findViewById(R.id.ambulance);
        fire = (CheckBox)findViewById(R.id.fire);
        police = (CheckBox)findViewById(R.id.police);

        ambulance.setChecked(true);

        voice = (ImageView)findViewById(R.id.voice_note);
        comment = (TextInputLayout)findViewById(R.id.comment);

        comment.getEditText().setText("none");

        help = (Button)findViewById(R.id.help);

        logoff = (ImageView)findViewById(R.id.logoff_user);
        edit_prof = (ImageView)findViewById(R.id.edit_prof_user);

        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SessionManager(UserDashboard.this).Logout();
                startActivity(new Intent(UserDashboard.this, Login.class));
                finish();
            }
        });

        edit_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDashboard.this, EditProfile.class));
            }
        });
    }
}

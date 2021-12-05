package com.example.dark.a99app.Employee;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dark.a99app.R;
import com.example.dark.a99app.SessionManager.SessionManager;
import com.example.dark.a99app.Singletons.RequestQueues;
import com.example.dark.a99app.User.UserDashboard;
import com.example.dark.a99app.Volley.helpRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.UUID;

public class ShowRequest extends AppCompatActivity {

    TextView name;
    Button show_location,respond;
    EditText comment;

    ImageView play_audio;

    Button show_med;

    MediaPlayer mPlayer;

    String status;

    String NAME,LAT,LON,VN,COMMENT,Req,USER_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_request);
        initiliaze();
        setvalues();
        work();



    }

    private void setvalues() {

        NAME = getIntent().getStringExtra("name");
        LAT = getIntent().getStringExtra("lat");
        LON = getIntent().getStringExtra("lon");
        VN = getIntent().getStringExtra("vn");
        COMMENT = getIntent().getStringExtra("comment");
        Req = getIntent().getStringExtra("req_id");
        USER_id = getIntent().getStringExtra("user_id");

        name.setText(NAME);

        show_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "geo:%s,%s?q=%s,%s", LAT, LON,LAT,LON);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        show_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShowRequest.this,ViewMedHistory.class);
                intent.putExtra("ID",USER_id);
                startActivity(intent);

            }
        });

        comment.setText(COMMENT);
    }

    private void work() {

        /*if(VN != ""){

            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference httpsReference = storage.getReferenceFromUrl(VN);


            try {
                localFile = File.createTempFile("Audio","3gp",);
            } catch (IOException e) {
                Toast.makeText(this, "Exception"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            httpsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(ShowRequest.this, "file fetched", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(ShowRequest.this, "Exception"+exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }*/

        play_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(VN.equals("none")){
                    Toast.makeText(ShowRequest.this, "لا يوجد تسجيل صوتي", Toast.LENGTH_SHORT).show();
                }


                   mPlayer = new MediaPlayer();
                   try {
                       mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                       mPlayer.setDataSource(VN);
                       mPlayer.prepare();
                       mPlayer.start();

                   } catch (IOException e) {
                       Log.e("Playing audio", "prepare() failed");
                   }

            }
        });
respond.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowRequest.this);
        builder.setTitle("Choose action!");
        builder.setMessage("What do you want to do?");
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                status = "approved";
                responserequest();
            }
        });
        builder.setNegativeButton("Disapprove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                status="dissaproved";
                responserequest();
            }
        });
        builder.show();
    }
});
        }

    private void responserequest() {
        final ProgressDialog progressDialog = new ProgressDialog(ShowRequest.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Responding to request");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Toast.makeText(this, Req, Toast.LENGTH_SHORT).show();
        helpRequest  request = new helpRequest(Req, status, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
progressDialog.dismiss();
                try {
                    if(new JSONObject(response).getBoolean("status")){
                        Toast.makeText(ShowRequest.this, "Responded to request.", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(ShowRequest.this,EmployeeDashboard.class));
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Log.i("Exception", e.getMessage());
                                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("Volley erro", error.getMessage());
            }
        });
        RequestQueues.getInstance(ShowRequest.this).addToRequestQue(request);

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(ShowRequest.this, EmployeeDashboard.class));
    }

    private void initiliaze() {

        name = (TextView)findViewById(R.id.user_name);
        show_location = (Button)findViewById(R.id.show_location);
        respond = (Button)findViewById(R.id.respond);
        show_med = (Button)findViewById(R.id.show_med);
        show_med.setVisibility(View.INVISIBLE);
        if(new SessionManager(ShowRequest.this).getDept_id().equals("2")){
            show_med.setVisibility(View.VISIBLE);
        }

        play_audio = (ImageView)findViewById(R.id.play_audio);

        comment = (EditText)findViewById(R.id.comment_emer);
        comment.setClickable(false);
    }
}

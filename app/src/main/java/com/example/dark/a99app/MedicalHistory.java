package com.example.dark.a99app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.dark.a99app.Singletons.RequestQueues;
import com.example.dark.a99app.Volley.RegisterRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class MedicalHistory extends AppCompatActivity {

    CheckBox heart,bloodPressure,diabetes,other;

    Button sumbit;

    EditText other_Details_text;

    FirebaseStorage storage;
    StorageReference storageReference;

    RadioButton apos,aneg,bpos,bneg,opos,oneg,abpos,abneg;

    String USER_ID,USERNAME,NAME,PASS,ADDRESS,NATIONAL_ID,PHONE,OTHER_DETAILS,BLOOD_GROUP;
    Uri filepath;

    RadioGroup group;
    ArrayList<String> diseases = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);
        initiliaze();
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radbutton = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(radbutton);
                int idx = radioGroup.indexOfChild(radioButton);

                RadioButton r = (RadioButton)  radioGroup.getChildAt(idx);
                BLOOD_GROUP = r.getText().toString();

            }
        });
        if(getIntent().getBooleanExtra("value",false)){
            sumbit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(group.getCheckedRadioButtonId()<=0){
                        Toast.makeText(MedicalHistory.this, "Please select blood group.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(heart.isChecked()){
                        diseases.add("Heart diesease");
                    }
                    if(bloodPressure.isChecked()){
                        diseases.add("Blood Pressure");
                    }if(diabetes.isChecked()){
                        diseases.add("Diabetes");

                    }
                    if(other.isChecked()){
                        OTHER_DETAILS = other_Details_text.getText().toString();
                        if(OTHER_DETAILS.equals("") || OTHER_DETAILS.equals(" ")){
                            Toast.makeText(MedicalHistory.this, "Empty feild not allowed", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }else{
                        OTHER_DETAILS = "none";
                    }
                USER_ID = getIntent().getStringExtra("user_id");
                    EditMedHistory();
                }
            });
        }else{
            setValues();
            work();
        }

    }

    private void EditMedHistory() {

        final ProgressDialog progressDialog = new ProgressDialog(MedicalHistory.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Updating medical history");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RegisterRequest request = new RegisterRequest(USER_ID, diseases, OTHER_DETAILS, BLOOD_GROUP,"lol", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Response", response.toString());

                try {
                    if(new JSONObject(response).getBoolean("status")){
                        progressDialog.dismiss();
                        Toast.makeText(MedicalHistory.this, "Medical history updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MedicalHistory.this,Login.class));
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(MedicalHistory.this, "Medical history update failed.", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(MedicalHistory.this, "Exception"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MedicalHistory.this, "Volley"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueues.getInstance(MedicalHistory.this).addToRequestQue(request);

    }

    private void work() {

        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(group.getCheckedRadioButtonId()<=0){
                    Toast.makeText(MedicalHistory.this, "Please select blood group.", Toast.LENGTH_SHORT).show();
                    return;
                }

            if(heart.isChecked()){
                diseases.add("Heart diesease");
            }
            if(bloodPressure.isChecked()){
                diseases.add("Blood Pressure");
            }if(diabetes.isChecked()){
                diseases.add("Diabetes");

            }
            if(other.isChecked()){
                OTHER_DETAILS = other_Details_text.getText().toString();
                if(OTHER_DETAILS.equals("") || OTHER_DETAILS.equals(" ")){
                    Toast.makeText(MedicalHistory.this, "Empty feild not allowed", Toast.LENGTH_SHORT).show();
                    return;
                }
            }else{
                OTHER_DETAILS = "none";
            }


                registerUser();
            }
        });


    }

    private void registerUser() {

        if(diseases.size() == 0){diseases.add("none");};

        final ProgressDialog progressDialog = new ProgressDialog(MedicalHistory.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Registering user");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if(filepath!=null){

            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();

            StorageReference ref = storageReference.child("user_images/" + UUID.randomUUID().toString());
            ref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String url = taskSnapshot.getDownloadUrl().toString();
                    RegisterRequest request = new RegisterRequest(USERNAME, NAME, PASS, NATIONAL_ID,ADDRESS,PHONE,url ,new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d("Register reponse", response.toString());
                                progressDialog.dismiss();
                                JSONObject object = new JSONObject(response);

                                if (object.getBoolean("success")) {

                                    Toast.makeText(MedicalHistory.this, "User registered.", Toast.LENGTH_SHORT).show();
                                    USER_ID = object.getString("user_id");
                                    uploadMedHistory();
                                    return;

                                } else {
                                    Toast.makeText(MedicalHistory.this, "Some error", Toast.LENGTH_SHORT).show();
                                }
                                if(object.getBoolean("username")){
                                    Toast.makeText(MedicalHistory.this, "Username exists in database,", Toast.LENGTH_SHORT).show();
                                    return;
                                }



                            } catch (Exception e) {
                                progressDialog.dismiss();
                                Log.i("Exception", e.getMessage().toString());
                                Toast.makeText(MedicalHistory.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            if (error instanceof ServerError)
                                Toast.makeText(MedicalHistory.this, "Server Error", Toast.LENGTH_SHORT).show();
                            else if (error instanceof TimeoutError)
                                Toast.makeText(MedicalHistory.this, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                            else if (error instanceof NetworkError)
                                Toast.makeText(MedicalHistory.this, "Bad Network Connection", Toast.LENGTH_SHORT).show();
                            Toast.makeText(MedicalHistory.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                    RequestQueues.getInstance(MedicalHistory.this).addToRequestQue(request);


                    Toast.makeText(MedicalHistory.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Log.d("on image upload failure", e.getMessage());
                    Toast.makeText(MedicalHistory.this, "Image upload failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });



        }else{
            progressDialog.dismiss();
            Toast.makeText(MedicalHistory.this, "Please select an image.", Toast.LENGTH_SHORT).show();



        }


    }

    private void uploadMedHistory() {

        final ProgressDialog progressDialog = new ProgressDialog(MedicalHistory.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Uploading medical history");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RegisterRequest request = new RegisterRequest(USER_ID, diseases, OTHER_DETAILS, BLOOD_GROUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Response", response.toString());

                try {
                    if(new JSONObject(response).getBoolean("status")){
                        progressDialog.dismiss();
                        Toast.makeText(MedicalHistory.this, "Medical history uploaded", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MedicalHistory.this,Login.class));
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(MedicalHistory.this, "Medical history upload failed.", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(MedicalHistory.this, "Exception"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MedicalHistory.this, "Volley"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueues.getInstance(MedicalHistory.this).addToRequestQue(request);
    }


    private void setValues() {

        USERNAME = getIntent().getStringExtra("username");
        NAME = getIntent().getStringExtra("name");
        PASS = getIntent().getStringExtra("pass");
        PHONE = getIntent().getStringExtra("phone");
        ADDRESS = getIntent().getStringExtra("address");
        NATIONAL_ID = getIntent().getStringExtra("national_id");
        filepath =Uri.parse(getIntent().getStringExtra("image")) ;
    }

    private void initiliaze() {

        other_Details_text = (EditText)findViewById(R.id.other_details_text);
        other_Details_text.setVisibility(View.INVISIBLE);
        sumbit = (Button)findViewById(R.id.submit_report);
        group = (RadioGroup)findViewById(R.id.radioGroup2);

        heart = (CheckBox)findViewById(R.id.heart_disease);
        bloodPressure = (CheckBox)findViewById(R.id.blood_pressure);
        diabetes = (CheckBox)findViewById(R.id.diabetes);
        other = (CheckBox)findViewById(R.id.other);

        other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(other.isChecked()){
                    other_Details_text.setVisibility(View.VISIBLE);
                }else{
                    other_Details_text.setVisibility(View.INVISIBLE);
                }
            }
        });
        apos = (RadioButton)findViewById(R.id.Apositive);

        bpos = (RadioButton)findViewById(R.id.Bpositive);
        opos = (RadioButton)findViewById(R.id.Opositive);
        abpos = (RadioButton)findViewById(R.id.ABpositive);
        aneg = (RadioButton)findViewById(R.id.Anegatve);
        bneg = (RadioButton)findViewById(R.id.Bnagative);
        oneg = (RadioButton)findViewById(R.id.Onegative);
        abneg = (RadioButton)findViewById(R.id.ABnagative);

    }


}

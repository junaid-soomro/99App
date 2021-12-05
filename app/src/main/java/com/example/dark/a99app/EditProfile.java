package com.example.dark.a99app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.dark.a99app.SessionManager.SessionManager;
import com.example.dark.a99app.Singletons.RequestQueues;
import com.example.dark.a99app.Volley.EditProdileRequest;
import com.example.dark.a99app.Volley.RegisterRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

public class EditProfile extends AppCompatActivity {

    TextInputLayout edit_name,edit_c_pass,edit_new_pass,edit_rep_pass,edit_phone,edit_address,edit_username;

    String ID,NAME,PASS,USERNAME,ADDRESS,PHONE,NEW_PASS,NEW_PASS2;

    CheckBox up_pass;

    Button UpdateProfile,edit_med_history;

    ImageView edit_image;

    Uri filepath;

    Bitmap product_picture;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initiliaze();
        edit_c_pass.setVisibility(View.GONE);
        edit_rep_pass.setVisibility(View.GONE);
        edit_new_pass.setVisibility(View.GONE);

        up_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(up_pass.isChecked()){
                    TranslateAnimation slide = new TranslateAnimation(0, 0, 500,0 );
                    slide.setDuration(1000);

                    edit_c_pass.setVisibility(View.VISIBLE);
                    edit_c_pass.setAnimation(slide);
                    edit_rep_pass.setVisibility(View.VISIBLE);
                    edit_rep_pass.setAnimation(slide);
                    edit_new_pass.setVisibility(View.VISIBLE);
                    edit_new_pass.setAnimation(slide);

                }else{
                    TranslateAnimation slide = new TranslateAnimation(0, 0, 0,1000 );
                    slide.setDuration(1000);

                    edit_c_pass.setVisibility(View.GONE);
                    edit_c_pass.setAnimation(slide);
                    edit_rep_pass.setVisibility(View.GONE);
                    edit_rep_pass.setAnimation(slide);
                    edit_new_pass.setVisibility(View.GONE);
                    edit_new_pass.setAnimation(slide);
                }
            }
        });
        setVaues();
        function();
        decision();
    }

    private void decision() {


        UpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NAME = edit_name.getEditText().getText().toString();
                USERNAME = edit_username.getEditText().getText().toString();
                ADDRESS = edit_address.getEditText().getText().toString();
                PHONE = edit_phone.getEditText().getText().toString();
                ID = new SessionManager(EditProfile.this).getId();
                if(up_pass.isChecked()){

                    PASS = edit_c_pass.getEditText().getText().toString();
                    NEW_PASS = edit_new_pass.getEditText().getText().toString();
                    NEW_PASS2 = edit_rep_pass.getEditText().getText().toString();
                        if(NEW_PASS.equals(NEW_PASS2)){

                            updateRequest();

                        }
                        else{
                            Toast.makeText(EditProfile.this, "كلمه المرور غير مطابقه", Toast.LENGTH_SHORT).show();
                            return;
                        }
                }
                else{

                    updateRequest2();
                }
            }
        });
    }

    private void updatewithoutImage() {
        final ProgressDialog progressDialog = new ProgressDialog(EditProfile.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Updating profile");
        progressDialog.setCancelable(false);
        progressDialog.show();

        EditProdileRequest request = new EditProdileRequest(new SessionManager(EditProfile.this).getId(), NAME,PHONE,ADDRESS,
                USERNAME ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("Update response", response.toString());
                    progressDialog.dismiss();
                    JSONObject object = new JSONObject(response);


                    if(object.getBoolean("PASSWORD")){
                        Toast.makeText(EditProfile.this, "كلمه المرور غير مطابقه", Toast.LENGTH_SHORT).show();
                    }
                    if (object.getBoolean("success")) {

                        Toast.makeText(EditProfile.this, "تم التحديث", Toast.LENGTH_SHORT).show();
                        new SessionManager(EditProfile.this).Logout();
                        startActivity(new Intent(EditProfile.this,Login.class));
                        finish();
                    }




                } catch (Exception e) {
                    progressDialog.dismiss();
                    Log.i("Exception", e.getMessage().toString());
                    Toast.makeText(EditProfile.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof ServerError)
                    Toast.makeText(EditProfile.this, "Server Error", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(EditProfile.this, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(EditProfile.this, "Bad Network Connection", Toast.LENGTH_SHORT).show();
                Toast.makeText(EditProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueues.getInstance(EditProfile.this).addToRequestQue(request);

    }

    private void updatewithImage() {
        final ProgressDialog progressDialog = new ProgressDialog(EditProfile.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Updating profile");
        progressDialog.setCancelable(false);
        progressDialog.show();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        StorageReference ref = storageReference.child("user_images/" + UUID.randomUUID().toString());
        ref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String url = taskSnapshot.getDownloadUrl().toString();
                EditProdileRequest request = new EditProdileRequest(new SessionManager(EditProfile.this).getId(), NAME,PHONE,ADDRESS,USERNAME,url ,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Update response", response.toString());
                            progressDialog.dismiss();
                            JSONObject object = new JSONObject(response);
                            if(object.getBoolean("PASSWORD")){
                                Toast.makeText(EditProfile.this, "كلمه المرور غير مطابقه", Toast.LENGTH_SHORT).show();
                                new SessionManager(EditProfile.this).Logout();
                                startActivity(new Intent(EditProfile.this,Login.class));
                                finish();
                            }
                            if (object.getBoolean("success")) {

                                Toast.makeText(EditProfile.this, "تم التحديث", Toast.LENGTH_SHORT).show();
                                return;
                            }




                        } catch (Exception e) {
                            progressDialog.dismiss();
                            Log.i("Exception", e.getMessage().toString());
                            Toast.makeText(EditProfile.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if (error instanceof ServerError)
                            Toast.makeText(EditProfile.this, "خطأ في الشبكه", Toast.LENGTH_SHORT).show();
                        else if (error instanceof TimeoutError)
                            Toast.makeText(EditProfile.this, "انتهي وقت الاتصال", Toast.LENGTH_SHORT).show();
                        else if (error instanceof NetworkError)
                            Toast.makeText(EditProfile.this, "الاتصال غير مستقر", Toast.LENGTH_SHORT).show();
                        Toast.makeText(EditProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                RequestQueues.getInstance(EditProfile.this).addToRequestQue(request);


                Toast.makeText(EditProfile.this, "لقد تم ارفاق الصوره", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Log.d("on image upload failure", e.getMessage());
                Toast.makeText(EditProfile.this, "خطأ في تحميل الصوره"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void updateRequest2() {
        if(filepath!=null){

            updatewithImage();


        }else{

            updatewithoutImage();

        }
    }

    private void updateRequest() {
            if(filepath!=null){

                updatePasswithImage();


            }else{

                updatePasswithoutImage();

            }

    }

    private void updatePasswithoutImage() {
        final ProgressDialog progressDialog = new ProgressDialog(EditProfile.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Updating profile");
        progressDialog.setCancelable(false);
        progressDialog.show();
        EditProdileRequest request = new EditProdileRequest(new SessionManager(EditProfile.this).getId(), NAME, USERNAME,PASS,
                NEW_PASS,PHONE,ADDRESS ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("Update response", response.toString());
                    progressDialog.dismiss();
                    JSONObject object = new JSONObject(response);
                    if(object.getBoolean("PASSWORD")){
                        Toast.makeText(EditProfile.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                        new SessionManager(EditProfile.this).Logout();
                        startActivity(new Intent(EditProfile.this,Login.class));
                        finish();
                    }
                    if (object.getBoolean("success")) {

                        Toast.makeText(EditProfile.this, "Updated profile.", Toast.LENGTH_SHORT).show();
                        return;
                    }




                } catch (Exception e) {
                    progressDialog.dismiss();
                    Log.i("Exception", e.getMessage().toString());
                    Toast.makeText(EditProfile.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof ServerError)
                    Toast.makeText(EditProfile.this, "Server Error", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(EditProfile.this, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(EditProfile.this, "Bad Network Connection", Toast.LENGTH_SHORT).show();
                Toast.makeText(EditProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueues.getInstance(EditProfile.this).addToRequestQue(request);

    }

    private void updatePasswithImage() {
        final ProgressDialog progressDialog = new ProgressDialog(EditProfile.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Updating profile");
        progressDialog.setCancelable(false);
        progressDialog.show();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        StorageReference ref = storageReference.child("user_images/" + UUID.randomUUID().toString());
        ref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String url = taskSnapshot.getDownloadUrl().toString();
                EditProdileRequest request = new EditProdileRequest(new SessionManager(EditProfile.this).getId(), NAME, USERNAME,PASS,
                        NEW_PASS,PHONE,ADDRESS,url ,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Update response", response.toString());
                            progressDialog.dismiss();
                            JSONObject object = new JSONObject(response);
                            if(object.getBoolean("PASSWORD")){
                                Toast.makeText(EditProfile.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                                new SessionManager(EditProfile.this).Logout();
                                startActivity(new Intent(EditProfile.this,Login.class));
                                finish();
                            }
                            if (object.getBoolean("success")) {

                                Toast.makeText(EditProfile.this, "Updated profile.", Toast.LENGTH_SHORT).show();
                                return;
                            }




                        } catch (Exception e) {
                            progressDialog.dismiss();
                            Log.i("Exception", e.getMessage().toString());
                            Toast.makeText(EditProfile.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if (error instanceof ServerError)
                            Toast.makeText(EditProfile.this, "Server Error", Toast.LENGTH_SHORT).show();
                        else if (error instanceof TimeoutError)
                            Toast.makeText(EditProfile.this, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                        else if (error instanceof NetworkError)
                            Toast.makeText(EditProfile.this, "Bad Network Connection", Toast.LENGTH_SHORT).show();
                        Toast.makeText(EditProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                RequestQueues.getInstance(EditProfile.this).addToRequestQue(request);


                Toast.makeText(EditProfile.this, "Image uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Log.d("on image upload failure", e.getMessage());
                Toast.makeText(EditProfile.this, "Image upload failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK && data != null) {
            //Image Successfully Selected
            try {
                //parsing the Intent data and displaying it in the imageview
                Uri imageUri = data.getData();//Geting uri of the data
                InputStream imageStream = EditProfile.this.getContentResolver().openInputStream(imageUri);//creating an imputstrea
                product_picture = BitmapFactory.decodeStream(imageStream);//decoding the input stream to bitmap
                edit_image.setImageBitmap(product_picture);
                filepath = imageUri;


            } catch (FileNotFoundException e) {
                Toast.makeText(EditProfile.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    private void function() {
        edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1000);
            }
        });

        edit_med_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EditProfile.this,MedicalHistory.class);
                intent.putExtra("value",true);
                intent.putExtra("user_id",new SessionManager(EditProfile.this).getId());
                startActivity(intent);

            }
        });



    }

    private void setVaues() {


        edit_username.getEditText().setText(new SessionManager(EditProfile.this).getUsername());
        edit_phone.getEditText().setText(new SessionManager(EditProfile.this).getPhone());
        edit_address.getEditText().setText(new SessionManager(EditProfile.this).getAddress());

        Picasso.with(EditProfile.this).load(new SessionManager(EditProfile.this).getimage()).into(edit_image);

        edit_name.getEditText().setText(new SessionManager(EditProfile.this).getName());

    }

    private void initiliaze() {

        edit_name = (TextInputLayout)findViewById(R.id.edit_name);
        edit_c_pass = (TextInputLayout)findViewById(R.id.edit_c_pass);
        edit_new_pass = (TextInputLayout)findViewById(R.id.edit_new_pass);
        edit_rep_pass = (TextInputLayout)findViewById(R.id.edit_rep_pass);
        edit_address = (TextInputLayout)findViewById(R.id.edit_address);
        edit_phone = (TextInputLayout)findViewById(R.id.edit_phone);
        edit_username = (TextInputLayout)findViewById(R.id.edit_username);


        UpdateProfile = (Button)findViewById(R.id.UpdateProfile);
        edit_med_history = (Button)findViewById(R.id.edit_medical_history);

        up_pass = (CheckBox)findViewById(R.id.up_edit_pass);

        edit_image = (ImageView)findViewById(R.id.edit_image);

    }
}

package com.example.dark.a99app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.RadioButton;
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

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

public class Register extends AppCompatActivity {



    Bitmap product_picture;

    Uri filepath = Uri.parse("android.resource://com.example.dark.a99app/drawable/profile");
    private TextInputLayout username,name,password,phone,address,national_id;

    String USERNAME,NAME,PASS,ADDRESS,NATIONAL_ID,PHONE;

    Button med_history;

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
        workfunction();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK && data != null) {
            //Image Successfully Selected
            try {
                //parsing the Intent data and displaying it in the imageview
                Uri imageUri = data.getData();//Geting uri of the data
                InputStream imageStream = Register.this.getContentResolver().openInputStream(imageUri);//creating an imputstrea
                product_picture = BitmapFactory.decodeStream(imageStream);//decoding the input stream to bitmap
                imageView.setImageBitmap(product_picture);
                filepath = imageUri;


            } catch (FileNotFoundException e) {
                Toast.makeText(Register.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    private void workfunction() {

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1000);
            }
        });

        med_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getEditText().getText().toString().equals("") || username.getEditText().getText().toString().equals("") || password.getEditText().getText().toString().equals("") || phone.getEditText().getText().toString().equals("") ||
                        national_id.getEditText().getText().toString().equals("") || address.getEditText().getText().toString().equals(""))
                {
                    Toast.makeText(Register.this, "One or more fields empty", Toast.LENGTH_SHORT).show();

                }else {
                    NAME = name.getEditText().getText().toString();
                    USERNAME = username.getEditText().getText().toString();
                    PASS = password.getEditText().getText().toString();
                    PHONE = phone.getEditText().getText().toString();
                    NATIONAL_ID = national_id.getEditText().getText().toString();
                    ADDRESS = address.getEditText().getText().toString();

                    Intent intent = new Intent(Register.this,MedicalHistory.class);
                    intent.putExtra("name",NAME);
                    intent.putExtra("username",USERNAME);
                    intent.putExtra("pass",PASS);
                    intent.putExtra("phone",PHONE);
                    intent.putExtra("address",ADDRESS);
                    intent.putExtra("national_id",NATIONAL_ID);
                    intent.putExtra("image",filepath.toString());
                    startActivity(intent);

                }
            }
        });
    }


    private void initialize() {

            med_history = (Button)findViewById(R.id.medical_history);

            username = (TextInputLayout) findViewById(R.id.reg_username);
            name = (TextInputLayout) findViewById(R.id.Name);
            password = (TextInputLayout) findViewById(R.id.reg_password);
            address = (TextInputLayout) findViewById(R.id.address);
            phone = (TextInputLayout) findViewById(R.id.phone);
            national_id = (TextInputLayout)findViewById(R.id.national_id);


            imageView = (ImageView)findViewById(R.id.main_image);
        }

}

package com.anisioaleixo.mychat_firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    private ImageView mImage;
    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private Button mCadastrar;
    private Uri mUriImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);

        mImage = findViewById(R.id.img_foto);
        mName = findViewById(R.id.edt_name);
        mEmail = findViewById(R.id.edt_email);
        mPassword = findViewById(R.id.edt_password);
        mCadastrar = findViewById(R.id.btn_enter);

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterarImage();
            }
        });

        mCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        //Log.i("AATJ","Solicitando cadastrar usuario!");
        String name = mEmail.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if (name.isEmpty()||name == null||email.isEmpty()||email == null||password.isEmpty()||password == null){
            Toast.makeText(getApplicationContext(),"Tosdos os campos devem ser preenchidos!",Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getApplicationContext(),"Cadastrado com sucesso!",Toast.LENGTH_LONG).show();
                        saveUserInFirebase();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("AATJ",e.getMessage());
                    }
                });
    }

    private void saveUserInFirebase() {
        String filename = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/imagens/"+filename);
        ref.putFile(mUriImagem)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(getApplicationContext(),uri.toString(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("AATJ",e.getMessage());
                    }
                });
    }

    private void alterarImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            try {
                mUriImagem = data.getData();
                Bitmap bitmap = null;
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),mUriImagem);
                mImage.setImageDrawable(new BitmapDrawable(bitmap));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
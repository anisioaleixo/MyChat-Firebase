package com.anisioaleixo.mychat_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private ImageView mImage;
    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private Button mCadastrar;

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


    }

    private void alterarImage() {
        Log.i("AATJ","Solicitando troca de imagem!");
    }
}
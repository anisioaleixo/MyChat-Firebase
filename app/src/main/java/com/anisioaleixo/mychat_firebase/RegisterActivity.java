package com.anisioaleixo.mychat_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterActivity extends AppCompatActivity {

    private ImageView image;
    private EditText name;
    private EditText password;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);

        image = findViewById(R.id.img_foto);
        name = findViewById(R.id.edt_name);
        password = findViewById(R.id.edt_password);
        cadastrar = findViewById(R.id.btn_enter);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterarImage();
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        //Log.i("AATJ","Solicitando cadastrar usuario!");
    }

    private void alterarImage() {
        // Log.i("AATJ","Solicitando troca de imagem!");
    }
}
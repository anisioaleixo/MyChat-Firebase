package com.anisioaleixo.mychat_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button btnEntar;
    private TextView txtAccount;
    private TextView txtRecuperaSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        email = findViewById(R.id.edt_email);
        senha = findViewById(R.id.edt_password);
        btnEntar = findViewById(R.id.btn_enter);
        txtAccount = findViewById(R.id.txt_account);
        txtRecuperaSenha = findViewById(R.id.txt_esqueci_senha);

        btnEntar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Código do clique no botão entrar
            }
        });

        txtAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Código do clique no Criar conta!
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        txtRecuperaSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Código do clique no recuperar senha!

                // Intent intent = new Intent(LoginActivity.this,RecuperaSenhaActivity.class);
                // startActivity(intent);
            }
        });
    }
}
package com.anisioaleixo.mychat_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mSenha;
    private Button mBtnEntar;
    private TextView mTxtAccount;
    private TextView mTxtRecuperaSenha;

    private final String TAG = "AATJ";

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        mEmail = findViewById(R.id.edt_email);
        mSenha = findViewById(R.id.edt_password);
        mBtnEntar = findViewById(R.id.btn_enter);
        mTxtAccount = findViewById(R.id.txt_account);
        mTxtRecuperaSenha = findViewById(R.id.txt_esqueci_senha);

        mBtnEntar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString();
                String password = mSenha.getText().toString();

                if (email.isEmpty() || email == null || password.isEmpty() || password == null) {
                    Toast.makeText(getApplicationContext(), "Tosdos os campos devem ser preenchidos!", Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth = FirebaseAuth.getInstance();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Intent intent = new Intent(LoginActivity.this, MessagesActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i(TAG, e.getMessage());
                            }
                        });
            }
        });

        mTxtAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Código do clique no Criar conta!
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mTxtRecuperaSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                naoImplemendado("Recuperar senha!");
                //Código do clique no recuperar senha!

                // Intent intent = new Intent(LoginActivity.this,RecuperaSenhaActivity.class);
                // startActivity(intent);
            }
        });
    }

    private void naoImplemendado(String nome) {
        Toast.makeText(getApplicationContext(), "Função " + nome + " não implementada!", Toast.LENGTH_SHORT).show();

    }
}
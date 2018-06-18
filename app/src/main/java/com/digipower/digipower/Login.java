package com.digipower.digipower;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private Button entrar;
    private TextView esqueci;
    private AlertDialog.Builder alerta;
    private EditText campo_email, campo_senha;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);



        componentes();
        botoes();
        testa_conexao_internet();
    }

    //    ¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬ METODOS ¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬

    private void testa_conexao_internet() {
        ConnectivityManager acesso = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = acesso.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){}
        else{
            alerta_sem_conexao();
        }
    }

     private void alerta_sem_conexao() {
         LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         final View formElementsView = inflater.inflate(R.layout.sem_internet_layout,null, false);

         new AlertDialog.Builder(Login.this).setView(formElementsView)
                 .setCancelable(false)
                 .setPositiveButton("OK", null)
                 .show();
        }

        private void botoes() {
            entrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                String email = campo_email.getText().toString().trim();
                String senha = campo_senha.getText().toString().trim();

                if (campo_email.getText().toString().trim().isEmpty() || campo_email == null) {
                    campo_email.setError("O campo e-mail não pode ficar vazio!");
                } else if (campo_senha.getText().toString().trim().isEmpty() || campo_senha == null) {
                    campo_senha.setError("O campo senha não pode ficar vazio!");
                }else{
                    login(email, senha);
                }
                }
            });
        }

    private void componentes(){
        entrar = (Button) findViewById(R.id.id_btnEntrar);
        campo_email          = (EditText)findViewById(R.id.id_email_login);
        campo_senha          = (EditText)findViewById(R.id.id_senha_login);
    }

    public void esqueci(View view) {
        alert_reset_senha();
    }

    private void login(final String email, String senha) {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                atencao( "Bem vindo! "+ email);
                campo_email.setText("");
                campo_senha.setText("");
                campo_email.setFocusable(true);
                finish();
                startActivity(new Intent( Login.this, Home.class));
            }else{
                atencao("E-mail ou senha incorretos, ou sem conexão com a internet! tente novamente!");
            }
            }
        });
    }
    private void atencao(String mensagem) {
        Toast.makeText(Login.this,mensagem,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();
    }

    // ¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬ layout reset senha ¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬
     public void alert_reset_senha() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.reset_senha_layout,null, false);
        final EditText nameEditText = (EditText) formElementsView.findViewById(R.id.id_email_reset);

        new AlertDialog.Builder(Login.this).setView(formElementsView)
            .setCancelable(false)
            .setNegativeButton("CANCELAR",null)
            .setPositiveButton("ENVIAR", new DialogInterface.OnClickListener() {
                @TargetApi(11)
                public void onClick(DialogInterface dialog, int id) {
                    String email = nameEditText.getText().toString().trim();
                    reset_email(email);
                }
            }).show();
    }

    private void reset_email(String email){
        auth.sendPasswordResetEmail(email).addOnCompleteListener(Login.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    atencao("Um e-mail lhe foi enviado com informações para redefinir sua senha, verifique sua caixa de e-mail!");
                    finish();
                }else{
                    atencao("E-mail não encontrado, tente novamente");
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

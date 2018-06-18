package com.digipower.digipower;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Cadastro extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button btn_cadastrar;
    private EditText campo_email, campo_senha, campo_confirma_senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_layout);

        testa_conexao_internet();
        componentes();
        botoes();
    }
//    ¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬ metodos ¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬
    private void atencao(String mensagem) {
        Toast.makeText(Cadastro.this,mensagem,Toast.LENGTH_LONG).show();
    }

    private void componentes() {
        btn_cadastrar = (Button) findViewById(R.id.id_btn_Cadastro_login);
        campo_email          = (EditText)findViewById(R.id.id_email_cadastro);
        campo_senha          = (EditText)findViewById(R.id.id_senha_cadastro);
        campo_confirma_senha = (EditText)findViewById(R.id.id_senha_confirm);
    }

    private void botoes() {
        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(campo_email.getText().length()==0) {
                    campo_email.setError("Digite um e-mail válido!");
                    campo_email.requestFocus();
                }else if(campo_senha.getText().length()== 0 || campo_senha.getText().length()<6) {
                    campo_senha.setError("O campo senha não pode ficar vazio e deve ter no minímo 6 caracteres");
                    campo_senha.requestFocus();
                }else if(campo_confirma_senha.getText().length() != campo_senha.getText().length()){
                    campo_confirma_senha.setError("As senhas devem ser iguais! Repita a senha.");
                    campo_confirma_senha.setText("");
                    campo_confirma_senha.requestFocus();
                }else {
                    String email = campo_email.getText().toString().trim();
                    String senha = campo_senha.getText().toString().trim();
                    criauser(email, senha);
                }
            }
        });
    }

    private void criauser(String email, String senha) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    atencao("Conta criada com sucesso, seja Bem-vindo! " + campo_email.getText().toString());
                    startActivity(new Intent(Cadastro.this, Home.class));
                    finish();
                }else{
                    alerta_erro_cadastro();
                }
            }
        });
    }

    public void alerta_erro_cadastro() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.erro_cadastro_layout,null, false);

        new AlertDialog.Builder(Cadastro.this).setView(formElementsView)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        campo_email.requestFocus();
                    }
                }).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

//    ¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬ conexão com a internet ¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬
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
            final View formElementsView = inflater.inflate(R.layout.sem_internet_cadastro_layout,null, false);

            new AlertDialog.Builder(Cadastro.this).setView(formElementsView)
                    .setCancelable(false)
                    .setPositiveButton("OK", null)
                    .show();
        }
}

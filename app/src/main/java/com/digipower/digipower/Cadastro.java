package com.digipower.digipower;

import android.content.Context;
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
//                if(campo_email.getText().length()==0) {
//                    campo_email.setError("Campo vazio! insira um e-mail válido.");
//                }else if((campo_senha.getText().length()==0)) {
//                    campo_senha.setError("Campo vazio! Informe uma senha.");
//                }else if((campo_senha.getText().length()<6)) {
//                    campo_senha.setError("Informe no minímo 6 caracteres!");
//                }else if((campo_confirma_senha.getText().length()==0)) {
//                    campo_confirma_senha.setError("Campo vazio! verifique.");
//                }else if( campo_confirma_senha.length()<6) {
//                    campo_confirma_senha.setError("Repita a senha informada no campo senha!");
//                }else {
//                    Snackbar.make(view, "E-mail já cadastrado! faça login ou tente recuperar a senha", Snackbar.LENGTH_LONG).setAction("Action",null).show();
//                    String email = campo_email.getText().toString().trim();
//                    String senha = campo_senha.getText().toString().trim();
//                    criauser(email, senha);
//                }
                if(campo_email.getText().length()==0) {
                    atencao("Digite um e-mail válido!");
                    campo_email.setFocusable(true);
                } else if((campo_senha.getText().length()<6 || (campo_confirma_senha.getText().length()==0))) {
                    atencao("Os campos 'Senha' e 'Confirmar senha' devem ter no minímo 6 caracteres! verifique.");
                }else {
                    Snackbar.make(view, "E-mail já cadastrado! faça login ou tente recuperar a senha", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    String email = campo_email.getText().toString().trim();
                    String senha = campo_senha.getText().toString().trim();
                    criauser(email, senha);
                }
            }
        });
    }
//    public void Cadastrar(View view){
//        Toast.makeText(getApplicationContext(),"Voce clicou em cadastrar se ",Toast.LENGTH_LONG).show();
//    }
    private void criauser(String email, String senha) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    atencao("Conta criada com sucesso, seja Bem-vindo! " + campo_email.getText().toString());
                    startActivity(new Intent(Cadastro.this, Home.class));
                    finish();
                }else{
                    atencao("Erro ao cadastrar usuário!");
                }
            }
        });
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

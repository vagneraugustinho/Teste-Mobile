package com.digipower.digipower;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Inicio extends AppCompatActivity {

    private Button login, cadastrese, cadastreface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.inicio_layout);

        componentes();
        botoes();

    }

    private void botoes() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(Inicio.this, Login.class));
            }
        });

        cadastrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(Inicio.this, Cadastro.class));
            }
        });

    }

    private void msg() {
        Toast.makeText(Inicio.this,"ms",Toast.LENGTH_LONG).show();
    }

    private void componentes() {
        login = (Button) findViewById(R.id.id_btnLogin);
        cadastrese = (Button) findViewById(R.id.id_btnCadastrese);
        cadastreface = (Button) findViewById(R.id.id_btnCadastreface);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Tchaul,Volte sempre!",Toast.LENGTH_LONG).show();
        finish();
    }
}

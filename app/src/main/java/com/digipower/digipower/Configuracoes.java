package com.digipower.digipower;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Configuracoes extends AppCompatActivity {

    private Button resetar;
    private TextView cod_verificacao;
    private TextView ajuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracoes_layout);

       botoes();
       Componentes();

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void Componentes() {
        resetar = (Button)findViewById(R.id.id_resetar_placa);
        cod_verificacao = (TextView)findViewById(R.id.id_cod_verificacao);
        ajuda = (TextView)findViewById(R.id.id_ajuda);
    }

    private void botoes() {
    }

    public void resetar_placa() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.reset_placa_layout,null, false);

        new AlertDialog.Builder(Configuracoes.this).setView(formElementsView)
                .setCancelable(false)
                .setNegativeButton("CANCELAR",null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        cod_verificacao.setText("- - - -");
                    }
                }).show();
    }

    public void resetarplaca(View view) {
        resetar_placa();
    }

    public void ajuda(View view) {
        String url = "https://www.digipower.com.br";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url.toString())));
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}


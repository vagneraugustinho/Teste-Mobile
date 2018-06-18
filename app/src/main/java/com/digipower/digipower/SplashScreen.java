package com.digipower.digipower;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {

    protected static final int TIMER_RUNTIME = 1000;
    protected  boolean mbActive;
    protected ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.splash_layout);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        final Thread timerThread = new Thread(){
            @Override
            public void run(){
                mbActive = true;
                try {
                    int waited = 0;
                    while (mbActive && (waited < TIMER_RUNTIME)){
                        sleep(200);;
                        if (mbActive){
                            waited += 200;
                            updateProgress(waited);
                        }
                    }
                    startActivity( new Intent(SplashScreen.this, Inicio.class));
                    finish();
                }catch (InterruptedException e){
//                    em caso de erro
                }
            }
        };
        timerThread.start();
    }
//DEPOIES DECIDO SE DEIXO OU NAO VISIVEL A BARRA DE PROGRASSO
    public void updateProgress(final int timePassed){
        if (null != progressBar){
            final int progress = progressBar.getMax() * timePassed / TIMER_RUNTIME;
            progressBar.setProgress(progress);
        }
    }
}
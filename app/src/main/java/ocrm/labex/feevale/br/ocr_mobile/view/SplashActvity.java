package ocrm.labex.feevale.br.ocr_mobile.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

import java.util.Date;
import java.util.HashSet;

import ocrm.labex.feevale.br.ocr_mobile.MainActivity;
import ocrm.labex.feevale.br.ocr_mobile.R;
import ocrm.labex.feevale.br.ocr_mobile.handler.DirectoryHandler;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.DirectoryDAO;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.DocumentDAO;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.ImageDAO;
import ocrm.labex.feevale.br.ocr_mobile.model.Directory;
import ocrm.labex.feevale.br.ocr_mobile.model.Docs;
import ocrm.labex.feevale.br.ocr_mobile.model.Image;

public class SplashActvity extends ActionBarActivity {
    private static final int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_actvity);
        getActionBar().hide();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActvity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}




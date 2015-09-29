package ocrm.labex.feevale.br.ocr_mobile.view;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.MenuItem;

import ocrm.labex.feevale.br.ocr_mobile.R;
import ocrm.labex.feevale.br.ocr_mobile.view.fragments.BaseFragment;
import ocrm.labex.feevale.br.ocr_mobile.view.fragments.CameraFragment;
import ocrm.labex.feevale.br.ocr_mobile.view.fragments.Galeria;

public class ScannerActivity extends FragmentActivity implements BaseFragment.OnFragmentInteractionListener {

    public static final int RESULT_LOAD_IMAGE = 0;
    private Uri imageUri;
    private ScannerActivity scanner;
    private FragmentTabHost mTabHost;
    private FragmentActivity context;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#780A0A")));

        initTab();
    }

    public void initTab(){
        FragmentManager fragManager = getSupportFragmentManager();
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        mTabHost.setup(this, fragManager, R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("Fotos").setIndicator("Fotos"),
                CameraFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Galeria").setIndicator("Galeria"),
                Galeria.class, null);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    public void onFragmentInteraction(int actionId) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.call_gallery:
                startGallery();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void startGallery(){
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent , RESULT_LOAD_IMAGE );
    }



}

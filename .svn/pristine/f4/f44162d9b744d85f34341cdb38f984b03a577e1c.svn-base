package ocrm.labex.feevale.br.ocr_mobile.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import ocrm.labex.feevale.br.ocr_mobile.R;
import ocrm.labex.feevale.br.ocr_mobile.utils.AppVariables;
import ocrm.labex.feevale.br.ocr_mobile.utils.FileUtils;
import ocrm.labex.feevale.br.ocr_mobile.utils.MessageResponse;
import ocrm.labex.feevale.br.ocr_mobile.view.adapters.ImageAdapter;
import ocrm.labex.feevale.br.ocr_mobile.view.CropActivity;


/**
 * Created by 0118424 on 24/10/2014.
 */
public class Galeria extends Fragment {

    Context context;
    View myFragmentView;
    GridView myView;
    ImageAdapter imageAdapter;
    String  pathDir;
    public static final int RESULT_LOAD_IMAGE = 0;
    Uri imageUri;

    private final String[] okFileExtensions =  new String[] {"jpg", "png", "gif","jpeg"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         context = getActivity();
         myFragmentView = inflater.inflate(R.layout.galeria, container, false);
         myView = (GridView) myFragmentView.findViewById(R.id.gridview2);
         startGallery();
         return null;
     }

    private void actionBasedGallery(int position){
        Log.d("Position", position+"");
    }

    private void startGallery(){
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent , RESULT_LOAD_IMAGE );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case RESULT_LOAD_IMAGE:
                try {

                    imageUri = data.getData();
                    Intent intent = new Intent(getActivity(), CropActivity.class);
                    Log.d("Passou por aqui", "Passou por aqui");
                    createBitmap(imageUri, getActivity());
                    startActivity(intent);

                }catch(Exception exception){
                    Log.d("Exception", exception.getMessage()+"");
                    exception.printStackTrace();
                    Toast toast = new Toast(getActivity());
                    toast.makeText(getActivity(), "Nenhuma imagem selecionada", Toast.LENGTH_LONG);
                }
                break;
        }
    }
    private static Bitmap createBitmap(Uri imageUri, Activity activity)  {
        Context context = (Context) activity;
        try{
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
            MessageResponse m = new FileUtils(activity).saveImage(bitmap, AppVariables.TEMP_IMAGE);
            if(m.getStatus());
             return bitmap;
        }catch(Exception e){
            Log.d("Exeception raised", "Raised");
        }



        /*
        try{

            File temp = new File(imageUri.getPath());
            if(temp instanceof  File){
                Bitmap bitmap = BitmapFactory.decodeFile(temp.getAbsolutePath().toString());
                Log.d("Bitmap", bitmap+"");
            }else{
                Log.d("Is not file", "is not file");
            }
        }catch(Exception e){
            Log.d("Exception", e.getMessage()+"");
        }
        */

       // Bitmap bitmap = BitmapFactory.decodeFile(temp.getAbsolutePath());

       // Log.d("Bitmap", bitmap+"");
        /*
        MessageResponse m = new FileUtils(activity).saveImage(bitmap, AppVariables.TEMP_IMAGE);
        if(m.getStatus());
        return bitmap;
        */
        return null;

    }


}

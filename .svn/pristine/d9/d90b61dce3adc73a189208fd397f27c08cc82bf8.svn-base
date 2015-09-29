package ocrm.labex.feevale.br.ocr_mobile.utils;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

import ocrm.labex.feevale.br.ocr_mobile.R;

/**
 * Created by 0126128 on 23/10/2014.
 */
public class ImageUtils {


    public static Bitmap getImage(String name, Activity activity){
        AssetManager assetManager = activity.getAssets();
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try{
            inputStream = assetManager.open(name);
            bitmap =  BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            bitmap = BitmapFactory.decodeResource(activity.getResources(),
                    R.drawable.ic_launcher);
        }finally {
            try {
                if(inputStream != null)
                    inputStream.close();
                inputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return bitmap;
    }
}

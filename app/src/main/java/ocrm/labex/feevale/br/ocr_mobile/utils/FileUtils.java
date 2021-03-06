package ocrm.labex.feevale.br.ocr_mobile.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import ocrm.labex.feevale.br.ocr_mobile.R;
import ocrm.labex.feevale.br.ocr_mobile.model.Image;

/**
 * Created by 0126128 on 28/10/2014.
 */
public class FileUtils {

    private Activity activity;
    private MessageResponse messageResponse;
    public FileUtils(Activity activity) {
        this.activity = activity;
    }

    public File getFileInternalStorage(String name){
        try {
            File file = new File(activity.getFilesDir(), name);
            if(file.exists())
                return file;

            FileOutputStream fileOutputStream = activity.openFileOutput(name, Context.MODE_PRIVATE);
            fileOutputStream.write("".getBytes());
            fileOutputStream.close();

            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public File getFileExternalStorage(String name){
        try {
            File myDirs = new File(
                    new DirectoryUtils().getAbsolutePathFromDevice("OCR"));

            if(!myDirs.exists())
                myDirs.mkdirs();

            File file = new File(myDirs.getAbsolutePath(), name);

            if(file.exists())
                return file;

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write("".getBytes());
            fileOutputStream.close();

            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getTemporaryImage(Activity activity){
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try{
            inputStream = new FileInputStream(new FileUtils(activity).getFileExternalStorage(AppVariables.TEMP_IMAGE));
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

    public static Bitmap getImage(Activity activity, String nameImage){
        AssetManager assetManager = activity.getAssets();
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try{
            inputStream = assetManager.open(nameImage);
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
            }
            return bitmap;
        }
    }

    public Bitmap getExternalImage(String nameImage){
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try{
            inputStream = new FileInputStream(getFileExternalStorage(nameImage).getAbsolutePath());
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
            }
            return bitmap;
        }
    }

    public MessageResponse saveTemporaryImage(byte[] bitmap){
       return saveImage(bitmap,AppVariables.TEMP_IMAGE);
    }

    public MessageResponse saveTemporaryImage(Bitmap bitmap){
        return saveImage(bitmap,AppVariables.TEMP_IMAGE);
    }

    public MessageResponse deleteTemporaryImage(){
        return deleteImage(AppVariables.TEMP_IMAGE);
    }

    private MessageResponse deleteImage(String imageName) {
        File file = getFileInternalStorage(imageName);
        if(file.exists())
           if(file.delete())
               return new MessageResponse("Imagem removida com sucesso", true);
            else
               return new MessageResponse("Imagem não pode ser removida!", false);

        return new MessageResponse("Imagem não encontrada!", false);
    }

    public MessageResponse saveImage(Bitmap bm,String name){

        Boolean status = false;
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(getFileExternalStorage(name));
            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.flush();
            stream.close();
            messageResponse = new MessageResponse("Imagem salva com sucesso", true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            messageResponse = new MessageResponse("Problemas ao localizar arquivo.", false);
        } catch (IOException e) {
            e.printStackTrace();
            messageResponse = new MessageResponse("Problemas ao ler/salvar arquivo.", false);
        }

        return messageResponse;
    }

    public MessageResponse saveImage(byte[] bm,String name){

        Boolean status = false;
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(getFileExternalStorage(name));
            stream.write(bm);
            stream.flush();
            stream.close();
            messageResponse = new MessageResponse("Imagem salva com sucesso", true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            messageResponse = new MessageResponse("Problemas ao localizar arquivo.", false);
        } catch (IOException e) {
            e.printStackTrace();
            messageResponse = new MessageResponse("Problemas ao ler/salvar arquivo.", false);
        }

        return messageResponse;
    }
}


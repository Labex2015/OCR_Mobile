package ocrm.labex.feevale.br.ocr_mobile.view.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import ocrm.labex.feevale.br.ocr_mobile.MainActivity;
import ocrm.labex.feevale.br.ocr_mobile.R;
import ocrm.labex.feevale.br.ocr_mobile.model.Image;

/**
 * Created by 0126128 on 22/10/2014.
 */
public class EditableTextImageAdapter extends BaseAdapter{

    private Activity activity;
    private Image image;
    private LayoutInflater inflater;
    EditText editText;

    public EditableTextImageAdapter(Activity activity, Image image) {
        this.activity = activity;
        this.image = image;
        this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return image;
    }

    @Override
    public long getItemId(int i) {
        return image.getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {




        return null;
    }

    private View.OnClickListener saveAlteration(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setText(editText.getText().toString());
                boolean sucess = saveEditedText();
                if(sucess)
                    ((MainActivity)activity).changeFragment(new Fragment());
                else
                    Toast.makeText(activity,"Ops! Problemas ao salvar o texto editado!", Toast.LENGTH_SHORT);

            }
        };
    }

    private View.OnClickListener restoreText(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity)
                .setTitle("Restaurar...")
                .setMessage("Deseja remover as alterações ?")
                .setNegativeButton("Cancelar" ,new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editText.setText(image.getText());
                    }
                });
            }
        };
    }

    /**
     * TODO Adicionar método a ser criado!
     * @return
     */
    private boolean saveEditedText(){

       return  true;
    }

    private Bitmap getImage(String name){
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

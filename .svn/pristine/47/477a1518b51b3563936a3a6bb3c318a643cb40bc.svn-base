package ocrm.labex.feevale.br.ocr_mobile.view.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ocrm.labex.feevale.br.ocr_mobile.MainActivity;
import ocrm.labex.feevale.br.ocr_mobile.R;
import ocrm.labex.feevale.br.ocr_mobile.model.Image;
import ocrm.labex.feevale.br.ocr_mobile.utils.FileUtils;

/**
 * Created by 0126128 on 22/10/2014.
 */
public class EditableTextFragment extends Fragment {

    private Activity activity;
    private Image image;
    private EditText editText;

    private EditableTextFragment(){}

    public EditableTextFragment(Activity activity, Image image) {
        this.activity = activity;
        this.image = image;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            setRetainInstance(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editable_text_image, null);

        editText = (EditText)view.findViewById(R.id.editTextImage);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageSaved);
        Button buttonSave = (Button)view.findViewById(R.id.saveEditedText);
        Button buttonClear = (Button)view.findViewById(R.id.clearEditedText);

        buttonSave.setOnClickListener(saveAlteration());
        buttonClear.setOnClickListener(restoreText());
        editText.setText(image.getText());
        imageView.setImageBitmap(new FileUtils(activity).getExternalImage(image.getName()));

        return view;
    }

    private View.OnClickListener saveAlteration(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setText(editText.getText().toString());

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                dialogBuilder.setTitle("Salvar?")
                        .setMessage("Deseja salvar as alterações ?")
                        .setNegativeButton("Cancelar" ,new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(saveEditedText())
                                    ((MainActivity)activity).changeFragment(new ImageTextFragment(activity, image));
                                else
                                    Toast.makeText(activity, "Ops! Problemas ao salvar o texto editado!", Toast.LENGTH_SHORT);
                            }
                        });
                Dialog dialog = dialogBuilder.create();
                dialog.show();



            }
        };
    }

    private View.OnClickListener restoreText(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                dialogBuilder.setTitle("Desfazer...")
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
                                ((MainActivity)activity).changeFragment(new ImageTextFragment(activity, image));
                            }
                        });
                Dialog dialog = dialogBuilder.create();
                dialog.show();
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

}

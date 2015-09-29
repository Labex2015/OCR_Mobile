package ocrm.labex.feevale.br.ocr_mobile.view.adapters.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ocrm.labex.feevale.br.ocr_mobile.MainActivity;
import ocrm.labex.feevale.br.ocr_mobile.R;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.DirectoryDAO;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.ImageDAO;
import ocrm.labex.feevale.br.ocr_mobile.model.Directory;
import ocrm.labex.feevale.br.ocr_mobile.model.Image;
import ocrm.labex.feevale.br.ocr_mobile.view.ScannerActivity;
import ocrm.labex.feevale.br.ocr_mobile.view.fragments.FilesManagerFragment;
import ocrm.labex.feevale.br.ocr_mobile.view.fragments.TagsManagerFragment;
import ocrm.labex.feevale.br.ocr_mobile.view.fragments.MyFolderFragment;

/**
 * Created by 0126128 on 21/11/2014.
 */
public enum ItemAdapter {

    SCANNER(R.drawable.scanner),
    TAGS(R.drawable.tags){
        public void executeAction(Activity activity){
            ((MainActivity)activity).changeFragment(new TagsManagerFragment(activity));
        }
    },
    FILES(R.drawable.diretorios){
        public void executeAction(Activity activity){
            ((MainActivity)activity).changeFragment(new MyFolderFragment(activity,(MainActivity)activity));
        }
    },
    REPOSITORIES(R.drawable.gerenciar){
        public void executeAction(Activity activity){
           new ProcessItemModelTask(activity).execute();
        }
    };

    private int idImage;
    private Activity activity;
    private DrawerHandler drawerHandler;

    ItemAdapter(int idImage){
        this.idImage = idImage;
    }

    public View setItemView(Activity activity, DrawerHandler drawerHandler){
        this.activity = activity;
        this.drawerHandler = drawerHandler;
        View myItem = activity.getLayoutInflater().inflate(R.layout.drawer_item, null);
        ImageView imageView = (ImageView)myItem.findViewById(R.id.drawerItemImageView);
        imageView.setImageResource(idImage);
        myItem.setOnClickListener(callFuncionality());
        return myItem;
    }

    public View.OnClickListener callFuncionality(){
        return  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerHandler.executeActionDrawer();
                executeAction(activity);
            }
        };
    }

    public void executeAction(Activity activity){
        Intent intent = new Intent(activity, ScannerActivity.class);
        activity.startActivity(intent);
    }
}

class ProcessItemModelTask extends AsyncTask<Void, Boolean, Boolean>{

    private Activity activity;
    private ProgressDialog progressDialog;
    private List<ItemModel> models;
    private DirectoryDAO directoryDAO;
    private HashSet<Directory> directories;

    ProcessItemModelTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Listando diretórios!");//TODO: Passar para o arquivo de Strings!
        progressDialog.show();

        directoryDAO = new DirectoryDAO(activity);
        models = new ArrayList<ItemModel>();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        directories = directoryDAO.getFirstLevel();
        for(Directory dir : directories)
            models.add(new ItemModel(dir.getId(), dir.getName(), ItemModel.DIRECTORY));

        return null;
    }

    @Override
    protected void onPostExecute(Boolean isOk) {
        if(progressDialog.isShowing())
            progressDialog.dismiss();

        ((MainActivity)activity).changeFragment(new FilesManagerFragment(activity, models));
    }
}

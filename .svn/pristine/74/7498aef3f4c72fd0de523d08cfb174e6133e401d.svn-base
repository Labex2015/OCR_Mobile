package ocrm.labex.feevale.br.ocr_mobile.view.adapters.utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ocrm.labex.feevale.br.ocr_mobile.MainActivity;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.DirectoryDAO;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.DocumentDAO;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.ImageDAO;
import ocrm.labex.feevale.br.ocr_mobile.model.Directory;
import ocrm.labex.feevale.br.ocr_mobile.model.Docs;
import ocrm.labex.feevale.br.ocr_mobile.model.Image;
import ocrm.labex.feevale.br.ocr_mobile.utils.MessageResponse;
import ocrm.labex.feevale.br.ocr_mobile.view.fragments.FilesManagerFragment;

/**
 * Created by 0126128 on 18/11/2014.
 */
public class LoadDirectoryAndImagesTask extends AsyncTask<Void, MessageResponse, MessageResponse> {

    private List<ItemModel> models;
    private MainActivity activity;
    private ImageDAO imageDAO;
    private DirectoryDAO directoryDAO;
    private DocumentDAO documentDAO;
    private MessageResponse response;
    private ProgressDialog progressDialog;
    private ItemModel itemModel;

    public LoadDirectoryAndImagesTask(MainActivity activity, ItemModel itemModel) {
        this.activity = activity;
        this.itemModel = itemModel;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Obtendo conteúdo, aguarde....");
        progressDialog.setTitle("Abrindo diretório: "+itemModel.getName());
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    protected MessageResponse doInBackground(Void... voids) {
        response = new MessageResponse("", false);
        directoryDAO = new DirectoryDAO(activity);
        models = new ArrayList<ItemModel>();

        HashSet<Directory> directories = directoryDAO.getDirectoriesFromNode((Long) itemModel.getId());
        if (directories != null)
            for (Directory directory : directories)
                models.add(new ItemModel(directory.getId(), directory.getName(), ItemModel.DIRECTORY));

        Directory directory = directoryDAO.getItemById(itemModel.getId());
        if (directory != null) {
            documentDAO = new DocumentDAO(activity);
            Docs doc = null;
            Image image = null;
            imageDAO = new ImageDAO(activity);
            for (Long idDoc : directory.getDocumentsIds()) {
                doc = documentDAO.getItemById(idDoc);
                if (doc != null) {
                    for (Long idImage : doc.getImagesIds()) {
                        image = imageDAO.getItemById(idImage);
                        if (image != null)
                            models.add(new ItemModel(image.getId(), image.getName(), ItemModel.IMAGE));
                    }
                }
            }
        }

        if (models.size() > 0){
            response.setStatus(true);
        }else{
            response.setMsg("O diretório está vazio!");
            isCancelled();
        }
        return response;
    }

    @Override
    protected void onPostExecute(MessageResponse messageResponse) {
        if(progressDialog.isShowing())
            progressDialog.dismiss();

        if(messageResponse.getStatus()){
             activity.changeFragment(new FilesManagerFragment(activity, models, (Long)itemModel.getId()));
        }else{
            Toast.makeText(activity, messageResponse.getMsg(),Toast.LENGTH_LONG).show();
        }



    }
}

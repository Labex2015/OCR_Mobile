package ocrm.labex.feevale.br.ocr_mobile.view.adapters.utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;

import ocrm.labex.feevale.br.ocr_mobile.MainActivity;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.DirectoryDAO;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.DocumentDAO;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.ImageDAO;
import ocrm.labex.feevale.br.ocr_mobile.model.Directory;
import ocrm.labex.feevale.br.ocr_mobile.model.Docs;
import ocrm.labex.feevale.br.ocr_mobile.utils.MessageResponse;
import ocrm.labex.feevale.br.ocr_mobile.view.fragments.MyFolderFragment;

/**
 * Created by 0126128 on 18/11/2014.
 */
public class RemoveDirectoryAndImagesTask extends AsyncTask<Void, MessageResponse, MessageResponse> {

    private List<ItemModel> models;
    private MainActivity activity;
    private ImageDAO imageDAO;
    private DirectoryDAO directoryDAO;
    private DocumentDAO documentDAO;
    private MessageResponse response;
    private ProgressDialog progressDialog;
    private ItemModel itemModel;
    private Long idFather;

    public RemoveDirectoryAndImagesTask(MainActivity activity, List<ItemModel> models) {
        this.activity = activity;
        this.models = models;
    }

    public RemoveDirectoryAndImagesTask(MainActivity activity, List<ItemModel> models, Long idFather) {
        this.activity = activity;
        this.models = models;
        this.idFather = idFather;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Removendo conteúdo, aguarde....");
        progressDialog.setTitle("Removendo itens......");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    protected MessageResponse doInBackground(Void... voids) {
        response = new MessageResponse("",false);
        directoryDAO = new DirectoryDAO(activity);
        imageDAO = new ImageDAO(activity);
        documentDAO = new DocumentDAO(activity);
        Directory directoryFather = null;
        Docs doc = null;
        HashSet<Long> docsIds = new HashSet<Long>();
        int erros = 0;
        if(idFather != null && idFather.floatValue() > 0L){
            directoryFather = directoryDAO.getItemById(idFather);
            docsIds = directoryFather.getDocumentsIds();
        }
        MODEL:
        for(ItemModel m : models){//TODO: Alterar, método muito ridículo, isso não é um código de qualidade. Acoplamento nível MASTER;
            if(m.getType() == ItemModel.IMAGE){
                imageDAO.remove((Long)m.getId());
                for(Long idDoc : docsIds){
                    doc = documentDAO.getItemById(idDoc);
                    if(doc != null && doc.getImagesIds().contains(m.getId())) {
                        doc.getImagesIds().remove(m.getId());
                        if(doc.getImagesIds().isEmpty() && doc.getImages().isEmpty()) {
                            erros = documentDAO.remove(doc).getStatus() ? erros : erros++;
                            if(directoryFather != null)
                                directoryFather.getDocumentsIds().remove(idDoc);
                        }else {
                            erros = documentDAO.update(doc).getStatus() ? erros : erros++;
                        }
                        continue MODEL;
                    }
                }
            }else{
                erros = directoryDAO.remove(new Directory((Long)m.getId(),"")).getStatus() ? erros : erros++;
            }
        }
        if(directoryFather != null)
            response = directoryDAO.update(directoryFather);

        if(erros  == models.size()) {
            response.setStatus(false);
            response.setMsg("Problemas ao remover itens, não foi possível remover nenhum item.");
        } else if(erros  > 0 ) {
            response.setStatus(false);
            response.setMsg(response.getMsg()+"\n "+erros+" erros ocorreram no processo.");
        }else{
            response.setStatus(true);
            response.setMsg("Itens removidos com sucesso!");
        }
        return response;
    }

    @Override
    protected void onPostExecute(MessageResponse messageResponse) {
        if(progressDialog.isShowing())
            progressDialog.dismiss();

        if(messageResponse.getStatus()){//TODO:chamar tela do Lucas!-Ok
            //Log.e("ID FATHER", idFather.toString());
            activity.changeFragment(new MyFolderFragment(activity, activity, new Directory(idFather, "")));
        }else{
            Toast.makeText(activity, response.getMsg(), Toast.LENGTH_LONG).show();
        }



    }
}


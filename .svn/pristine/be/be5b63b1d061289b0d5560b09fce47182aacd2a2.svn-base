package ocrm.labex.feevale.br.ocr_mobile.view.adapters.utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import ocrm.labex.feevale.br.ocr_mobile.MainActivity;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.DirectoryDAO;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.ImageDAO;
import ocrm.labex.feevale.br.ocr_mobile.model.Directory;
import ocrm.labex.feevale.br.ocr_mobile.model.Image;
import ocrm.labex.feevale.br.ocr_mobile.utils.MessageResponse;
import ocrm.labex.feevale.br.ocr_mobile.utils.TagObjectHelper;
import ocrm.labex.feevale.br.ocr_mobile.view.fragments.MyFolderFragment;

/**
 * Created by 0126128 on 18/11/2014.
 */
public class TaguearImagesAndDirectoriesTask extends AsyncTask<Void, MessageResponse, MessageResponse> {

    private List<Image> images;
    private List<Directory> directories;
    private MainActivity activity;
    private ImageDAO imageDAO;
    private DirectoryDAO directoryDAO;
    private MessageResponse response;
    private ProgressDialog progressDialog;
    private List<TagObjectHelper> helpers;
    private Long idFather;

    public TaguearImagesAndDirectoriesTask(MainActivity activity, List<Image> images,
                                           List<Directory> directories, List<TagObjectHelper> helpers) {
        this.activity = activity;
        this.images = images;
        this.directories = directories;
        this.helpers = helpers;
    }

    public TaguearImagesAndDirectoriesTask(MainActivity activity, List<Image> images,
                                           List<Directory> directories, List<TagObjectHelper> helpers, Long idFather) {
        this.activity = activity;
        this.images = images;
        this.directories = directories;
        this.helpers = helpers;
        this.idFather = idFather;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Alterando conteúdo, aguarde....");
        progressDialog.setTitle("Adicionando tags......");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    protected MessageResponse doInBackground(Void... voids) {
        response = new MessageResponse("",false);
        directoryDAO = new DirectoryDAO(activity);
        imageDAO = new ImageDAO(activity);
        int errors = 0;

        for(Image image : images){
            for(TagObjectHelper  tagHelper : helpers){
                if(tagHelper.isStatus() && !tagHelper.isSecundaryStatus())
                    image.getTagsIds().add(tagHelper.getObject().getId());
                else if (!tagHelper.isStatus() && !tagHelper.isSecundaryStatus())
                    if(image.getTagsIds().contains(tagHelper.getObject().getId()))
                        image.getTagsIds().remove(tagHelper.getObject().getId());
            }
            errors = imageDAO.update(image).getStatus() ? errors : errors++;
        }

        for(Directory directory : directories){
            for(TagObjectHelper  tagHelper : helpers){
                if(tagHelper.isStatus() && !tagHelper.isSecundaryStatus())
                    directory.getTagsIds().add(tagHelper.getObject().getId());
                else if (!tagHelper.isStatus() && !tagHelper.isSecundaryStatus())
                    if(directory.getTagsIds().contains(tagHelper.getObject().getId()))
                        directory.getTagsIds().remove(tagHelper.getObject().getId());
            }
            errors = directoryDAO.update(directory).getStatus() ? errors : errors++;
        }

        if(errors  == (images.size() + directories.size())) {
            response.setStatus(false);
            response.setMsg("Ops! Não foi possível adicionar tags nos itens!");
        } else if(errors  > 0 ) {
            response.setStatus(false);
            response.setMsg(response.getMsg()+"\n "+errors+" erros ocorreram no processo.");
        }else{
            response.setStatus(true);
            response.setMsg("Tag(s) adicionada(s) com sucesso!");
        }
        return response;
    }

    @Override
    protected void onPostExecute(MessageResponse messageResponse) {
        if(progressDialog.isShowing())
            progressDialog.dismiss();

        if(messageResponse.getStatus()){//TODO:chamar tela do Lucas!
            //Log.e("ID FATHER", idFather.toString());
            activity.changeFragment(new MyFolderFragment(activity, activity, new Directory(idFather, "")));
        }else{
            Toast.makeText(activity, response.getMsg(), Toast.LENGTH_LONG).show();
        }



    }
}



package ocrm.labex.feevale.br.ocr_mobile.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import ocrm.labex.feevale.br.ocr_mobile.MainActivity;
import ocrm.labex.feevale.br.ocr_mobile.R;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.DirectoryDAO;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.ImageDAO;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.TagsDAO;
import ocrm.labex.feevale.br.ocr_mobile.model.Directory;
import ocrm.labex.feevale.br.ocr_mobile.model.Image;
import ocrm.labex.feevale.br.ocr_mobile.model.Tags;
import ocrm.labex.feevale.br.ocr_mobile.view.adapters.TagListViewAdapter;
import ocrm.labex.feevale.br.ocr_mobile.view.adapters.utils.ItemModel;
import ocrm.labex.feevale.br.ocr_mobile.view.adapters.utils.Observer;
import ocrm.labex.feevale.br.ocr_mobile.view.adapters.utils.RemoveDirectoryAndImagesTask;
import ocrm.labex.feevale.br.ocr_mobile.view.adapters.utils.TaguearImagesAndDirectoriesTask;

/**
 * Created by 0126128 on 06/11/2014.
 */
public class DialogUtils {

    private Activity activity;
    private AlertDialog alertDialog;
    private List<TagObjectHelper> dialogList;
    private Directory directory;
    private Tags tag;
    private HashSet<Directory> directories;
    private HashSet<Image> images;
    private EditText editText;
    private List<ItemModel> models;
    private BaseAdapter baseAdapter;
    private Observer observer;
    private Long idFather;

    private DialogUtils() {
    }

    public DialogUtils(Activity activity) {
        this();
        this.activity = activity;
    }

    public void createTagsDialog(List<ItemModel> models) {
        createTagsDialog(models, 0L);
    }

    public void createTagsDialog(List<ItemModel> models, Long idFather){
        this.models = models;
        this.idFather = idFather;
        new LoadTagsDialog(models, activity).execute();
    }

    public AlertDialog showTagsDialog(List<TagObjectHelper> helpers, SortedMap<Integer,
                                     Integer> tagsByItems, List<Image> images, List<Directory> directories){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(true);
        View dialog = activity.getLayoutInflater().inflate(R.layout.tags_dialog, null);
        ListView listViewTags = (ListView)dialog.findViewById(R.id.listViewTags);

        this.dialogList = helpers;

        listViewTags.setAdapter(new TagListViewAdapter(this.dialogList, tagsByItems,activity));
        ImageView addTags = (ImageView)dialog.findViewById(R.id.addTagImageView);
        ImageView cancelTags = (ImageView)dialog.findViewById(R.id.cancelTagImageView);
        cancelTags.setOnClickListener(cancelDialogClick());
        addTags.setOnClickListener(selectTagsClick(images, directories));
        builder.setView(dialog);
        alertDialog = builder.create();

        return alertDialog;
    }

    public void showRemoveFilesAndDirectories(List<ItemModel> models){
        showRemoveFilesAndDirectories(models, 0L);
    }

    public void showRemoveFilesAndDirectories(List<ItemModel> models, Long idFather){
        this.idFather = idFather;
        this.models = models;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.dialog_remove_files_title);
        builder.setMessage(R.string.dialog_remove_files_body);
        builder.setCancelable(true);
        builder.setNegativeButton(R.string.dialog_remove_files_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setPositiveButton(R.string.dialog_remove_files_confirm, removeItens());
        builder.create().show();
    }

    public void showNewDirectoryDialog(Directory node){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.new_directory_dialog, null);
        editText = (EditText)view.findViewById(R.id.newDirEditText);

        Button buttonCancel = (Button)view.findViewById(R.id.cancelNewDirButton);
        buttonCancel.setOnClickListener(cancelDialogClick());

        Button buttonConfirm = (Button)view.findViewById(R.id.addNewDirButton);
        buttonConfirm.setOnClickListener(createDialogClick(node));

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void showSaveTagDialog(Observer observer) {
        showSaveTagDialog(observer, new Tags(""));
    }

    public void showSaveTagDialog(Observer observer,Tags tag){
        setObserver(observer);
        this.tag = tag;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        View view = activity.getLayoutInflater().inflate(R.layout.new_edit_tag, null);
        builder.setTitle(R.string.dialog_add_edit_tags_title);

        editText = (EditText)view.findViewById(R.id.tagNameEditText);
        editText.setText(tag.getName());

        Button buttonCancel = (Button)view.findViewById(R.id.cancelTagButton);
        buttonCancel.setOnClickListener(cancelDialogClick());

        Button buttonConfirm = (Button)view.findViewById(R.id.saveTagButton);
        buttonConfirm.setOnClickListener(performConfirmTagClick());

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private View.OnClickListener selectTagsClick(final List<Image> images, final List<Directory> directories){
       return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alertDialog.isShowing())
                    alertDialog.dismiss();
                new TaguearImagesAndDirectoriesTask((MainActivity)activity,images, directories,dialogList, idFather).execute();
            }
        };
    }

    private View.OnClickListener cancelDialogClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(alertDialog.isShowing())
                    alertDialog.dismiss();
            }
        };
    }

    private View.OnClickListener createDialogClick(final Directory node){

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageResponse response = new MessageResponse("",false);
                DirectoryDAO dao = new DirectoryDAO(activity);
                directory = new Directory(editText.getText().toString());
                if(node != null && node.getId() > 0)
                      response.setStatus(dao.verifyNameAvailabilityByNode(node.getId(), directory.getName()));
                else
                    response.setStatus(dao.verifyNameAvailabilityByTrunk(directory.getName()));


                if(response.getStatus())
                     response = dao.insert(directory);
                else
                    response.setMsg("Nome indisponível, tente outro!");

                /**
                 * TODO: Adicionar outro evento para o que vir acontecer após criar um novo diretório!
                 */
                if(response.getStatus()) {
                    if (alertDialog.isShowing())
                        alertDialog.dismiss();
                    Toast.makeText(activity, response.getMsg(), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(activity, response.getMsg(), Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private View.OnClickListener performConfirmTagClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageResponse response = null;
                TagsDAO tagsDAO = new TagsDAO(activity);
                tag.setName(editText.getText().toString());

                if (tagsDAO.checkTagNameAvailability(tag.getName())) {
                    if (tag.getId() > 0)
                        response = tagsDAO.update(tag);
                    else
                        response = tagsDAO.insert(tag);

                    observer.update(response);
                    if (alertDialog.isShowing())
                        alertDialog.dismiss();
                } else {
                    Toast.makeText(activity, "Ops! Existe uma tag com esse nome!", Toast.LENGTH_LONG).show();
                }
            }
        };
    }



    private DialogInterface.OnClickListener removeItens(){
            return new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   new RemoveDirectoryAndImagesTask((MainActivity)activity, models, idFather).execute();
                    dialogInterface.dismiss();
                }
            };
    }

    public Observer getObserver() {
        return observer;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    class LoadTagsDialog extends AsyncTask<Void, Void, Void>{

        private List<ItemModel> models;
        private Activity activity;
        private DirectoryDAO directoryDAO;
        private ImageDAO imageDAO;
        private ProgressDialog dialog;
        private List<Image> images;
        private List<Directory> directories;
        private SortedMap<Integer, Integer> tagsByItems;
        private TagsDAO tagsDAO;
        private List<TagObjectHelper> helpers;

        LoadTagsDialog(List<ItemModel> models, Activity activity) {
            this.models = models;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(activity);
            dialog.setTitle("Verificando tags....");//TODO:Colocar no string values depois
            dialog.setMessage("Aguarde.......");
            dialog.setCancelable(false);
            dialog.show();

            images = new ArrayList<Image>();
            directories = new ArrayList<Directory>();
            tagsByItems = new TreeMap<Integer, Integer>();
            tagsDAO = new TagsDAO(activity);
            imageDAO = new ImageDAO(activity);
            directoryDAO = new DirectoryDAO(activity);
            helpers = new ArrayList<TagObjectHelper>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Image image = null;
            Directory directory = null;
            HashSet<Tags> tagsHashSet = tagsDAO.getItens();
            for(Tags t : tagsHashSet) {
                helpers.add(new TagObjectHelper(t, false, false));
                tagsByItems.put(t.getId(), 0);
            }

            for(ItemModel model : models){
                if(model.getType() == ItemModel.IMAGE){
                    image = imageDAO.getItemById(model.getId());
                    this.images.add(image);
                    for (Integer i : image.getTagsIds()) {
                        if (tagsByItems.containsKey(i))
                            tagsByItems.put(i, tagsByItems.get(i) + 1);
                        else
                            tagsByItems.put(i, 0);
                    }
                }else if (model.getType() == ItemModel.DIRECTORY){
                    directory = directoryDAO.getItemById(model.getId());
                    this.directories.add(directory);
                    for(Integer i : directory.getTagsIds())
                        if(tagsByItems.containsKey(i))
                            tagsByItems.put(i, tagsByItems.get(i)+1);
                        else
                            tagsByItems.put(i, 0);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void b) {
            if(dialog.isShowing())
                dialog.dismiss();

            showTagsDialog(helpers, tagsByItems, images, directories).show();
        }
    }
}



package ocrm.labex.feevale.br.ocr_mobile.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import ocrm.labex.feevale.br.ocr_mobile.R;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.TagsDAO;
import ocrm.labex.feevale.br.ocr_mobile.model.Directory;
import ocrm.labex.feevale.br.ocr_mobile.model.Image;
import ocrm.labex.feevale.br.ocr_mobile.utils.DialogUtils;
import ocrm.labex.feevale.br.ocr_mobile.view.adapters.FilesManagerAdapter;
import ocrm.labex.feevale.br.ocr_mobile.view.adapters.utils.ItemModel;

/**
 * Created by 0126128 on 17/11/2014.
 */
public class FilesManagerFragment extends Fragment{

    private Activity activity;
    private List<Image> images;
    private List<Directory> directories;
    private List<ItemModel> models;
    private Button buttonTags;
    private Button buttonDelete;
    private Long idFather;

    private FilesManagerFragment() {}

    public FilesManagerFragment(Activity activity, HashSet<Directory> directories, HashSet<Image> images) {
        this();
        this.activity = activity;
        this.directories = changeHashToList(directories);
        this.images = changeHashToList(images);
    }

    public FilesManagerFragment(Activity activity, List<ItemModel> models) {
        this();
        this.activity = activity;
        this.models = models;
        this.idFather = 0L;
    }

    public FilesManagerFragment(Activity activity, List<ItemModel> models, Long idFather) {
        this();
        this.activity = activity;
        this.models = models;
        this.idFather = idFather;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            setRetainInstance(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager, null);
        GridView gridView = (GridView)view.findViewById(R.id.gridViewManagerDirectories);
        FilesManagerAdapter adapter = new FilesManagerAdapter(models,activity);

        buttonTags = (Button)view.findViewById(R.id.tagsManagerFileButton);
        buttonTags.setOnClickListener(tagsButtonListener());

        buttonDelete = (Button)view.findViewById(R.id.deleteManagerFileButton);
        buttonDelete.setOnClickListener(removeButtonListener());

        gridView.setAdapter(adapter);

        return view;
    }


    private View.OnClickListener tagsButtonListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ItemModel> selecteds = new ArrayList<ItemModel>();
                for(ItemModel m : models){
                    if(m.isSelected()){
                        selecteds.add(m);
                    }
                }
                TagsDAO tagsDAO = new TagsDAO(activity);
                new DialogUtils(activity).createTagsDialog(selecteds, idFather);
            }
        };
    }

    private View.OnClickListener removeButtonListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ItemModel> selecteds = new ArrayList<ItemModel>();
                for(ItemModel m : models){
                    if(m.isSelected())
                        selecteds.add(m);
                }
                new DialogUtils(activity).showRemoveFilesAndDirectories(selecteds, idFather);
            }
        };
    }

    private List changeHashToList(HashSet objects) {
        List listItems = new ArrayList();
        for(Object o : objects){
            listItems.add(o);
        }
        return listItems;
    }
}

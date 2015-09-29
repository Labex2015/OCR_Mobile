package ocrm.labex.feevale.br.ocr_mobile.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import ocrm.labex.feevale.br.ocr_mobile.R;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.TagsDAO;
import ocrm.labex.feevale.br.ocr_mobile.model.Tags;
import ocrm.labex.feevale.br.ocr_mobile.utils.DialogUtils;
import ocrm.labex.feevale.br.ocr_mobile.utils.MessageResponse;
import ocrm.labex.feevale.br.ocr_mobile.view.adapters.TagsManagerAdapter;

/**
 * Created by 0126128 on 11/11/2014.
 */
public class TagsManagerFragment extends Fragment {

    private Activity activity;
    private TagsDAO dao;
    private Tags tagReference;
    private Button button;
    private TagsManagerAdapter adapter;
    private ListView listView;

    public TagsManagerFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tags_manager, null);
        button = (Button)view.findViewById(R.id.tags_manager_save_edit);
        button.setOnClickListener(saveListener());
        listView = (ListView)view.findViewById(R.id.tagsListViewManager);
        listView.setAdapter(adapter);
        return view;
    }

    private View.OnClickListener saveListener() {
         return new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                new DialogUtils(activity).showSaveTagDialog(adapter);
             }
         };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            dao = new TagsDAO(activity);
            tagReference = new Tags("");
            adapter = new TagsManagerAdapter(activity, dao.getItens());
            setRetainInstance(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.activity = getActivity();
        //this.topicAdapter.setMyActivity(activity);
    }

    public Activity getActivityFragment(){
        if(getActivity() != null)
            return getActivity();
        else
            return this.activity;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        //this.topicAdapter.setMyActivity(getActivity());
    }
}

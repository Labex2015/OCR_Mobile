package ocrm.labex.feevale.br.ocr_mobile.view.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import ocrm.labex.feevale.br.ocr_mobile.R;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.TagsDAO;
import ocrm.labex.feevale.br.ocr_mobile.model.Tags;
import ocrm.labex.feevale.br.ocr_mobile.utils.DialogUtils;
import ocrm.labex.feevale.br.ocr_mobile.utils.MessageResponse;
import ocrm.labex.feevale.br.ocr_mobile.view.adapters.utils.Observer;

/**
 * Created by 0126128 on 11/11/2014.
 */
public class TagsManagerAdapter extends BaseAdapter implements Observer{



    private List<Tags> tagsList;
    private Tags referenceTag;
    private Activity activity;
    private TagsDAO dao;
    private AlertDialog dialog;
    private MessageResponse response;


    public TagsManagerAdapter( Activity activity, HashSet<Tags> tagsHashSet) {
        this.activity = activity;
        this.dao = new TagsDAO(activity);
        changeHashToList(tagsHashSet);
    }

    @Override
    public int getCount() {
        return tagsList.size();
    }

    @Override
    public Object getItem(int i) {
        return tagsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return tagsList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int p = i;
        view = activity.getLayoutInflater().inflate(R.layout.tags_manager_item, null);

        TextView textView = (TextView) view.findViewById(R.id.tagManagerItemTextView);
        textView.setText(tagsList.get(p).toString());

       ImageView removeTag = (ImageView) view.findViewById(R.id.removeTagsManager);
       removeTag.setOnClickListener(removeButtonAction(tagsList.get(p)));

        view.setOnLongClickListener(setTagToEdit(this, tagsList.get(p)));
        return view;
    }

    private View.OnLongClickListener setTagToEdit(final Observer observer, Tags tag) {
        final Tags t = tag;
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new DialogUtils(activity).showSaveTagDialog(observer, t);
                return true;
            }
        };
    }

    private void changeHashToList(HashSet<Tags> tagsHashSet){
        tagsList = new ArrayList<Tags>();
        for(Tags t : tagsHashSet)
            this.tagsList.add(t);
    }

    private View.OnClickListener removeButtonAction(Tags tagToRemove){
        final Tags t = tagToRemove;
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItemDialog(t);
            }
        };
    }

    private void removeItemDialog(Tags tagToRemove){
        TagsDAO tagsDAO = new TagsDAO(activity);
        if(tagsDAO.verifyTagToRemove(tagToRemove.getId())){
            Toast.makeText(activity, "Ops! Essa tag está em uso no momento!", Toast.LENGTH_LONG).show();
        }else{
            AlertDialog.Builder builderDialog = new AlertDialog.Builder(activity);
            builderDialog.setTitle(R.string.dialog_remove_tags_title);
            builderDialog.setMessage(R.string.dialog_remove_tags_body);
            builderDialog.setCancelable(true);
            builderDialog.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builderDialog.setPositiveButton(R.string.dialog_confirm, removeTag(tagToRemove));
            dialog = builderDialog.create();
            dialog.show();
        }

    }

    private DialogInterface.OnClickListener removeTag(Tags tag) {
        dao = new TagsDAO(activity);
        final Tags t = tag;
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                response = dao.remove(t);
                if(response.getStatus()){
                    reseteItems(dao);
                    if(dialog.isShowing())
                        dialog.dismiss();
                }
                notifyDataSetChanged();

            }
        };
    }

    private void reseteItems(TagsDAO tagsDAO){
        changeHashToList(tagsDAO.getItens());
    }

    @Override
    public void update(MessageResponse messageResponse) {
        if(messageResponse.getStatus()) {
            reseteItems(new TagsDAO(activity));
            this.notifyDataSetChanged();
        }
        Toast.makeText(activity, messageResponse.getMsg(), Toast.LENGTH_LONG).show();
    }
}

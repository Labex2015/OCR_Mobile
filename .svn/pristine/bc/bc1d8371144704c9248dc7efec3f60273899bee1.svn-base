package ocrm.labex.feevale.br.ocr_mobile.view.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.SortedMap;

import ocrm.labex.feevale.br.ocr_mobile.R;
import ocrm.labex.feevale.br.ocr_mobile.model.Directory;
import ocrm.labex.feevale.br.ocr_mobile.model.Image;
import ocrm.labex.feevale.br.ocr_mobile.model.Tags;
import ocrm.labex.feevale.br.ocr_mobile.utils.TagObjectHelper;
import ocrm.labex.feevale.br.ocr_mobile.view.adapters.utils.ItemModel;

/**
 * Created by 0126128 on 06/11/2014.
 */
public class TagListViewAdapter extends BaseAdapter {

    private List<TagObjectHelper>  myItemList;
    private LayoutInflater layoutInflater;
    private SortedMap<Integer, Integer> tagsByItems;

    public TagListViewAdapter(List<TagObjectHelper> myItemList, SortedMap<Integer, Integer> tagsByItems, Activity activity) {
        this.myItemList = myItemList;
        this.tagsByItems = tagsByItems;
        this.layoutInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return myItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return myItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null)
            view = layoutInflater.inflate(R.layout.tags_listview_item, null);

        TextView label = (TextView) view.findViewById(R.id.labelTag);
        label.setText(myItemList.get(i).getObject().getName());

        CheckBox checkBox = (CheckBox)view.findViewById(R.id.tagChecked);
        if(!tagsByItems.containsKey(myItemList.get(i).getObject().getId())
                ||tagsByItems.get(myItemList.get(i).getObject().getId()) == 0) {
            checkBox.setSelected(false);
            checkBox.setChecked(false);
        }else if(tagsByItems.get(myItemList.get(i).getObject().getId()) >= 1) {
            checkBox.setSelected(true);
            checkBox.setChecked(true);
            myItemList.get(i).setSecundaryStatus(true);
        }

        final int position = i;

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                myItemList.get(position).setStatus(isChecked);
                myItemList.get(position).setSecundaryStatus(false);
                CheckBox cb = (CheckBox)compoundButton;
                cb.setSelected(false);
            }
        });

        return view;
    }

}

package ocrm.labex.feevale.br.ocr_mobile.view.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ocrm.labex.feevale.br.ocr_mobile.MainActivity;
import ocrm.labex.feevale.br.ocr_mobile.R;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.TagsDAO;
import ocrm.labex.feevale.br.ocr_mobile.model.Image;
import ocrm.labex.feevale.br.ocr_mobile.utils.DialogUtils;
import ocrm.labex.feevale.br.ocr_mobile.utils.FileUtils;
import ocrm.labex.feevale.br.ocr_mobile.view.adapters.utils.ItemModel;

/**
 * Created by 0126128 on 23/10/2014.
 */
public class ImageTextFragment extends Fragment {

    private Activity activity;
    private Image image;
    private ImageView imageView;
    private ImageView iconTag;
    private TextView textView;

    public ImageTextFragment(Activity activity, Image image) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_image, null);
        textView = (TextView)view.findViewById(R.id.textItem);
        imageView = (ImageView)view.findViewById(R.id.imageItem);
        iconTag = (ImageView)view.findViewById(R.id.imageTag);

        textView.setText(image.getText());
        imageView.setImageBitmap(new FileUtils(activity).getExternalImage(image.getName()));

        textView.setOnClickListener(clickToEditText());
        imageView.setOnTouchListener(clickImage());
        iconTag.setOnClickListener(clickToAddTags(image));

        return view;
    }

    private View.OnClickListener clickToEditText(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)activity).changeFragment(new EditableTextFragment(activity,image));
            }
        };
    }

    private View.OnClickListener clickToAddTags(final Image image){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils dialogUtils  = new DialogUtils(getActivity());
                ItemModel item = new ItemModel(image.getId(), image.getName(), ItemModel.IMAGE);
                List<ItemModel> itemM = new ArrayList<ItemModel>();
                itemM.add(item);
                dialogUtils.createTagsDialog(itemM);
           }
        };
    }

    private View.OnTouchListener clickImage(){
        return new View.OnTouchListener() {
             @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                 if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                     if(iconTag.getVisibility() == View.INVISIBLE)
                        new ThreadComponentStats().execute();
                 }
                 return false;
            }
        };
    }

    private class ThreadComponentStats extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    iconTag.setVisibility(View.VISIBLE);
                }
            });
        }

        @Override
        protected Void doInBackground(Void... voids) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iconTag.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }, 5000);
            return null;
        }
    }
}

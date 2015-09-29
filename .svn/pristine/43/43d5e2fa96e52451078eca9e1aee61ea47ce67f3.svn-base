package ocrm.labex.feevale.br.ocr_mobile.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import ocrm.labex.feevale.br.ocr_mobile.R;

public class CustomGrid extends BaseAdapter{
    private Context mContext;
    private final String[] web;
    private final int[] Imageid;

    public CustomGrid(Context c, String[] web, int[] Imageid ) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Imageid.length;
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.custom_gridview, null);
            ImageView imageView = (ImageView)grid.findViewById(R.id.thumbImage);
            imageView.setImageResource(Imageid[position]);



        } else {
            grid = (View) convertView;
        }





        return grid;
    }
}

package ocrm.labex.feevale.br.ocr_mobile.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;

import ocrm.labex.feevale.br.ocr_mobile.R;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.DirectoryDAO;
import ocrm.labex.feevale.br.ocr_mobile.model.Directory;
import ocrm.labex.feevale.br.ocr_mobile.model.Image;
import ocrm.labex.feevale.br.ocr_mobile.utils.FileUtils;

public class GridViewCustomAdapter extends ArrayAdapter{

    Context context;
    List<Object> files = null;
    HashSet<Image> imagens;
    public GridViewCustomAdapter( Context context, List<Object> files){

        super( context, 0 );
        this.context = context;
        this.files = files;
        this.imagens = imagens;

    }

    public int getCount(){ return files.size();}

    @Override
    public Object getItem(int position) {
        return this.files.get(position);
    }

    @Override
    public View getView( final int position, View convertView, ViewGroup parent ){

        View row = convertView;

        if ( row == null ){


            LayoutInflater inflater = ( ( Activity ) context ).getLayoutInflater();
            row = inflater.inflate( R.layout.grid_row, parent, false );
            TextView textViewTitle = ( TextView  ) row.findViewById( R.id.textView  );
            ImageView imageViewIte = ( ImageView ) row.findViewById( R.id.imageView );
            if(files.get(position) instanceof Directory){

                DirectoryDAO dDao = new DirectoryDAO((Activity)context);

                Directory dir = (Directory) this.files.get(position);
                int numberFile = dDao.getNumberOfImage(dir.getId());
                StringBuilder strBuilder = new StringBuilder();
                strBuilder.append(dir.getName());
                strBuilder.append("(");
                strBuilder.append(numberFile+"");
                strBuilder.append(")");
                textViewTitle.setText(strBuilder);
                imageViewIte.setImageResource(R.drawable.pasta_arquivos);

            }else if(files.get(position) instanceof Image){

                Image image = (Image) this.files.get(position);
                textViewTitle.setText(image.getName());
                FileUtils fileUtils = new FileUtils((Activity)context);
                Bitmap bit = fileUtils.getExternalImage(image.getName());
                Drawable d = new BitmapDrawable(context.getResources(),bit);
                imageViewIte.setImageDrawable(d);
            }
         }
         return row;

    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }
}
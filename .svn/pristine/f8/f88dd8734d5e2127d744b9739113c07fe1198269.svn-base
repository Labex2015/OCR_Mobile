package ocrm.labex.feevale.br.ocr_mobile.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import ocrm.labex.feevale.br.ocr_mobile.R;

public class ManagerAdapter extends ArrayAdapter{

    Context   context;
    TextView  textViewTitle;
    ImageView imageViewItem;
    CheckBox  checkBoxItem;

    public ManagerAdapter( Context context ){

        super( context, 0 );
        this.context = context;

    }

    public int getCount(){ return 60; }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ){

        View row = convertView;

        if ( row == null ){

            LayoutInflater inflater = ( ( Activity ) context ).getLayoutInflater();

            row = inflater.inflate( R.layout.manager_fragment_item, parent, false );

            textViewTitle = ( TextView  ) row.findViewById( R.id.textView  );
            imageViewItem = ( ImageView ) row.findViewById( R.id.imageView );
            checkBoxItem  = ( CheckBox )  row.findViewById( R.id.checkBox );

            textViewTitle.setText( position + "" );
            imageViewItem.setImageResource( R.drawable.ic_launcher );

        }

        return row;

    }

}
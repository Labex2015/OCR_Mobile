package ocrm.labex.feevale.br.ocr_mobile.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ocrm.labex.feevale.br.ocr_mobile.R;
import ocrm.labex.feevale.br.ocr_mobile.view.adapters.ManagerAdapter;

import android.widget.GridView;

public class ManagerFragment extends Fragment{

    GridView       gridView;
    ManagerAdapter grisViewCustomeAdapter;

    private View   myFragmentView;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){

        myFragmentView = inflater.inflate( R.layout.fragment_manager, container, false );

        gridView               = ( GridView ) myFragmentView.findViewById(R.id.gridViewManagerDirectories);
        grisViewCustomeAdapter = new ManagerAdapter( getActivity() );

        gridView.setAdapter( grisViewCustomeAdapter );

        return myFragmentView;

    }

}

package ocrm.labex.feevale.br.ocr_mobile.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ocrm.labex.feevale.br.ocr_mobile.MainActivity;
import ocrm.labex.feevale.br.ocr_mobile.R;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.DirectoryDAO;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.DocumentDAO;
import ocrm.labex.feevale.br.ocr_mobile.logicmodel.ImageDAO;
import ocrm.labex.feevale.br.ocr_mobile.model.Directory;
import ocrm.labex.feevale.br.ocr_mobile.model.Image;
import ocrm.labex.feevale.br.ocr_mobile.view.adapters.GridViewCustomAdapter;

public class MyFolderFragment extends Fragment {

    private Context activity;
    private View myFragmentView;
    private GridView myView;
    private int[] mThumbIds = {
            R.drawable.pasta_arquivos,  R.drawable.pasta_arquivos,
            R.drawable.pasta_arquivos,  R.drawable.pasta_arquivos
    };
    DirectoryDAO directoryDAO;
    List<Object> files;

    private Long idFolder;
    private EventosClick eventos;
    private Directory dirSaved;





    public MyFolderFragment(Context activity, EventosClick eventos){
         this.activity =  activity;
         this.eventos = eventos;
         this.dirSaved = null;
    }
    public MyFolderFragment(Context activity, EventosClick eventos, Directory dirSaved){
        this.activity = activity;
        this.eventos = eventos;
        this.dirSaved = dirSaved;
    }
    public interface EventosClick {
        public void retornaId(Directory dir);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.main, container, false);
        myView = (GridView) myFragmentView.findViewById(R.id.myfolder);

        if(dirSaved != null && dirSaved.getId() > 0){
            List<Object> files = processOp(dirSaved.getId());
            inflaGridView(myView, files);
        }else{
            inflaGridView(myView, null);
        }

        return myFragmentView;
    }

    public void inflaGridView(final GridView myView, List<Object> files){
        directoryDAO = new DirectoryDAO(getActivity());
        if(files == null){
            files = new ArrayList<Object>();
            HashSet<Directory> hashDirectory =  directoryDAO.getFirstLevel();
            HashSet<Long> hashDocs = new HashSet<Long>();
            DirectoryDAO dao = new DirectoryDAO(getActivity());
            HashSet<Long> idDocument = new HashSet<Long>();

            DocumentDAO documentDAO = new DocumentDAO(getActivity());
            for(Directory elemento: hashDirectory){
                files.add(elemento);
            }
        }
        myView.setAdapter(new GridViewCustomAdapter((Context)getActivity(), files));

        myView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i) instanceof  Directory){
                    // diretorio clicado

                    Directory clicked = (Directory) adapterView.getItemAtPosition(i);
                    eventos.retornaId(clicked);
                    //diretorio dentro do diretorio
                    HashSet<Directory> hashDirectory =  directoryDAO.getDirectoriesFromNode((Long) clicked.getId());
                    if(hashDirectory.size() == 0){
                        Log.d("Diretório vazio", "Vazio");
                    }else{

                        List<Object> listDir = new ArrayList<Object>();
                        HashSet<Long> hashDocs = new HashSet<Long>();
                        DocumentDAO documentDAO = new DocumentDAO(getActivity());

                        DirectoryDAO dDao = new DirectoryDAO(getActivity());
                        HashSet<Long> documents = dDao.getDocumentsByDirectory(clicked.getId());
                        DocumentDAO docDao = new DocumentDAO(getActivity());

                        for(Long idDocument: documents){
                            // para cada documento obtem uma lista de iumagens
                            HashSet<Long> imageHash = docDao.getImagesIdByDocument(idDocument);
                            ImageDAO imgDAO = new ImageDAO(getActivity());
                            HashSet<Image> imgHash = imgDAO.getImagesByDocument(idDocument);
                            for(Image imgs : imgHash){
                                listDir.add(imgs);
                            }

                        }
                        // preenche o diretório atual com outros diretorios
                        for(Directory elemento: hashDirectory){
                            listDir.add(elemento);
                            HashSet<Long> idDocument = elemento.getDocumentsIds();
                        }

                        inflaGridView(myView, listDir);
                    }



                }else{
                    ((MainActivity)activity).changeFragment(new ImageTextFragment((MainActivity)activity, (Image)adapterView.getItemAtPosition(i)));
                }
            }
        });

    }


    public HashSet<Image> getImageFromDoc(){
        return new HashSet<Image>();
    }



    public List<Object> processOp(Long idDiretorio){
           //diretorio dentro do diretorio
        DirectoryDAO directoryD = new DirectoryDAO(getActivity());
        Directory aux = directoryD.getItemById(idDiretorio);
        HashSet<Directory> hashDirectory =  directoryD.getDirectoriesFromNode(aux.getNodeDirectory());
        if(hashDirectory.size() == 0){
            Log.d("Diretório vazio", "Vazio");
        }else{

            DirectoryDAO dDao = new DirectoryDAO(getActivity());
            Directory directoryFather = dDao.getItemById(aux.getNodeDirectory());
            eventos.retornaId(directoryFather == null ? new Directory(0L,"") : directoryFather);

            List<Object> listDir = new ArrayList<Object>();
            HashSet<Long> hashDocs = new HashSet<Long>();
            DocumentDAO documentDAO = new DocumentDAO(getActivity());

            HashSet<Long> documents = directoryD.getDocumentsByDirectory(aux.getNodeDirectory());
            DocumentDAO docDao = new DocumentDAO(getActivity());

            for(Long idDocument: documents){
                // para cada documento obtem uma lista de iumagens
                HashSet<Long> imageHash = docDao.getImagesIdByDocument(idDocument);
                ImageDAO imgDAO = new ImageDAO(getActivity());
                HashSet<Image> imgHash = imgDAO.getImagesByDocument(idDocument);
                for(Image imgs : imgHash){
                    listDir.add(imgs);
                }

            }
             for(Directory elemento: hashDirectory){
                listDir.add(elemento);
                HashSet<Long> idDocument = elemento.getDocumentsIds();
            }
            return listDir;
        }
        return null;
    }

}

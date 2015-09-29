package ocrm.labex.feevale.br.ocr_mobile.logicmodel;

import android.app.Activity;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import ocrm.labex.feevale.br.ocr_mobile.handler.DirectoryHandler;
import ocrm.labex.feevale.br.ocr_mobile.model.Directory;
import ocrm.labex.feevale.br.ocr_mobile.model.Docs;
import ocrm.labex.feevale.br.ocr_mobile.model.Tags;
import ocrm.labex.feevale.br.ocr_mobile.utils.AppVariables;
import ocrm.labex.feevale.br.ocr_mobile.utils.MessageResponse;
import ocrm.labex.feevale.br.ocr_mobile.utils.SharedPreferencesUtils;

/**
 * Created by 0126128 on 05/11/2014.
 */
public class DirectoryDAO implements ModelDAO<Directory> {

    private Activity activity;
    private MessageResponse response;
    private DirectoryHandler handler;


    public DirectoryDAO(Activity activity) {
        this.activity = activity;
        this.handler = new DirectoryHandler(activity);
    }

    private Long getId(){
        Long lastId = new SharedPreferencesUtils().verifySharedPreferencesIDLong(activity,AppVariables.ID_DIRECTORY);
        if(lastId == 0)
            lastId++;
        if(lastId == null)
            return 1L;

        return lastId;
    }


    @Override
    public MessageResponse insert(Directory object) {
        Long id = 0L;
        id = getId();
        try {
            object.setId(id);
            handler.insertDirectories(object);
            new SharedPreferencesUtils().saveNewLongID(id,activity,AppVariables.ID_DIRECTORY);
            response = new MessageResponse("Diretório salvo com sucesso!",true);
        } catch (IOException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao tentar carregar xml!",false);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao tentar converter dados!",false);
        } catch (SAXException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas com o XML!",false);
        } catch (JDOMException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas com o XML!",false);
        }
        return response;
    }

    @Override
    public MessageResponse update(Directory object) {
        try {
            handler.updateDirectory(object);
            response = new MessageResponse("Diretório alterado com sucesso!",true);
        } catch (IOException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao tentar carregar xml!",false);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao tentar converter dados!",false);
        } catch (SAXException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas com o XML!",false);
        } catch (JDOMException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas com o XML!",false);
        }
        return response;
    }

    @Override
    public MessageResponse remove(Directory object) {
        try {
            HashSet<Long> docsIds = getDocumentsByDirectory(object.getId());
            DocumentDAO documentDAO = new DocumentDAO(activity);
            for(Long idDoc : docsIds)
                documentDAO.remove(idDoc);

            handler.removeDirectory(object);
            response = new MessageResponse("Diretório removido com sucesso!",true);
        } catch (IOException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao tentar carregar xml!",false);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao tentar converter dados!",false);
        } catch (SAXException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas com o XML!",false);
        } catch (JDOMException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas com o XML!",false);
        }
        return response;
    }

    public MessageResponse remove(Long id) {
        Directory directory = getItemById(id);
        if(directory != null)
            return  remove(directory);
        else
            return new MessageResponse("Ops! Não foi possível localizar o diretório!", false);
    }

    @Override
    public HashSet<Directory> getItens() {
        return handler.directories();
    }

    /**
     * TODO: Verificar a necessidade
     * @param param
     * @return
     */
    @Override
    public HashSet<Directory> getItensByParam(String param) {
        return null;
    }

    @Override
    public Directory getItemById(Number id) {
        Directory directory = null;
        try {
            directory =  handler.getDirectory((Long)id);
            if(directory != null) {
                if (directory.getTagsIds().size() > 0) {
                    Tags tag = null;
                    TagsDAO dao = new TagsDAO(activity);
                    for (Integer i : directory.getTagsIds()) {
                        tag = dao.getItemById(i);
                        if (tag != null)
                            directory.getTags().add(tag);
                    }
                }

                if (directory.getDocumentsIds().size() > 0) {
                    Docs doc = null;
                    DocumentDAO dao = new DocumentDAO(activity);
                    for (Long i : directory.getDocumentsIds()) {
                        doc = dao.getItemById(i);
                        if (doc != null)
                            directory.getDocList().add(doc);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
        return directory;
    }

    public HashSet<Directory> getFirstLevel(){
        return handler.getDirectoriesFirstLvl();
    }

    public Boolean verifyTag(Integer id){
       return handler.verifyTagInDirectories(id);
    }

    public HashSet<Directory> getDirectoriesFromNode(Long id){
        return handler.getDirectoriesByNode(id);
    }

    public MessageResponse addTagsToDirectory(Directory directory, Tags... tagList){
        for(Tags tag : tagList)
            directory.getTagsIds().add(tag.getId());

        return update(directory);
    }

    public MessageResponse addTagsToDirectories(List<Directory> directories, Tags... tagList){
        for(Directory dir : directories){
            response = addTagsToDirectory(dir, tagList);
            if(!response.getStatus())
                return response;
        }

        return response;
    }

    public MessageResponse insertDocumentsToDirectory(Directory directory, Docs... docsList){

        for(Docs docs : docsList)
            directory.getDocumentsIds().add(docs.getId());

        return update(directory);
    }

    public MessageResponse insertDocumentsToDirectory(Long idDirectory, Docs... docsList){

        Directory directory = getItemById(idDirectory);

        if(directory == null)
            return new MessageResponse("Diretório não encontrado", false);

        for(Docs docs : docsList)
            directory.getDocumentsIds().add(docs.getId());

        return update(directory);
    }

    public MessageResponse insertDocumentsToDirectory(Long idDirectory, Long... docsIds){

        Directory directory = getItemById(idDirectory);

        if(directory == null)
            return new MessageResponse("Diretório não encontrado", false);

        for(Long id : docsIds)
            directory.getDocumentsIds().add(id);

        return update(directory);
    }

    public HashSet<Long> getDocumentsByDirectory(Long idDirectory){
        HashSet<Long> idsDocuments = new HashSet<Long>();
        try {
            idsDocuments = handler.getDocumentsByNode(idDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return idsDocuments;
    }

    public Boolean verifyNameAvailabilityByNode(Long idNode, String param){
        boolean status = false;
        try {
            status = handler.verifyNameInNode(idNode, param);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }finally {
            return status;
        }
    }

    public int getNumberOfImage(Long idDir){
        int number = 0;
        try {
            if(handler.verifyImagesInDir(idDir) > 0){
                 number = handler.verifyImagesInDir(idDir);
             }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }finally {
            return number;
        }
    }

    public Boolean verifyNameAvailabilityByTrunk(String param){
        boolean status = false;
        try {
            status = handler.verifyNameTrunk(param);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }finally {
            return status;
        }
    }

}

package ocrm.labex.feevale.br.ocr_mobile.logicmodel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import ocrm.labex.feevale.br.ocr_mobile.handler.TagHandler;
import ocrm.labex.feevale.br.ocr_mobile.model.Image;
import ocrm.labex.feevale.br.ocr_mobile.model.Tags;
import ocrm.labex.feevale.br.ocr_mobile.utils.AppVariables;
import ocrm.labex.feevale.br.ocr_mobile.utils.MessageResponse;

/**
 * Created by 0126128 on 29/10/2014.
 */
public class TagsDAO implements ModelDAO<Tags> {


    private TagHandler tagHandler;
    private Activity activity;
    private MessageResponse response;

    public TagsDAO(Activity activity) {
        this.activity = activity;
        this.tagHandler = new TagHandler(activity);
    }


    private Integer getId(){
        Integer lastId = verifySharedPreferencesID();
        if(lastId == 0)
            lastId++;
        if(lastId == null)
            return 1;

        return lastId;
    }

    private Integer verifySharedPreferencesID(){
        Integer lastId = 0;
        SharedPreferences sharedPref = activity.getSharedPreferences(AppVariables.OCR_SHAREDPREFS, Context.MODE_PRIVATE);
        if(sharedPref.contains(AppVariables.ID_TAG)) {
            lastId = sharedPref.getInt(AppVariables.ID_TAG, 0);
        }
        return lastId;
    }

    private void saveNewID(Integer integer){
        SharedPreferences sharedPref = activity.getSharedPreferences(AppVariables.OCR_SHAREDPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(AppVariables.ID_TAG, ++integer);
        editor.commit();
    }

    @Override
    public MessageResponse insert(Tags object) {
        Integer id = 0;
        try {
            id = getId();
            object.setId(id);
            tagHandler.insertTags(object);
            response = new MessageResponse("Dados salvos com sucesso!", true);
        } catch (IOException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao acessar o arquivo interno!", false);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao persistir dados!", false);
        } catch (SAXException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao persistir dados!", false);
        } catch (JDOMException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao persistir dados!", false);
        }finally {
            saveNewID(id);
            return response;
        }
    }

    @Override
    public MessageResponse update(Tags object) {
        try {
            boolean status = tagHandler.updateTag(object);
            response = new MessageResponse("Dados alterados com sucesso!", true);
        } catch (IOException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao acessar o arquivo interno!", false);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao persistir dados!", false);
        } catch (SAXException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao persistir dados!", false);
        } catch (JDOMException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao persistir dados!", false);
        }finally {
            return response;
        }
    }

    @Override
    public MessageResponse remove(Tags object) {
        try {
            ImageDAO imageDAO = new ImageDAO(activity);
            if(imageDAO.hasTag(object.getId()))
                return new MessageResponse("A tag está em uso por algum arquivo!", false);

            boolean status = tagHandler.removeTag(object);
            if(status)
                return new MessageResponse("Tag removida com sucesso!", true);
            else
                return new MessageResponse("Ops! Problemas ao tentar excluír a tag!", false);
        } catch (IOException e) {
            e.printStackTrace();
            return new MessageResponse("Problemas ao acessar o arquivo interno!", false);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return new MessageResponse("Problemas ao persistir dados!", false);
        } catch (SAXException e) {
            e.printStackTrace();
            return new MessageResponse("Problemas ao persistir dados!", false);
        } catch (JDOMException e) {
            e.printStackTrace();
            return new MessageResponse("Problemas ao persistir dados!", false);
        }
    }

    @Override
    public HashSet<Tags> getItens() {
        return tagHandler.tagList();
    }

    @Override
    public HashSet<Tags> getItensByParam(String param) {
        return tagHandler.getTagsByParam(param);
    }

    @Override
    public Tags getItemById(Number id) {
        try {
            return tagHandler.getTag((Integer)id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
        return null;
    }

   public Boolean verifyTagToRemove(Integer idTag){

       if(new ImageDAO(activity).hasTag(idTag))
           return true;

       if(new DirectoryDAO(activity).verifyTag(idTag))
           return true;

       return false;
   }

    public Boolean checkTagNameAvailability(String name){
        return tagHandler.checkTagNameAvailability(name);
    }
}

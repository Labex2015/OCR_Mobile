package ocrm.labex.feevale.br.ocr_mobile.logicmodel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashSet;

import javax.xml.parsers.ParserConfigurationException;

import ocrm.labex.feevale.br.ocr_mobile.handler.ImageHandler;
import ocrm.labex.feevale.br.ocr_mobile.model.Image;
import ocrm.labex.feevale.br.ocr_mobile.model.Tags;
import ocrm.labex.feevale.br.ocr_mobile.utils.AppVariables;
import ocrm.labex.feevale.br.ocr_mobile.utils.MessageResponse;

/**
 * Created by 0126128 on 30/10/2014.
 */
public class ImageDAO implements ModelDAO<Image> {

    private Activity activity;
    private ImageHandler imageHandler;
    private MessageResponse messageResponse;

    public ImageDAO(Activity activity) {
        this.activity = activity;
        imageHandler = new ImageHandler(activity);
    }

    private Long getId(){
        Long lastId = verifySharedPreferencesID();
        if(lastId == 0)
            lastId++;
        if(lastId == null)
            return 1L;

        return lastId;
    }

    private Long verifySharedPreferencesID(){
        Long lastId = 0L;
        try {
            SharedPreferences sharedPref = activity.getSharedPreferences(AppVariables.OCR_SHAREDPREFS, Context.MODE_PRIVATE);
            if (sharedPref.contains(AppVariables.ID_IMAGE)) {
                lastId = sharedPref.getLong(AppVariables.ID_IMAGE, 0L);
            } else {
                saveNewID(0L);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return lastId;
    }

    private void saveNewID(Long integer){
        SharedPreferences sharedPref = activity.getSharedPreferences(AppVariables.OCR_SHAREDPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(AppVariables.ID_IMAGE, ++integer);
        editor.commit();
    }

    public MessageResponse saveImageAndTags(Image image,Tags... tags){
        HashSet<Integer> listTags = new HashSet<Integer>();

        for(Tags t : tags)
            listTags.add(t.getId());

        image.setTagsIds(listTags);
        return insert(image);
    }
    @Override
    public MessageResponse insert(Image image) {
        Long idImage = 0L;
        try {
            idImage = getId();
            image.setId(idImage);
            imageHandler.insertImages(image);
            messageResponse = new MessageResponse("Imagem salva com sucesso!", true);
            saveNewID(idImage);
        } catch (IOException e) {
            e.printStackTrace();
            messageResponse = new MessageResponse("Problemas ao tentar ler arquivo interno!", false);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            messageResponse = new MessageResponse("Problemas ao salvar dados!", false);
        } catch (SAXException e) {
            e.printStackTrace();
            messageResponse = new MessageResponse("Problemas ao salvar dados!", false);
        } catch (JDOMException e) {
            e.printStackTrace();
            messageResponse = new MessageResponse("Problemas ao salvar dados!", false);
        }catch (Exception e){
            e.printStackTrace();
            messageResponse = new MessageResponse("Problemas ao salvar dados!", false);
        }finally {
            return messageResponse;
        }
    }

    @Override
    public MessageResponse update(Image object) {

        try{
            if(imageHandler.updateImage(object))
                return new MessageResponse("Dados alterados com sucesso!", true);

            return new MessageResponse("Ops! Problemas com a atualização dos dados da imagem!", false);
        } catch (JDOMException e) {
            e.printStackTrace();
            return new MessageResponse("Ops! Problemas com arquivos internos!", false);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return new MessageResponse("Ops! Problemas ao salvar dados!", false);
        } catch (SAXException e) {
            e.printStackTrace();
            return new MessageResponse("Ops! Problemas com a atualização dos dados da imagem!", false);
        } catch (IOException e) {
            e.printStackTrace();
            return new MessageResponse("Ops! Problemas ao ler arquivo interno!", false);
        }
    }

    @Override
    public MessageResponse remove(Image object) {
        try{
            if(imageHandler.removeImage(object))
                return new MessageResponse("Imagem removida!", true);

            return new MessageResponse("Ops! Problemas ao tentar excluir dados!", false);
        } catch (JDOMException e) {
            e.printStackTrace();
            return new MessageResponse("Ops! Problemas com arquivos internos!", false);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return new MessageResponse("Ops! Problemas ao excluir dados!", false);
        } catch (SAXException e) {
            e.printStackTrace();
            return new MessageResponse("Ops! Problemas com a excluir dados!", false);
        } catch (IOException e) {
            e.printStackTrace();
            return new MessageResponse("Ops! Problemas ao ler arquivo interno!", false);
        }
    }

    public MessageResponse remove(Long id) {
        Image image = getItemById(id);
        if(image != null)
            return remove(image);
        else
            return new MessageResponse("Não foi possível localizar a imagem!", false);
    }

    @Override
    public HashSet<Image> getItens() {
        return imageHandler.imageList();
    }

    @Override
    public HashSet<Image> getItensByParam(String param) {
        return imageHandler.getImagesByParam(param);
    }

    @Override
    public Image getItemById(Number id) {
        Image image = null;
        try {
            image = imageHandler.getImage((Long)id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }finally {
            return image;
        }
    }

    public Boolean hasTag(Integer integer){
        return imageHandler.verifyTagInImages(integer);
    }

    public HashSet<Image> getImagesByDocument(Long idDoc){
        DocumentDAO documentDAO = new DocumentDAO(activity);
        HashSet<Long> ids = documentDAO.getImagesIdByDocument(idDoc);
        HashSet<Image> images = new HashSet<Image>();
        Image image = null;
        for(Long id : ids) {
            image = getItemById(id);
            if(image != null)
                images.add(image);
        }

        return images;
    }
}

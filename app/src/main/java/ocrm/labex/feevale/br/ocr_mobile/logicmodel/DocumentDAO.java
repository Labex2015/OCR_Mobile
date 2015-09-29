package ocrm.labex.feevale.br.ocr_mobile.logicmodel;

import android.app.Activity;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashSet;

import javax.xml.parsers.ParserConfigurationException;

import ocrm.labex.feevale.br.ocr_mobile.handler.DocumentHandler;
import ocrm.labex.feevale.br.ocr_mobile.model.Docs;
import ocrm.labex.feevale.br.ocr_mobile.utils.AppVariables;
import ocrm.labex.feevale.br.ocr_mobile.utils.MessageResponse;
import ocrm.labex.feevale.br.ocr_mobile.utils.SharedPreferencesUtils;

/**
 * Created by 0126128 on 05/11/2014.
 */
public class DocumentDAO implements ModelDAO<Docs>{

    private Activity activity;
    private DocumentHandler handler;
    private MessageResponse response;

    public DocumentDAO(Activity activity) {
        this.activity = activity;
        this.handler = new DocumentHandler(activity);
    }

    private Long getId(){
        Long lastId = new SharedPreferencesUtils().verifySharedPreferencesIDLong(activity, AppVariables.ID_DOCUMENT);
        if(lastId == 0)
            lastId++;
        if(lastId == null)
            return 1L;

        return lastId;
    }
    @Override
    public MessageResponse insert(Docs object) {
        Long id = 0L;
        id = getId();
        object.setId(id);
        try {
            handler.inserDocument(object);
            new SharedPreferencesUtils().saveNewLongID(id,activity,AppVariables.ID_DOCUMENT);
            response = new MessageResponse("Documento salvo com sucesso!", true);
        } catch (IOException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao carregar XML!", false);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao converter dados para o XML!", false);
        } catch (SAXException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao carregar XML!", false);
        } catch (JDOMException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao carregar XML!", false);
        }finally {
            return response;
        }

    }

    @Override
    public MessageResponse update(Docs object) {

        try {
            handler.updateDocument(object);
            response = new MessageResponse("Documento alterado com sucesso!", true);
        } catch (IOException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao carregar XML!", false);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao converter dados!", false);
        } catch (SAXException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao carregar XML!", false);
        } catch (JDOMException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao carregar XML!", false);
        }finally {
            return response;
        }
    }

    @Override
    public MessageResponse remove(Docs doc) {
        try {
            ImageDAO imageDAO = new ImageDAO(activity);
            for(Long i: doc.getImagesIds()){
                response = imageDAO.remove(i);
                if(!response.getStatus())
                    return  response;
            }

            handler.removeDocument(doc);
            response = new MessageResponse("Documento removido com sucesso!", true);
        } catch (IOException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao carregar XML!", false);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao converter dados!", false);
        } catch (SAXException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao carregar XML!", false);
        } catch (JDOMException e) {
            e.printStackTrace();
            response = new MessageResponse("Problemas ao carregar XML!", false);
        }finally {
            return response;
        }
    }

    public MessageResponse remove(Long id){
        Docs doc = getItemById(id);
        if(doc != null)
            return remove(doc);
        else
            return new MessageResponse("Não foi possível localizar o documento!", false);
    }

    @Override
    public HashSet<Docs> getItens() {
        return handler.docs();
    }

    @Override
    public HashSet<Docs> getItensByParam(String param) {
        return null;
    }

    @Override
    public Docs getItemById(Number id) {
        Docs doc = null;
        try {
            doc = handler.getDocument((Long)id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }finally {
            return doc;
        }
    }

    public HashSet<Long> getImagesIdByDocument(Long id){
        return handler.getImagesFromDoc(id);
    }

    public Docs getDocByImage(Long id){
        Docs doc = null;
        try {
            doc = handler.getDocumentByImage(id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }finally {
            return doc;
        }
    }

    public MessageResponse update(Docs... documents) {
        for(Docs d : documents){
            response = update(d);
            if(!response.getStatus())
                return response;
        }
        response.setMsg("Documentos alterados com sucesso");
        return response;
    }
}

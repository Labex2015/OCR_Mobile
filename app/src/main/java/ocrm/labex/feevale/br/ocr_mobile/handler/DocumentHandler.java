package ocrm.labex.feevale.br.ocr_mobile.handler;

import android.app.Activity;
import android.util.Log;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.SAXException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import ocrm.labex.feevale.br.ocr_mobile.model.Docs;
import ocrm.labex.feevale.br.ocr_mobile.utils.FileUtils;

/**
 * Created by 0126128 on 28/10/2014.
 */
public class DocumentHandler {

    private HashSet<Docs> docsList;
    org.jdom2.Document document;
    static Activity activity;
    public static final String DOCUMENT_FILE = "documents.xml";
    private static final String ROOT = "Documents";
    private static final String NODE_DIR = "Document";

    public DocumentHandler(Activity activityParam) {
        activity = activityParam;
    }

    private static org.jdom2.Document useDOMParser() throws ParserConfigurationException, SAXException, IOException {
        //creating DOM Document
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            return saxBuilder.build(new FileUtils(activity).getFileInternalStorage(DOCUMENT_FILE));
        } catch (JDOMException e) {
            org.jdom2.Document doc = new org.jdom2.Document();
            doc.setRootElement(new Element(ROOT));
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(new FileUtils(activity).getFileInternalStorage(DOCUMENT_FILE)));
            return doc;
        }
    }


    public HashSet<Docs> docs(){
        try{
            docsList = new HashSet<Docs>();
            document = useDOMParser();
            Element elementRoot = document.getRootElement();
            List<Element> elements = elementRoot.getChildren(NODE_DIR);

            for(Element e : elements){

                Docs doc = new Docs(Long.parseLong(e.getAttributeValue("id")),
                        e.getChildText("date"));

                List<Element> children = e.getChildren("Images");
                if(children != null && children.size() > 0) {
                    HashSet<Long> idsImages = new HashSet<Long>();
                    for (Element child : children) {
                        for(Element son : child.getChildren()) {
                            idsImages.add(Long.parseLong(son.getValue()));
                        }
                    }
                    doc.setImagesIds(idsImages);
                }
                docsList.add(doc);
            }
        }catch (IllegalArgumentException e){
            Log.e("ERROR_DIRECTORY","Erro ao converter dados para o objeto: "+e.getMessage());
            e.printStackTrace();
        }catch (Exception e){
            Log.e("ERROR_DIRECTORY","Erro ao ler xml de diretórios: "+e.getMessage());
            e.printStackTrace();
        }
        return docsList;
    }


    public static void inserDocument(Docs... listItens) throws IOException, ParserConfigurationException, SAXException, JDOMException {
        org.jdom2.Document doc = useDOMParser();

        if(doc.getRootElement() == null)
            doc.setRootElement(new Element(ROOT));

        for(Docs item : listItens){
            Element element = new Element(NODE_DIR);
            element.setAttribute("id", "" + item.getId());
            element.addContent(new Element("date").setText(item.getDateString()));

            if(item.getImagesIds().size() > 0) {
                Element elementInter = new Element("Images");
                for (Long i : item.getImagesIds()) {
                    Element child = new Element("Image");
                    child.setText(i + "");
                    elementInter.addContent(child);
                }
                element.addContent(elementInter);
            }
            doc.getRootElement().addContent(element);
        }
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(doc, new FileOutputStream(new FileUtils(activity).getFileInternalStorage(DOCUMENT_FILE)));
    }

    public static boolean updateDocument(Docs doc) throws IOException, ParserConfigurationException, SAXException, JDOMException {
        boolean status = false;
        org.jdom2.Document document = useDOMParser();
        Element elementRoot = document.getRootElement();
        List<Element> elements = elementRoot.getChildren(NODE_DIR);

        for(Element e : elements){
             Long id = Long.parseLong(e.getAttributeValue("id"));
            if(id.floatValue() == doc.getId()) {
                e.addContent(new Element("date").setText(""+doc.getDateString()));

                Element elementInter = e.getChild("Images");
                if(doc.getImagesIds().size() > 0 && elementInter != null) {
                    elementInter.removeChildren("Image");
                    for (Long i : doc.getImagesIds()) {
                        Element child = new Element("Image");
                        child.setText(i + "");
                        elementInter.addContent(child);
                    }
                }else{
                    e.removeChild("Images");
                }
                status = true;
                break;
            }
        }
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(document, new FileOutputStream(new FileUtils(activity).getFileInternalStorage(DOCUMENT_FILE)));

        return status;
    }

    public static boolean removeDocument(Docs doc) throws IOException, ParserConfigurationException, SAXException, JDOMException {
        boolean status = false;
        org.jdom2.Document document = useDOMParser();
        Element elementRoot = document.getRootElement();
        List<Element> elements = elementRoot.getChildren(NODE_DIR);
        Element removeMe = null;
        for(Element e : elements){
            Long id = Long.parseLong(e.getAttributeValue("id"));
            if(id.floatValue() == doc.getId().floatValue()) {
               removeMe = e;
               status = true;
               break;
            }
        }
        if(removeMe != null)
            elements.remove(removeMe);

        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(document, new FileOutputStream(new FileUtils(activity).getFileInternalStorage(DOCUMENT_FILE)));

        return status;
    }

    public static Docs getDocument(Long id) throws IOException, ParserConfigurationException, SAXException, JDOMException {
        org.jdom2.Document document = useDOMParser();
        Element elementRoot = document.getRootElement();
        List<Element> elements = elementRoot.getChildren(NODE_DIR);
        Long idXML = 0L;
        for(Element e : elements){
            idXML = Long.parseLong(e.getAttributeValue("id"));
            if(id.floatValue() == idXML.floatValue()) {
                Docs doc = new Docs(Long.parseLong(e.getAttributeValue("id")),
                        e.getChildText("date"));

                List<Element> children = e.getChildren("Images");
                if(children != null && children.size() > 0) {
                    HashSet<Long> idsImages = new HashSet<Long>();
                    for (Element child : children) {
                        for(Element son : child.getChildren()) {
                            idsImages.add(Long.parseLong(son.getValue()));
                        }
                    }
                    doc.setImagesIds(idsImages);
                }
                return doc;

            }
        }
        return null;
    }

    public HashSet<Long> getImagesFromDoc(Long docId) {
        try {
            document = useDOMParser();
            Element elementRoot = document.getRootElement();
            List<Element> elements = elementRoot.getChildren(NODE_DIR);
            Long idXML = 0L;
            for (Element e : elements) {
                idXML = Long.parseLong(e.getAttributeValue("id"));
                if(docId.floatValue() == idXML.floatValue()) {
                    List<Element> children = e.getChildren("Images");
                    if(children != null && children.size() > 0) {
                        HashSet<Long> idsImages = new HashSet<Long>();
                        for (Element child : children) {
                            for(Element son : child.getChildren()) {
                                idsImages.add(Long.parseLong(son.getValue()));
                            }
                        }
                        return idsImages;
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashSet<Long>();
    }

    public static Docs getDocumentByImage(Long id) throws IOException, SAXException, ParserConfigurationException {
        Docs doc = null;
        org.jdom2.Document document = useDOMParser();
        Element elementRoot = document.getRootElement();
        List<Element> elements = elementRoot.getChildren(NODE_DIR);
        boolean exists = false;
        for(Element e : elements){
            exists = false;
           doc = new Docs(Long.parseLong(e.getAttributeValue("id")),
                        e.getChildText("date"));
            List<Element> children = e.getChildren("Images");
            if(children != null && children.size() > 0) {
                HashSet<Long> idsImages = new HashSet<Long>();
                for (Element child : children) {
                    for(Element son : child.getChildren()) {
                        Long idImage = Long.parseLong(son.getValue());
                        if(idImage != null && idImage == id)
                            exists = true;
                        idsImages.add(idImage);
                    }
                }
                if(exists) {
                    doc.setImagesIds(idsImages);
                    return doc;
                }else{
                    doc = null;
                }
            }
        }
        return doc;
    }


}

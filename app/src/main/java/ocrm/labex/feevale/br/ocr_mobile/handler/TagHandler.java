package ocrm.labex.feevale.br.ocr_mobile.handler;

import android.app.Activity;
import android.util.Log;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.DOMBuilder;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.StAXEventBuilder;
import org.jdom2.input.StAXStreamBuilder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import ocrm.labex.feevale.br.ocr_mobile.model.Tags;
import ocrm.labex.feevale.br.ocr_mobile.utils.FileUtils;

/**
 * Created by 0126128 on 28/10/2014.
 */
public class TagHandler {

    private HashSet<Tags> tagsList;
    org.jdom2.Document document;
    static Activity activity;
    public static final String TAG_FILE = "tags.xml";

    public TagHandler(Activity activityParam) {
        activity = activityParam;
    }


    public HashSet<Tags> tagList(){
        tagsList = new HashSet<Tags>();
        try{
            document = useDOMParser();
            Element elementRoot = document.getRootElement();
            List<Element> elements = elementRoot.getChildren("Tag");

            for(Element e : elements){
                tagsList.add(new Tags(
                        Integer.parseInt(e.getAttributeValue("id")),
                        e.getChildText("name")
                ));
            }
        }catch (IllegalArgumentException e){
            Log.e("ERROR_TAG","Erro ao converter dados para tags: "+e.getMessage());
        }catch (Exception e){
            Log.e("ERROR_TAG","Erro ao ler xml de tags: "+e.getMessage());
            e.printStackTrace();
        }
        return tagsList;
    }

    private static org.jdom2.Document useDOMParser() throws ParserConfigurationException, SAXException, IOException {
        //creating DOM Document
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            return saxBuilder.build(new FileUtils(activity).getFileInternalStorage(TAG_FILE));
        } catch (JDOMException e) {
            org.jdom2.Document doc = new org.jdom2.Document();
            doc.setRootElement(new Element("Tags"));
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(new FileUtils(activity).getFileInternalStorage(TAG_FILE)));
            return doc;
        }
    }


    public static void insertTags(Tags... tagList) throws IOException, ParserConfigurationException, SAXException, JDOMException {
        org.jdom2.Document doc = useDOMParser();

        if(doc.getRootElement() == null)
            doc.setRootElement(new Element("Tags"));

        for(Tags tag : tagList){
            Element tagElement = new Element("Tag");
            tagElement.setAttribute("id",""+tag.getId());
            tagElement.addContent(new Element("name").setText(""+tag.getName()));
            doc.getRootElement().addContent(tagElement);
        }
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(doc, new FileOutputStream(new FileUtils(activity).getFileInternalStorage(TAG_FILE)));
    }

    public static boolean updateTag(Tags updateThisTag) throws IOException, ParserConfigurationException, SAXException, JDOMException {
        boolean status = false;
        org.jdom2.Document document = useDOMParser();
        Element elementRoot = document.getRootElement();
        List<Element> elements = elementRoot.getChildren("Tag");
        for(Element e : elements){
            int id = Integer.parseInt(e.getAttributeValue("id"));
            if(updateThisTag.getId() == id){
                e.getChild("name").setText(updateThisTag.getName());
                status = true;
                break;
            }
        }
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(document, new FileOutputStream(new FileUtils(activity).getFileInternalStorage(TAG_FILE)));

        return status;
    }

    public static boolean removeTag(Tags removeThisTag) throws IOException, ParserConfigurationException, SAXException, JDOMException {
        boolean status = false;
        org.jdom2.Document document = useDOMParser();
        Element elementRoot = document.getRootElement();
        List<Element> elements = elementRoot.getChildren("Tag");
        Element removeThisGuy = null;
        for(Element e : elements){
            int id = Integer.parseInt(e.getAttributeValue("id"));
            if(removeThisTag.getId() == id){
                removeThisGuy = e;
                status = true;
                break;
            }
        }
        elements.remove(removeThisGuy);
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(document, new FileOutputStream(new FileUtils(activity).getFileInternalStorage(TAG_FILE)));

        return status;
    }

    public static Tags getTag(Integer id) throws IOException, ParserConfigurationException, SAXException, JDOMException {
        org.jdom2.Document document = useDOMParser();
        Element elementRoot = document.getRootElement();
        List<Element> elements = elementRoot.getChildren("Tag");

        for(Element e : elements){
            int idElement = Integer.parseInt(e.getAttributeValue("id"));
            if(idElement == id.intValue()){
                return new Tags(
                    Integer.parseInt(e.getAttributeValue("id")),
                    e.getChildText("name")
                );
            }
        }
        return null;
    }

    public HashSet<Tags> getTagsByParam(String param){
        tagsList = new HashSet<Tags>();
        try{
            document = useDOMParser();
            Element elementRoot = document.getRootElement();
            List<Element> elements = elementRoot.getChildren("Tag");

            for(Element e : elements){
                if(e.getChildText("name").contains(param))
                    tagsList.add(new Tags(
                            Integer.parseInt(e.getAttributeValue("id")),
                            e.getChildText("name")
                    ));
            }
        }catch (IllegalArgumentException e){
            Log.e("ERROR_TAG","Erro ao converter dados para tags: "+e.getMessage());
        }catch (Exception e){
            Log.e("ERROR_TAG","Erro ao ler xml de tags: "+e.getMessage());
            e.printStackTrace();
        }
        return tagsList;
    }

    public Boolean checkTagNameAvailability(String param){
        tagsList = new HashSet<Tags>();
        try{
            document = useDOMParser();
            Element elementRoot = document.getRootElement();
            List<Element> elements = elementRoot.getChildren("Tag");

            for(Element e : elements){
                if(e.getChildText("name").equalsIgnoreCase(param))
                    return false;
            }
        }catch (IllegalArgumentException e){
            Log.e("ERROR_TAG","Erro ao converter dados para tags: "+e.getMessage());
        }catch (Exception e){
            Log.e("ERROR_TAG","Erro ao ler xml de tags: "+e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

}

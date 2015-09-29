package ocrm.labex.feevale.br.ocr_mobile.handler;

import android.app.Activity;
import android.util.Log;

import org.jdom2.Attribute;
import org.jdom2.Content;
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

import ocrm.labex.feevale.br.ocr_mobile.model.Image;
import ocrm.labex.feevale.br.ocr_mobile.model.Tags;
import ocrm.labex.feevale.br.ocr_mobile.utils.FileUtils;

/**
 * Created by 0126128 on 28/10/2014.
 */
public class ImageHandler {

    private HashSet<Image> imageList;
    org.jdom2.Document document;
    static Activity activity;
    public static final String IMAGE_FILE = "images.xml";

    public ImageHandler(Activity activityParam) {
        activity = activityParam;
    }

    private static org.jdom2.Document useDOMParser() throws ParserConfigurationException, SAXException, IOException {
        //creating DOM Document
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            return saxBuilder.build(new FileUtils(activity).getFileExternalStorage(IMAGE_FILE));
        } catch (JDOMException e) {
            org.jdom2.Document doc = new org.jdom2.Document();
            doc.setRootElement(new Element("Images"));
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(new FileUtils(activity).getFileExternalStorage(IMAGE_FILE)));
            return doc;
        }
    }


    public HashSet<Image> imageList(){
        imageList = new HashSet<Image>();
        try{
            document = useDOMParser();
            Element elementRoot = document.getRootElement();
            List<Element> elements = elementRoot.getChildren("Image");

            for(Element e : elements){

                Image image = new Image(
                        Long.parseLong(e.getAttributeValue("id")),
                        e.getChildText("name"),
                        e.getChildText("path"),
                        e.getChildText("text"));
                HashSet<Integer> idsTags = new HashSet<Integer>();

                List<Element> children = e.getChildren("Tags");
                if(children != null && children.size() > 0) {
                    for (Element child : children) {
                        for(Element son : child.getChildren()) {
                            idsTags.add(Integer.parseInt(son.getValue()));
                        }
                    }
                }
                image.setTagsIds(idsTags);
                imageList.add(image);
            }
        }catch (IllegalArgumentException e){
            Log.e("ERROR_IMAGE","Erro ao converter dados para imagens: "+e.getMessage());
            e.printStackTrace();
        }catch (Exception e){
            Log.e("ERROR_IMAGE","Erro ao ler xml de imagens: "+e.getMessage());
            e.printStackTrace();
        }
        return imageList;
    }


    public static void insertImages(Image... images) throws IOException, ParserConfigurationException, SAXException, JDOMException {
        org.jdom2.Document doc = useDOMParser();

        if(doc.getRootElement() == null)
            doc.setRootElement(new Element("Images"));

        for(Image image : images){
            Element element = new Element("Image");
            element.setAttribute("id",""+image.getId());
            element.addContent(new Element("name").setText(""+image.getName()));
            element.addContent(new Element("path").setText(""+image.getPath()));
            element.addContent(new Element("text").setText(""+image.getText()));
            if(image.getTagsIds().size() > 0) {
                Element elementInter = new Element("Tags");
                for (Integer i : image.getTagsIds()) {
                    Element child = new Element("Tag");
                    child.setText(i + "");
                    elementInter.addContent(child);
                }
                element.addContent(elementInter);
            }
            doc.getRootElement().addContent(element);
        }
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(doc, new FileOutputStream(new FileUtils(activity).getFileExternalStorage(IMAGE_FILE)));
    }

    public static boolean updateImage(Image updateThisImage) throws IOException, ParserConfigurationException, SAXException, JDOMException {
        boolean status = false;
        org.jdom2.Document document = useDOMParser();
        Element elementRoot = document.getRootElement();
        List<Element> elements = elementRoot.getChildren("Image");

        for(Element e : elements){
             Long id = Long.parseLong(e.getAttributeValue("id"));
            if(id.floatValue() == updateThisImage.getId()) {
                boolean addNode = false;
                e.getChild("name").setText(updateThisImage.getName());
                e.getChild("path").setText(updateThisImage.getPath());
                e.getChild("text").setText(updateThisImage.getText());
                Element elementInter = e.getChild("Tags");
                if(elementInter != null && elementInter.getChildren("Tag") != null) {
                    elementInter.removeChildren("Tag");
                }else{
                    elementInter = new Element("Tags");
                    addNode = true;
                }

                if (updateThisImage.getTagsIds().size() > 0) {
                    for (Integer i : updateThisImage.getTagsIds()) {
                        Element child = new Element("Tag");
                        child.setText(i + "");
                        elementInter.addContent(child);
                    }
                    if(addNode)
                        e.addContent(elementInter);
                } else {
                    e.removeChild("Tags");
                }
                status = true;
                break;
            }
        }
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(document, new FileOutputStream(new FileUtils(activity).getFileExternalStorage(IMAGE_FILE)));

        return status;
    }

    public static boolean removeImage(Image removeThisImage) throws IOException, ParserConfigurationException, SAXException, JDOMException {
        boolean status = false;
        org.jdom2.Document document = useDOMParser();
        Element elementRoot = document.getRootElement();
        List<Element> elements = elementRoot.getChildren("Image");
        Element removeMe = null;
        for(Element e : elements){
            Long id = Long.parseLong(e.getAttributeValue("id"));
            if(id.floatValue() == removeThisImage.getId()) {
               removeMe = e;
               status = true;
               break;
            }
        }
        elements.remove(removeMe);
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(document, new FileOutputStream(new FileUtils(activity).getFileExternalStorage(IMAGE_FILE)));

        return status;
    }

    public static Image getImage(Long id) throws IOException, ParserConfigurationException, SAXException, JDOMException {
        Image image = null;
        org.jdom2.Document document = useDOMParser();
        Element elementRoot = document.getRootElement();
        List<Element> elements = elementRoot.getChildren("Image");

        for(Element e : elements){
            Long idXML = Long.parseLong(e.getAttributeValue("id"));
            if(id.floatValue() == idXML.floatValue()) {
                image = new Image(idXML,
                        e.getChildText("name"),
                        e.getChildText("path"),
                        e.getChildText("text"));
                HashSet<Integer> idsTags = new HashSet<Integer>();

                List<Element> children = e.getChildren("Tags");
                if(children != null  && children.size() > 0) {
                    if (children.size() > 0) {
                        for (Element child : children) {
                            for (Element son : child.getChildren()) {
                                idsTags.add(Integer.parseInt(son.getValue()));
                            }
                        }
                    }
                    image.setTagsIds(idsTags);
                }
                return image;
            }
        }
        return image;
    }

    public HashSet<Image> getImagesByParam(String param){
        imageList = new HashSet<Image>();

        try {
            document = useDOMParser();
            Element elementRoot = document.getRootElement();
            List<Element> elements = elementRoot.getChildren("Image");

            for(Element e : elements){
                if(e.getChildText("name").contains(param)
                 ||e.getChildText("text").contains(param)){
                    Image image = new Image(
                            Long.parseLong(e.getAttributeValue("id")),
                            e.getChildText("name"),
                            e.getChildText("path"),
                            e.getChildText("text"));
                    HashSet<Integer> idsTags = new HashSet<Integer>();

                    List<Element> children = e.getChildren("Tags");
                    if(children != null  && children.size() > 0) {
                        if (children.size() > 0) {
                            for (Element child : children) {
                                for (Element son : child.getChildren()) {
                                    idsTags.add(Integer.parseInt(son.getValue()));
                                }
                            }
                        }
                        image.setTagsIds(idsTags);
                    }
                    imageList.add(image);
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageList;
    }

    public Boolean verifyTagInImages(Integer idTag) {
        try {
            document = useDOMParser();
            Element elementRoot = document.getRootElement();
            List<Element> elements = elementRoot.getChildren("Image");
            for (Element e : elements) {
                List<Element> children = e.getChildren("Tags");
                if (children.size() > 0) {
                    for (Element child : children) {
                        for (Element son : child.getChildren()) {
                           Integer id = Integer.parseInt(son.getValue());
                            if(id.intValue() == idTag.intValue())
                                return true;
                        }
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
        return false;
    }

    public Boolean verifyTagInImages(Integer idTag, Long idImage) {
        try {
            document = useDOMParser();
            Element elementRoot = document.getRootElement();
            List<Element> elements = elementRoot.getChildren("Image");
            for (Element e : elements) {
                Long idXML = Long.parseLong(e.getAttributeValue("id"));
                if(idImage.floatValue() == idXML.floatValue()) {
                    List<Element> children = e.getChildren("Tags");
                    if (children.size() > 0) {
                        for (Element child : children) {
                            for (Element son : child.getChildren()) {
                                Integer id = Integer.parseInt(son.getValue());
                                if (id.intValue() == idTag.intValue())
                                    return true;
                            }
                        }
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
        return false;
    }

    public int checkOccurrenceTags(Integer idTag) {
        int occurrence = 0;
        try {
            document = useDOMParser();
            Element elementRoot = document.getRootElement();
            List<Element> elements = elementRoot.getChildren("Image");
            for (Element e : elements) {
                List<Element> children = e.getChildren("Tags");
                if (children.size() > 0) {
                    external_element:
                    for (Element child : children) {
                        for (Element son : child.getChildren()) {
                            Integer id = Integer.parseInt(son.getValue());
                            if(id.intValue() == idTag.intValue()){
                                if(occurrence >= 1)
                                    return ++occurrence;
                                occurrence++;
                                continue external_element;
                            }
                        }
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
        return occurrence;
    }

}

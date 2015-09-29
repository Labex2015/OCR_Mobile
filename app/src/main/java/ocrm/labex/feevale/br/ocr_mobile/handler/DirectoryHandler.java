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
import ocrm.labex.feevale.br.ocr_mobile.model.Directory;
import ocrm.labex.feevale.br.ocr_mobile.utils.FileUtils;

/**
 * Created by 0126128 on 28/10/2014.
 */
public class DirectoryHandler {

    private HashSet<Directory> directories;
    org.jdom2.Document document;
    static Activity activity;
    public static final String DIRECTORY_FILE = "directory.xml";
    private static final String ROOT = "Directories";
    private static final String NODE_DIR = "Directory";

    public DirectoryHandler(Activity activityParam) {
        activity = activityParam;
    }

   public int verifyImagesInDir(Long idDir) throws IOException, SAXException, ParserConfigurationException {

        HashSet<Long> documents = new HashSet<Long>();
        org.jdom2.Document document = useDOMParser();
        Element elementRoot = document.getRootElement();

        List<Element> elements = elementRoot.getChildren(NODE_DIR);
        Long idNode = 0L;
        for(Element e : elements){

            idNode = !e.getChildText("directory-node").trim().equals("") ?
                    Long.parseLong(e.getChildText("directory-node")) : 0L;
            if(idNode.longValue() == idDir.longValue()) {
                HashSet<Long> idsDocs = null;
                List<Element> children =  e.getChildren("Documents");
                if (children != null && children.size() > 0) {
                    for (Element child : children) {
                        for (Element son : child.getChildren()) {
                            documents.add(Long.parseLong(son.getValue()));
                        }
                    }
                }

                return documents.size();
            }
        }
        return 0;


    }


    private static org.jdom2.Document useDOMParser() throws ParserConfigurationException, SAXException, IOException {
        //creating DOM Document
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            return saxBuilder.build(new FileUtils(activity).getFileInternalStorage(DIRECTORY_FILE));
        } catch (JDOMException e) {
            org.jdom2.Document doc = new org.jdom2.Document();
            doc.setRootElement(new Element(ROOT));
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(new FileUtils(activity).getFileInternalStorage(DIRECTORY_FILE)));
            return doc;
        }
    }


    public HashSet<Directory> directories(){
        try{
            directories = new HashSet<Directory>();
            document = useDOMParser();
            Element elementRoot = document.getRootElement();
            List<Element> elements = elementRoot.getChildren(NODE_DIR);

            for(Element e : elements){

                Directory dir = new Directory(
                        Long.parseLong(e.getAttributeValue("id")),
                        e.getChildText("name"));
                Long idNode = (e.getChildText("directory-node") != null && !e.getChildText("directory-node").trim().equals("")) ?
                        Long.parseLong(e.getChildText("directory-node")) : 0L;
                dir.setNodeDirectory(idNode);
                HashSet<Integer> idsTags = null;
                HashSet<Long> idsDocs = null;

                List<Element> children = e.getChildren("Tags");
                if(children != null && children.size() > 0) {
                    idsTags = new HashSet<Integer>();
                    for (Element child : children) {
                        for(Element son : child.getChildren()) {
                            idsTags.add(Integer.parseInt(son.getValue()));
                        }
                    }
                    dir.setTagsIds(idsTags);
                }
                children = null;
                children = e.getChildren("Documents");
                if(children != null && children.size() > 0) {
                    idsDocs = new HashSet<Long>();
                    for (Element child : children) {
                        for(Element son : child.getChildren()) {
                            idsDocs.add(Long.parseLong(son.getValue()));
                        }
                    }
                    dir.setDocumentsIds(idsDocs);
                }

                directories.add(dir);
            }
        }catch (IllegalArgumentException e){
            Log.e("ERROR_DIRECTORY","Erro ao converter dados para o objeto: "+e.getMessage());
            e.printStackTrace();
        }catch (Exception e){
            Log.e("ERROR_DIRECTORY","Erro ao ler xml de diretórios: "+e.getMessage());
            e.printStackTrace();
        }
        return directories;
    }


    public static void insertDirectories(Directory... directories) throws IOException, ParserConfigurationException, SAXException, JDOMException {
        org.jdom2.Document doc = useDOMParser();

        if(doc.getRootElement() == null)
            doc.setRootElement(new Element(ROOT));

        for(Directory dir : directories){
            Element element = new Element(NODE_DIR);
            element.setAttribute("id",""+dir.getId());
            element.addContent(new Element("name").setText(""+dir.getName()));
            element.addContent(new Element("directory-node").setText(""+dir.getNodeDirectory()));
            if(dir.getTagsIds().size() > 0) {
                Element elementInter = new Element("Tags");
                for (Integer i : dir.getTagsIds()) {
                    Element child = new Element("Tag");
                    child.setText(i + "");
                    elementInter.addContent(child);
                }
                element.addContent(elementInter);
            }
            if(dir.getDocumentsIds().size() > 0) {
                Element elementInter = new Element("Documents");
                for (Long i : dir.getDocumentsIds()) {
                    Element child = new Element("Document");
                    child.setText(i + "");
                    elementInter.addContent(child);
                }
                element.addContent(elementInter);
            }
            doc.getRootElement().addContent(element);
        }
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(doc, new FileOutputStream(new FileUtils(activity).getFileInternalStorage(DIRECTORY_FILE)));
    }

    public static boolean updateDirectory(Directory dir) throws IOException, ParserConfigurationException, SAXException, JDOMException {
        boolean status = false;
        org.jdom2.Document document = useDOMParser();
        Element elementRoot = document.getRootElement();
        List<Element> elements = elementRoot.getChildren(NODE_DIR);

        for(Element e : elements){
             Long id = Long.parseLong(e.getAttributeValue("id"));
            if(id.floatValue() == dir.getId()) {
                e.addContent(new Element("name").setText(""+dir.getName()));
                e.addContent(new Element("directory-node").setText(""+dir.getNodeDirectory()));

                Element elementInter = e.getChild("Tags");
                if(dir.getTagsIds().size() > 0 && elementInter != null) {
                    elementInter.removeChildren("Tag");
                    for (Integer i : dir.getTagsIds()) {
                        Element child = new Element("Tag");
                        child.setText(i + "");
                        elementInter.addContent(child);
                    }
                }else{
                    e.removeChild("Tags");
                }

                elementInter = e.getChild("Documents");
                if(dir.getDocList().size() > 0 && elementInter != null) {
                    elementInter.removeChildren("Document");
                    for (Long i : dir.getDocumentsIds()) {
                        Element child = new Element("Document");
                        child.setText(i + "");
                        elementInter.addContent(child);
                    }
                }else{
                    e.removeChild("Documents");
                }
                status = true;
                break;
            }
        }
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(document, new FileOutputStream(new FileUtils(activity).getFileInternalStorage(DIRECTORY_FILE)));

        return status;
    }

    public static boolean removeDirectory(Directory dir) throws IOException, ParserConfigurationException, SAXException, JDOMException {
        boolean status = false;
        org.jdom2.Document document = useDOMParser();
        Element elementRoot = document.getRootElement();
        List<Element> elements = elementRoot.getChildren(NODE_DIR);
        Element removeMe = null;
        for(Element e : elements){
            Long id = Long.parseLong(e.getAttributeValue("id"));
            if(id.floatValue() == dir.getId().floatValue()) {
               removeMe = e;
               status = true;
               break;
            }
        }
        if(removeMe != null)
            elements.remove(removeMe);

        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(document, new FileOutputStream(new FileUtils(activity).getFileInternalStorage(DIRECTORY_FILE)));

        return status;
    }

    public static Directory getDirectory(Long id) throws IOException, ParserConfigurationException, SAXException, JDOMException {
        Directory directory = null;
        org.jdom2.Document document = useDOMParser();
        Element elementRoot = document.getRootElement();
        List<Element> elements = elementRoot.getChildren(NODE_DIR);

        for(Element e : elements){
            Long idXML = Long.parseLong(e.getAttributeValue("id"));
            if(id.floatValue() == idXML.floatValue()) {
                directory = new Directory(
                        Long.parseLong(e.getAttributeValue("id")),
                        e.getChildText("name"));
                Long idNode = (e.getChildText("directory-node") != null && !e.getChildText("directory-node").trim().equals("")) ?
                        Long.parseLong(e.getChildText("directory-node")) : 0L;
                directory.setNodeDirectory(idNode);
                HashSet<Integer> idsTags = null;
                HashSet<Long> idsDocs = null;

                List<Element> children = e.getChildren("Tags");
                if(children != null && children.size() > 0) {
                    idsTags = new HashSet<Integer>();
                    for (Element child : children) {
                        for(Element son : child.getChildren()) {
                            idsTags.add(Integer.parseInt(son.getValue()));
                        }
                    }
                    directory.setTagsIds(idsTags);
                }
                children = null;
                children = e.getChildren("Documents");
                if(children != null && children.size() > 0) {
                    idsDocs = new HashSet<Long>();
                    for (Element child : children) {
                        for(Element son : child.getChildren()) {
                            idsDocs.add(Long.parseLong(son.getValue()));
                        }
                    }
                    directory.setDocumentsIds(idsDocs);
                }

            }
        }
        return directory;
    }

    public Boolean verifyTagInDirectories(Integer idTag) {
        try {
            document = useDOMParser();
            Element elementRoot = document.getRootElement();
            List<Element> elements = elementRoot.getChildren(NODE_DIR);
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

    public Boolean verifyTagInDirectories(Integer idTag, Long idDirectory) {
        try {
            document = useDOMParser();
            Element elementRoot = document.getRootElement();
            List<Element> elements = elementRoot.getChildren(NODE_DIR);
            for (Element e : elements) {
                Long idXML = Long.parseLong(e.getAttributeValue("id"));
                if(idDirectory.floatValue() == idXML.floatValue()) {
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
            List<Element> elements = elementRoot.getChildren(NODE_DIR);
            external_element:
            for (Element e : elements) {
                List<Element> children = e.getChildren("Tags");
                if (children.size() > 0) {
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

    public static HashSet<Directory> getDirectoriesByNode(Long id) {
        HashSet<Directory> directories = new HashSet<Directory>();

        try{
           org.jdom2.Document document = useDOMParser();
            Element elementRoot = document.getRootElement();
            List<Element> elements = elementRoot.getChildren(NODE_DIR);
            Long idNode = 0L;
            for(Element e : elements){
                idNode = !e.getChildText("directory-node").trim().equals("") ?
                        Long.parseLong(e.getChildText("directory-node")) : 0L;
                if(idNode.longValue() == id.longValue()) {
                    Directory dir = new Directory(
                            Long.parseLong(e.getAttributeValue("id")),
                            e.getChildText("name"));
                    Long idN = (e.getChildText("directory-node") != null && !e.getChildText("directory-node").trim().equals("")) ?
                            Long.parseLong(e.getChildText("directory-node")) : 0L;
                    dir.setNodeDirectory(idN);
                    HashSet<Integer> idsTags = null;
                    HashSet<Long> idsDocs = null;

                    List<Element> children = e.getChildren("Tags");
                    if (children != null && children.size() > 0) {
                        idsTags = new HashSet<Integer>();
                        for (Element child : children) {
                            for (Element son : child.getChildren()) {
                                idsTags.add(Integer.parseInt(son.getValue()));
                            }
                        }
                        dir.setTagsIds(idsTags);
                    }
                    children = null;
                    children = e.getChildren("Documents");
                    if (children != null && children.size() > 0) {
                        idsDocs = new HashSet<Long>();
                        for (Element child : children) {
                            for (Element son : child.getChildren()) {
                                idsDocs.add(Long.parseLong(son.getValue()));
                            }
                        }
                        dir.setDocumentsIds(idsDocs);
                    }

                    directories.add(dir);
                }
            }
        }catch (IllegalArgumentException e){
            Log.e("ERROR_DIRECTORY","Erro ao converter dados para o objeto: "+e.getMessage());
            e.printStackTrace();
        }catch (Exception e){
            Log.e("ERROR_DIRECTORY","Erro ao ler xml de diretórios: "+e.getMessage());
            e.printStackTrace();
        }
        return directories;
    }

    public static HashSet<Directory> getDirectoriesFirstLvl() {
        HashSet<Directory> directories = new HashSet<Directory>();

        try{
            org.jdom2.Document document = useDOMParser();
            Element elementRoot = document.getRootElement();
            List<Element> elements = elementRoot.getChildren(NODE_DIR);
            Long idNode = 0L;
            for(Element e : elements){
                idNode = !e.getChildText("directory-node").trim().equals("") ?
                        Long.parseLong(e.getChildText("directory-node")) : 0L;
                if(idNode.longValue() == 0L) {
                    Directory dir = new Directory(
                            Long.parseLong(e.getAttributeValue("id")),
                            e.getChildText("name"));
                    Long idN = (e.getChildText("directory-node") != null && !e.getChildText("directory-node").trim().equals("")) ?
                            Long.parseLong(e.getChildText("directory-node")) : 0L;
                    dir.setNodeDirectory(idN);
                    HashSet<Integer> idsTags = null;
                    HashSet<Long> idsDocs = null;

                    List<Element> children = e.getChildren("Tags");
                    if (children != null && children.size() > 0) {
                        idsTags = new HashSet<Integer>();
                        for (Element child : children) {
                            for (Element son : child.getChildren()) {
                                idsTags.add(Integer.parseInt(son.getValue()));
                            }
                        }
                        dir.setTagsIds(idsTags);
                    }
                    children = null;
                    children = e.getChildren("Documents");
                    if (children != null && children.size() > 0) {
                        idsDocs = new HashSet<Long>();
                        for (Element child : children) {
                            for (Element son : child.getChildren()) {
                                idsDocs.add(Long.parseLong(son.getValue()));
                            }
                        }
                        dir.setDocumentsIds(idsDocs);
                    }

                    directories.add(dir);
                }
            }
        }catch (IllegalArgumentException e){
            Log.e("ERROR_DIRECTORY","Erro ao converter dados para o objeto: "+e.getMessage());
            e.printStackTrace();
        }catch (Exception e){
            Log.e("ERROR_DIRECTORY","Erro ao ler xml de diretórios: "+e.getMessage());
            e.printStackTrace();
        }
        return directories;
    }

    public static HashSet<Long> getDocumentsByNode(Long id) throws IOException, SAXException, ParserConfigurationException {
        HashSet<Long> documents = new HashSet<Long>();

           org.jdom2.Document document = useDOMParser();
            Element elementRoot = document.getRootElement();
            List<Element> elements = elementRoot.getChildren(NODE_DIR);
            Long idNode = 0L;
            for(Element e : elements){
                idNode = !e.getChildText("directory-node").trim().equals("") ?
                        Long.parseLong(e.getChildText("directory-node")) : 0L;
                if(idNode.longValue() == id.longValue()) {
                      HashSet<Long> idsDocs = null;

                    List<Element> children =  e.getChildren("Documents");
                    if (children != null && children.size() > 0) {
                        for (Element child : children) {
                            for (Element son : child.getChildren()) {
                                documents.add(Long.parseLong(son.getValue()));
                            }
                        }
                    }

                    return documents;
                }
            }
        return documents;
    }

    public Boolean verifyNameTrunk(String name) throws IOException, SAXException, ParserConfigurationException {
        document = useDOMParser();
        Element elementRoot = document.getRootElement();
        List<Element> elements = elementRoot.getChildren(NODE_DIR);
        for (Element e : elements) {
            if(e.getChildText("name").equals(name))
                return false;
            continue;
        }
        return true;
    }

    public Boolean verifyNameInNode(Long idNode, String name) throws IOException, SAXException, ParserConfigurationException {
        document = useDOMParser();
        Element elementRoot = document.getRootElement();
        List<Element> elements = elementRoot.getChildren(NODE_DIR);
        Long id = 0L;
        for (Element e : elements) {
            id = !e.getChildText("directory-node").trim().equals("") ?
                    Long.parseLong(e.getChildText("directory-node")) : 0L;
            if(idNode.longValue() == id.longValue()) {
                if (e.getChildText("name").equals(name))
                    return false;
            }
            continue;
        }
        return true;
    }
}

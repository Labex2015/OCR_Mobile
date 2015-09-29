package ocrm.labex.feevale.br.ocr_mobile.model;

import java.util.HashSet;

/**
 * Created by 0126128 on 04/11/2014.
 */
public class Directory {

    private Long id;
    private String name;
    private Directory node;
    private Long nodeDirectory;
    private int drawable;
    private HashSet<Docs> docList;
    private HashSet<Tags> tags;

    private HashSet<Long> documentsIds;
    private HashSet<Integer> tagsIds;

    public Directory(String name) {
        this.name = name;
    }

    public Directory(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setDrawable(int d){
        this.drawable = d;
    }
    public int getDrawable(){
        return this.drawable;
    }
    public Directory(Long id, String name, HashSet<Long> documentsIds, HashSet<Integer> tagsIds) {
        this.id = id;
        this.name = name;
        this.documentsIds = documentsIds;
        this.tagsIds = tagsIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Directory getNode() {
        return node;
    }

    public void setNode(Directory node) {
        this.node = node;
        this.nodeDirectory = node.getId();
    }

    public HashSet<Docs> getDocList() {
        if(this.docList == null)
            this.docList = new HashSet<Docs>();

        return docList;
    }

    public void setDocList(HashSet<Docs> docList) {
        this.docList = docList;
        this.documentsIds = new HashSet<Long>();
        for(Docs docs : docList)
            this.documentsIds.add(docs.getId());
    }

    public HashSet<Tags> getTags() {
        if(this.tags == null)
            this.tags = new HashSet<Tags>();

        return tags;
    }

    public void setTags(HashSet<Tags> tags) {
        this.tags = tags;
        this.tagsIds = new HashSet<Integer>();
        for(Tags t : tags)
            this.tagsIds.add(t.getId());
    }

    public HashSet<Long> getDocumentsIds() {
        if(this.documentsIds == null)
            if(this.docList == null)
                return this.documentsIds = new HashSet<Long>();
            else{
                this.documentsIds = new HashSet<Long>();
                for(Docs doc : docList)
                    this.documentsIds.add(doc.getId());
            }
        return documentsIds;
    }

    public HashSet<Integer> getTagsIds() {
        if(tagsIds == null)
            if(tags == null)
                return this.tagsIds = new HashSet<Integer>();
            else {
                this.tagsIds = new HashSet<Integer>();
                for(Tags tag : tags)
                    this.tagsIds.add(tag.getId());
            }
        return tagsIds;
    }

    public Long getNodeDirectory(){
        if(nodeDirectory == null)
            return 0L;

        return nodeDirectory;
    }

    public void setNodeDirectory(Long id){
        this.nodeDirectory = id;
    }

    public void setDocumentsIds(HashSet<Long> documentsIds) {
        this.documentsIds = documentsIds;
    }

    public void setTagsIds(HashSet<Integer> tagsIds) {
        this.tagsIds = tagsIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Directory)) return false;

        Directory directory = (Directory) o;

        if (!id.equals(directory.id)) return false;
        if (!name.equals(directory.name)) return false;
        if (nodeDirectory != null ? !nodeDirectory.equals(directory.nodeDirectory) : directory.nodeDirectory != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (nodeDirectory != null ? nodeDirectory.hashCode() : 0);
        return result;
    }
}

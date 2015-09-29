package ocrm.labex.feevale.br.ocr_mobile.model;

import java.util.HashSet;
import java.util.List;

/**
 * Created by 0126128 on 22/10/2014.
 */
public class Image {

    private Long id;
    private String name;
    private String path;
    private String text;

    private HashSet<Tags> tags;

    private HashSet<Integer> tagsIds;

    public Image(Long id, String name, String path, String text) {
        this(name, path, text);
        this.id = id;
    }

    public Image(String name, String path, String text) {
        this.path = path;
        this.text = text;
        this.name = name;
    }

    public Image(Long id, String name, String path, String text, HashSet<Tags> tags) {
        this.id = id;
        this.path = path;
        this.text = text;
        this.tags = tags;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    /**
     * TODO: Fazer com que ele pego o ID a partir do sharedpreferences
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public HashSet<Tags> getTags() {
        return tags;
    }

    public void setTags(HashSet<Tags> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<Integer> getTagsIds() {
        if(tagsIds == null)
            return tagsIds = new HashSet<Integer>();
        return tagsIds;
    }

    public void setTagsIds(HashSet<Integer> tagsIds) {
        this.tagsIds = null;
        this.tagsIds = tagsIds;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;

        Image image = (Image) o;

        if (!id.equals(image.id)) return false;
        if (!name.equals(image.name)) return false;
        if (path != null ? !path.equals(image.path) : image.path != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }
}

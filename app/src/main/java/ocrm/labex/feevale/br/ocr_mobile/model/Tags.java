package ocrm.labex.feevale.br.ocr_mobile.model;

import android.app.Activity;

/**
 * Created by 0126128 on 22/10/2014.
 */
public class Tags {

    private int id;
    private String name;

    public Tags(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tags(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * TODO: Implementar método para buscar último ID.
     */
    public static int setId(Activity activity) {
        return 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tags)) return false;

        Tags tags = (Tags) o;

        if (id != tags.id) return false;
        if (name != null ? !name.equals(tags.name) : tags.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}

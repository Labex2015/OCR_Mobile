package ocrm.labex.feevale.br.ocr_mobile.view.adapters.utils;

/**
 * Created by 0126128 on 17/11/2014.
 */
public class ItemModel {

    public static final int DIRECTORY = 1;
    public static final int IMAGE = 2;

    private Number id;
    private String name;
    private int type;
    private boolean selected;

    public ItemModel(Number id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

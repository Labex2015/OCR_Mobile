package ocrm.labex.feevale.br.ocr_mobile.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by 0126128 on 04/11/2014.
 */
public class Docs {

    private Long id;
    private Date date;
    private String dateString;
    private HashSet<Image> images;
    private HashSet<Long> imagesIds;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Docs(Long id, String dateString) {
        this.id = id;
        this.dateString = dateString;
    }

    public Docs(Long id, Date date) {
        this.id = id;
        this.date = date;
    }

    public Docs(String dateString) {
        this.dateString = dateString;
    }

    public Docs(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HashSet<Image> getImages() {
        if(images == null)
            return images = new HashSet<Image>();
        return images;
    }

    public void setImages(HashSet<Image> images) {
        this.images = images;
        this.imagesIds = new HashSet<Long>();
        for(Image image : images)
            this.getImagesIds().add(image.getId());
    }

    public String getDateString() {
        if(date != null)
            return this.dateString = dateFormat.format(date);
        else
            return  this.dateString = dateFormat.format(new Date());
    }

    /**
     * TODO: remover o log, depois de verificar se o split funciona beleza!
     * @param dateString
     */
    public void setDateString(String dateString) {
        try {
            if(dateString.split("/").length == 3)
                this.date = dateFormat.parse(dateString);
            else
                Log.e("DATA","Data falsa");
        } catch (ParseException e) {
            e.printStackTrace();
            this.date = new Date();
        }
        this.dateString = dateString;
    }

    public HashSet<Long> getImagesIds() {
        if(imagesIds == null){
            imagesIds = new HashSet<Long>();
            if(getImages().size() > 0)
                for(Image image : getImages())
                    imagesIds.add(image.getId());
        }
        return imagesIds;
    }

    public void setImagesIds(HashSet<Long> imagesIds) {
        this.imagesIds = imagesIds;
    }
}

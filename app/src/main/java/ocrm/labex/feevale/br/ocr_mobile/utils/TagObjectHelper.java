package ocrm.labex.feevale.br.ocr_mobile.utils;

import ocrm.labex.feevale.br.ocr_mobile.model.Tags;

/**
 * Created by 0126128 on 06/11/2014.
 */
public class TagObjectHelper {

    private Tags object;
    private boolean status;
    private boolean secundaryStatus;

    public TagObjectHelper(Tags object, boolean status, boolean secundaryStatus) {
        this.object = object;
        this.status = status;
        this.secundaryStatus = secundaryStatus;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Tags getObject() {
        return object;
    }

    public boolean isStatus() {
        return status;
    }

    public void setSecundaryStatus(boolean secundaryStatus) {
        this.secundaryStatus = secundaryStatus;
    }

    public boolean isSecundaryStatus() {
        return secundaryStatus;
    }
}

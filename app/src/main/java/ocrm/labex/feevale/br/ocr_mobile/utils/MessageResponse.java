package ocrm.labex.feevale.br.ocr_mobile.utils;

/**
 * Created by PabloGilvan on 26/08/2014.
 */
public class MessageResponse {

    private String msg;
    private Boolean status;

    public MessageResponse(String msg, Boolean status) {
        this.msg = msg;
        this.status = status;
    }

    public MessageResponse() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}

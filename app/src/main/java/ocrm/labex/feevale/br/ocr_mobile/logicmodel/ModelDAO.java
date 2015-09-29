package ocrm.labex.feevale.br.ocr_mobile.logicmodel;

import android.nfc.Tag;

import java.util.HashSet;
import java.util.List;

import ocrm.labex.feevale.br.ocr_mobile.model.Image;
import ocrm.labex.feevale.br.ocr_mobile.utils.MessageResponse;

/**
 * Created by 0126128 on 28/10/2014.
 */
public interface ModelDAO<T> {

    MessageResponse insert(T object);
    MessageResponse update(T object);
    MessageResponse remove(T object);
    HashSet<T> getItens();
    HashSet<T> getItensByParam(String param);
    T getItemById(Number id);

}

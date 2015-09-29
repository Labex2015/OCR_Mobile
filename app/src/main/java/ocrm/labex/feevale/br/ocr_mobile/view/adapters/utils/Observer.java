package ocrm.labex.feevale.br.ocr_mobile.view.adapters.utils;

import android.widget.BaseAdapter;

import ocrm.labex.feevale.br.ocr_mobile.utils.MessageResponse;

/**
 * Created by 0126128 on 12/11/2014.
 */
public interface Observer {

    void update(MessageResponse messageResponse);
}

package ocrm.labex.feevale.br.ocr_mobile.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 0126128 on 05/11/2014.
 */
public class SharedPreferencesUtils {


    public Long verifySharedPreferencesIDLong(Activity activity, String variable){
        Long lastId = 0L;
        try {
            SharedPreferences sharedPref = activity.getSharedPreferences(AppVariables.OCR_SHAREDPREFS, Context.MODE_PRIVATE);
            if (sharedPref.contains(variable)) {
                lastId = sharedPref.getLong(variable, 0L);
            } else {
                saveNewLongID(0L, activity, variable);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return lastId;
    }

    public void saveNewLongID(Long integer, Activity activity, String variable){
        SharedPreferences sharedPref = activity.getSharedPreferences(AppVariables.OCR_SHAREDPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(variable, ++integer);
        editor.commit();
    }
}

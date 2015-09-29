package ocrm.labex.feevale.br.ocr_mobile.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by 0126128 on 01/09/2014.
 */
public class DirectoryUtils {

        public String getAbsolutePathFromDevice(String... subDirs){
            File file = null;
            file = new File(getExternalAbsolutePathFromDevice(subDirs));
            return file.getAbsolutePath();
        }

        private String getExternalAbsolutePathFromDevice(String... subDirs){
            File root = Environment.getExternalStorageDirectory();
            StringBuilder sbPath = new StringBuilder();
            sbPath.append(root.getAbsolutePath()+assemblyDirs(subDirs));
            return sbPath.toString();
        }


        private String assemblyDirs(String...strings){
            StringBuilder sbDir = new StringBuilder();
            for(String dir:strings){
                sbDir.append(File.separator);
                sbDir.append(dir);
            }
            return sbDir.toString();
        }

        public boolean removeDirs(String path){
            File dir = null;
            try{
                dir = new File(getAbsolutePathFromDevice(new String[] {path}));
                if (dir.isDirectory()) {
                    String[] children = dir.list();
                    for (int i = 0; i < children.length; i++) {
                        new File(dir, children[i]).delete();
                    }
                }
                dir.delete();
                return true;
            }catch(Exception e){
                Log.e("ERROR: ", e.getMessage());
                return false;
            }

        }

        public boolean removeImage(String imageName){
            File image = null;
            try{
                image = new File(getAbsolutePathFromDevice(AppVariables.EXTERNAL_FILE, imageName));
                if(image.exists()) {
                    return image.delete();
                }
            }catch(Exception e){
                Log.e("ERROR: ", e.getMessage());
            }
            return false;
        }

        public boolean contentExists(String... subDirs){
            File file = new File(getAbsolutePathFromDevice(subDirs));
            if(file.exists()){
                return true;
            }else{
                return false;
            }
        }




}

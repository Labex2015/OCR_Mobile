package ocrm.labex.feevale.br.ocr_mobile.view.fragments;import android.app.Activity;import android.app.ProgressDialog;import android.content.Intent;import android.graphics.Bitmap;import android.graphics.BitmapFactory;import android.graphics.Matrix;import android.graphics.Point;import android.hardware.Camera;import android.os.AsyncTask;import android.os.Bundle;import android.support.annotation.Nullable;import android.support.v4.app.Fragment;import android.view.LayoutInflater;import android.view.Surface;import android.view.View;import android.view.ViewGroup;import android.view.WindowManager;import android.widget.Button;import android.widget.FrameLayout;import android.widget.Toast;import java.io.File;import ocrm.labex.feevale.br.ocr_mobile.R;import ocrm.labex.feevale.br.ocr_mobile.utils.AppVariables;import ocrm.labex.feevale.br.ocr_mobile.utils.FileUtils;import ocrm.labex.feevale.br.ocr_mobile.utils.MessageResponse;import ocrm.labex.feevale.br.ocr_mobile.view.adapters.CameraPreview;import ocrm.labex.feevale.br.ocr_mobile.view.CropActivity;/** * Created by 0126128 on 24/10/2014. */public class CameraFragment extends Fragment {    private Camera camera;    private CameraPreview cameraPreview;    private static Activity activity;    public CameraFragment() {        super();    }    public static CameraFragment newInstance(int sectionNumber, Activity activityParam){        CameraFragment cameraFragment = new CameraFragment();        Bundle bundle = new Bundle();        bundle.putInt("ARG_SECTION_NUMBER",sectionNumber);        cameraFragment.setArguments(bundle);        activity = activityParam;        return cameraFragment;    }    @Override    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {        View view = inflater.inflate(R.layout.camera_container, container,false);        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);        cameraPreview = new CameraPreview(getActivity(), camera);        FrameLayout preview = (FrameLayout)view.findViewById(R.id.camera_preview);        preview.addView(cameraPreview);        Button button = (Button)view.findViewById(R.id.button_capture);        button.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                camera.takePicture(null, null, takedPicture);            }        });        return view;    }    private void releaseCamera() {        if (camera != null) {            camera.release();            camera = null;        }    }    @Override    public void onPause() {        super.onPause();        releaseCamera();    }    @Override    public void onResume() {        super.onResume();        camera = getCameraInstance(getActivity());        cameraPreview.refreshCamera(camera);    }    public static Camera getCameraInstance(Activity activity){        Camera c = null;        try {            c = Camera.open();            Camera.CameraInfo info = new Camera.CameraInfo();            Camera.getCameraInfo(0, info);            int rotation = activity.getWindowManager().getDefaultDisplay()                    .getRotation();            int degrees = 0;            switch (rotation) {                case Surface.ROTATION_0: degrees = 0; break;                case Surface.ROTATION_90: degrees = 90; break;                case Surface.ROTATION_180: degrees = 180; break;                case Surface.ROTATION_270: degrees = 270; break;            }            int result;            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {                result = (info.orientation + degrees) % 360;                result = (360 - result) % 360;  // compensate the mirror            } else {  // back-facing                result = (info.orientation - degrees + 360) % 360;            }            c.setDisplayOrientation(result);        }catch (Exception e){            e.printStackTrace();        }        return  c;    }    public static boolean isLandscape(Activity activity){        Point size = new Point();        activity.getWindowManager().getDefaultDisplay().getSize(size);        int width = size.x;        int height = size.y;        return height < width;    }    /**     * TODO: Adicionar corpo do método que cria o arquivo temporário e corpo do método para salvar a imagem e chamar a próxima activity     */    private Camera.PictureCallback takedPicture = new Camera.PictureCallback() {        @Override        public void onPictureTaken(byte[] bytes, Camera camera) {                new ProcessBitmapImageTask(getActivity(), bytes, getTempFile()).execute();        }        private File getTempFile() {            return new FileUtils(activity).getFileExternalStorage(AppVariables.TEMP_IMAGE);        }    };}class ProcessBitmapImageTask extends AsyncTask<Void, MessageResponse, MessageResponse>{    private Bitmap bitmapToProcess;    private Activity activity;    private byte bytes[];    private File pictureFileTemp;    private ProgressDialog progressDialog;    ProcessBitmapImageTask(Activity activity, byte[] bytes, File pictureFileTemp) {        this.activity = activity;        this.bytes = bytes;        this.pictureFileTemp = pictureFileTemp;    }    @Override    protected void onPreExecute() {        progressDialog = new ProgressDialog(activity);        progressDialog.setCancelable(false);        progressDialog.setMessage("Processando imagem....");        progressDialog.show();    }    @Override    protected MessageResponse doInBackground(Void... voids) {        bitmapToProcess = BitmapFactory.decodeByteArray(bytes,0,bytes.length);        MessageResponse response = new MessageResponse("", false);        if(pictureFileTemp != null){            Bitmap old = bitmapToProcess;            Matrix matrix = new Matrix();            matrix.postRotate(getRotation(activity));            bitmapToProcess = Bitmap.createBitmap(bitmapToProcess, 0,0,bitmapToProcess.getWidth(), bitmapToProcess.getHeight(),matrix, false);            response = new FileUtils(activity).saveTemporaryImage(bitmapToProcess);            old.recycle();        }else{           response.setMsg("Problemas ao obter imagem!");        }        return response;    }    @Override    protected void onPostExecute(MessageResponse response) {        if(progressDialog.isShowing())            progressDialog.dismiss();        if(response.getStatus())            changeFase();        else            Toast.makeText(activity, response.getMsg(), Toast.LENGTH_SHORT).show();    }    private void changeFase(){        Intent intent = new Intent(activity,CropActivity.class);        activity.startActivity(intent);    }    private int getRotation(Activity activity){        if(CameraFragment.isLandscape(activity))            return 0;        else            return 90;    }}
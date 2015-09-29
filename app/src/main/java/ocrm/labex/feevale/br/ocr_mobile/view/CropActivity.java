package ocrm.labex.feevale.br.ocr_mobile.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import ocrm.labex.feevale.br.ocr_mobile.R;
import ocrm.labex.feevale.br.ocr_mobile.components.MyLine;
import ocrm.labex.feevale.br.ocr_mobile.components.MyShape;
import ocrm.labex.feevale.br.ocr_mobile.utils.AppVariables;
import ocrm.labex.feevale.br.ocr_mobile.utils.FileUtils;
import ocrm.labex.feevale.br.ocr_mobile.utils.ImageUtils;
import ocrm.labex.feevale.br.ocr_mobile.utils.MessageResponse;
import ocrm.labex.feevale.br.ocr_mobile.utils.crop.BitmapCoordinate;
import ocrm.labex.feevale.br.ocr_mobile.utils.crop.Quadrilateral;

/**
 * Created by 0118424 on 21/10/2014.
 */
public class CropActivity extends Activity implements View.OnTouchListener {

    private ViewGroup group;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private int deltaX;
    private int deltaY;
    private MyLine lineA;
    private MyLine lineB;
    private MyLine lineC;
    private MyLine lineD;
    private Button buttonCrop;
    private MyShape myShape;
    private int maxX;
    private int maxY;
    private ImageView cropMain;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);




        group = (ViewGroup)findViewById(R.id.dady);

       /* acoes para configurar a imagem */

        Drawable myIcon = getResources().getDrawable(R.drawable.ic_drawer);
        cropMain = (ImageView) findViewById(R.id.dummy);
        buttonCrop = (Button)findViewById(R.id.buttonCrop);
        buttonCrop.setOnClickListener(cropEvent(this));

        bitmap = FileUtils.getTemporaryImage(this);
        cropMain.setImageBitmap(bitmap);




        /*encerra acoes de configuracao */

        imageView1 = (ImageView)findViewById(R.id.imagem1);
        imageView1.setScaleType(ImageView.ScaleType.MATRIX);
        imageView1.setOnTouchListener(this);
        imageView1.setTag("1");



        imageView2 = (ImageView)findViewById(R.id.imagem2);
        imageView2.setTag("2");
        imageView2.setScaleType(ImageView.ScaleType.MATRIX);
        imageView2.setOnTouchListener(this);


        imageView3 = (ImageView)findViewById(R.id.imagem3);
        imageView3.setTag("3");
        imageView3.setScaleType(ImageView.ScaleType.MATRIX);
        imageView3.setOnTouchListener(this);

        imageView4 = (ImageView)findViewById(R.id.imagem4);
        imageView4.setScaleType(ImageView.ScaleType.MATRIX);
        imageView4.setTag("4");

        imageView4.setOnTouchListener(this);
        lineA = new MyLine(this, Color.WHITE);
        lineB = new MyLine(this, Color.WHITE);
        lineC = new MyLine(this, Color.WHITE);
        lineD = new MyLine(this, Color.WHITE);



        myShape = new MyShape(this, imageView1, imageView2, imageView3, imageView4);

        group.addView(lineA);
        group.addView(lineB);
        group.addView(lineC);
        group.addView(lineD);

/*        if(myShape.getWidth() > myShape.getHeight())
            myShape.rot //TODO: Rotate da image, se preceisar.
*/
        if(isNewPicture())
            drawInitialPath();

        group.addView(myShape);


    }

    public static Bitmap getImage(){
         return null;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        int heightImage = bitmap.getHeight();
        int widthImage  = bitmap.getWidth();

        //Posicao em x e y de cada nó
        final int X = (int) motionEvent.getRawX();
        final int Y = (int) motionEvent.getRawY();

        // cropmain representa a view de corte
        int wImageView =  cropMain.getWidth();
        int hImageView =  cropMain.getHeight();

        float positionX = (float) cropMain.getWidth();
        float positionY = (float) cropMain.getHeight();



        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                FrameLayout.LayoutParams Params = (FrameLayout.LayoutParams) view.getLayoutParams();
                deltaX = X - Params.leftMargin;
                deltaY = Y - Params.topMargin;
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                FrameLayout.LayoutParams layoutParams =
                        (FrameLayout.LayoutParams) view.getLayoutParams();
                layoutParams.leftMargin = X - deltaX;
                layoutParams.topMargin = Y - deltaY;
                layoutParams.rightMargin = -50;
                layoutParams.bottomMargin = -50;
                view.setLayoutParams(layoutParams);
                drawMyLine();
                break;
        }

        group.invalidate();
        return true;
    }

    private void drawMyLine(){
        lineA.invalidate();
        lineA.setPosition(imageView1.getX()+20,imageView1.getY()+20, imageView2.getX()+20,imageView2.getY()+20);
        lineB.invalidate();
        lineB.setPosition(imageView3.getX()+20,imageView3.getY()+20, imageView1.getX()+20,imageView1.getY()+20);
        lineC.invalidate();
        lineC.setPosition(imageView4.getX()+20,imageView4.getY()+20, imageView2.getX()+20,imageView2.getY()+20);

        lineD.invalidate();
        lineD.setPosition(imageView3.getX()+20,imageView3.getY()+20, imageView4.getX()+20,imageView4.getY()+20);

    }

    private View.OnClickListener cropEvent(final Activity activity){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapCoordinate pontoA = new BitmapCoordinate(0,0);
                BitmapCoordinate pontoB = new BitmapCoordinate(71,71);
                BitmapCoordinate pontoC = new BitmapCoordinate(113,113);
                BitmapCoordinate pontoD = new BitmapCoordinate(185,185);

                Bitmap bitmapEnvio = ImageUtils.getImage("macaco.png", activity);

                Log.e("ENVIO","Height : "+bitmapEnvio.getHeight());
                Bitmap bitmapResult = new Quadrilateral(pontoA,pontoB,pontoC,pontoD, bitmapEnvio).getQuadrilateral();
                Log.e("ENVIO","Height : "+bitmapResult.getHeight());
                MessageResponse response = new FileUtils(activity).saveImage(bitmapResult, AppVariables.TEMP_IMAGE);
                Toast.makeText(activity, response.getMsg(),Toast.LENGTH_LONG).show();
            }
        };
    }



    private void drawInitialPath(){
        Display display = getWindowManager().getDefaultDisplay();
        double height = getWindowManager().getDefaultDisplay();
        double width = display.getWidth();
        imageView1.setX((float)(height * 0.02));
        imageView1.setY(imageView1.getY());
        imageView2.setX((float)((height * 0.02) - height));
        imageView2.setY(imageView2.getY());

        imageView3.setX((float)(height - (height * 0.02)));
        imageView3.setY(imageView3.getY());
        imageView4.setX((float)(height- (height * 0.02)));
        imageView4.setY(imageView4.getY());
    }

    private boolean isNewPicture(){
        if(imageView1.getX() == imageView2.getX()
                && imageView2.getX() == imageView3.getX()
                && imageView3.getX() == imageView4.getX())
            return true;

        return false;
    }



}

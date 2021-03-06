package ocrm.labex.feevale.br.ocr_mobile.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by 0118424 on 21/10/2014.
 */
public class MyLine extends View {

    Paint paint = new Paint();
    private float xA;
    private float yA;

    private float xB;
    private float yB;

    public MyLine(Context context, int color) {
        super(context);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{5,10,15,20},0));//TODO: Colocar o estilo como dashed
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(xA,yA,xB,yB, paint);
    }

    public void setPosition(float xA, float yA,float xB, float yB){
        this.xA = xA;
        this.yA = yA;
        this.xB = xB;
        this.yB = yB;
    }

}

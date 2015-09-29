package ocrm.labex.feevale.br.ocr_mobile.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by 0126128 on 13/10/2014.
 */
public class MyShape extends View{

    private ImageView a1, a2, a3, a4;
    private Path path;

    public MyShape(Context context,ImageView a1, ImageView a2, ImageView a3, ImageView a4){
        super(context);
        path = new Path();
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
    }

    @Override
    public void draw(Canvas canvas) {

        path.moveTo(a2.getX()+20, a2.getY()+20);
        path.lineTo(a1.getX()+20,a1.getY()+20);
        path.lineTo(a3.getX()+20,a3.getY()+20);
        path.lineTo(a4.getX()+20,a4.getY()+20);
        path.lineTo(a2.getX()+20,a2.getY()+20);
        path.close();
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawPath(path, paint);
    }

    public void setMyPath(ImageView a1, ImageView a2, ImageView a3, ImageView a4){
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        path = new Path();
    }
}

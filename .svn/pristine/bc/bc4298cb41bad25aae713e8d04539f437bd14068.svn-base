package ocrm.labex.feevale.br.ocr_mobile.utils.crop;

import android.graphics.Bitmap;

public class Square{

    public static Bitmap get( BitmapCoordinate start, Integer width, Integer height, Bitmap input ){

        Integer a = 0;
        Integer b = 0;

        Bitmap output = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );

        for( Integer y = start.getY(); y < height; y++ ){

            for( Integer x = start.getX(); x < width; x++ ){

                output.setPixel( a, b, input.getPixel( x, y ) );
                a++;

            }

            b++;
            a = 0;

        }

        return output;

    }

}
package ocrm.labex.feevale.br.ocr_mobile.utils.crop;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Triangle{

    public static void printTriangleTopLeft( Integer width, Integer height, Bitmap input ){

        Integer line;

        if( width > height ){

            line = width;

            for( Integer y = 0; y < height; y++ ){

                for( Integer x = 0; x < line; x++ ){

                    input.setPixel( x, y, Color.TRANSPARENT );

                }

                line -= ( int ) Math.ceil( ( double ) width / ( double ) height );

                if( line < 0 ){

                    line = 0;

                }

            }

        } else {

            line = height;

            for( Integer x = 0; x < width; x++ ){

                for( Integer y = 0; y < line; y++ ){

                    input.setPixel( x, y, Color.TRANSPARENT );

                }

                line -= ( int ) Math.ceil( ( double ) height / ( double ) width );

                if( line < 0 ){

                    line = 0;

                }

            }

        }

    }

    public static void printTriangleTopRight( Integer width, Integer height, Bitmap input ){

        Integer line;

        if( width > height ){

            line = width;

            for( Integer y = 0; y < height; y++ ){

                for( Integer x = ( input.getWidth() - 1 ); x > ( ( input.getWidth() - 1 ) - line ); x-- ){

                    input.setPixel( x, y, Color.TRANSPARENT );

                }

                line -= ( int ) Math.ceil((double) width / (double) height);

                if( line < 0 ){

                    line = 0;

                }

            }

        } else {

            line = height;

            for( Integer x = ( input.getWidth() - 1 ); x > ( input.getWidth() - width ); x-- ){

                for( Integer y = 0; y < line; y++ ){

                    input.setPixel( x, y, Color.TRANSPARENT );

                }

                line -= ( int ) Math.ceil( ( double ) height / ( double ) width );

                if( line < 0 ){

                    line = 0;

                }

            }

        }

    }

    public static void printTriangleBottomLeft( Integer width, Integer height, Bitmap input ){

        Integer line;

        if( width > height ){

            line = width;

            for( Integer y = ( input.getHeight() - 1 ); y > ( input.getHeight() - height ); y-- ){

                for( Integer x = 0; x < line; x++ ){

                    input.setPixel( x, y, Color.TRANSPARENT );

                }

                line -= ( int ) Math.ceil( ( double ) width / ( double ) height );

                if( line < 0 ){

                    line = 0;

                }

            }

        } else {

            line = height;

            for( Integer x = 0; x < width; x++ ){

                for( Integer y = ( input.getHeight() - 1 ); y > ( input.getHeight() - line ); y-- ){

                    input.setPixel( x, y, Color.TRANSPARENT );

                }

                line -= ( int ) Math.ceil( ( double ) height / ( double ) width );

                if( line < 0 ){

                    line = 0;

                }

            }

        }

    }

    public static void printTriangleBottomRight( Integer width, Integer height, Bitmap input ){

        Integer line;

        if( width > height ){

            line = width;

            for( Integer y = ( input.getHeight() - 1 ); y > ( input.getHeight() - height ); y-- ){

                for( Integer x = ( input.getWidth() - 1 ); x > ( input.getWidth() - line ); x-- ){

                    input.setPixel( x, y, Color.TRANSPARENT );

                }

                line -= ( int ) Math.ceil( ( double ) width / ( double ) height );

                if( line < 0 ){

                    line = 0;

                }

            }

        } else {

            line = height;

            for( Integer x = ( input.getWidth() - 1 ); x > ( input.getWidth() - width ); x-- ){

                for( Integer y = ( input.getHeight() - 1 ); y > ( input.getHeight() - line ); y-- ){

                    input.setPixel( x, y, Color.TRANSPARENT );

                }

                line -= ( int ) Math.ceil( ( double ) height / ( double ) width );

                if( line < 0 ){

                    line = 0;

                }

            }

        }

    }

}
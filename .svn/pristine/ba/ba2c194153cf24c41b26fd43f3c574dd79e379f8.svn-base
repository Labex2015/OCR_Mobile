package ocrm.labex.feevale.br.ocr_mobile.utils.crop;

import android.graphics.Bitmap;

public class Quadrilateral{

    private BitmapCoordinate A;
    private BitmapCoordinate B;
    private BitmapCoordinate C;
    private BitmapCoordinate D;

    private Integer width;
    private Integer height;

    private Bitmap input;
    private Bitmap quadrilateral;

    public Quadrilateral( BitmapCoordinate A, BitmapCoordinate B, BitmapCoordinate C, BitmapCoordinate D, Bitmap input ) {

        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;

        this.width  = Math.max( this.B.getX(), this.D.getX() );
        this.height = Math.max( this.C.getY(), this.D.getY() );
        this.input  = input;

        this.quadrilateral = setQuadrilateral();

        setTriangles();

    }

    public Bitmap getInput(){

        return this.input;

    }

    public Bitmap getQuadrilateral(){

        return this.quadrilateral;

    }

    private Bitmap setQuadrilateral(){

        BitmapCoordinate start = new BitmapCoordinate( Math.min( this.A.getX(), this.C.getX() ), Math.min( this.A.getY(), this.B.getY() ) );

        return Square.get( start, this.width, this.height, this.input );

    }

    private void setTriangleTopLeft(){

        if( this.A.getY() > this.B.getY() ){

            Triangle.printTriangleTopLeft( ( this.B.getX() - this.A.getX() ), ( this.A.getY() - this.B.getY() ), this.quadrilateral );

        } if( this.A.getX() > this.C.getX() ){

            Triangle.printTriangleTopLeft( ( this.A.getX() - this.C.getX() ), ( this.C.getY() - this.A.getY() ), this.quadrilateral );

        }

    }

    private void setTriangleTopRight(){

        if( this.A.getY() < this.B.getY() ){

            Triangle.printTriangleTopRight( ( this.B.getX() - this.A.getX() ), this.B.getY(), this.quadrilateral );

        } if( this.B.getX() < this.D.getX() ){

            Triangle.printTriangleTopRight( ( this.D.getX() - this.B.getX() ), ( this.D.getY() - this.B.getY() ), this.quadrilateral );

        }

    }

    private void setTriangleBottomLeft(){

        if( this.A.getX() < this.C.getX() ){

            Triangle.printTriangleBottomLeft( ( this.C.getX() - this.A.getX() ), ( this.C.getY() - this.A.getY() ), this.quadrilateral );

        } if( C.getY() < D.getY() ){

            Triangle.printTriangleBottomLeft( ( this.D.getX() - this.C.getX() ), ( this.D.getY() - this.C.getY() ), this.quadrilateral );

        }

    }

    private void setTriangleBottomRight(){

        if( this.C.getY() > this.D.getY() ){

            Triangle.printTriangleBottomRight( ( this.D.getX() - this.C.getX() ), ( this.C.getY() - this.D.getY() ), this.quadrilateral );

        } if( this.B.getX() > this.D.getX() ){

            Triangle.printTriangleBottomRight( ( this.B.getX() - this.D.getX() ), ( this.D.getY() - this.B.getY() ), this.quadrilateral );

        }

    }

    private void setTriangles(){

        this.setTriangleTopLeft();
        this.setTriangleTopRight();
        this.setTriangleBottomLeft();
        this.setTriangleBottomRight();

    }

}
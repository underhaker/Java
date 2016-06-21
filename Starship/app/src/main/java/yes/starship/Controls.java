package yes.starship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Joni on 31.5.2016 г..
 */
public class Controls {
    private Bitmap bitmap;
    private float length;
    private float height;
    private float x;
    private float y;
    public Controls(Context context, int screenX, int screenY,boolean isLeft){
        length = screenX / 5;
        height = screenY / 10;
        if(isLeft == true){
//            x = 50;
//            y = 50;
            x = 0;
            y = screenY - height;
            bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.left_arrow);
            bitmap = Bitmap.createScaledBitmap(bitmap,(int) (length), (int) (height), false);
        }
        else {
            x = screenX-length;
            y = screenY - height;
            bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.right_arrow);
            bitmap = Bitmap.createScaledBitmap(bitmap,(int) (length),(int) (height), false);
        }
    }
    public float getLength(){
        return length;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }

}

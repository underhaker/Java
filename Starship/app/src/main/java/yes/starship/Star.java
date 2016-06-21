package yes.starship;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by Joni on 16.5.2016 г..
 */
public class Star {
    private float x;
    private float y;
    private float width;
    private float height;
    private RectF rect;
    private Bitmap bitmap;
    private Random generator = new Random();
    private int starSpeed;
    private final int MINIMUM_STAR_SPEED = 400;
    private final int STAR_SPEED_THRESHOLD = 250;
    public Star(Context context,int numStar, int screenX, int screenY) {
        rect = new RectF();
        width = screenX / 100;
        height = screenY / 100;
        y = generator.nextInt(screenY);
        x = (numStar * 40 )- generator.nextInt(40);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.star);
        bitmap = Bitmap.createScaledBitmap(bitmap,(int) width,(int) height, false);
        starSpeed = MINIMUM_STAR_SPEED + generator.nextInt(STAR_SPEED_THRESHOLD);
    }
    public Bitmap getBitmap() {
        return bitmap;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public void update(long fps){
        y = y + starSpeed / fps;
        rect.left = x;
        rect.right = x + width;
        rect.top = y;
        rect.bottom = y + height;

    }
    public void resetPosition(){
        y = 0;
    }
//
//    private void controlStars() {
//        Animation[] starAnimations = new Animation[STARS_COUNT];
//        Random randomDuration = new Random();
//        for (int i=0; i<STARS_COUNT; i++){
//            starAnimations[i] = AnimationUtils.loadAnimation(context,R.anim.anim_translate);
//            int animationDuration = MINIMUM_STAR_SPEED+randomDuration.nextInt(STAR_SPEED_THRESHOLD);
//            starAnimations[i].setDuration(animationDuration);
//            stars[i].startAnimation(starAnimations[i]);
//        }
//    }
}

package yes.starship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import java.util.Random;

/**
 * Created by Joni on 19.5.2016 г..
 */
public class Enemy {
    RectF rect;
    Random generator = new Random();
    private Bitmap bitmap;
    private float length;
    private float height;
    private float x;
    private float y;
    private float shipSpeed;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    private final float SHIP_SPEED = 90;
    private int shipMoving = RIGHT;
    boolean isVisible;

    public Enemy(Context context, int row, int column, int screenX, int screenY) {
        rect = new RectF();
        length = screenX / 12;
        height = screenY / 12;
        isVisible = true;
        int padding = screenX / 25;
        x = column * (length + padding) + 10;
        y = row * (height + padding / 4) + 50;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (length), (int) (height), false);
        shipSpeed = SHIP_SPEED;
    }

    public void setInvisible() {
        isVisible = false;
    }

    public boolean getVisibility() {
        return isVisible;
    }

    public RectF getRect() {
        return rect;
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

    public float getLength() {
        return length;
    }

    public void update(long fps) {
        if (shipMoving == LEFT) {
            x = x - shipSpeed / fps;
        }
        if (shipMoving == RIGHT) {
            x = x + shipSpeed / fps;
        }
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;
    }
    public void reverse(){
        if (shipMoving == LEFT) {
            shipMoving = RIGHT;
        }else {
            shipMoving = LEFT;
        }
    }
    public void dropDownAndReverse() {
        if (shipMoving == LEFT) {
            shipMoving = RIGHT;
        } else {
            shipMoving = LEFT;
        }
        y = y + height;
//        shipSpeed = shipSpeed * 1.18f;
    }

    public boolean takeAim(float playerShipX, float playerShipLength, int difficultyLevel) {
        int randomNumber = -1;

        if ((playerShipX + playerShipLength > x && playerShipX + playerShipLength < x + length) ||
                (playerShipX > x && playerShipX < x + length)) {
            randomNumber = generator.nextInt(150);
            if (randomNumber == 0) {
                return true;
            }
        }
//        randomNumber = generator.nextInt(10);
        randomNumber = generator.nextInt(1000-difficultyLevel*100);
        if (randomNumber == 0) {
            return true;
        }
        return false;
    }
}

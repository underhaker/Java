package yes.starship;

import android.graphics.RectF;

/**
 * Created by Joni on 19.5.2016 г..
 */
public class Blaster {
    private float x;
    private float y;
    private RectF rect;
    public final int UP = 0;
    public final int DOWN = 1;

    int heading = -1;//-1 going nowhere
    float speed = 550;
    private int width = 1;
    private int height;
    private boolean isActive;

    public Blaster(int screenY) {
        height = screenY / 20;
        isActive = false;
        rect = new RectF();
    }

    public RectF getRect() {
        return rect;
    }

    public boolean getStatus() {
        return isActive;
    }

    public void setInactive() {
        isActive = false;
    }

    public float getImpactPointY() {
        if (heading == DOWN) {
            return y + height;
        } else {
            return y;
        }
    }

    public boolean shoot(float startX, float startY, int direction) {
        if (!isActive) {
            x = startX;
            y = startY;
            heading = direction;
            isActive = true;
            return true;
        }
        return false; // if already active
    }

    public void update(long fps) {
        if (heading == UP) {
            y = y - speed / fps;
        } else {
            y = y + speed / fps;
        }
        rect.left = x;
        rect.right = x + width;
        rect.top = y;
        rect.bottom = y + height;
    }
}

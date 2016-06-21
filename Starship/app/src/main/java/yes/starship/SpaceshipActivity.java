package yes.starship;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.RelativeLayout;

public class SpaceshipActivity extends Activity {

    SpaceshipView spaceshipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        spaceshipView = new SpaceshipView(this, size.x, size.y);
        setContentView(spaceshipView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        spaceshipView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        spaceshipView.pause();
    }

}


package yes.starship;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Joni on 19.5.2016 г..
 */
public class SpaceshipView extends SurfaceView implements Runnable {
    Context context;
    private Thread gameThread = null;
    private SurfaceHolder ourHolder;
    private volatile boolean playing;
    private boolean paused = true;
    private boolean startedRound = false;
    private Canvas canvas;
    private Paint paint;
    private Controls leftArrow;
    private Controls rightArrow;
    private long fps;
    private long timeThisFrame;
    private int screenX;
    private int screenY;
    private final int LEFT = 1;
    private final int RIGHT = 2;
    private final long TIME_BETWEEN_SHOTS = 100;
    private final int NUMBER_OF_MAXIMUM_ENEMIES = 30;
    private Star[] stars = new Star[35];
    private PlayerShip playerShip;
    private Blaster[] blasters = new Blaster[10];
    private Blaster[] enemyBlasters = new Blaster[200];
    private int nextBlaster = 0;
    private int nextBlasterEnemy = 0;
    private int maxBlasters = 10;
    private int maxEnemyBlasters = 10;
    private Enemy[] enemies = new Enemy[NUMBER_OF_MAXIMUM_ENEMIES];
    private int enemyCounter = 0;
    private int numEnemies = 0;
    private int score = 0;
    private int maxScore = 0;
    private int level = 0;
    private int lives = 4;

    private long timeShotFired = 0;
    private int columnMax;
    private int rowMax;
    private boolean printRoundMessage = false;
    private boolean printLostMessage = false;
    private boolean printWonMessage = false;
    private boolean gameLost = false;
    private boolean lost = false;
    private boolean won = false;
    private boolean bumped = false;
    private long startingTime = 0;

    public SpaceshipView(Context context, int x, int y) {
        super(context);
        this.context = context;
        ourHolder = getHolder();
        paint = new Paint();
        screenX = x;
        screenY = y;
        leftArrow = new Controls(context, screenX, screenY, true);
        rightArrow = new Controls(context, screenX, screenY, false);
        // drawRound(level);
        prepareStars();
        prepareLevel();
    }

    private void prepareStars() {
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(context, i+1, screenX, screenY);
        }
    }

    private void preparePlayerShip() {
        playerShip = new PlayerShip(context, screenX, screenY);
    }

    private void preparePlayerBlasters() {
        for (int i = 0; i < blasters.length; i++) {
            blasters[i] = new Blaster(screenY);
        }
    }

    private void prepareEnemyBlasters() {
        for (int i = 0; i < enemyBlasters.length; i++) {
            enemyBlasters[i] = new Blaster(screenY);
        }
    }

    private void prepareEnemies() {
        enemyCounter = 0;
        numEnemies += 5;
        rowMax = numEnemies / 5;
        if (rowMax != 0) {
            columnMax = numEnemies / rowMax;
        } else {
            rowMax = 1;
            columnMax = numEnemies;
        }
        for (int row = 0; row < rowMax; row++) {
            for (int column = 0; column < columnMax; column++) {
                enemies[enemyCounter] = new Enemy(context, row, column, screenX, screenY);
                enemyCounter++;
            }
        }
    }

    private void prepareLevel() {
        if (numEnemies > NUMBER_OF_MAXIMUM_ENEMIES) {
            printWonMessage = true;
            won = true;
            startedRound = false;
            startingTime = System.currentTimeMillis();
//            returnToMenu();
        }else {
            level++;
            printRoundMessage = true;
            startedRound = false;
            startingTime = System.currentTimeMillis();
            preparePlayerShip();
            preparePlayerBlasters();
            prepareEnemyBlasters();
            prepareEnemies();
        }
    }

    @Override
    public void run() {
        while (playing) {
            long startFrameTime = System.currentTimeMillis();
            if (!paused) {
                update();
            }
            draw();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    private void updateStars() {
        for (int i = 0; i < stars.length; i++) {
            stars[i].update(fps);
            if (stars[i].getY() >= screenY) {
                stars[i].resetPosition();
            }
        }
    }

    private void updatePlayerShip() {
        if ((playerShip.getX() + 1.5 * playerShip.getLength() < screenX && playerShip.getMovementState() == RIGHT) ||
                (playerShip.getX() - playerShip.getLength() >= 0 && playerShip.getMovementState() == LEFT)) {
            playerShip.update(fps);
        }
    }

    private void updateEnemies() {
        bumped = false;
        for (int i = 0; i < enemyCounter; i++) {
            if (enemies[i].getVisibility()) {
                enemies[i].update(fps);
                if (enemies[i].takeAim(playerShip.getX(), playerShip.getLength(),level)) {
                    if (enemyBlasters[nextBlasterEnemy].shoot(enemies[i].getX() + enemies[i].getLength() / 2, enemies[i].getY(), enemyBlasters[nextBlasterEnemy].DOWN)) {
                        nextBlasterEnemy++;
                        if (nextBlasterEnemy == maxEnemyBlasters) {
                            nextBlasterEnemy = 0;
                        }
                    }
                }
                if (enemies[i].getX() > screenX - 1.5*enemies[i].getLength() || enemies[i].getX() < 0) {
                    bumped = true;
                }
            }
        }
        if (bumped) {
            for (int i = 0; i < enemyCounter; i++) {
//                    enemies[i].dropDownAndReverse();
                enemies[i].reverse();
//                if (enemies[i].getY() > screenY - screenY / 10) {
//                    lost = true;
//                }
            }
        }
    }

    private void updatePlayerBlasters() {
        for (int i = 0; i < blasters.length; i++) {
            if (blasters[i].getStatus()) {
                blasters[i].update(fps);
                if (blasters[i].getImpactPointY() < 0) {
                    blasters[i].setInactive();
                }
            }
        }
//        for (int i = 0; i < blasters.length; i++) {
//            if (blasters[i].getImpactPointY() < 0) {
//                blasters[i].setInactive();
//            }
//        }
    }

    private void updateEnemyBlasters() {
        for (int i = 0; i < enemyBlasters.length; i++) {
            if (enemyBlasters[i].getStatus()) {
                enemyBlasters[i].update(fps);
                if (enemyBlasters[i].getImpactPointY() > screenY) {
                    enemyBlasters[i].setInactive();
                }
            }
        }
//        for (int i = 0; i < enemyBlasters.length; i++) {
//            if (enemyBlasters[i].getImpactPointY() > screenY) {
//                enemyBlasters[i].setInactive();
//            }
//        }
    }

    private void updatePlayerEnemyCollision() {
        for (int i = 0; i < blasters.length; i++) {
            if (blasters[i].getStatus()) {
                for (int j = 0; j < enemyCounter; j++) {
                    if (enemies[j].getVisibility()) {
                        if (RectF.intersects(blasters[i].getRect(), enemies[j].getRect())) {
                            enemies[j].setInvisible();
                            blasters[i].setInactive();
                            score = score + 10;
                            maxScore = maxScore + score;
                            if (score == enemyCounter * 10) {
                                paused = true;
                                score = 0;
//                                lives = 4;
//                                returnToMenu();
                                prepareLevel();
                            }
                        }
                    }
                }
            }
        }
    }

    private void updateEnemyPlayerCollision() {
        for (int i = 0; i < enemyBlasters.length; i++) {
            if (enemyBlasters[i].getStatus()) {
                if (RectF.intersects(playerShip.getRect(), enemyBlasters[i].getRect())) {
                    enemyBlasters[i].setInactive();
                    lives--;
                    if (lives <= 0) {
                        paused = true;
//                            lives = 3;
//                            score = 0;
                        lost = true;
                        gameLost = lost;

                    }
                    if (lost) {
                        printLostMessage = true;
                        startedRound = false;
                        startingTime = System.currentTimeMillis();
                        break;
//                        returnToMenu();
                    }
                }
            }
        }
    }

    private void update() {
//        boolean bumped = false;
        boolean lost = false;
        updateStars();
        if (startedRound == true) {
            updatePlayerShip();
            updateEnemies();
            updatePlayerBlasters();
            updateEnemyBlasters();
            updatePlayerEnemyCollision();
            updateEnemyPlayerCollision();

        }
    }

    private void returnToMenu() {
        Intent resultIntent = new Intent();
        String stringScore = Integer.toString(maxScore);
        resultIntent.putExtra("highscore", stringScore);
        ((Activity) getContext()).setResult(Activity.RESULT_OK, resultIntent);
        ((Activity) getContext()).finish();
    }

    private void draw() {
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.rgb(0, 0, 0));
            paint.setColor(Color.argb(255, 255, 255, 255));
            //draw controls
            canvas.drawBitmap(leftArrow.getBitmap(), leftArrow.getX(), leftArrow.getY(), paint);
            canvas.drawBitmap(rightArrow.getBitmap(), rightArrow.getX(), rightArrow.getY(), paint);
            //draw stars
            for (int i = 0; i < stars.length; i++) {
                canvas.drawBitmap(stars[i].getBitmap(), stars[i].getX(), stars[i].getY(), paint);
            }

            //draw player
            canvas.drawBitmap(playerShip.getBitmap(), playerShip.getX(), screenY - playerShip.getHeight(), paint);
            // Draw the invaders
            for (int i = 0; i < enemyCounter; i++) {
                if (enemies[i].getVisibility()) {
                    canvas.drawBitmap(enemies[i].getBitmap(), enemies[i].getX(), enemies[i].getY(), paint);
                }
            }
            //draw player blaster
            paint.setColor(Color.rgb(255, 0, 0));
            for (int i = 0; i < blasters.length; i++) {
                if (blasters[i].getStatus()) {
                    canvas.drawRect(blasters[i].getRect(), paint);
                }
            }

            //draw enemy blaster
            paint.setColor(Color.rgb(0, 255, 0));
            for (int i = 0; i < enemyBlasters.length; i++) {
                if (enemyBlasters[i].getStatus()) {
                    canvas.drawRect(enemyBlasters[i].getRect(), paint);
                }
            }
            //draw level and lives
            paint.setColor(Color.argb(255, 249, 129, 0));
            paint.setTextSize(40);
            canvas.drawText("Level: " + level + "  Score: " + maxScore + "  Lives: " + lives, 10, 50, paint);
            //draw message if game is lost
            if(gameLost){
                if(printLostMessage == true){
                    if(System.currentTimeMillis() - startingTime > 5000){
                        returnToMenu();
                    }

                } else {
                    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                    int width = metrics.widthPixels;
                    int height = metrics.heightPixels;
                    String message = "You lost the game!";
                    paint.setColor(Color.CYAN);
                    paint.setTextSize(50);
                    canvas.drawText(message, width / 4, height / 2, paint);
                }
            }
            if(won){
                if(printWonMessage == true){
                    if(System.currentTimeMillis() - startingTime > 5000){
                        returnToMenu();
                    }
                } else {
                    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                    int width = metrics.widthPixels;
                    int height = metrics.heightPixels;
                    String message = "You won the game!";
                    paint.setColor(Color.CYAN);
                    paint.setTextSize(50);
                    canvas.drawText(message, width / 4, height / 2, paint);
                }
            }
            //draw message for next round
            if (printRoundMessage == true) {
                if (System.currentTimeMillis() - startingTime > 5000) {
                    printRoundMessage = false;
                    startedRound = true;
                    paused = false;
                } else {
                    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                    int width = metrics.widthPixels;
                    int height = metrics.heightPixels;
                    String roundMessage = "Prepare for round " + level + "!";
//                    paint.setColor(Color.argb(255, 249, 129, 0));
                    paint.setColor(Color.CYAN);
                    paint.setTextSize(50);
                    canvas.drawText(roundMessage, width / 4, height / 2, paint);
                }
            }

            ourHolder.unlockCanvasAndPost(canvas);

        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
//                paused = false;
                if (motionEvent.getX() >= rightArrow.getX() && motionEvent.getY() >= rightArrow.getY()) {
                    playerShip.setMovementState(playerShip.RIGHT);
                } else if (motionEvent.getX() <= leftArrow.getLength() && motionEvent.getY() >= leftArrow.getY()) {
                    playerShip.setMovementState(playerShip.LEFT);
                } else {
                    if (blasters[nextBlaster].shoot(playerShip.getX() + playerShip.getLength() / 2, screenY, blasters[nextBlaster].UP)) {
                        if (System.currentTimeMillis() - timeShotFired >= TIME_BETWEEN_SHOTS) {
                            nextBlaster++;
                            timeShotFired = System.currentTimeMillis();
                        }
                        if (nextBlaster == maxBlasters) {
                            nextBlaster = 0;
                        }

                    }
                }
//                if (motionEvent.getY() > screenY - screenY / 8) {
//                    if (motionEvent.getX() > screenX / 2) {
//                        playerShip.setMovementState(playerShip.RIGHT);
//                        return true;
//
//                    } else {
//                        playerShip.setMovementState(playerShip.LEFT);
//                        return true;
//                    }
//                }
//                if (motionEvent.getY() < screenY - screenY / 8) {


//                        soundPool.play(shootID, 1, 1, 0, 0, 1);
//                    }
//                }

                break;
            case MotionEvent.ACTION_UP:
                if (motionEvent.getY() > screenY - screenY / 10) {
                    playerShip.setMovementState(playerShip.STOPPED);
                }
                break;
        }
        return true;
    }
}

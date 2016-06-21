package yes.starship;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MenuActivity extends Activity {
    private Button button_start;
    private Button button_settings;
    private Button button_exit;
    private Button button_about;
    private String maxScore = "0";
    public final static String EXTRA_MESSAGE = "yes.starship.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        prepareButtons();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        resetButtons();
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                maxScore = data.getStringExtra("highscore");
                SharedPreferences sharedPref = getSharedPreferences("settings",0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("highscore",maxScore);
                editor.commit();
            }

//            if (resultCode == RESULT_CANCELED) {
//// Operation failed or cancelled. Handle as-good-as-wecan
            }
    }
    private void prepareButtons() {
        button_start = (Button) findViewById(R.id.button_start);
        button_settings = (Button) findViewById(R.id.button_settings);
        button_about = (Button) findViewById(R.id.button_about);
        button_exit = (Button) findViewById(R.id.button_exit);
        resetButtons();
    }
    private void resetButtons() {
        button_start.setBackground(this.getResources().getDrawable(R.drawable.bg_button_unpressed));
        button_settings.setBackground(this.getResources().getDrawable(R.drawable.bg_button_unpressed));
        button_about.setBackground(this.getResources().getDrawable(R.drawable.bg_button_unpressed));
        button_exit.setBackground(this.getResources().getDrawable(R.drawable.bg_button_unpressed));
    }

    public void button_start_click(View view) {
        button_start.setBackground(this.getResources().getDrawable(R.drawable.bg_button_pressed));
        Intent intent = new Intent(this, SpaceshipActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent, 1);
    }

    public void button_settings_click(View view) {
        button_settings.setBackground(this.getResources().getDrawable(R.drawable.bg_button_pressed));
        Intent intent = new Intent(this, SettingsActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("highscore",maxScore);
        startActivityForResult(intent, 1);
    }
    public void button_about_click(View view) {
        button_about.setBackground(this.getResources().getDrawable(R.drawable.bg_button_pressed));
        Intent intent = new Intent(this, AboutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent, 1);
    }

    public void button_exit_click(View view) {
        button_exit.setBackground(this.getResources().getDrawable(R.drawable.bg_button_pressed));
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}


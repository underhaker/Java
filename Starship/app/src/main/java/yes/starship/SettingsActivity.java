package yes.starship;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SettingsActivity extends Activity {
    private TextView highscoreText;
    private TextView nameText;
    private TextView nameToChangeText;
    private Button button_return;
    private Button button_reset;
    private Button button_changeName;
    private Button button_setName;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        prepareData();
    }

    private void prepareData() {
        button_reset = (Button) findViewById(R.id.button_settings_reset);
        button_reset.setBackground(this.getResources().getDrawable(R.drawable.bg_button_unpressed));
        button_return = (Button) findViewById(R.id.button_settings_return);
        button_return.setBackground(this.getResources().getDrawable(R.drawable.bg_button_unpressed));
        button_changeName = (Button) findViewById(R.id.button_settings_changeName);
        button_changeName.setBackground(this.getResources().getDrawable(R.drawable.bg_button_unpressed));
        button_setName = (Button) findViewById(R.id.button_settings_setName);
        button_setName.setBackground(this.getResources().getDrawable(R.drawable.bg_button_unpressed));
        nameToChangeText = (TextView) findViewById(R.id.editText_nameToChange);
        highscoreText = (TextView) findViewById(R.id.textView_highscore);
        nameText = (TextView) findViewById(R.id.textView_name);
        String highscore = getHighscore();
        String name = getName();
        if (name.length() == 0) {
            name = new StringBuilder("Player One").toString();
            saveName(name);
            saveHighscore("0");
        }
        nameText.setTextColor(Color.YELLOW);
        nameText.setTextSize(40);
        nameText.setText(name + " " + highscore);

        highscoreText.setTextColor(Color.YELLOW);
        highscoreText.setTextSize(40);
        highscoreText.setText("HIGHSCORE");
    }

    public void button_settings_changeName_click(View view) {
        button_changeName.setBackground(this.getResources().getDrawable(R.drawable.bg_button_pressed));
        button_changeName.setEnabled(false);
        nameToChangeText.setVisibility(View.VISIBLE);
        nameToChangeText.setText("");
        button_setName.setVisibility(View.VISIBLE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                button_changeName.setBackground(getResources().getDrawable(R.drawable.bg_button_unpressed));
            }
        }, 100);
    }

    public void button_settings_setName_click(View view) {

        String name = nameToChangeText.getText().toString().trim();
        if(name.length()!=0) {
            button_setName.setBackground(this.getResources().getDrawable(R.drawable.bg_button_pressed));
            saveName(name);
            saveHighscore("0");
            nameText.setText(name + " " + getHighscore());

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    button_setName.setBackground(getResources().getDrawable(R.drawable.bg_button_unpressed));
                }
            }, 100);

            button_setName.setVisibility(View.INVISIBLE);
            nameToChangeText.setVisibility(View.INVISIBLE);
            button_changeName.setEnabled(true);
        }
    }

    private void saveName(String name) {
        sharedPref = getSharedPreferences("settings", 0);
        editor = sharedPref.edit();
        editor.putString("name", name);
        editor.commit();
    }

    private void saveHighscore(String highscore) {
        sharedPref = getSharedPreferences("settings", 0);
        editor = sharedPref.edit();
        editor.putString("highscore", highscore);
        editor.commit();
    }

    private String getName() {
        sharedPref = getSharedPreferences("settings", 0);
        return sharedPref.getString("name", "");
    }

    private String getHighscore() {
        sharedPref = getSharedPreferences("settings", 0);
        return sharedPref.getString("highscore", "");
    }

    public void button_settings_reset_click(View view) {
        button_reset.setBackground(this.getResources().getDrawable(R.drawable.bg_button_pressed));
        saveHighscore("0");
        nameText.setText(getName() + " " + getHighscore());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                button_reset.setBackground(getResources().getDrawable(R.drawable.bg_button_unpressed));
            }
        }, 100);
    }

    public void button_settings_return_click(View view) {
        button_return.setBackground(this.getResources().getDrawable(R.drawable.bg_button_pressed));
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}

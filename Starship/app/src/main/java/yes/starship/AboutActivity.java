package yes.starship;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends Activity {
    Button button_return;
    TextView aboutText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        prepareData();
    }

    private void prepareData() {
        button_return = (Button) findViewById(R.id.button_about_return);
        button_return.setBackground(this.getResources().getDrawable(R.drawable.bg_button_unpressed));
        String text="This application is created by Yoan Stoyanov, student in Faculty of Mathematics and Informatics.\nThe game is a simplified version of the arcade game Galaga.\nAdditional features will be added in the near future.\nVersion 1.0";
        aboutText = (TextView) findViewById(R.id.textView_about);
        aboutText.setTextColor(Color.YELLOW);
        aboutText.setTextSize(20);
        aboutText.setText(text);
    }
    public void button_about_return_click(View view){
        button_return.setBackground(this.getResources().getDrawable(R.drawable.bg_button_pressed));
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

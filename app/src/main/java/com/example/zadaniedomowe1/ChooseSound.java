package com.example.zadaniedomowe1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ChooseSound extends AppCompatActivity {
    private int current_sound;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sound);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        Intent receivedIntent = getIntent();
        current_sound = receivedIntent.getIntExtra(MainActivity.SOUND_ID, 0);
        radioGroup.check(current_sound);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                String radioButtonString = (String) radioButton.getText();  //null pointer
                switch(radioButtonString)
                {
                    case "Sound 1": current_sound=0; break;
                    case "Sound 2": current_sound=1; break;
                    case "Sound 3": current_sound=2; break;
                    case "Sound 4": current_sound=3; break;
                    case "Sound 5": current_sound=4; break;
                    default: current_sound=0; break;
                }
            }
        });
    }
    public void soundOnClick(View view)
    {
        Button button = (Button) findViewById(view.getId());
        Intent data = new Intent();
        if(button.getId() == R.id.soundOkButton)
        {
            data.putExtra(MainActivity.SOUND_ID, current_sound);
            setResult(RESULT_OK, data);
        }
        else setResult(RESULT_CANCELED, data);
        finish();
    }
}
package com.example.zadaniedomowe1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class ChooseContact extends AppCompatActivity
{
    private int current_contact = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_contact);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                current_contact = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent receivedIntent = getIntent();
        current_contact = receivedIntent.getIntExtra(MainActivity.CONTACT_ID, 0);
        spinner.setSelection(current_contact);
    }

    public void contactOnClick(View view)
    {
        Button button = (Button) findViewById(view.getId());
        Intent data = new Intent();
        if(button.getId() == R.id.contactOkButton)
        {
            data.putExtra(MainActivity.CONTACT_ID, current_contact);
            setResult(RESULT_OK, data);
        }
        else setResult(RESULT_CANCELED, data);
        finish();
    }
}

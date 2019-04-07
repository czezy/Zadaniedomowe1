package com.example.zadaniedomowe1;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
class Contact
{
    private String name;
    private int sound;
    private int avatar;
    Contact(String name, int sound, int avatar)
    {
        this.name = name;
        this.sound = sound;
        this.avatar = avatar;
    }
    public int getSound() { return sound; }
    public int getAvatar() { return avatar; }
    public String getName() { return name; }
    public void setSound(int sound)
    {
        this.sound = sound;
    }
}

public class MainActivity extends AppCompatActivity {

    public static final String SOUND_ID = "sound id";
    public static final String CONTACT_ID = "contact id";
    private int current_contact = 0;
    public static final int CONTACT_REQUEST = 1;
    public static final int SOUND_REQUEST = 2;
    public static ArrayList<Contact> contactList = new ArrayList<Contact>();

    private static MediaPlayer player;
    public static Uri[] sounds = new Uri[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contactList.add(new Contact("John Doe", 0, R.drawable.avatar1));
        contactList.add(new Contact("Jane Doe", 1, R.drawable.avatar2));
        contactList.add(new Contact("Jan Kowalski", 2, R.drawable.avatar3));
        contactList.add(new Contact("Krystyna Kowalska", 3, R.drawable.avatar4));
        contactList.add(new Contact("Jakub Anonimowy", 4, R.drawable.avatar5));

        sounds[0]  =  Uri.parse("android.resource://"  +  getPackageName()  +  "/"  + R.raw.mario);
        sounds[1]  =  Uri.parse("android.resource://"  +  getPackageName()  +  "/"  + R.raw.ring01);
        sounds[2]  =  Uri.parse("android.resource://"  + getPackageName()  +  "/"  + R.raw.ring02);
        sounds[3]  =  Uri.parse("android.resource://"  +  getPackageName()  +  "/"  + R.raw.ring03);
        sounds[4]  =  Uri.parse("android.resource://"  +  getPackageName()  +  "/"  + R.raw.ring04);

        TextView textView = (TextView) findViewById(R.id.textView);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        textView.setText(contactList.get(current_contact).getName());
        imageView.setImageResource(contactList.get(current_contact).getAvatar());

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(!player.isPlaying())
                {
                    Snackbar.make(view, "Odtwarzanie dźwięku", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    try
                    {
                        player.prepare();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                    player.start();
                    fab.setImageResource(R.drawable.ic_media_pause);
                }
                else
                {
                    Snackbar.make(view, "Zatrzymanie dźwięku", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    player.stop();
                    fab.setImageResource(R.drawable.ic_media_play);
                }
            }
        });
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        player.release();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if(player.isPlaying()) player.stop();
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_media_play);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        try
        {
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(this, sounds[contactList.get(current_contact).getSound()]);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                    FloatingActionButton fab = findViewById(R.id.fab);
                    fab.setImageResource(R.drawable.ic_media_play);
                }
            });
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) //nie zmienia się wybrany kontakt
    {
        if(resultCode == RESULT_OK)
        {
            switch(requestCode)
            {
                case CONTACT_REQUEST:
                    current_contact = data.getIntExtra(CONTACT_ID, 0);
                    break;
                case SOUND_REQUEST:
                    contactList.get(current_contact).setSound(data.getIntExtra(SOUND_ID, 0));
                    break;
                default: break;
            }
        }
        else if(resultCode == RESULT_CANCELED)
        {
            Toast.makeText(getApplicationContext(),getText(R.string.back_message),Toast.LENGTH_SHORT).show();
        }
        TextView textView = (TextView) findViewById(R.id.textView);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        textView.setText(contactList.get(current_contact).getName());
        imageView.setImageResource(contactList.get(current_contact).getAvatar());
    }

    public void soundOnClick(View view)
    {
        Intent soundIntent = new Intent(getApplicationContext(), ChooseSound.class);
        soundIntent.putExtra(SOUND_ID, contactList.get(current_contact).getSound());
        startActivityForResult(soundIntent, SOUND_REQUEST);
    }

    public void contactOnClick(View view)
    {
        Intent contactIntent = new Intent(getApplicationContext(), ChooseContact.class);
        contactIntent.putExtra(CONTACT_ID, current_contact);
        startActivityForResult(contactIntent, CONTACT_REQUEST);
    }
}
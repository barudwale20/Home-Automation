package com.umangshah.homeautomation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    ImageButton Light;
    ImageButton Fan;
    ImageButton Mic;
    int LightStatus = 0;
    int FanStatus = 0;
    int MicStatus = 0;
    SharedPreferences DeviceStatus;
    OkHttpClient client;
    MediaType JSON;

    RequestQueue ExampleRequestQueue;

    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Light = findViewById(R.id.LightButton);
        Fan = findViewById(R.id.FanButton);
        Mic = findViewById(R.id.MicButton);
        ExampleRequestQueue = Volley.newRequestQueue(this);
        client = new OkHttpClient();
        JSON = MediaType.parse("application/json; charset=utf-8");
        DeviceStatus = getSharedPreferences("DeviceStatus", Context.MODE_PRIVATE);
        LightStatus = Integer.parseInt(DeviceStatus.getString("Light","0"));
        FanStatus = Integer.parseInt(DeviceStatus.getString("Fan","0"));
        if(LightStatus==1)
            SwitchLightOn();
        else
            SwitchLightOff();
        if(FanStatus==1)
            SwitchFanOn();
        else
            SwitchFanOff();

    }
    public void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak and Voila!!");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }
    public void Recognize(View v) {

        SwitchMic();
        startVoiceRecognitionActivity();
        SwitchMic();
    }
    public void voiceinputbuttons() {
        Mic = findViewById(R.id.MicButton);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it
            // could have heard
            ArrayList Result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String input = Result.get(0).toString();
            Log.d("Listened",input);
            if (input.toLowerCase().contains("Light".toLowerCase()))
            {
                if(input.toLowerCase().contains("On".toLowerCase()))
                {
                    SwitchLightOn();
                    try {
                        sendGetRequest("on1");
                    }catch (Exception ex){}
                }
                else if(input.toLowerCase().contains("Of".toLowerCase()))
                {
                    SwitchLightOff();
                    try {
                        sendGetRequest("off1");
                    }catch (Exception ex){}
                }
            }
            if (input.toLowerCase().contains("Fan".toLowerCase()))
            {
                if(input.toLowerCase().contains("On".toLowerCase()))
                {
                    SwitchFanOn();
                    try {
                        sendGetRequest("on2");
                    }catch (Exception ex){}
                }
                else if(input.toLowerCase().contains("Of".toLowerCase()))
                {
                    SwitchFanOff();
                    try {
                        sendGetRequest("off2");
                    }catch (Exception ex){}
                }
            }


            // matches is the result of voice input. It is a list of what the
            // user possibly said.
            // Using an if statement for the keyword you want to use allows the
            // use of any activity if keywords match
            // it is possible to set up multiple keywords to use the same
            // activity so more than one word will allow the user
            // to use the activity (makes it so the user doesn't have to
            // memorize words from a list)
            // to use an activity from the voice input information simply use
            // the following format;
            // if (matches.contains("keyword here") { startActivity(new
            // Intent("name.of.manifest.ACTIVITY")
        }
    }
    public void informationMenu() {
        startActivity(new Intent("android.intent.action.INFOSCREEN"));
    }

    public void SwitchLight(View vw){
        if(LightStatus == 0) {
            SwitchLightOn();
            try {
                sendGetRequest("on1");
            }catch (Exception ex){}
        }
        else{
            SwitchLightOff();
            try {
                sendGetRequest("off1");
            }catch (Exception ex){}

        }

    }
    public void SwitchFan(View vw){
        if(FanStatus == 0) {
            SwitchFanOn();
            try {
                sendGetRequest("on2");
            }catch (Exception ex){}

        }
        else{
            SwitchFanOff();
            try {
                sendGetRequest("off2");
            }catch (Exception ex){}
        }

    }
    public void SwitchLightOn()
    {
        Light.setImageResource(R.drawable.light_on);
        LightStatus = 1;
        SharedPreferences.Editor editor = DeviceStatus.edit();
        editor.putString("Light", "1");
        editor.commit();

    }
    public void SwitchLightOff()
    {
        Light.setImageResource(R.drawable.light_off);
        LightStatus = 0;
        SharedPreferences.Editor editor = DeviceStatus.edit();
        editor.putString("Light", "0");
        editor.commit();
    }
    public void SwitchFanOn()
    {
        Fan.setImageResource(R.drawable.fan_on);
        FanStatus = 1;
        SharedPreferences.Editor editor = DeviceStatus.edit();
        editor.putString("Fan", "1");
        editor.commit();

    }
    public void SwitchFanOff()
    {
        Fan.setImageResource(R.drawable.fan_off);
        FanStatus = 0;
        SharedPreferences.Editor editor = DeviceStatus.edit();
        editor.putString("Fan", "0");
        editor.commit();
    }
    public void SwitchMic(){
        if(MicStatus == 0) {
            Mic.setImageResource(R.drawable.mic_on);
            MicStatus = 1;
        }
        else{
            Mic.setImageResource(R.drawable.mic_off);
            MicStatus = 0;
        }
    }
    public void sendGetRequest(String key) throws IOException {
        GetTask task = new GetTask();
        task.url ="https://barudwale20.000webhostapp.com/ha/button.php?"+key+"=sdsd";
        task.execute();



// Request a string response from the provided URL.

        }
    public class GetTask extends AsyncTask {
        private Exception exception;
        public String url;
        protected String doInBackground(String...urls) {
            try {
                String getResponse = get(url);
                return getResponse;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(String getResponse) {
            System.out.println(getResponse);
        }

        public String get(String url) throws IOException {
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .build();

            okhttp3.Response response = client.newCall(request).execute();
            return response.body().string();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                String getResponse = get(url);
                return getResponse;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }
    }

}

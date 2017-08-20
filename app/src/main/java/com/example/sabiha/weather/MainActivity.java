package com.example.sabiha.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationDetails ld = new LocationDetails(this);

        String stringLongitude = String.valueOf(ld.getLongitude());
        textview = (TextView)findViewById(R.id.tb);
        textview.setText(stringLongitude);

        String stringLatitude = String.valueOf(ld.getLatitude());
        textview = (TextView)findViewById(R.id.textView2);
        textview.setText(stringLatitude);
    }
}

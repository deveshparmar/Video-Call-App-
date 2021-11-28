package com.codewithdevesh.letsmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.URL;
import java.util.Random;

public class DashboardActivity extends AppCompatActivity {
    private TextInputLayout code;
    MaterialButton join,share;
    TextView genCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        code = findViewById(R.id.code);
        join = findViewById(R.id.join_btn);
        share = findViewById(R.id.share_btn);
        genCode = findViewById(R.id.gen_code);
        URL server_URL;

        try{
            server_URL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(server_URL)
                    .setWelcomePageEnabled(false)
                    .build();
            JitsiMeet.setDefaultConferenceOptions(options);
        }catch (Exception e){
            e.printStackTrace();
        }

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(code.getEditText().getText().toString())
                        .setWelcomePageEnabled(false)
                        .build();
                JitsiMeetActivity.launch(DashboardActivity.this,options);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random r = new Random();
                int num = r.nextInt(999999);
                String c = String.valueOf(num);
                genCode.setVisibility(View.VISIBLE);
                genCode.setText(c);
                Toast.makeText(getApplicationContext(), "Code:"+c, Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT,"Code for joining meeting - "+ c);
                i.setType("text/plain");
                Intent si = Intent.createChooser(i,null);
                startActivity(si);
            }
        });

    }
}
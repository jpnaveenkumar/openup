package com.example.naveen.openup;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onButtonClick(View v)
    {
        if(v.getId()==R.id.imageButton)
        {
            convertotext();
        }
    }
    public void convertotext()
    {
        Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"speak something");
        try
        {
            startActivityForResult(i,100);
        }
        catch(ActivityNotFoundException e)
        {
            Toast.makeText(MainActivity.this,"your device is not compatible",Toast.LENGTH_LONG).show();
        }
    }
    public void onActivityResult(int request_code,int result_code,Intent i)
    {
        super.onActivityResult(request_code,result_code,i);
        switch(request_code) {
            case 100:
                if (result_code == RESULT_OK && i != null)
                {
                    ArrayList<String> result=i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    TextView t1=(TextView)findViewById(R.id.textView);
                    Toast.makeText(MainActivity.this,"You Said: "+result.get(0),Toast.LENGTH_LONG).show();
                    openapps(result);
                    //t1.setText(result.get(0));
                }

        }
    }
    public void openapps(ArrayList<String> d)
    {
        String ans=d.get(0);
        int r=ans.indexOf(" ");
        String app=ans.substring(r+1);
        PackageManager pm=getPackageManager();
        //List<ApplicationInfo> li=pm.getInstalledApplications(pm.GET_META_DATA);
        List<PackageInfo> apps1 = getPackageManager().getInstalledPackages(0);
        //Toast.makeText(MainActivity.this,"You Said is: "+li.get(0),Toast.LENGTH_LONG).show();
        ApplicationInfo df;
        int y=0;
        for(PackageInfo pp : apps1)
        {
            ApplicationInfo ai=pp.applicationInfo;
            String a1=String.valueOf(pm.getApplicationLabel(ai));
            String pac=pp.packageName;
            //Toast.makeText(MainActivity.this,"You packagename: "+pac,Toast.LENGTH_LONG).show();
            //Toast.makeText(MainActivity.this,"You appname: "+a1,Toast.LENGTH_LONG).show();
            Log.i("MyActivity",pp.packageName);
            Log.i("MyActivity",a1);
            //Log.i("MyActivity",pp.applicationInfo);
            String r1=app.replaceAll(" ","");
            String r2=a1.replaceAll(" ","");
            if(r1.toLowerCase().equals(r2.toLowerCase()))
            {
                y++;
                //Log.i("MyActivity","finalyy got itttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(pac);
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
            }
        }
        if(y==0)
        {
            Toast.makeText(MainActivity.this,"Sorry, the app doesn't exist",Toast.LENGTH_LONG).show();
        }
    }
        /*for(ApplicationInfo ai : li)
        {
            String a1=String.valueOf(pm.getApplicationLabel(ai));
            Log.i("MyActivity",a1);
            if(app.toLowerCase().equals(a1.toLowerCase()))
            {
                Toast.makeText(MainActivity.this,"You Said: "+app,Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this,"You Said: "+a1,Toast.LENGTH_LONG).show();
                Log.i("MyActivity2","found it");
                PackageInfo p=ai;
                break;
            }
        }*/
       // Log.i("MyActivity1",app);
        //pm.getApplicationLabel();
    }


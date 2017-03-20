package com.example.rohit.newapp1;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Communicater{

    private Button send;
    private Button receive;
    private Button edit;
    private TextView username;
    private RelativeLayout topLayout;




    FragmentManager manager;
    private EditFragment editFrag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setupWidowAnimations();
        setContentView(R.layout.activity_main);

        topLayout=(RelativeLayout)findViewById(R.id.topLayout);



        //sharedPreferences=getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);

       // PersonalInfo.THIS_IS_MY_NAME=sharedPreferences.getString("SHAREDPREFERENCEUSERNAME","DEFAULT");

        //PersonalInfo.THIS_IS_MY_NAME="hi";

        if(savedInstanceState==null)
        {loadDetails();}



       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        username=(TextView)findViewById(R.id.username);
        username.setText(PersonalInfo.THIS_IS_MY_NAME);
        username.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                addFragment();
            }
        });

        send=(Button)findViewById(R.id.send);



        receive=(Button)findViewById(R.id.receive);
        edit=(Button)findViewById(R.id.editButton);



        edit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        //addFragment();
                                        Intent i=new Intent(MainActivity.this,EditMyProfile.class);
                                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
                                        {
                                            /*
                                            View username=v.findViewById(R.id.username);
                                            View editButton=v.findViewById(R.id.editButton);
                                            View userImage=v.findViewById(R.id.topLayout);
                                            */
                                            Pair<View,String> pair1=Pair.create((View)username,username.getTransitionName());
                                            Pair<View,String> pair2=Pair.create((View)edit,edit.getTransitionName());
                                            Pair<View,String> pair3=Pair.create((View)topLayout, topLayout.getTransitionName());

                                           // ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,pair1,pair2);
                                            ActivityOptionsCompat options = ActivityOptionsCompat.
                                                    makeSceneTransitionAnimation(MainActivity.this, pair1, pair2, pair3);
                                            startActivity(i,options.toBundle());
                                        }
                                        else
                                        {
                                            startActivity(i);
                                        }

                                    }


                                }
        );


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, SelectFileActivity.class);
                startActivity(i);

            }
        });


        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Receive.class);
                startActivity(i);

            }
        });
    }



    public void addFragment()
    {
            editFrag=new EditFragment();
            manager=getFragmentManager();
            FragmentTransaction t=manager.beginTransaction();
            t.add(R.id.mainActivityFragmentContainer,editFrag);
            t.commit();
    }

    public static void changeUsername()
    {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void someMethos(String msg)
    {
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRespond(String name) {

        SharedPreferences sharedPreferences=getSharedPreferences("Personal_detail", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("SAVED_USERNAME",name);
        editor.commit();
        PersonalInfo.THIS_IS_MY_NAME=name;
        this.username.setText(name);
    }

    private void loadDetails()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("Personal_detail", Context.MODE_PRIVATE);
        PersonalInfo.THIS_IS_MY_NAME=sharedPreferences.getString("SAVED_USERNAME","DEFAULT_n");
    }


    private void setupWidowAnimations()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Fade fade=new Fade();
            fade.setDuration(1000);

            Slide slide=new Slide();
            slide.setDuration(1000);
            getWindow().setReturnTransition(slide);
            getWindow().setExitTransition(slide);

        }
    }

}


interface Communicater{

    public void onRespond(String name);
}
package com.example.nash.modernui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.Random;


public class MainActivity extends ActionBarActivity{
    private Random rand = new Random();
    private int MAX_PARTS_ALLOWED = 3;
    private int TOTAL_WEIGHT_PER_PART = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Main layout from the xml file
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        makeMondrian(mainLayout, 0);
    }

    private void makeMondrian(LinearLayout parentLayout, int partCount) {
        if(partCount >= MAX_PARTS_ALLOWED) return;
        int partitionWeight = rand.nextInt(TOTAL_WEIGHT_PER_PART - 1) + 1; // Avoids the generation of 0
        LinearLayout firstPartition = createLinearLayout(partitionWeight);
        LinearLayout secondPartition = createLinearLayout(TOTAL_WEIGHT_PER_PART - partitionWeight);

        parentLayout.addView(firstPartition);
        parentLayout.addView(secondPartition);


        makeMondrian(secondPartition, partCount + 1);
        makeMondrian(firstPartition, partCount + 1);
    }


    /*
    * Function : createLayout(weight of the layout)
    * Usage    : LinearLayout sampleLayout = createLayout(2);
    * ------------------------------------------------------------------S-
    * Creates a LinearLayout View with the specified weight in parameter
    */
    private LinearLayout createLinearLayout(int layoutWeight) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setBackgroundColor(randomColor());
        linearLayout.setOrientation(rand.nextBoolean() ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        linearLayoutParams.weight = (float) layoutWeight;

        linearLayout.setLayoutParams(linearLayoutParams);

        return linearLayout;
    }

    /*
    * Function : randomColor()
    * Usage    : viewVariable.setColor(randomColor);
    * -----------------------------------------------
    */
    private int randomColor() {
        int randColor = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        return randColor;
    }

    final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {
            recreate();
        }
    });

    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    /*
    * Function : makeToast(message to display in toast)
    * --------------------------------------------------
    * Creates and shows toast with the message that is passed in as parameter
    */
    public void makeToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
    // Methods for Options Menu

    /*
    * Function : onCreateOptionsMenu()
    * ----------------------------------
    * Intrinsic function of android platform
    * to inflate the the menu with layout tap of the action overflow icon
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
    * Function : onOptionsItemSelected(item that is selected)
    * -------------------------------------------------------
    * Intrinsic method provided by android API to handle click
    * on the menu items.
    */
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
}

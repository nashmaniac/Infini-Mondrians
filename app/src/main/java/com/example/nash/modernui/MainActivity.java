package com.example.nash.modernui;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.Random;


public class MainActivity extends ActionBarActivity{
    private Random rand = new Random();
    // Number of parts/squares visible in the Mondrian
    private int MAX_PARTS = 6;
    private int TOTAL_WEIGHT_PER_PART = 4;
    private int BORDER_WIDTH = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Main layout from the xml file
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        makeMondrian(mainLayout, 0);
    }

    private void makeMondrian(LinearLayout parentLayout, int partCount) {
        if(partCount >= MAX_PARTS) return;
        int partitionWeight = rand.nextInt(TOTAL_WEIGHT_PER_PART - 1) + 1; // Avoids the generation of 0
        LinearLayout firstPartition = createPartition(partitionWeight, parentLayout);
        LinearLayout border = getPartitionBorder(parentLayout.getOrientation());
        LinearLayout secondPartition = createPartition(TOTAL_WEIGHT_PER_PART - partitionWeight, parentLayout);

        parentLayout.addView(firstPartition);
        parentLayout.addView(border);
        parentLayout.addView(secondPartition);


        makeMondrian(secondPartition, partCount + 2);
        makeMondrian(firstPartition, partCount + 2);
    }


    /*
    * Function : getPartitionBorder(orientation of the parent container)
    * -------------------------------------------------------------------
    * Returns a thin LinearLayout black in color to act as a border between
    * two Partitions of the parent.
    */
    private LinearLayout getPartitionBorder(int containerOrientation) {
        LinearLayout border = new LinearLayout(this);
        border.setBackgroundColor(Color.BLACK);

        LinearLayout.LayoutParams borderParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        if(containerOrientation == LinearLayout.VERTICAL) {
            borderParams.height = BORDER_WIDTH;
        }else{
            borderParams .width = BORDER_WIDTH;
        }

        border.setLayoutParams(borderParams);
        return border;
    }

    /*
    * Function : createPartition(weight of the layout)
    * Usage    : LinearLayout sampleLayout = createLayout(2);
    * ------------------------------------------------------------------
    * Creates a LinearLayout View with the specified weight in parameter
    */
    private LinearLayout createPartition(float layoutWeight, LinearLayout parentLayout) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setBackgroundColor(randomColor());
        linearLayout.setOrientation(rand.nextBoolean() ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayoutParams.weight = layoutWeight;

        if(parentLayout.getOrientation() == LinearLayout.VERTICAL) {
            linearLayoutParams.height = 0; // Will be automatically set by provided weight
        }else {
            linearLayoutParams.width = 0; // Will be automatically set by provided weight
        }

        linearLayout.setLayoutParams(linearLayoutParams);

        return linearLayout;
    }

    /*
    * Function : randomColor()
    * Usage    : viewVariable.setColor(randomColor);
    * -----------------------------------------------
    */
    private int randomColor() {
        boolean shouldBeWhite = generateBiasedBoolean(0.65f);
        if(shouldBeWhite) {
            return (Color.argb(255, 255, 255,255));
        } else {
            return (Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
        }
    }

    /*
     * Function : generateBiasedBoolean(bias Value)
     * -----------------------------------------------------------------------------------
     * Favors true more if passed in "float bias"/bias Value is greater than 0.5 and favors false more if passed
     * in "float bias"/bias Value is less than 0.5
     */
    private boolean generateBiasedBoolean(float bias){
        return (rand.nextFloat() < bias);
    }

    /*
    * Function : gestureDetector() and onTouchEvent
    * ----------------------------------------------
    * Detects tap and hold on the display
    */
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

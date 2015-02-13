package com.example.nash.modernui;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import com.example.nash.modernui.AlertDialogFragment;


public class MainActivity extends ActionBarActivity{
    private LinearLayout mMainLayout;
    private SeekBar mSeekBar;
    private Mondrian mMondrian;
    private DialogFragment mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Main layout from the xml file
        mMainLayout = (LinearLayout) findViewById(R.id.main_layout);

        // Create a new Mondrian object
        mMondrian = new Mondrian(mMainLayout, this);
        mMondrian.draw();

        // The seekBar for changing the color of the colored squares of the Mondrian
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                mMondrian.recolorMondrian();
            }

            // Implementation of the methods are not required for this app
            // But there existence is required for the code to function properly
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
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
    * Mostly used for debugging purposes.
    */
    public void makeToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    /*
    * Function : onCreateOptionsMenu()
    * ---------------------------------------------------------
    * Intrinsic function of android platform to inflate the the
    * menu with layout on tap of the action overflow icon.
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
        if (id == R.id.action_more_info) {
            mAlertDialog = AlertDialogFragment.newInstance();
            mAlertDialog.show(getFragmentManager(), "MoreInfo");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

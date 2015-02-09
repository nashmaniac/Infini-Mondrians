package com.example.nash.modernui;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Random;

/*
* Class : Mondrian.java
* ------------------------------------------------------------------------------
* Mondrian is named after the famous Dutch painter Piet Mondrian who is one of the pioneers of Modern art.
* He evolved a non-representational form of art that consisted of white background, upon which was painted
* a grid of vertical and horizontal black lines and three primary colors.
* These form of paintings are also referred to as Mondrians.
* This java class serves the purpose of drawing such Mondrians using recursion.
*/

public class Mondrian {
    private Random rand = new Random();
    // Total number of Parts created inside the mondrian = 2^(MAX_PARTS_POWER)
    // e.g if MAX_PARTS_POWER = 4, total parts inside Mondrian = 2^(4) = 16 parts
    private static final int MAX_PARTS_POWER = 4;
    private static final int TOTAL_WEIGHT_PER_PART = 4;
    private static final int BORDER_WIDTH = 5;
    // The closer the probability of part being white approaches 1, the more white parts are added to the Mondrian
    private static final float PROBABILITY_PART_WHITE = 0.4f;

    private Context parentActivityContext;
    private LinearLayout main_layout;

    private ArrayList<LinearLayout> coloredParts = new ArrayList<>();

    public Mondrian(LinearLayout mainLayout,Context parentContext) {
        main_layout = mainLayout;
        parentActivityContext = parentContext;
    }



    /*
    * Function : draw()
    * ------------------------------------------------------------------------------
    * Draws a Mondrian over the mainLayout that is passed into the constructor class.
    */

    public void draw() {
        makeMondrian(main_layout, 0);
    }

    /*
    * Function : recolorMondrian();
    * -------------------------------
    * Grabs the rectangles in the Mondrian that are colored and resets their color
    * again to a random color.
    */
    public void recolorMondrian() {
        for (int i = 0; i < coloredParts.size(); i++) {
            coloredParts.get(i).setBackgroundColor(getRandomColor(false));
        }
    }




//###################################################### PRIVATE METHODS

    /*
    * Function : getPartitionBorder(orientation of the parent container)
    * -------------------------------------------------------------------
    * Returns a thin LinearLayout black in color to act as a border between
    * two Partitions of the parent.
    */
    private void makeMondrian(LinearLayout parentLayout, int partCount) {
        if (partCount >= MAX_PARTS_POWER) return;
        int partitionWeight = rand.nextInt(TOTAL_WEIGHT_PER_PART - 1) + 1; // Avoids generation of 0
        LinearLayout firstPartition = createPartition(partitionWeight, parentLayout);
        LinearLayout border = getPartitionBorder(parentLayout.getOrientation());
        LinearLayout secondPartition = createPartition(TOTAL_WEIGHT_PER_PART - partitionWeight, parentLayout);

        parentLayout.addView(firstPartition);
        parentLayout.addView(border);
        parentLayout.addView(secondPartition);

        makeMondrian(secondPartition, partCount + 1);
        makeMondrian(firstPartition, partCount + 1);
    }

    private LinearLayout getPartitionBorder(int containerOrientation) {
        LinearLayout border = new LinearLayout(parentActivityContext);
        border.setBackgroundColor(Color.BLACK);

        LinearLayout.LayoutParams borderParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        if (containerOrientation == LinearLayout.VERTICAL) {
            borderParams.height = BORDER_WIDTH;
        } else {
            borderParams.width = BORDER_WIDTH;
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
        LinearLayout linearLayout = new LinearLayout(parentActivityContext);
        int randomColor = getRandomColor(true);
        linearLayout.setBackgroundColor(randomColor);
        linearLayout.setOrientation(rand.nextBoolean() ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayoutParams.weight = layoutWeight;

        if (parentLayout.getOrientation() == LinearLayout.VERTICAL) {
            linearLayoutParams.height = 0; // Will be automatically set by provided weight
        } else {
            linearLayoutParams.width = 0; // Will be automatically set by provided weight
        }

        linearLayout.setLayoutParams(linearLayoutParams);

        if (randomColor != Color.WHITE) coloredParts.add(linearLayout);

        return linearLayout;
    }

    /*
    * Function : getRandomColor(should favor white more or not)
    * ------------------------------------------------------------------------------
    * Returns a random color. If passed in true for favorWhite it return white
    * more of the times than it returns any other color. This is for the Mondrian
    * drawing purposes since in Mondrian most of the squares/rectangles are white in color
    */
    private int getRandomColor(boolean favorWhite) {
        boolean shouldBeWhite = generateBiasedBoolean(PROBABILITY_PART_WHITE);
        if (shouldBeWhite && favorWhite) {
            return (Color.argb(255, 255, 255, 255));
        } else {
            return (Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
        }
    }

    /*
    * Function : generateBiasedBoolean(probability of returning true out of one)
    * --------------------------------------------------------------------------
    * Generates random boolean variable either true or false. The closer the value of
    * "float bias" approaches to 1 the more generation of true is favored.
    */
    private boolean generateBiasedBoolean(float bias) {
        return (rand.nextFloat() < bias);
    }

}
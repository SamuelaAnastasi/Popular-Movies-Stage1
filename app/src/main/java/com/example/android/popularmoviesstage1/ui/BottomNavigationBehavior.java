package com.example.android.popularmoviesstage1.ui;

/**
 * Created by Samuela Anastasi on 18/03/2018.
 *
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

// Class handles BottomNavigationView Behavior to move on and off screen
// in response to the scrolling in the RecyclerView
public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {

    private static final String LOG_TAG = BottomNavigationBehavior.class.getSimpleName();

    public BottomNavigationBehavior() {
        super();
    }

    public BottomNavigationBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, BottomNavigationView child, View dependency) {
        return dependency instanceof FrameLayout;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull BottomNavigationView child,
                                       @NonNull View directTargetChild,
                                       @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                  @NonNull BottomNavigationView child,
                                  @NonNull View target, int dx, int dy,
                                  @NonNull int[] consumed, int type) {
        if (dy < 0) {
            child.animate().translationY(0);
        } else if (dy > 0) {
            child.animate().translationY(child.getHeight());
        }
    }
}
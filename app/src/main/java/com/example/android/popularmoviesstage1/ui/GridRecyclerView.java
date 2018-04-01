package com.example.android.popularmoviesstage1.ui;

/**
 * Created by Samuela Anastasi on 18/03/2018.
 *
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 */

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.GridLayoutAnimationController;

// Custom RecyclerView with custom AnimationParameters to be handled by GridLayoutManager
// As by instructions in this tutorial:
// https://proandroiddev.com/enter-animation-using-recyclerview-and-layoutanimation-part-2-grids-688829b1d29b
public class GridRecyclerView extends RecyclerView {

    private static final String LOG_TAG = GridRecyclerView.class.getSimpleName();

    public GridRecyclerView(Context context) { super(context); }

    /** @see View#View(Context, AttributeSet) */
    public GridRecyclerView(Context context, AttributeSet attrs) { super(context, attrs); }

    /** @see View#View(Context, AttributeSet, int) */
    public GridRecyclerView(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); }

    @Override
    protected void attachLayoutAnimationParameters(View child, ViewGroup.LayoutParams params,
                                                   int index, int count) {
        final LayoutManager layoutManager = getLayoutManager();
        if (getAdapter() != null && layoutManager instanceof GridLayoutManager){

            GridLayoutAnimationController.AnimationParameters animationParams =
                    (GridLayoutAnimationController.AnimationParameters) params.layoutAnimationParameters;

            if (animationParams == null) {
                animationParams = new GridLayoutAnimationController.AnimationParameters();
                params.layoutAnimationParameters = animationParams;
            }

            final int columns = ((GridLayoutManager) layoutManager).getSpanCount();

            animationParams.count = count;
            animationParams.index = index;
            animationParams.columnsCount = columns;
            animationParams.rowsCount = count / columns;

            final int invertedIndex = count - 1 - index;
            animationParams.column = columns - 1 - (invertedIndex % columns);
            animationParams.row = animationParams.rowsCount - 1 - invertedIndex / columns;

        } else {
            super.attachLayoutAnimationParameters(child, params, index, count);
        }
    }
}
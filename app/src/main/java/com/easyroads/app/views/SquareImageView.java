package com.easyroads.app.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.easyroads.app.R;

public class SquareImageView extends ImageView {

    private int squareWith = 0;

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @SuppressLint("NewApi")
    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs) {
        if (!isInEditMode()) {
            TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.SquareImageView);
            squareWith = styledAttrs.getInt(R.styleable.SquareImageView_squareWith, 0);
            styledAttrs.recycle();
        }
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (!isInEditMode()) {
            TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.SquareImageView, defStyleAttr, defStyleRes);
            squareWith = styledAttrs.getInt(R.styleable.SquareImageView_squareWith, 0);
            styledAttrs.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (2 == squareWith) {
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        } else if (1 == squareWith) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}

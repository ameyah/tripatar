package com.easyroads.app.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.easyroads.app.R;
import com.easyroads.app.utils.TypefaceLoader;

public class TypefacedTextView extends TextView {
	private Context context;

	public TypefacedTextView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public TypefacedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context, attrs);
	}

	public TypefacedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init(context, attrs);
	}

	private void init() {
		if (!isInEditMode()) {
			Typeface typeface = TypefaceLoader.get(context, "fonts/OpenSans-Regular.ttf");
			setTypeface(typeface);
		}
	}

	private void init(Context context, AttributeSet attrs) {
		if (!isInEditMode()) {
			TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TypefacedTextView);
			int index = styledAttrs.getInt(R.styleable.TypefacedTextView_customTypeface, 1);

			String font = "OpenSans-Regular.ttf";
			switch (index) {
				case 2:
					font = "OpenSans-Light.ttf";
					break;
				case 3:
					font = "OpenSans-Semibold.ttf";
					break;
				case 4:
					font = "OpenSans-Bold.ttf";
					break;
				case 5:
					font = "Museo-Regular.ttf";
					break;
				case 6:
					font = "Museo-Light.ttf";
					break;
				case 7:
					font = "Museo-Bold.ttf";
					break;
			}
			styledAttrs.recycle();
			Typeface typeface = TypefaceLoader.get(context, "fonts/" + font);
			setTypeface(typeface);
		}
	}
}

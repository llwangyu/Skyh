package android.com.skyh.tool;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class InScrollGridView extends GridView {
	private boolean haveScrollbar = true;

	public InScrollGridView(Context context) {
		super(context);
	}

	public InScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public InScrollGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setHaveScrollbar(boolean haveScrollbar) {
		this.haveScrollbar = haveScrollbar;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (haveScrollbar == true) {
			int expandSpec = MeasureSpec.makeMeasureSpec(
					Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
}

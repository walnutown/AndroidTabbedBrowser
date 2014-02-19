package com.taohu.android.browser;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class TabIndicatorBar extends LinearLayout implements View.OnFocusChangeListener {

	private OnTabSelectionChangedListener mOnTabSelectionChangedListener;
	private int mSelectedTab = 0;

	public TabIndicatorBar(Context context) {
		super(context);
		initTabIndicatorBar();
	}

	public TabIndicatorBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initTabIndicatorBar();
	}

	public void initTabIndicatorBar() {
		setOrientation(HORIZONTAL);
		setOnFocusChangeListener(this);
	}

	public View getChildTabIndicatorViewAt(int index) {
		return getChildAt(index);
	}

	public int getTabIndicatorCount() {
		return getChildCount();
	}

	public void setCurrentTabIndicator(int index) {
		if (index < 0 || index >= getTabIndicatorCount())
			return;
		getChildTabIndicatorViewAt(mSelectedTab).setSelected(false);
		mSelectedTab = index;
		getChildTabIndicatorViewAt(mSelectedTab).setSelected(true);
	}

	public void setOnTabSelectionChangedListener(OnTabSelectionChangedListener l) {
		mOnTabSelectionChangedListener = l;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (v == this && hasFocus) {
			getChildTabIndicatorViewAt(mSelectedTab).requestFocus();
		}
		if (hasFocus) {
			int i = 0;
			int numTabs = getTabIndicatorCount();
			while (i < numTabs) {
				if (getChildTabIndicatorViewAt(i) == v) {
					setCurrentTabIndicator(i);
					mOnTabSelectionChangedListener.onTabSelectionChanged(i, false);
					break;
				}
				i++;
			}
		}
	}

	@Override
	public void addView(View child) {
		// Ensure you can navigate to the tab with the keyboard, and you can
		// touch it
		child.setFocusable(true);
		child.setClickable(true);
		super.addView(child);
		child.setOnClickListener(new OnTabIndicatorClickListener(getTabIndicatorCount() - 1));
		child.setOnFocusChangeListener(this);
	}

	// registered with each tab indicator so we can notify tab manager
	private class OnTabIndicatorClickListener implements OnClickListener {
		private final int tabIndex;

		private OnTabIndicatorClickListener(int tabIndex) {
			this.tabIndex = tabIndex;
		}

		@Override
		public void onClick(View v) {
			mOnTabSelectionChangedListener.onTabSelectionChanged(tabIndex, true);
		}

	}

	/**
	 * @param clicked
	 *            whether the selection changed due to a touch/click or due to
	 *            focus entering the tab through navigation. Pass true if it was
	 *            due to a press/click and false otherwise.
	 */
	static interface OnTabSelectionChangedListener {
		void onTabSelectionChanged(int tabIndex, boolean clicked);
	}

}

package com.taohu.android.browser;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class TabManager extends FrameLayout implements ViewTreeObserver.OnTouchModeChangeListener {

	private TabIndicatorBar mTabIndicatorBar;
	private FrameLayout mTabContent;
	protected int mCurrentTabIndex;
	private View mCurrentView;
	private Tab mCurrentTab;
	private FragmentManager mFragmentManager;
	private FragmentActivity mFragmentActivity;
	private int mTabContentContainerId;
	// private OnTabSelectionChangedListener mOnTabSelectionChangedListener;
	private OnKeyListener mTabKeyListener;
	private ArrayList<Tab> mTabs;
	private static long tagCount = 0;

	/**
	 * Type of tab content, web or home
	 */
	public enum TabType {
		HOME, WEB
	}

	public class Tab {
		private long tag;
		private String label;
		private Fragment fragment;
		private TabType type;

		Tab(long tagCount, String label, TabType type) {
			this.tag = tagCount;
			this.label = label;
			this.type = type;
		}

		public Class<?> getFragmentClass() {
			return this.getType() == TabType.HOME ? HomeTabFragment.class : WebTabFragment.class;
		}

		public TabType getType() {
			return type;
		}

		public long getTag() {
			return tag;
		}

		public String getLabel() {
			return label;
		}

		public void setFragment(Fragment fragment) {
			this.fragment = fragment;
		}

		public Fragment getFragment() {
			return fragment;
		}

		public String toString() {
			return String.valueOf(tag);
		}
	}

	public TabManager(Context context) {
		super(context);
		initTabManager();
	}

	public TabManager(Context context, FragmentActivity fragmentActivity) {
		this(context);
		mFragmentActivity = fragmentActivity;
		mFragmentManager = mFragmentActivity.getSupportFragmentManager();
		setup();
	}

	public void initTabManager() {
		setFocusableInTouchMode(true);
		setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
		mCurrentTabIndex = -1;
		mCurrentView = null;
		mTabContentContainerId = R.id.tabcontent;
		mTabs = new ArrayList<Tab>();
	}

	public void setup() {
		mTabIndicatorBar = (TabIndicatorBar) mFragmentActivity.findViewById(R.id.tabindicatorbar);
		// KeyListener to attach to all tabs. Detects non-navigation keys
		// and relays them to the tab content.
		mTabKeyListener = new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_CENTER:
				case KeyEvent.KEYCODE_DPAD_LEFT:
				case KeyEvent.KEYCODE_DPAD_RIGHT:
				case KeyEvent.KEYCODE_DPAD_UP:
				case KeyEvent.KEYCODE_DPAD_DOWN:
				case KeyEvent.KEYCODE_ENTER:
					return false;

				}
				mTabContent.requestFocus(View.FOCUS_FORWARD);
				return mTabContent.dispatchKeyEvent(event);
			}
		};

		mTabIndicatorBar.setOnTabSelectionChangedListener(new TabIndicatorBar.OnTabSelectionChangedListener() {
			public void onTabSelectionChanged(int tabIndex, boolean clicked) {
				setCurrentTab(tabIndex);
				if (clicked) {
					mTabContent.requestFocus(View.FOCUS_FORWARD);
				}
			}
		});
		mTabContent = (FrameLayout) mFragmentActivity.findViewById(mTabContentContainerId);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		final ViewTreeObserver treeObserver = getViewTreeObserver();
		if (treeObserver != null) {
			treeObserver.addOnTouchModeChangeListener(this);
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		final ViewTreeObserver treeObserver = getViewTreeObserver();
		if (treeObserver != null) {
			treeObserver.removeOnTouchModeChangeListener(this);
		}
	}

	public void onTouchModeChanged(boolean isInTouchMode) {
		if (!isInTouchMode) {
			// leaving touch mode.. if nothing has focus, let's give it to
			// the indicator of the current tab
			if (!mCurrentView.hasFocus() || mCurrentView.isFocused()) {
				mTabIndicatorBar.getChildTabIndicatorViewAt(mCurrentTabIndex).requestFocus();
			}
		}
	}

	public void addTab(String label, TabType type) {
		Tab tab = new Tab(tagCount++, label, type);
		addTabIndicator(tab);
		addTabFragment(tab);
		mTabs.add(tab);
//		if (mCurrentTabIndex == -1)
//			setCurrentTab(0);
	}

	public void addTabIndicator(Tab tab) {
		TabIndicatorView tabindicator = new TabIndicatorView(getContext(), tab.getTag());
		tabindicator.setLabel(tab.getLabel());
		mTabIndicatorBar.addView(tabindicator);
	}

	public void addTabFragment(Tab tab) {
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		mFragmentManager.executePendingTransactions();
		tab.setFragment(createTabFragment(tab));
		ft.add(mTabContentContainerId, tab.getFragment(), String.valueOf(tab.getTag()));
		mCurrentTab = tab;
		ft.commit();
		mFragmentManager.executePendingTransactions();
	}

	public Fragment createTabFragment(Tab tab) {
		Fragment fragment = Fragment.instantiate(mFragmentActivity, tab.getFragmentClass().getName());
		Bundle bundle = new Bundle();
		bundle.putString(AppSettings.TAB_TITLE, tab.getLabel());
		fragment.setArguments(bundle);
		return fragment;
	}

	public void setCurrentTab(int index) {
		if (index < 0 || index >= mTabs.size() || index == mCurrentTabIndex)
			return;

	}

}

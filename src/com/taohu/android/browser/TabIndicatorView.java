package com.taohu.android.browser;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabIndicatorView extends LinearLayout{

	private long tag;
	private ImageView icon;
	private TextView label;
	private ImageView imgClose;
	private TabCloseOnClickListener mTabCloseOnClickListener = null;
	
	public interface TabCloseOnClickListener{
		public void OnTabCloseClicked(long tag);
	}
	
	public TabIndicatorView(Context cxt) {
		super(cxt);
		createView(cxt);
	}
	
	public TabIndicatorView(Context cxt, long tag) {
		this(cxt);
		this.tag = tag;
	}
	
	public void createView(Context cxt){
		LayoutInflater.from(cxt).inflate(R.layout.view_tabindicator, this, true);
		icon = (ImageView) findViewById(R.id.tab_icon);
	    label = (TextView) findViewById(R.id.tab_label);
		imgClose = (ImageView) findViewById(R.id.tab_close);
		imgClose.setOnClickListener(new ImgCloseOnClickListener());
	}
	
	public void setIcon(int icon){
		this.icon.setImageResource(icon);
	}
	
	public void setLabel(String label){
		this.label.setText(label);
	}
	
	public long getTabTag(){
		return tag;
	}
	
	public void setTabCloseOnClickListener(TabCloseOnClickListener l){
		mTabCloseOnClickListener = l;
	}
	
	public class ImgCloseOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			if (mTabCloseOnClickListener != null){
				mTabCloseOnClickListener.OnTabCloseClicked(tag);
			}
		}
	}

}

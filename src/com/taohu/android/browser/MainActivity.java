package com.taohu.android.browser;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

import com.taohu.android.browser.TabManager.TabType;

public class MainActivity extends FragmentActivity {
	
	private TabManager tabManager;
	private ImageButton btnAdd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnAdd = (ImageButton) findViewById(R.id.icon_add);
		btnAdd.setOnClickListener(new AddAppButtonOnclickListener());
		tabManager = new TabManager(getApplicationContext(),this);
		//tabManager.setup();
		tabManager.addTab("Bitium", TabType.HOME);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public class AddAppButtonOnclickListener implements View.OnClickListener {
		@Override
		public void onClick(View arg0) {
			tabManager.addTab("http://www.google.com", TabType.WEB);
		}
	}

}

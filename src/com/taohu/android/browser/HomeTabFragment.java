package com.taohu.android.browser;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomeTabFragment extends Fragment {

	private Button btnLogout;
	private Context cxt;
	private TextView txtName;
	private boolean isCreated = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d("Tab", "onCreate() " + this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = null;
		if (!isCreated) {

			isCreated = true;
			cxt = this.getActivity();
			v = inflater.inflate(R.layout.webhometab_fragment, container, false);
			btnLogout = (Button) v.findViewById(R.id.btn_logout);
			// btnLogout.setOnClickListener(new LogoutButtonOnclickListener());

			txtName = (TextView) v.findViewById(R.id.txt_name);
			// get the user profile
			// FileManager fm = new FileManager(cxt);
			// JSONObject json = null;
			// json = new JSONObject(fm.read(AppSettings.USER_PROFILE));
			// txtName.setText(json.toString());
			txtName.setText(this.toString());
			Log.d("Tab", "onCreateView() " + this);

		}
		return v;
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d("Tab", "onPause() " + this);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.d("Tab", "onDetach() " + this);
	}

	// public class LogoutButtonOnclickListener implements View.OnClickListener
	// {
	// @Override
	// public void onClick(View arg0) {
	// FileManager fm = new FileManager(cxt);
	// fm.delete(AppSettings.USER_PROFILE);
	// fm.delete(AppSettings.USER_SUBSCRIPTION);
	// Intent actLogin = new Intent(cxt, LoginActivity.class);
	// startActivity(actLogin);
	// }
	// }
}

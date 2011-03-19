package org.smc.silpa.dict;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class SearchActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Spinner spinner = (Spinner) findViewById(R.id.lang);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.lang_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		final EditText keyword = (EditText) findViewById(R.id.entry);
		final ImageView result = (ImageView) findViewById(R.id.result);

		final Spinner lang = (Spinner) findViewById(R.id.lang);
		Button okButton = (Button) findViewById(R.id.ok);

		okButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final ProgressDialog progress = ProgressDialog.show(
						SearchActivity.this, "", "Loading...Please wait", true);
				String word = keyword.getText().toString();
				String dict = "en-ml";
				if (lang.getSelectedItem().toString()
						.equalsIgnoreCase("malayalam")) {
					dict = "en-ml";
				} else if (lang.getSelectedItem().toString()
						.equalsIgnoreCase("hindi")) {
					dict = "en-hi";

				} else if (lang.getSelectedItem().toString()
						.equalsIgnoreCase("telugu")) {
					dict = "en-te";

				} else if (lang.getSelectedItem().toString()
						.equalsIgnoreCase("tamil")) {
					dict = "en-ta";

				} else if (lang.getSelectedItem().toString()
						.equalsIgnoreCase("kannada")) {
					dict = "en-kn";

				} else if (lang.getSelectedItem().toString()
						.equalsIgnoreCase("oriya")) {
					dict = "en-or";

				} else if (lang.getSelectedItem().toString()
						.equalsIgnoreCase("bengali")) {
					dict = "en-bn";

				} else if (lang.getSelectedItem().toString()
						.equalsIgnoreCase("gujarati")) {
					dict = "en-gu";

				}

				result.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						progress.dismiss();
						keyword.requestFocus();

					}
				});

				ImageDownloader imageDownloader = new ImageDownloader();

				imageDownloader.download(word, dict, result);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(keyword.getWindowToken(), 0);
			}
		});

		Button clearButton = (Button) findViewById(R.id.clear);
		clearButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				keyword.setText("");				
			}
		});

		keyword.dispatchWindowFocusChanged(true);
		//keyword.dispatchTouchEvent(null);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(keyword, InputMethodManager.SHOW_IMPLICIT);

	}

	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Context context = SearchActivity.this;		
		Dialog aboutScreen  = new Dialog(context);
		aboutScreen.setTitle("Silpa Dictionary for Indic languages");
		aboutScreen.setContentView(R.layout.about);
		
		return aboutScreen;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.about:
			showDialog(0);
			return true;
		case R.id.exit:
			this.finish();
			return true;
		}
		
		return true;
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

}
package ncl.team22.languagetutor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ncl.team22.languagetutor.data.DatabaseAdapter;
import ncl.team22.languagetutor.profile.Profile;
import ncl.team22.languagetutor.profile.ProfileOptions;

public class LanguagetutorActivity extends Activity
{
	private static final int		LOGOUT					= Menu.FIRST;
	private static final int		PROFILE_LOGIN_REQUEST	= 1;
	private static final String		TAG						= "LT-Main";

	public static final String		PREFS_NAME				= "ltprefs";
	public static final String		ACTIVE_PROFILE_ID		= "activeprofile";

	public static DatabaseAdapter	sDBa;
	public static Profile			currentProfile			= null;
	public TextView					headerText;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		headerText = (TextView) findViewById(R.id.main_header_text);

		sDBa = new DatabaseAdapter(this);

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		int activeProfileID = settings.getInt(ACTIVE_PROFILE_ID, -1);

		if (activeProfileID == -1)
		{
			startActivityForResult(new Intent(LanguagetutorActivity.this, ncl.team22.languagetutor.profile.Login.class), LanguagetutorActivity.PROFILE_LOGIN_REQUEST);
			// startActivity(new Intent(LanguagetutorActivity.this,
			// ncl.team22.languagetutor.profile.Login.class));
		}
		else
		{
			currentProfile = Profile.load(activeProfileID);
			headerText.setText("Hola " + currentProfile.display_name + "!");
		}

		final Button learnButton = (Button) findViewById(R.id.learnbutton);
		learnButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(LanguagetutorActivity.this, TutorialActivity.class);
				startActivity(i);
			}
		});

		final Button optionsButton = (Button) findViewById(R.id.optionsbutton);
		optionsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(LanguagetutorActivity.this, ProfileOptions.class);
				startActivity(i);
			}
		});

		// Add click listener to test button
		final Button testButton = (Button) findViewById(R.id.testsbutton);
		testButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(LanguagetutorActivity.this, ncl.team22.languagetutor.test.LevelSelect.class);
				startActivity(i);
			}
		});

		// Add click listener to stats button
		final Button statsButton = (Button) findViewById(R.id.statsbutton);
		statsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(LanguagetutorActivity.this, ncl.team22.languagetutor.profile.Statistics.class);
				startActivity(i);
			}
		});
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		sDBa.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		menu.add(0, LOGOUT, 0, R.string.logout);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch (item.getItemId())
		{
		// Should probably be split in to a method under Profile
			case LOGOUT :

				LanguagetutorActivity.currentProfile = null;

				SharedPreferences settings = getSharedPreferences(LanguagetutorActivity.PREFS_NAME, MODE_PRIVATE);
				Editor e = settings.edit();
				e.putInt(LanguagetutorActivity.ACTIVE_PROFILE_ID, -1);
				e.apply();

				startActivity(new Intent(LanguagetutorActivity.this, ncl.team22.languagetutor.profile.Login.class));
				finish();

				break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode)
		{
			case LanguagetutorActivity.PROFILE_LOGIN_REQUEST :
			{
				if (resultCode == Activity.RESULT_OK)
				{
					Log.d(TAG, "Login activity returned OK");
					headerText.setText("Hola " + currentProfile.display_name
							+ "!");

				}
				break;
			}
		}
	}
}

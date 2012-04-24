package ncl.team22.languagetutor.profile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ncl.team22.languagetutor.LanguagetutorActivity;
import ncl.team22.languagetutor.R;

/**
 * Activity for logging into profiles
 * 
 * @author Misha
 * 
 */
public class Login extends Activity
{

	private static final int	REQUEST_CREATENEW	= 1;
	private static final int	CREATE_NEW_PROFILE	= Menu.FIRST;
	private static final int	RECOVER_PASS		= Menu.FIRST + 1;
	private EditText			userName;
	private EditText			password;
	private String				userString;
	private String				passString;
	private String				errorMessage		= "";

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		userName = (EditText) findViewById(R.id.input_username);
		password = (EditText) findViewById(R.id.input_password);

		Button loginButton = (Button) findViewById(R.id.login_button);
		loginButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view)
			{
				userString = userName.getText().toString().trim();
				passString = password.getText().toString().trim();

				Profile validatedProfile = validate(userString, passString);

				if (validatedProfile != null)
				{
					LanguagetutorActivity.currentProfile = validatedProfile;

					SharedPreferences settings = getSharedPreferences(LanguagetutorActivity.PREFS_NAME, MODE_PRIVATE);
					Editor e = settings.edit();
					e.putInt(LanguagetutorActivity.ACTIVE_PROFILE_ID, LanguagetutorActivity.currentProfile.profileID);
					e.apply();

					setResult(Activity.RESULT_OK);
					finish();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		menu.add(0, CREATE_NEW_PROFILE, 0, R.string.create_profile);
		menu.add(0, RECOVER_PASS, 0, R.string.recover_pass);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch (item.getItemId())
		{
			case CREATE_NEW_PROFILE :
				Intent createProfile_intent = new Intent(Login.this, CreateProfile.class);
				startActivityForResult(createProfile_intent, REQUEST_CREATENEW);
				break;
			case RECOVER_PASS :
				Intent recover_intent = new Intent(Login.this, ProfileSelection.class);
				startActivity(recover_intent);
				break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	/**
	 * Checks that the user input a valid username and valid matching password
	 * 
	 * First checks that the username the user is trying to log in to exists
	 * then checks that the provided the correct password. If one of these
	 * checks return false the user is informed about what they input that was
	 * wrong.
	 * 
	 * @param user
	 *            The profile that the user wants to log in to.
	 * @param pass
	 *            The password for that the user entered.
	 * @return The profile that the user wants to log in to, otherwise null.
	 */
	private Profile validate(String user, String pass)
	{
		if (Profile.checkName(userString))
		{
			Profile result = Profile.authenticate(user, pass);

			if (result != null)
			{
				return result;
			}
			else
			{
				errorMessage = "Incorrect password, please try again.";
			}
		}
		else
		{
			errorMessage = "No such user, please try again.";
		}

		Toast toast = Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
		return null;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
			case REQUEST_CREATENEW :
				if (resultCode == Activity.RESULT_OK)
				{
					int newProfileID = data.getIntExtra(CreateProfile.NEW_PROFILEID, -1);
					if (newProfileID == -1)
					{
						throw new RuntimeException("CreateProfile didn't return a valid profile ID");
					}

					Profile newProfile = Profile.load(newProfileID);
					LanguagetutorActivity.currentProfile = newProfile;

					SharedPreferences settings = getSharedPreferences(LanguagetutorActivity.PREFS_NAME, MODE_PRIVATE);
					Editor e = settings.edit();
					e.putInt(LanguagetutorActivity.ACTIVE_PROFILE_ID, LanguagetutorActivity.currentProfile.profileID);
					e.apply();
					setResult(Activity.RESULT_OK);
					finish();
				}
		}
	}
}

package ncl.team22.languagetutor.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ncl.team22.languagetutor.LanguagetutorActivity;
import ncl.team22.languagetutor.R;

public class Login extends Activity
{

	private static final int	CREATE_NEW_PROFILE	= Menu.FIRST;
	private static final int	RECOVER_PASS		= Menu.FIRST + 1;
	private EditText			userName;
	private EditText			password;
	private String				userString;
	private String				passString;
	private String				errorMessage		= "";

	private AlertDialog.Builder	builder;

	@Override
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
					startActivity(new Intent(ncl.team22.languagetutor.profile.Login.this, LanguagetutorActivity.class));
					finish();
				}
			}
		});

		builder = new AlertDialog.Builder(this);
		builder.setMessage(errorMessage).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id)
			{
				dialog.cancel();
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
				startActivity(createProfile_intent);
				finish();
				break;
			case RECOVER_PASS :
				Intent recover_intent = new Intent(Login.this, ProfileSelection.class);
				startActivity(recover_intent);
				break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

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

		builder.setMessage(errorMessage);
		builder.show();
		errorMessage = "";
		return null;
	}

}

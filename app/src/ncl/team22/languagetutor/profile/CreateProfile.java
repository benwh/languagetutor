package ncl.team22.languagetutor.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ncl.team22.languagetutor.LanguagetutorActivity;
import ncl.team22.languagetutor.R;

public class CreateProfile extends Activity
{
	private EditText			userName;
	private String				userString;
	private EditText			password;
	private String				passString;
	private EditText			confirmPass;
	private String				cPassString;
	private EditText			secretQuestion;
	private String				sQString;
	private EditText			secretAnswer;
	private String				sAString;
	private String				errorMessage	= "";

	private AlertDialog.Builder	builder;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_profile);

		userName = (EditText) findViewById(R.id.create_username);
		password = (EditText) findViewById(R.id.create_password);
		confirmPass = (EditText) findViewById(R.id.confirm_password);
		secretQuestion = (EditText) findViewById(R.id.create_sq);
		secretAnswer = (EditText) findViewById(R.id.create_sa);
		Button createProfileButton = (Button) findViewById(R.id.create_profile_button);

		builder = new AlertDialog.Builder(this);
		builder.setMessage(errorMessage).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id)
			{
				dialog.cancel();
			}
		});

		createProfileButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view)
			{

				userString = userName.getText().toString().trim();
				passString = password.getText().toString().trim();
				cPassString = confirmPass.getText().toString().trim();
				sQString = secretQuestion.getText().toString().trim();
				sAString = secretAnswer.getText().toString().trim();

				if (validate(userString, passString, cPassString, sQString, sAString))
				{
					LanguagetutorActivity.currentProfile = Profile.create(userString, passString, sQString, sAString);

					SharedPreferences settings = getSharedPreferences(LanguagetutorActivity.PREFS_NAME, MODE_PRIVATE);
					Editor e = settings.edit();
					e.putInt(LanguagetutorActivity.ACTIVE_PROFILE_ID, LanguagetutorActivity.currentProfile.profileID);
					e.apply();
					startActivity(new Intent(ncl.team22.languagetutor.profile.CreateProfile.this, LanguagetutorActivity.class));
					finish();

				}
			}
		});
	}

	private boolean validate(String vUsename, String vPass, String vCPass,
			String vSQ, String vSA)
	{
		boolean valid = false;
		if (!Profile.checkName(vUsename))
		{
			if (vCPass.equals(vPass) && (vPass.length() > 0))
			{
				valid = true;

			}
			else
			{
				errorMessage = "Your passwords didn't match, please try again.";
			}

		}
		else
		{
			errorMessage = "Username already taken please try another.";
		}

		if (!valid)
		{
			builder.setMessage(errorMessage);
			builder.show();
			errorMessage = "";
		}
		return valid;
	}
}

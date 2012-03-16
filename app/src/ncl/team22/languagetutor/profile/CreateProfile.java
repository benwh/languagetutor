package ncl.team22.languagetutor.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ncl.team22.languagetutor.LanguagetutorActivity;
import ncl.team22.languagetutor.R;

public class CreateProfile extends Activity
{
	private EditText	userName;
	private String		userString;
	private EditText	password;
	private String		passString;
	private EditText	confirmPass;
	private String		cPassString;
	private EditText	secretQuestion;
	private String		sQString;
	private EditText	secretAnswer;
	private String		sAString;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_profile);

		userName = (EditText) findViewById(R.id.input_username);
		password = (EditText) findViewById(R.id.input_password);
		confirmPass = (EditText) findViewById(R.id.input_password);
		secretQuestion = (EditText) findViewById(R.id.input_password);
		secretAnswer = (EditText) findViewById(R.id.input_password);
		Button createProfileButton = (Button) findViewById(R.id.create_profile_button);

		createProfileButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view)
			{
				userString = userName.getText().toString();
				passString = password.getText().toString();
				cPassString = confirmPass.getText().toString();
				sQString = secretQuestion.getText().toString();
				sAString = secretAnswer.getText().toString();
				if (validate(userString, passString, cPassString, sQString, sAString))
				{
					// SET THE PROFILE TO THE RELEVENT USER TO BE ADDED
					Intent i = new Intent(CreateProfile.this, LanguagetutorActivity.class);
					startActivity(i);
				}
			}
		});
	}

	private boolean validate(String vUsename, String vPass, String vCPass,
			String vSQ, String vSA)
	{
		boolean valid = false;
		// TODO: Write validation method
		if ((!Profile.checkName(vUsename, this)))
		{
			if (vCPass.equals(vPass) && (vPass.length() > 0))
			{
				valid = true;
				// PASSWORDS DID NOT MATCH OR TO SHORT
			}
			// USERNAME ALREADY IN USE ERROR MESSAGE
		}
		return valid;
	}
}

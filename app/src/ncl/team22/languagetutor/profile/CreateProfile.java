package ncl.team22.languagetutor.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ncl.team22.languagetutor.R;

/**
 * Activity for creating new profiles
 * 
 * @author Misha
 */
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

	public static final String	NEW_PROFILEID	= "ncl.team22.languagetutor.profile.CreateProfile.NEW_PROFILEID";

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
					int newProfileID = Profile.create(userString, passString, sQString, sAString);

					Intent resultData = new Intent();
					resultData.putExtra(NEW_PROFILEID, newProfileID);
					setResult(Activity.RESULT_OK, resultData);
					finish();
				}
			}
		});
	}

	/**
	 * Checks that it is OK to create a profile with the user's input
	 * 
	 * First check that the requested username is not already taken, then checks
	 * that the user knows what they set their password to by comparing vPass to
	 * vCPass and that the password uses the correct number of characters.
	 * 
	 * @param vUsename
	 *            What the user wanted their username to be.
	 * @param vPass
	 *            What the user wanted their password to be.
	 * @param vCPass
	 *            The users attempt to reenter what they just put in the
	 *            password field.
	 * @param vSQ
	 *            What the user wanted their secrete question to be.
	 * @param vSA
	 *            What the user wanted their secrete answer to be.
	 * @return True if the user input allows for the creation of a new password,
	 *         other wise false.
	 */
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
			Toast toast = Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
		}
		return valid;
	}
}

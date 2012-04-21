package ncl.team22.languagetutor.profile;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ncl.team22.languagetutor.LanguagetutorActivity;
import ncl.team22.languagetutor.R;
import ncl.team22.languagetutor.data.DatabaseAdapter;

public class ReplacePassword extends Activity
{

	private String	theQuestion;
	private String	theAnswer;
	private String	inputAnswer;
	private String	inPass;
	private String	inConfirmPass;
	private String	mssg;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.replacepassword);

		// get question and answer from profile class **
		theQuestion = LanguagetutorActivity.currentProfile.secret_q;
		theAnswer = LanguagetutorActivity.currentProfile.secret_a;

		// display question
		final TextView textViewToChange = (TextView) findViewById(R.id.textQuestion1);
		textViewToChange.setText(theQuestion);

		// when the "ok" button is clicked
		final Button doneButton = (Button) findViewById(R.id.donebutton);
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{

				// get input from editText fields
				inputAnswer = ((EditText) findViewById(R.id.donebutton)).getText().toString();
				inPass = ((EditText) findViewById(R.id.intextpass)).getText().toString();
				inConfirmPass = ((EditText) findViewById(R.id.intextconfirmpass1)).getText().toString();

				// should the new password be set? + set appropriate message
				if (theAnswer.equals(inputAnswer))
				{
					if (inPass.equals(inConfirmPass))
					{
						// set password
						SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

						ContentValues cv = new ContentValues();
						cv.put("password_hash", Profile.hashPassword(inPass));

						sDb.update(DatabaseAdapter.TABLE_PROFILE, cv, "profileID=?", new String[]
						{Integer.toString(LanguagetutorActivity.currentProfile.profileID)});

						LanguagetutorActivity.currentProfile = Profile.load(LanguagetutorActivity.currentProfile.profileID);
						SharedPreferences settings = getSharedPreferences(LanguagetutorActivity.PREFS_NAME, MODE_PRIVATE);
						Editor e = settings.edit();
						e.putInt(LanguagetutorActivity.ACTIVE_PROFILE_ID, LanguagetutorActivity.currentProfile.profileID);
						e.apply();

						mssg = "New Password saved";
						sDb.close();
						finish();
					}
					else
					{
						mssg = "passwords are not the same";
					}
				}
				else
				{
					mssg = "incorrect answer";
				}

				// show message to user
				Toast toast = Toast.makeText(getApplicationContext(), mssg, Toast.LENGTH_SHORT);
				toast.show();

				// close activity
			}
		});
	}
}

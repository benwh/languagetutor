package ncl.team22.languagetutor.profile;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ncl.team22.languagetutor.LanguagetutorActivity;
import ncl.team22.languagetutor.R;
import ncl.team22.languagetutor.data.DatabaseAdapter;

/**
 * Activity for Resetting a profiles password
 * 
 * @author Kyran, Misha
 */
public class ReplacePasswordActivity extends Activity
{

	private String	theQuestion;
	private String	theAnswer;
	private String	inputAnswer;
	private String	inPass;
	private String	inConfirmPass;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.replacepassword);

		// get question and answer from profile class **
		theQuestion = LanguagetutorActivity.currentProfile.secret_q;
		theAnswer = LanguagetutorActivity.currentProfile.secret_a;

		// display question
		final TextView textViewToChange = (TextView) findViewById(R.id.textQuestion1);
		textViewToChange.setText("Answer your secret question:\n   "
				+ theQuestion);

		// when the "ok" button is clicked
		final Button doneButton = (Button) findViewById(R.id.donebutton);
		doneButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v)
			{

				// get input from editText fields
				inputAnswer = ((EditText) findViewById(R.id.intextanswer)).getText().toString();
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

						Toast.makeText(getApplicationContext(), "New Password saved", Toast.LENGTH_SHORT).show();

						sDb.close();

						if (getIntent().getExtras() != null)
						{
							LanguagetutorActivity.currentProfile = null;

							settings = getSharedPreferences(LanguagetutorActivity.PREFS_NAME, MODE_PRIVATE);
							e = settings.edit();
							e.putInt(LanguagetutorActivity.ACTIVE_PROFILE_ID, -1);
							e.apply();
						}
						finish();
					}
					else
					{
						Toast toast = Toast.makeText(getApplicationContext(), "Your passwords didn't match, please try again.", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
						toast.show();
					}
				}
				else
				{
					Toast toast = Toast.makeText(getApplicationContext(), "Incorrect answer, please try again.", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					toast.show();
				}
			}
		});
	}

	@Override
	public void onBackPressed()
	{
		if (getIntent().getExtras() != null)
		{
			LanguagetutorActivity.currentProfile = null;

			SharedPreferences settings = getSharedPreferences(LanguagetutorActivity.PREFS_NAME, MODE_PRIVATE);
			Editor e = settings.edit();
			e.putInt(LanguagetutorActivity.ACTIVE_PROFILE_ID, -1);
			e.apply();
		}
		super.onBackPressed();
	}
}

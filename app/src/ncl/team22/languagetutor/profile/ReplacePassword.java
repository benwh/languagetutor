package ncl.team22.languagetutor.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ncl.team22.languagetutor.R;

public class ReplacePassword extends Activity
{

	private String	theQuestion;
	private String	inputAnswer;
	private String	inPass;
	private String	inConfirmPass;
	private String	mssg;
	private int		proID;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.replacepassword);

		// get profileID??
		proID = 1;

		// get question and answer from db **
		theQuestion = Profile.getSQ(proID);

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
				inputAnswer = ((EditText) findViewById(R.id.intextanswer)).getText().toString();
				inPass = ((EditText) findViewById(R.id.intextpass)).getText().toString();
				inConfirmPass = ((EditText) findViewById(R.id.intextconfirmpass1)).getText().toString();

				// should the new password be set? + set appropriate message
				if (Profile.authenticateSA(proID, inputAnswer))
				{
					if (inPass.equals(inConfirmPass))
					{
						// set password
						mssg = "New Password saved";
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

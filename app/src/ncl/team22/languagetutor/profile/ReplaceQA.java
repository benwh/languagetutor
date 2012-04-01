package ncl.team22.languagetutor.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ncl.team22.languagetutor.LanguagetutorActivity;
import ncl.team22.languagetutor.R;

public class ReplaceQA extends Activity
{
	private String	username;
	private String	inPass;
	private String	inQ;
	private String	inA;
	private String	mssg;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.replaceqa);

		username = LanguagetutorActivity.currentProfile.display_name;

		// when the "ok" button is clicked
		final Button doneButton = (Button) findViewById(R.id.button1);
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{

				// get input from editText fields
				inPass = ((EditText) findViewById(R.id.editText1)).getText().toString();
				inQ = ((EditText) findViewById(R.id.editText2)).getText().toString();
				inA = ((EditText) findViewById(R.id.editText3)).getText().toString();

				// should the new password be set? + set appropriate message
				if (Profile.authenticate(username, inPass) != null)
				{
					if (!inQ.isEmpty() && !inA.isEmpty())
					{
						// set q + a
						mssg = "New Question + Answer saved";
					}
					else
					{
						mssg = "Question and Answer Required";
					}
				}
				else
				{
					mssg = "incorrect Password";
				}

				// show message to user
				Toast toast = Toast.makeText(getApplicationContext(), mssg, Toast.LENGTH_SHORT);
				toast.show();

				// close activity

			}
		});
	}
}

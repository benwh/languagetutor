package ncl.team22.languagetutor.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ncl.team22.languagetutor.R;

public class ReplacePassword extends Activity
{

	private String	theQuestion;
	private String	inputAnswer;
	private String	inPass;
	private String	inConfirmPass;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.replacepassword);

		// get question from db **
		theQuestion = "This is the Question";

		// display question
		final TextView textViewToChange = (TextView) findViewById(R.id.textQuestion1);
		textViewToChange.setText(theQuestion);

		// get input, and store in temp variables
		inputAnswer = (String) findViewById(R.id.intextanswer).toString();
		inPass = (String) findViewById(R.id.intextpass).toString();
		inConfirmPass = (String) findViewById(R.id.intextconfirmpass1).toString();

		final Button doneButton = (Button) findViewById(R.id.donebutton);
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				if (theQuestion == inputAnswer)
				{
					if (inPass == inConfirmPass)
					{
						// set password
					}
					else
					{
						// message "passwords are not the same"
					}
				}
				else
				{
					// message "incorrect answer"
				}
			}
		});
	}
}

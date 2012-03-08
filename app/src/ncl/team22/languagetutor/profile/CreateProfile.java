package ncl.team22.languagetutor.profile;

import ncl.team22.languagetutor.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateProfile extends Activity {

	private EditText userName;
	private EditText password;
	private EditText confirmPass;
	private EditText secretQuestion;
	private EditText secretAnswer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_profile_layout);

		userName = (EditText) findViewById(R.id.input_username);
		password = (EditText) findViewById(R.id.input_password);
		confirmPass = (EditText) findViewById(R.id.input_password);
		secretQuestion = (EditText) findViewById(R.id.input_password);
		secretAnswer = (EditText) findViewById(R.id.input_password);
		Button createProfileButton = (Button) findViewById(R.id.create_profile_button);

		createProfileButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (validate(userName, password, confirmPass, secretQuestion,
						secretAnswer)) {
					// START THE LANGUAGETUTORACTIVITY TO BE DONE
				}
			}
		});
	}

	private boolean validate(EditText vUsename, EditText vPass,
			EditText vCPass, EditText vSQ, EditText vSA) {
		boolean valid = false;

		// TODO: Write validation method
		return valid;
	}
}

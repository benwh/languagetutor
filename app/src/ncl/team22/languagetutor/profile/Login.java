package ncl.team22.languagetutor.profile;

import ncl.team22.languagetutor.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {

	private EditText userName;
	private EditText password;
	private String userString;
	private String passString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);

		userName = (EditText) findViewById(R.id.input_username);
		password = (EditText) findViewById(R.id.input_password);
		Button loginButton = (Button) findViewById(R.id.login_button);

		loginButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				userString = userName.getText().toString();
				passString = password.getText().toString();
				if (validate(userString, passString)) {
					// START THE LANGUAGETUTORACTIVITY TO BE DONE
				}
			}
		});
	}

	private boolean validate(String user, String pass) {
		boolean valid = false;
		ProfileManager pManager = new ProfileManager(this);

		if (pManager.checkName(userString)) {
			if (pManager.getPass(userString).equals(passString)) {
				valid = true;
			} else {
				// PASSWORD INVALID MESSAGE TO BE ADDED
			}

		} else {
			// USERNAME INVALID MESSAGE TO BE ADDED
		}

		return valid;
	}
}

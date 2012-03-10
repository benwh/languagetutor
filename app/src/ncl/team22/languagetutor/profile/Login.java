package ncl.team22.languagetutor.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ncl.team22.languagetutor.LanguagetutorActivity;
import ncl.team22.languagetutor.R;

public class Login extends Activity
{

	private static final int	CREATE_NEW_PROFILE	= Menu.FIRST;
	private static final int	RECOVER_PASS		= Menu.FIRST + 1;
	private EditText			userName;
	private EditText			password;
	private String				userString;
	private String				passString;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		userName = (EditText) findViewById(R.id.input_username);
		password = (EditText) findViewById(R.id.input_password);
		Button loginButton = (Button) findViewById(R.id.login_button);

		loginButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view)
			{
				userString = userName.getText().toString();
				passString = password.getText().toString();
				if (validate(userString, passString))
				{
					// SET THE PROFILE TO THE RELEVENT USER TO BE ADDED
					Intent i = new Intent(Login.this,
							LanguagetutorActivity.class);
					startActivity(i);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		menu.add(0, CREATE_NEW_PROFILE, 0, R.string.create_profile);
		menu.add(0, RECOVER_PASS, 0, R.string.recover_pass);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch (item.getItemId())
		{
			case CREATE_NEW_PROFILE :
				Intent createProfile_intent = new Intent(Login.this,
						CreateProfile.class);
				startActivity(createProfile_intent);
				break;
			case RECOVER_PASS :
				Intent recover_intent = new Intent(Login.this,
						ProfileSelection.class);
				startActivity(recover_intent);
				break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private boolean validate(String user, String pass)
	{
		boolean valid = false;
		Profile pManager = new Profile(this);

		if (pManager.checkName(userString))
		{
			if (pManager.getPass(userString).equals(passString))
			{
				valid = true;
			}
			else
			{
				// PASSWORD INVALID MESSAGE TO BE ADDED
			}

		}
		else
		{
			// USERNAME INVALID MESSAGE TO BE ADDED
		}

		return valid;
	}
}

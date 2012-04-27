package ncl.team22.languagetutor.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ncl.team22.languagetutor.R;

/**
 * Activity for selecting which options to change.
 * 
 * @author Kyran
 */
public class ProfileOptions extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_options);

		final Button passwordButton = (Button) findViewById(R.id.passwordbutton);
		passwordButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(ProfileOptions.this, ReplacePassword.class);
				startActivity(i);
			}
		});

		final Button QAButton = (Button) findViewById(R.id.qabutton);
		QAButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(ProfileOptions.this, ReplaceQA.class);
				startActivity(i);
			}
		});
	}
}

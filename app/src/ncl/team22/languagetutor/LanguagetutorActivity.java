package ncl.team22.languagetutor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ncl.team22.languagetutor.profile.ProfileOptions;
import ncl.team22.languagetutor.profile.ProfileSelection;

public class LanguagetutorActivity extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final Button profileButton = (Button) findViewById(R.id.profilebutton);
		profileButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(LanguagetutorActivity.this,
						ProfileSelection.class);
				startActivity(i);
			}
		});

		// repeated code(thinking of using an array/2Darray of some sort...)
		final Button optionsButton = (Button) findViewById(R.id.optionsbutton);
		optionsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(LanguagetutorActivity.this,
						ProfileOptions.class);
				startActivity(i);
			}
		});
	}
}

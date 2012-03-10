package ncl.team22.languagetutor.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ncl.team22.languagetutor.R;

/**
 * Activity where test options/settings will be decided to launch new test
 * 
 * @author james
 */
public class Setup extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_setup);

		// Launch level one test options
		final Button one = (Button) findViewById(R.id.level1);
		one.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(Setup.this, Topics1.class);
				startActivity(i);
			}
		});
	}
}

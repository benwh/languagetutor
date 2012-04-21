package ncl.team22.languagetutor.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ncl.team22.languagetutor.R;
import ncl.team22.languagetutor.profile.Profile;

/**
 * Activity where test options/settings will be decided to launch new test
 * 
 * @author james
 */
public class LevelSelect extends Activity
{
	private static int					level		= 0;
	private int							userLevel	= Profile.getUserLevel();
	private static AlertDialog.Builder	builder;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_level_select);

		// Launch level one test options
		final Button one = (Button) findViewById(R.id.level1);
		one.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				level = 1;
				Intent i = new Intent(LevelSelect.this, Topics.class);
				startActivity(i);
			}
		});

		// Launch level two test options
		final Button two = (Button) findViewById(R.id.level2);
		two.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				level = 2;
				if (userLevel < level)
				{
					showErrorMessage();
				}
				else
				{
					Intent i = new Intent(LevelSelect.this, Topics.class);
					startActivity(i);
				}
			}
		});

		// Launch level three test options
		final Button three = (Button) findViewById(R.id.level3);
		three.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				level = 3;
				if (userLevel < level)
				{
					showErrorMessage();
				}
				else
				{
					Intent i = new Intent(LevelSelect.this, Topics.class);
					startActivity(i);
				}
			}
		});
	}

	public static int getLevel()
	{
		return level;
	}

	private static void showErrorMessage()
	{
		builder.setMessage("This level has not been unlocked yet.\nTry passing all the tests at the previous level first.");
		builder.show();
	}
}

package ncl.team22.languagetutor.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ncl.team22.languagetutor.R;
import ncl.team22.languagetutor.profile.Profile;

/**
 * Activity where the difficulty level for a test will be decided, test levels
 * below the current user level will not be enabled
 * 
 * @author james
 */
public class LevelSelect extends Activity
{
	private static int	level		= 0;
	private int			userLevel	= Profile.getUserLevel();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_level_select);

		// Launch level one topic select
		final Button one = (Button) findViewById(R.id.level1);
		one.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				level = 1;
				Intent i = new Intent(LevelSelect.this, Topics.class);
				i.addFlags(1);
				startActivity(i);
			}
		});

		// Launch level two topic select
		final Button two = (Button) findViewById(R.id.level2);
		two.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				level = 2;
				Intent i = new Intent(LevelSelect.this, Topics.class);
				i.addFlags(1);
				startActivity(i);
			}
		});

		// Launch level three topic select
		final Button three = (Button) findViewById(R.id.level3);
		three.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				level = 3;
				Intent i = new Intent(LevelSelect.this, Topics.class);
				i.addFlags(1);
				startActivity(i);
			}
		});

		// Logic for disabling buttons if current user level is not high enough
		if (userLevel < 2)
		{
			two.setEnabled(false);
		}
		if (userLevel < 3)
		{
			three.setEnabled(false);
		}
	}

	/**
	 * Get the test level that has been selected through LevelSelect class
	 * 
	 * @return the test level
	 */
	public static int getLevel()
	{
		return level;
	}
}

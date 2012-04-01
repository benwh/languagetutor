package ncl.team22.languagetutor.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import ncl.team22.languagetutor.R;

/**
 * Activity to allow user to view their final test score and review progress,
 * methods to submit test and reset counter values will be called here
 * 
 * @author james
 * 
 */
public class TestReview extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_review);

		// Set score text
		final TextView score = (TextView) findViewById(R.id.test_score);
		score.setText("(" + TestResult.getScore() + "/"
				+ TestResult.getMaximum() + ") - "
				+ TestResult.calculateTestPercentage() + "%");

		// Set rank text (will mirror whatever the stats page would say for a
		// respective score)

		// Submit the score
		TestResult.submitScore(0, 0, 0);
		// Reset values
		TestResult.reset();
	}
}

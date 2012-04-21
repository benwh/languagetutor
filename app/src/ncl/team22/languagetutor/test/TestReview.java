package ncl.team22.languagetutor.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
	public static final String	TAG	= "LT-TestReview";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_review);

		double result = TestResult.calculateTestPercentage();

		// Set score text
		final TextView score = (TextView) findViewById(R.id.test_score);
		score.setText("(" + TestResult.getScore() + "/"
				+ TestResult.getMaximum() + ") | " + result + "%");

		// Set rank text (will mirror whatever the stats page would say for a
		// respective score)
		final TextView rank = (TextView) findViewById(R.id.rank_awarded);
		if (result < 20)
		{
			rank.setText("Foreigner");
		}
		else if (result < 40)
		{
			rank.setText("Tourist");
		}
		else if (result < 60)
		{
			rank.setText("Resident");
		}
		else if (result < 80)
		{
			rank.setText("Bilingual");
		}
		else if (result < 100)
		{
			rank.setText("Fluent");
		}
		else
		{
			rank.setText("Native");
		}

		// Find the buttons
		Button review = (Button) findViewById(R.id.review_test);

		review.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(TestReview.this, TestReviewGrid.class);
				startActivity(i);
			}
		});
	}

	@Override
	public void onBackPressed()
	{
		Test.backPressed = true;
		finish();
		Log.d(TAG, "Reverting back to topic select...");
	}
}

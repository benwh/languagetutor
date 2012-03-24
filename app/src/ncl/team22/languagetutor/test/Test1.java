package ncl.team22.languagetutor.test;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ncl.team22.languagetutor.R;
import ncl.team22.languagetutor.data.LanguageEntity;
import ncl.team22.languagetutor.data.Topic;

/**
 * Activity for first style of test, multiple choice questions
 * 
 * @author james
 */
public class Test1 extends Activity
{
	Topic						selectedTopic;
	ArrayList<LanguageEntity>	entities;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (TestResult.getCounter() == 0)
		{
			setContentView(R.layout.test1);

			// Pull the selected topic out of the intent from the previous
			// activity
			Intent i = getIntent();
			selectedTopic = (Topic) i.getSerializableExtra(Topics1.intentTopic);

			entities = selectedTopic.getEntities(this);

			getMultiQuestion();
		}
		else if (TestResult.getCounter() < 4)
		{
			setContentView(R.layout.test1);

			getMultiQuestion();
		}
		else if (TestResult.getCounter() < 7)
		{
			setContentView(R.layout.test2);

			getWrittenQuestion();
		}
		else if (TestResult.getCounter() == 7)
		{
			setContentView(R.layout.test2);

			getWrittenQuestion();

			// Submit the scores
			TestResult.submitScore(this);
			// Reset the values
			TestResult.reset();
		}
	}

	public void getMultiQuestion()
	{
		// At the moment this just shows which topic you selected to go to the
		// test, but eventually it will formulate some kind of question -
		// however, this value could be used to show the kind of test you are
		// doing as well
		final TextView question = (TextView) findViewById(R.id.multi_question);
		question.setText("You picked a test on " + selectedTopic.toString());

		// TODO: Set up button activity and comparisons, random activity,
		// proceed action etc.
	}

	public void getWrittenQuestion()
	{
		// TODO: Set up word to translate and comparison algorithm, random
		// activity, proceed action etc.
	}

	// TODO: HANDLE BACK BUTTON ACTIVITY, PAUSE ETC. TO RESET TestResult DATA TO
	// DEFAULT VALUES
}

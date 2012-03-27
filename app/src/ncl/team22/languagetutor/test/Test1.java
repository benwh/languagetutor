package ncl.team22.languagetutor.test;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
	ArrayList<LanguageEntity>	entitiesList;
	// Prevent asked questions being asked again but enable them to be fed as
	// incorrect options
	ArrayList<LanguageEntity>	entitiesRemoved;
	LanguageEntity				current;

	Random						randGen	= new Random();
	int							switchVal;
	int							entityID;

	public static final String	TAG		= "LT-Test1";

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
			Log.d(TAG, "Selected topic was: " + selectedTopic);

			entitiesList = selectedTopic.getEntities();

			// TRACE
			for (int itr = 0; itr < entitiesList.size(); itr++)
			{
				Log.d(TAG, entitiesList.get(itr).toString());
			}

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
			TestResult.submitScore(0, 0, 0);
			// Reset the values
			TestResult.reset();
		}
	}

	public void getMultiQuestion()
	{
		// Generate a number 0 or 1 for input to switch statement
		switchVal = randGen.nextInt(2);

		switch (switchVal)
		{
			case 0 :
			// Spanish question
			{
				Log.d(TAG, "Case is: " + switchVal); // TRACE

				final TextView question = (TextView) findViewById(R.id.multi_question);
				// Generate random id
				Log.d(TAG, "Entity list size is: " + entitiesList.size()); // TRACE
				entityID = randGen.nextInt(entitiesList.size());
				Log.d(TAG, "Entity ID is: " + entityID); // TRACE
				Log.d(TAG, "Entity information: "
						+ entitiesList.get(entityID).toString()); // TRACE
				// Set question text
				question.setText("" + entitiesList.get(entityID).toDestString());
				// Add entity to removed list
				// entitiesRemoved.add(current);
				// Remove entity from initial list
				entitiesList.remove(entityID);
				break;
			}
			case 1 :
			// English question
			{
				Log.d(TAG, "Case is: " + switchVal); // TRACE

				final TextView question = (TextView) findViewById(R.id.multi_question);
				// Generate random id
				Log.d(TAG, "Entity list size is: " + entitiesList.size()); // TRACE
				entityID = randGen.nextInt(entitiesList.size());
				Log.d(TAG, "Entity ID is: " + entityID); // TRACE
				Log.d(TAG, "Entity information: "
						+ entitiesList.get(entityID).toString()); // TRACE
				// Set question text
				question.setText(""
						+ entitiesList.get(entityID).toSourceString());
				// Add entity to removed list
				// entitiesRemoved.add(current);
				// Remove entity from initial list
				entitiesList.remove(entityID);
				break;
			}
		}

		// At the moment this just shows which topic you selected to go to the
		// test, but eventually it will formulate some kind of question -
		// however, this value could be used to show the kind of test you are
		// doing as well
		// final TextView question = (TextView)
		// findViewById(R.id.multi_question);
		// question.setText("You picked a test on " + selectedTopic.toString());

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

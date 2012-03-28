package ncl.team22.languagetutor.test;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import ncl.team22.languagetutor.R;
import ncl.team22.languagetutor.data.LanguageEntity;
import ncl.team22.languagetutor.data.Topic;

/**
 * Activity for handling test generation
 * 
 * @author james
 */
public class Test1 extends Activity
{
	Topic						selectedTopic;

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

			// Occupy list with topics
			TestResult.setEntitiesListByTopic(selectedTopic);

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

		Log.d(TAG, "Case is: " + switchVal); // TRACE
		Log.d(TAG, "Entity list size is: "
				+ TestResult.getEntitiesList().size()); // TRACE

		// SET THE QUESTION

		final TextView question = (TextView) findViewById(R.id.multi_question);
		// Generate random id
		entityID = randGen.nextInt(TestResult.getEntitiesList().size());
		Log.d(TAG, "Entity ID is: " + entityID); // TRACE
		current = TestResult.getEntitiesList().get(entityID);
		Log.d(TAG, "Entity information: " + current.toString()); // TRACE
		switch (switchVal)
		{
			case 0 :
			// Spanish question
			{
				// Set question text
				question.setText("" + current.toDestString());
				break;
			}
			case 1 :
			// English question
			{
				// Set question text
				question.setText("" + current.toSourceString());
				break;
			}
		}
		// Add entity to removed list
		TestResult.addToRemovedList(current);
		// Remove entity from initial list
		TestResult.removeFromEntitiesList(entityID);

		Log.d(TAG, "Entities list:"); // TRACE
		for (int itr = 0; itr < TestResult.getEntitiesList().size(); itr++)
		{
			Log.d(TAG, TestResult.getEntitiesList().get(itr).toString());
		}
		Log.d(TAG, "Removed list:"); // TRACE
		for (int itr1 = 0; itr1 < TestResult.getRemovedList().size(); itr1++)
		{
			Log.d(TAG, TestResult.getRemovedList().get(itr1).toString());
		}

		int rand1 = 0;
		int rand2 = 0;
		int rand3 = 0;
		int rand4 = 0;

		if (!(TestResult.getRemovedList().isEmpty()))
		{
			rand1 = randGen.nextInt(TestResult.getRemovedList().size());
			rand2 = randGen.nextInt(TestResult.getRemovedList().size());
			rand3 = randGen.nextInt(TestResult.getRemovedList().size());
			rand4 = randGen.nextInt(TestResult.getRemovedList().size());
		}

		// SET THE ANSWERS

		// Determine the correct answer position
		final int correctPosition = randGen.nextInt(4);
		Log.d(TAG, "Correct position is: " + correctPosition);

		// Find the buttons
		final Button option0 = (Button) findViewById(R.id.option0);
		final Button option1 = (Button) findViewById(R.id.option1);
		final Button option2 = (Button) findViewById(R.id.option2);
		final Button option3 = (Button) findViewById(R.id.option3);

		// Button zero logic
		switch (switchVal)
		{
			case 0 :
				if (0 == correctPosition)
				{
					option0.setText("" + current.toSourceString());
					break;
				}
				else if (!(TestResult.getEntitiesList().isEmpty()))
				{
					option0.setText(""
							+ TestResult.getEntitiesList().get(rand1).toSourceString());
					break;
				}
				else
				{
					// Check to avoid duplicate answers
					while (rand1 == TestResult.getRemovedList().indexOf(current))
					{
						rand1 = randGen.nextInt(TestResult.getRemovedList().size());
					}
					option0.setText(""
							+ TestResult.getRemovedList().get(rand1).toSourceString());
					break;
				}
			case 1 :
				if (0 == correctPosition)
				{
					option0.setText("" + current.toDestString());
					break;
				}
				else if (!(TestResult.getEntitiesList().isEmpty()))
				{
					option0.setText(""
							+ TestResult.getEntitiesList().get(rand1).toDestString());
					break;
				}
				else
				{
					// Check to avoid duplicate answers
					while (rand1 == TestResult.getRemovedList().indexOf(current))
					{
						rand1 = randGen.nextInt(TestResult.getRemovedList().size());
					}
					option0.setText(""
							+ TestResult.getRemovedList().get(rand1).toDestString());
					break;
				}
		}

		// Button one logic
		switch (switchVal)
		{
			case 0 :
				if (1 == correctPosition)
				{
					option1.setText("" + current.toSourceString());
					break;
				}
				else if (TestResult.getRemovedList().size() > 1)
				{
					// This button shall prefer entities which have been removed
					while (rand2 == TestResult.getRemovedList().indexOf(current)
							|| rand2 == rand1)
					{
						rand2 = randGen.nextInt(TestResult.getRemovedList().size());
					}
					option1.setText(""
							+ TestResult.getRemovedList().get(rand2).toSourceString());
					break;
				}
				else
				{
					while (rand2 == rand1)
					{
						rand2 = randGen.nextInt(TestResult.getEntitiesList().size());
					}
					option1.setText(""
							+ TestResult.getEntitiesList().get(rand2).toSourceString());
					break;
				}
			case 1 :
				if (1 == correctPosition)
				{
					option1.setText("" + current.toDestString());
					break;
				}
				else if (TestResult.getRemovedList().size() > 1)
				{
					// This button shall prefer entities which have been removed
					while (rand2 == TestResult.getRemovedList().indexOf(current)
							|| rand2 == rand1)
					{
						rand2 = randGen.nextInt(TestResult.getRemovedList().size());
					}
					option1.setText(""
							+ TestResult.getRemovedList().get(rand2).toDestString());
					break;
				}
				else
				{
					while (rand2 == rand1)
					{
						rand2 = randGen.nextInt(TestResult.getEntitiesList().size());
					}
					option1.setText(""
							+ TestResult.getEntitiesList().get(rand2).toDestString());
					break;
				}
		}

		// Button two logic
		switch (switchVal)
		{
			case 0 :
				if (2 == correctPosition)
				{
					option2.setText("" + current.toSourceString());
					break;
				}
				else if (!(TestResult.getEntitiesList().isEmpty()))
				{
					while (rand3 == rand2 || rand3 == rand1)
					{
						rand3 = randGen.nextInt(TestResult.getEntitiesList().size());
					}
					option2.setText(""
							+ TestResult.getEntitiesList().get(rand3).toSourceString());
					break;
				}
				else
				{
					// Check to avoid duplicate answers
					while (rand3 == TestResult.getRemovedList().indexOf(current)
							|| rand3 == rand2 || rand3 == rand1)
					{
						rand3 = randGen.nextInt(TestResult.getRemovedList().size());
					}
					option2.setText(""
							+ TestResult.getRemovedList().get(rand3).toSourceString());
					break;
				}
			case 1 :
				if (2 == correctPosition)
				{
					option2.setText("" + current.toDestString());
					break;
				}
				else if (!(TestResult.getEntitiesList().isEmpty()))
				{
					while (rand3 == rand2 || rand3 == rand1)
					{
						rand3 = randGen.nextInt(TestResult.getEntitiesList().size());
					}
					option2.setText(""
							+ TestResult.getEntitiesList().get(rand3).toDestString());
					break;
				}
				else
				{
					// Check to avoid duplicate answers
					while (rand3 == TestResult.getRemovedList().indexOf(current)
							|| rand3 == rand2 || rand3 == rand1)
					{
						rand3 = randGen.nextInt(TestResult.getRemovedList().size());
					}
					option2.setText(""
							+ TestResult.getRemovedList().get(rand3).toDestString());
					break;
				}
		}

		// Button three logic
		switch (switchVal)
		{
			case 0 :
				if (3 == correctPosition)
				{
					option3.setText("" + current.toSourceString());
					break;
				}
				else if (TestResult.getRemovedList().size() > 3)
				{
					// This button shall prefer entities which have been removed
					while (rand4 == TestResult.getRemovedList().indexOf(current)
							|| rand4 == rand3
							|| rand4 == rand2
							|| rand4 == rand1)
					{
						rand4 = randGen.nextInt(TestResult.getRemovedList().size());
					}
					option3.setText(""
							+ TestResult.getRemovedList().get(rand4).toSourceString());
					break;
				}
				else
				{
					while (rand4 == rand3 || rand4 == rand2 || rand4 == rand1)
					{
						rand4 = randGen.nextInt(TestResult.getEntitiesList().size());
					}
					option3.setText(""
							+ TestResult.getEntitiesList().get(rand4).toSourceString());
					break;
				}
			case 1 :
				if (3 == correctPosition)
				{
					option3.setText("" + current.toDestString());
					break;
				}
				else if (TestResult.getRemovedList().size() > 3)
				{
					// This button shall prefer entities which have been removed
					while (rand4 == TestResult.getRemovedList().indexOf(current)
							|| rand4 == rand3
							|| rand4 == rand2
							|| rand4 == rand1)
					{
						rand4 = randGen.nextInt(TestResult.getRemovedList().size());
					}
					option3.setText(""
							+ TestResult.getRemovedList().get(rand4).toDestString());
					break;
				}
				else
				{
					while (rand4 == rand3 || rand4 == rand2 || rand4 == rand1)
					{
						rand4 = randGen.nextInt(TestResult.getEntitiesList().size());
					}
					option3.setText(""
							+ TestResult.getEntitiesList().get(rand4).toDestString());
					break;
				}
		}

		// Listener for button clicks
		OnClickListener buttonListener = new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				if (correctPosition == Integer.parseInt((String) v.getTag()))
				{
					Log.d(TAG, "Correct answer!"); // TRACE
					// Increase counter
					TestResult.increaseCounter();
					// Increase score
					TestResult.increaseScore(2);
					// Start new intent
					Intent i = new Intent(Test1.this, Test1.class);
					startActivity(i);
				}
				else
				{
					Log.d(TAG, "Wrong answer!"); // TRACE
					// Increase counter
					TestResult.increaseCounter();
					// Start new intent
					Intent i = new Intent(Test1.this, Test1.class);
					startActivity(i);
				}
			}
		};

		// Set the buttons with click listener
		option0.setOnClickListener(buttonListener);
		option1.setOnClickListener(buttonListener);
		option2.setOnClickListener(buttonListener);
		option3.setOnClickListener(buttonListener);

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

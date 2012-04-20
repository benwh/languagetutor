package ncl.team22.languagetutor.test;

import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ncl.team22.languagetutor.LanguagetutorActivity;
import ncl.team22.languagetutor.R;
import ncl.team22.languagetutor.data.LanguageEntity;
import ncl.team22.languagetutor.data.Topic;

/**
 * Activity for handling test generation
 * 
 * @author james
 */
public class Test extends Activity
{
	static Topic				selectedTopic;
	static boolean				backPressed	= false;
	static int					TEST_MAX	= 8;
	static int					TEST_MID	= 4;

	LanguageEntity				current;
	Random						randGen		= new Random();
	int							switchVal;
	int							entityID;

	public static final String	TAG			= "LT-Test";

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
			selectedTopic = (Topic) i.getSerializableExtra(Topics.intentTopic);
			Log.d(TAG, "Selected topic was: " + selectedTopic);

			// Occupy list with topics
			if (getIntent().getFlags() == 100)
			{
				TestData.setEntitiesList(Topic.getAllEntities());
			}
			else
			{
				TestData.setEntitiesListByTopic(selectedTopic);
			}

			// Add header for review
			TestData.addToReviewTest("ORIGINAL");
			TestData.addToReviewTest("TRANSLATION");
			TestData.addToReviewTest("YOUR ANSWER");

			if (TestData.getEntitiesList().size() < TEST_MAX)
			{
				setTestLength(TestData.getEntitiesList().size());
			}

			getMultiQuestion();
		}
		else if (TestResult.getCounter() < TEST_MID)
		{
			setContentView(R.layout.test1);

			getMultiQuestion();
		}
		else if (TestResult.getCounter() < TEST_MAX)
		{
			setContentView(R.layout.test2);

			getWrittenQuestion();
		}
		else if (TestResult.getCounter() == TEST_MAX)
		{
			setContentView(R.layout.test_submitted);

			// Get a UNIX time-stamp
			long currentTime = System.currentTimeMillis() / 1000L;
			// Convert to string for insertion to DB
			String tS = Long.toString(currentTime);
			// Turn in to a date object. Needs conversion back to milliseconds
			Date currentDate = new java.util.Date(currentTime * 1000);
			Log.d(TAG, "Timestamp: " + currentDate.toString());
			// Submit the score
			TestResult.submitScore(LanguagetutorActivity.currentProfile.profileID, Topic.getIdByTopic(selectedTopic), tS);
			Log.d(TAG, "Test complete, taking you to the TestReview...");
			Intent i = new Intent(Test.this, TestReview.class);
			i.setFlags(99);
			startActivity(i);
		}
	}

	/**
	 * Used to set question text based on the switch value and question viewId,
	 * removes some duplicated code
	 * 
	 * @param switchVal
	 *            - the random generated switch value
	 * @param viewId
	 *            - the id of the view where the question text is set
	 */
	public void setQuestion(int switchVal, int viewId)
	{
		// SET THE QUESTION

		final TextView question = (TextView) findViewById(viewId);
		// Generate random id
		entityID = randGen.nextInt(TestData.getEntitiesList().size());
		Log.d(TAG, "Entity ID is: " + entityID); // TRACE
		current = TestData.getEntitiesList().get(entityID);
		Log.d(TAG, "Entity information: " + current.toString()); // TRACE
		switch (switchVal)
		{
			case 0 :
			// Spanish question
			{
				// Set question text
				question.setText("" + current.toDestString());
				// Add question to review array
				TestData.addToReviewTest(current.toDestString());
				// Add answer to review array
				TestData.addToReviewTest(current.toSourceString());
				break;
			}
			case 1 :
			// English question
			{
				// Set question text
				question.setText("" + current.toSourceString());
				// Add question to review array
				TestData.addToReviewTest(current.toSourceString());
				// Add answer to review array
				TestData.addToReviewTest(current.toDestString());
				break;
			}
		}
		// Add entity to removed list
		TestData.addToRemovedList(current);
		// Remove entity from initial list
		TestData.removeFromEntitiesList(entityID);
	}

	public void getMultiQuestion()
	{
		// Generate a number 0 or 1 for input to switch statement
		switchVal = randGen.nextInt(2);

		Log.d(TAG, "Case is: " + switchVal); // TRACE
		Log.d(TAG, "Entity list size is: " + TestData.getEntitiesList().size()); // TRACE

		setQuestion(switchVal, R.id.multi_question);

		Log.d(TAG, "Entities list:"); // TRACE
		for (int itr = 0; itr < TestData.getEntitiesList().size(); itr++)
		{
			Log.d(TAG, TestData.getEntitiesList().get(itr).toString());
		}
		Log.d(TAG, "Removed list:"); // TRACE
		for (int itr1 = 0; itr1 < TestData.getRemovedList().size(); itr1++)
		{
			Log.d(TAG, TestData.getRemovedList().get(itr1).toString());
		}

		int rand1 = 0;
		int rand2 = 0;
		int rand3 = 0;
		int rand4 = 0;

		if (!(TestData.getRemovedList().isEmpty()))
		{
			rand1 = randGen.nextInt(TestData.getRemovedList().size());
			rand2 = randGen.nextInt(TestData.getRemovedList().size());
			rand3 = randGen.nextInt(TestData.getRemovedList().size());
			rand4 = randGen.nextInt(TestData.getRemovedList().size());
		}
		Log.d(TAG, "Rand 1: " + rand1); // TRACE
		Log.d(TAG, "Rand 2: " + rand2); // TRACE
		Log.d(TAG, "Rand 3: " + rand3); // TRACE
		Log.d(TAG, "Rand 4: " + rand4); // TRACE

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
				else if (!(TestData.getEntitiesList().isEmpty()))
				{
					rand1 = randGen.nextInt(TestData.getEntitiesList().size());
					Log.d(TAG, "Rand 1: " + rand1); // TRACE
					option0.setText(""
							+ TestData.getEntitiesList().get(rand1).toSourceString());
					break;
				}
				else if (TestData.getRemovedList().size() > 1)
				{
					// Check to avoid duplicate answers
					while (rand1 == TestData.getRemovedList().indexOf(current))
					{
						rand1 = randGen.nextInt(TestData.getRemovedList().size());
						Log.d(TAG, "Rand 1: " + rand1); // TRACE
					}
					option0.setText(""
							+ TestData.getRemovedList().get(rand1).toDestString());
					break;
				}
			case 1 :
				if (0 == correctPosition)
				{
					option0.setText("" + current.toDestString());
					break;
				}
				else if (!(TestData.getEntitiesList().isEmpty()))
				{
					rand1 = randGen.nextInt(TestData.getEntitiesList().size());
					Log.d(TAG, "Rand 1: " + rand1); // TRACE
					option0.setText(""
							+ TestData.getEntitiesList().get(rand1).toDestString());
					break;
				}
				else if (TestData.getRemovedList().size() > 1)
				{
					// Check to avoid duplicate answers
					while (rand1 == TestData.getRemovedList().indexOf(current))
					{
						rand1 = randGen.nextInt(TestData.getRemovedList().size());
						Log.d(TAG, "Rand 1: " + rand1); // TRACE
					}
					option0.setText(""
							+ TestData.getRemovedList().get(rand1).toDestString());
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
				else if (TestData.getRemovedList().size() > 2)
				{
					// This button shall prefer entities which have been removed
					while (rand2 == TestData.getRemovedList().indexOf(current)
							|| rand2 == rand1)
					{
						rand2 = randGen.nextInt(TestData.getRemovedList().size());
						Log.d(TAG, "Rand 2: " + rand2); // TRACE
					}
					option1.setText(""
							+ TestData.getRemovedList().get(rand2).toSourceString());
					break;
				}
				else
				{
					while (rand2 == rand1)
					{
						rand2 = randGen.nextInt(TestData.getEntitiesList().size());
						Log.d(TAG, "Rand 2: " + rand2); // TRACE
					}
					option1.setText(""
							+ TestData.getEntitiesList().get(rand2).toSourceString());
					break;
				}
			case 1 :
				if (1 == correctPosition)
				{
					option1.setText("" + current.toDestString());
					break;
				}
				else if (TestData.getRemovedList().size() > 2)
				{
					// This button shall prefer entities which have been removed
					while (rand2 == TestData.getRemovedList().indexOf(current)
							|| rand2 == rand1)
					{
						rand2 = randGen.nextInt(TestData.getRemovedList().size());
						Log.d(TAG, "Rand 2: " + rand2); // TRACE
					}
					option1.setText(""
							+ TestData.getRemovedList().get(rand2).toDestString());
					break;
				}
				else
				{
					while (rand2 == rand1)
					{
						rand2 = randGen.nextInt(TestData.getEntitiesList().size());
						Log.d(TAG, "Rand 2: " + rand2); // TRACE
					}
					option1.setText(""
							+ TestData.getEntitiesList().get(rand2).toDestString());
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
				else if (TestData.getRemovedList().size() > 3)
				{
					// Check to avoid duplicate answers
					while (rand3 == TestData.getRemovedList().indexOf(current)
							|| rand3 == rand2 || rand3 == rand1)
					{
						rand3 = randGen.nextInt(TestData.getRemovedList().size());
						Log.d(TAG, "Rand 3: " + rand3); // TRACE
					}
					option2.setText(""
							+ TestData.getRemovedList().get(rand3).toSourceString());
					break;
				}
				else
				{
					while (rand3 == rand2 || rand3 == rand1)
					{
						rand3 = randGen.nextInt(TestData.getEntitiesList().size());
						Log.d(TAG, "Rand 3: " + rand3); // TRACE
					}
					option2.setText(""
							+ TestData.getEntitiesList().get(rand3).toSourceString());
					break;
				}
			case 1 :
				if (2 == correctPosition)
				{
					option2.setText("" + current.toDestString());
					break;
				}
				else if (TestData.getRemovedList().size() > 3)
				{
					// Check to avoid duplicate answers
					while (rand3 == TestData.getRemovedList().indexOf(current)
							|| rand3 == rand2 || rand3 == rand1)
					{
						rand3 = randGen.nextInt(TestData.getRemovedList().size());
						Log.d(TAG, "Rand 3: " + rand3); // TRACE
					}
					option2.setText(""
							+ TestData.getRemovedList().get(rand3).toDestString());
					break;
				}
				else
				{
					while (rand3 == rand2 || rand3 == rand1)
					{
						rand3 = randGen.nextInt(TestData.getEntitiesList().size());
						Log.d(TAG, "Rand 3: " + rand3); // TRACE
					}
					option2.setText(""
							+ TestData.getEntitiesList().get(rand3).toDestString());
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
				else if (TestData.getRemovedList().size() > 4)
				{
					// This button shall prefer entities which have been removed
					while (rand4 == TestData.getRemovedList().indexOf(current)
							|| rand4 == rand3 || rand4 == rand2
							|| rand4 == rand1)
					{
						rand4 = randGen.nextInt(TestData.getRemovedList().size());
						Log.d(TAG, "Rand 4: " + rand4); // TRACE
					}
					option3.setText(""
							+ TestData.getRemovedList().get(rand4).toSourceString());
					break;
				}
				else if (TestData.getEntitiesList().size() > 3)
				{
					while (rand4 == rand3 || rand4 == rand2 || rand4 == rand1)
					{
						rand4 = randGen.nextInt(4);
						Log.d(TAG, "Rand 4: " + rand4); // TRACE
					}
					option3.setText(""
							+ TestData.getEntitiesList().get(rand4).toSourceString());
					break;
				}
				else
				{
					try
					{
						int i = TestData.getRemovedList().size();
						while (i > 0)
						{
							if (TestData.getRemovedList().size() - i != rand3
									|| TestData.getRemovedList().size() - i != rand2
									|| TestData.getRemovedList().size() - i != rand1)
							{
								option3.setText(""
										+ TestData.getRemovedList().get(TestData.getRemovedList().size()
												- i).toSourceString());
								break;
							}
							i--;
						}
						int j = TestData.getEntitiesList().size();
						while (j > 0)
						{
							if (TestData.getEntitiesList().size() - j != rand3
									|| TestData.getEntitiesList().size() - j != rand2
									|| TestData.getEntitiesList().size() - j != rand1)
							{
								option3.setText(""
										+ TestData.getEntitiesList().get(TestData.getEntitiesList().size()
												- j).toSourceString());
								break;
							}
							j--;
						}
					} catch (IndexOutOfBoundsException e)
					{
						Log.d(TAG, "Exception caught: " + e);
					}
				}
			case 1 :
				if (3 == correctPosition)
				{
					option3.setText("" + current.toDestString());
					break;
				}
				else if (TestData.getRemovedList().size() > 4)
				{
					// This button shall prefer entities which have been removed
					while (rand4 == TestData.getRemovedList().indexOf(current)
							|| rand4 == rand3 || rand4 == rand2
							|| rand4 == rand1)
					{
						rand4 = randGen.nextInt(TestData.getRemovedList().size());
						Log.d(TAG, "Rand 4: " + rand4); // TRACE
					}
					option3.setText(""
							+ TestData.getRemovedList().get(rand4).toDestString());
					break;
				}
				else if (TestData.getEntitiesList().size() > 3)
				{
					while (rand4 == rand3 || rand4 == rand2 || rand4 == rand1)
					{
						rand4 = randGen.nextInt(4);
						Log.d(TAG, "Rand 4: " + rand4); // TRACE
					}
					option3.setText(""
							+ TestData.getEntitiesList().get(rand4).toDestString());
					break;
				}
				else
				{
					try
					{
						int i = TestData.getRemovedList().size();
						while (i > 0)
						{
							if (TestData.getRemovedList().size() - i != rand3
									|| TestData.getRemovedList().size() - i != rand2
									|| TestData.getRemovedList().size() - i != rand1)
							{
								option3.setText(""
										+ TestData.getRemovedList().get(TestData.getRemovedList().size()
												- i).toDestString());
								break;
							}
							i--;
						}
						int j = TestData.getEntitiesList().size();
						while (j > 0)
						{
							if (TestData.getEntitiesList().size() - j != rand3
									|| TestData.getEntitiesList().size() - j != rand2
									|| TestData.getEntitiesList().size() - j != rand1)
							{
								option3.setText(""
										+ TestData.getEntitiesList().get(TestData.getEntitiesList().size()
												- j).toDestString());
								break;
							}
							j--;
						}
					} catch (IndexOutOfBoundsException e)
					{
						Log.d(TAG, "Exception caught: " + e);
					}
				}
		}

		// Listener for button clicks
		OnClickListener buttonListener = new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				// Add user's answer to review string
				int clickedButton = Integer.parseInt((String) v.getTag());
				switch (clickedButton)
				{
					case 0 :
						TestData.addToReviewTest((String) option0.getText());
						break;
					case 1 :
						TestData.addToReviewTest((String) option1.getText());
						break;
					case 2 :
						TestData.addToReviewTest((String) option2.getText());
						break;
					case 3 :
						TestData.addToReviewTest((String) option3.getText());
						break;
				}
				if (correctPosition == Integer.parseInt((String) v.getTag()))
				{
					Log.d(TAG, "Correct answer!"); // TRACE
					// Increase counter
					TestResult.increaseCounter();
					// Increase score
					TestResult.increaseScore(2);
					// Increase maximum score
					TestResult.increaseMaximum(2);
					// Start new intent
					Intent i = new Intent(Test.this, Test.class);
					i.setFlags(99);
					startActivity(i);
				}
				else
				{
					Log.d(TAG, "Wrong answer!"); // TRACE
					// Increase counter
					TestResult.increaseCounter();
					// Increase maximum score
					TestResult.increaseMaximum(2);
					// Start new intent
					Intent i = new Intent(Test.this, Test.class);
					i.setFlags(99);
					startActivity(i);
				}
			}
		};

		// Set the buttons with click listener
		option0.setOnClickListener(buttonListener);
		option1.setOnClickListener(buttonListener);
		option2.setOnClickListener(buttonListener);
		option3.setOnClickListener(buttonListener);
	}

	public void getWrittenQuestion()
	{
		// Generate a number 0 or 1 for input to switch statement
		switchVal = randGen.nextInt(2);

		Log.d(TAG, "Case is: " + switchVal); // TRACE
		Log.d(TAG, "Entity list size is: " + TestData.getEntitiesList().size()); // TRACE

		setQuestion(switchVal, R.id.written_question);

		Log.d(TAG, "Entities list:"); // TRACE
		for (int itr = 0; itr < TestData.getEntitiesList().size(); itr++)
		{
			Log.d(TAG, TestData.getEntitiesList().get(itr).toString());
		}
		Log.d(TAG, "Removed list:"); // TRACE
		for (int itr1 = 0; itr1 < TestData.getRemovedList().size(); itr1++)
		{
			Log.d(TAG, TestData.getRemovedList().get(itr1).toString());
		}

		// SET THE ANSWER

		final EditText answer = (EditText) findViewById(R.id.written_answer);

		// SET THE SUBMIT

		final Button submit = (Button) findViewById(R.id.submit_answer);
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				// Add user's answer to review string
				TestData.addToReviewTest(answer.getText().toString());

				int actualScore = 0;
				int maxScore = 0;
				char a;
				char b;
				int submissionScore = 0;
				switch (switchVal)
				{
					case 0 :
						for (int i = 0; i < answer.getText().length()
								|| i < current.toSourceString().length(); i++)
						{
							try
							{
								a = answer.getText().charAt(i);
								b = current.toSourceString().charAt(i);
								if (a == b)
								{
									actualScore++;
								}
							} catch (IndexOutOfBoundsException e)
							{
								Log.d(TAG, "Caught: " + e);
							}
							maxScore++;
						}
						break;
					case 1 :
						for (int i = 0; i < answer.getText().length()
								|| i < current.toDestString().length(); i++)
						{
							try
							{
								a = answer.getText().charAt(i);
								b = current.toDestString().charAt(i);
								if (a == b)
								{
									actualScore++;
								}
							} catch (IndexOutOfBoundsException e)
							{
								Log.d(TAG, "Caught: " + e);
							}
							maxScore++;
						}
						break;
				}
				double percentage = ((double) actualScore / (double) maxScore) * 100;
				Log.d(TAG, "Actual score is: " + actualScore); // TRACE
				Log.d(TAG, "Max score is: " + maxScore); // TRACE
				Log.d(TAG, "Percentage is: " + percentage); // TRACE
				if (percentage == 100)
				{
					submissionScore = 3;
				}
				else if (percentage >= 70)
				{
					submissionScore = 2;
				}
				else if (percentage >= 40)
				{
					submissionScore = 1;
				}
				Log.d(TAG, "Score incremented by: " + submissionScore); // TRACE
				if (TestResult.getCounter() != TEST_MAX)
				{
					// Increase counter
					TestResult.increaseCounter();
					// Increase score
					TestResult.increaseScore(submissionScore);
					// Increase maximum score
					TestResult.increaseMaximum(3);
					// Start new intent
					Intent i = new Intent(Test.this, Test.class);
					i.setFlags(99);
					startActivity(i);
				}
			}
		});
	}

	/**
	 * Sets the length of static test variable and midpoint
	 * 
	 * @param length
	 *            - The length of the overall test
	 */
	public static void setTestLength(int length)
	{
		TEST_MAX = length;
		Log.d(TAG, "Test max is: " + TEST_MAX); // TRACE
		TEST_MID = TEST_MAX / 2;
		if (TEST_MID % 2 == 1)
		{
			TEST_MID++;
		}
		Log.d(TAG, "Test mid is: " + TEST_MID); // TRACE
	}

	@Override
	public void onBackPressed()
	{
		backPressed = true;
		finish();
		Log.d(TAG, "Reverting back to topic select...");
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		if (getIntent().getFlags() == 99 && backPressed == true)
		{
			onBackPressed();
		}
		else if ((getIntent().getFlags() == 98 || getIntent().getFlags() == 100)
				&& backPressed == true)
		{
			backPressed = false;
			TestResult.reset();
			TestData.resetData();
			// Reset test length values to default
			setTestLength(8);
			finish();
		}
	}
}

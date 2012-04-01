package ncl.team22.languagetutor.test;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ncl.team22.languagetutor.LanguagetutorActivity;

/**
 * Class containing static methods and integer value for keeping track of a test
 * result as a user moves through test screens
 * 
 * @author james
 */
public class TestResult
{
	private static int			testScore		= 0;
	private static int			testMaximum		= 0;
	private static double		testPercentage	= 0.0;
	private static int			counter			= 0;

	public static final String	TAG				= "LT-TestResult";

	/**
	 * Increase the test score by the value achieved on a test activity
	 * 
	 * @param i
	 *            - The test result value
	 */
	public static void increaseScore(int i)
	{
		testScore = testScore + i;
		Log.d(TAG, "Score value is: " + testScore);
	}

	/**
	 * Get the test score
	 * 
	 * @return the test score
	 */
	public static int getScore()
	{
		Log.d(TAG, "Score value is: " + testScore);
		return testScore;
	}

	/**
	 * Increase the maximum score by the highest value available for a question
	 * 
	 * @param i
	 *            - The maximum question value
	 */
	public static void increaseMaximum(int i)
	{
		testMaximum = testMaximum + i;
		Log.d(TAG, "Maximum value is: " + testMaximum);
	}

	/**
	 * Get the test maximum value
	 * 
	 * @return the maximum score available
	 */
	public static int getMaximum()
	{
		Log.d(TAG, "Maximum value is: " + testMaximum);
		return testMaximum;
	}

	/**
	 * Increase the counter value by 1
	 */
	public static void increaseCounter()
	{
		counter++;
		Log.d(TAG, "Counter value is: " + counter);
	}

	/**
	 * Gets counter value
	 * 
	 * @return the counter value
	 */
	public static int getCounter()
	{
		Log.d(TAG, "Counter value is: " + counter);
		return counter;
	}

	/**
	 * Get the final percentage for the test as a double value
	 * 
	 * @return the double percentage value
	 */
	public static double calculateTestPercentage()
	{
		return testPercentage = ((double) testScore / (double) testMaximum) * 100;
	}

	/**
	 * Resets the test score and counter value (in the case of the test being
	 * quit midway through, or after the test result has been written to
	 * database)
	 */
	public static void reset()
	{
		counter = 0;
		testScore = 0;
		testMaximum = 0;
		testPercentage = 0.0;
		Log.d(TAG, "reset");
		Log.d(TAG, "Counter value is: " + counter);
		Log.d(TAG, "Score value is: " + testScore);
		Log.d(TAG, "Maximum value is: " + testMaximum);
		Log.d(TAG, "Percentage value is: " + testPercentage);
	}

	/**
	 * Submits the testScore value and time-stamp for the relevant setID and
	 * profileID to database
	 */
	public static void submitScore(int profileID, int setID, int tS)
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		// TODO: Write test result and time-stamp to database

	}
}

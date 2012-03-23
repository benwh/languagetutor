package ncl.team22.languagetutor.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ncl.team22.languagetutor.data.DatabaseAdapter;

/**
 * Class containing static methods and integer value for keeping track of a test
 * result as a user moves through test screens
 * 
 * @author james
 */
public class TestResult
{
	private static int	testScore	= 0;
	private static int	counter		= 0;

	/**
	 * Increase the test score by the value achieved on a test activity
	 * 
	 * @param i
	 *            - The test result value
	 */
	public static void increaseScore(int i)
	{
		testScore = testScore + i;
	}

	/**
	 * Increase the counter value by 1
	 * 
	 * @return the counter value
	 */
	public static int increaseCounter()
	{
		return counter++;
	}

	/**
	 * Resets the test score and counter value (in the case of the test being
	 * quit midway through, or after the test result has been written to
	 * database)
	 */
	public static void reset()
	{
		testScore = 0;
		counter = 0;
	}

	/**
	 * Submits the testScore value and time-stamp for the relevant setID and
	 * profileID to database
	 */
	public static void submitScore(Context ctx/*
											 * int profileID, int setID,
											 * time-stamp tS
											 */)
	{
		DatabaseAdapter sDba = new DatabaseAdapter(ctx);
		SQLiteDatabase sDb = sDba.getWritableDatabase();

		// TODO: Write test result and time-stamp to database

		sDba.close();
	}

}

package ncl.team22.languagetutor.test;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ncl.team22.languagetutor.LanguagetutorActivity;
import ncl.team22.languagetutor.data.LanguageEntity;
import ncl.team22.languagetutor.data.Topic;

/**
 * Class containing static methods and integer value for keeping track of a test
 * result as a user moves through test screens
 * 
 * @author james
 */
public class TestResult
{
	private static int			testScore	= 0;
	private static int			counter		= 0;

	public static final String	TAG			= "LT-TestResult";

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
	public static void submitScore(int profileID, int setID, int tS)
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		// TODO: Write test result and time-stamp to database

	}

	// DATA SECTION

	private static ArrayList<LanguageEntity>	entitiesList;
	// Prevent asked questions being asked again but enable them to be fed as
	// incorrect options
	private static ArrayList<LanguageEntity>	entitiesRemoved	= new ArrayList<LanguageEntity>();

	public static void setEntitiesListByTopic(Topic selectedTopic)
	{
		entitiesList = selectedTopic.getEntities();
	}

	public static ArrayList<LanguageEntity> getEntitiesList()
	{
		return entitiesList;
	}

	public static ArrayList<LanguageEntity> getRemovedList()
	{
		return entitiesRemoved;
	}

	public static void addToRemovedList(LanguageEntity entity)
	{
		entitiesRemoved.add(entity);
	}

	public static void removeFromEntitiesList(int entityID)
	{
		entitiesList.remove(entityID);
	}
}

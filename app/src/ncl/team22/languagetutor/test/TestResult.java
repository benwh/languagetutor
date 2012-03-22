package ncl.team22.languagetutor.test;

/**
 * Class containing static methods and integer value for keeping track of a test
 * result as a user moves through test screens
 * 
 * @author james
 */
public class TestResult
{
	private static int	testScore	= 0;

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
	 * Resets the test score (in the case of the test being quit midway through,
	 * or after the test result has been written to database)
	 */
	public static void resetScore()
	{
		testScore = 0;
	}

	/**
	 * Submits the testScore value and time-stamp for the relevant setID to
	 * database
	 */
	public static void submitScore(/* int setID, time-stamp tS */)
	{
		// TODO: Write test result and time-stamp to database
	}

}

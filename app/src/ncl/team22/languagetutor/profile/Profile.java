package ncl.team22.languagetutor.profile;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;
import android.util.Log;

import ncl.team22.languagetutor.LanguagetutorActivity;
import ncl.team22.languagetutor.data.DatabaseAdapter;

/**
 * Class to represent a users profile
 * 
 * @author Ben
 */
public class Profile
{

	public static final String	TAG				= "LT-Profile";
	private static final String	SALT			= "93nsa9j2b0sb0f6v3lstzlpu2n";
	private static int			HASH_ITERATIONS	= 5000;

	public int					profileID;
	public String				display_name;
	public String				password_hash;
	public String				secret_q;
	public String				secret_a;
	public int					theme;

	/**
	 * Class constructor creates and initializes the variables for a new profile
	 * 
	 * @param profileID
	 *            Used to initialize the profile ID of the new profile
	 * @param display_name
	 *            Used to initialize the username of the new profile
	 * @param password_hash
	 *            Used to initialize the password of the new profile
	 * @param secret_q
	 *            Used to initialize the secret question of the new profile
	 * @param secret_a
	 *            Used to initialize the secret answer of the new profile
	 * @param theme
	 *            Used to initialize the theme of the new profile
	 */
	public Profile(int profileID, String display_name, String password_hash,
			String secret_q, String secret_a, int theme)
	{
		this.profileID = profileID;
		this.display_name = display_name;
		this.password_hash = password_hash;
		this.secret_q = secret_q;
		this.secret_a = secret_a;
		this.theme = theme;
	}

	/**
	 * Used to retrieve all the users in the database
	 * 
	 * Gets a table with the usernames and profileIDs of all users in the
	 * database.
	 * 
	 * @return returns a cursor on a table of all users in the database.
	 */
	public static Cursor getProfiles()
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		return sDb.query(DatabaseAdapter.TABLE_PROFILE, new String[]
		{"profileID _id", "display_name"}, null, null, null, null, null);
	}

	/**
	 * Creates a new profile in the database with the values given
	 * 
	 * NOTE: Don't pass in a profileID, will be inserted automatically
	 * 
	 * @param userName
	 *            Username that will be inserted to the database.
	 * @param password
	 *            Password that will be inserted to the database.
	 * @param secretQ
	 *            Secret question that will be inserted to the database.
	 * @param secretA
	 *            Secret answer that will be inserted to the database.
	 * @return The id number of the profile that was just inserted in the
	 *         database.
	 */
	public static int create(String userName, String password, String secretQ,
			String secretA)
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put("display_name", userName);
		cv.put("password_hash", hashPassword(password));
		cv.put("secret_q", secretQ);
		cv.put("secret_a", secretA);
		cv.put("theme", 1);

		int profileID = (int) sDb.insert("profile", null, cv);

		return profileID;
	}

	/**
	 * Loads a profile from the database, specified by the profileID.
	 * 
	 * Retrieves the data that corresponds to the requested ProfileID, and
	 * creates a profile with these values then returns the profile. Does not
	 * update the active profile.
	 * 
	 * @param profileID
	 *            The profileID of the profile you want to load
	 * @return A profile with the values of the requested ProfileID otherwise
	 *         throws RuntimeException
	 */
	public static Profile load(int profileID)
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		Cursor c = sDb.query(DatabaseAdapter.TABLE_PROFILE, new String[]
		{"profileID _id", "display_name", "password_hash", "secret_q",
				"secret_a", "theme"}, "_id = ?", new String[]
		{Integer.toString(profileID)}, null, null, null);

		if (c.getCount() > 0)
		{
			c.moveToFirst();
			Profile p = new Profile(profileID, c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getInt(5));
			return p;
		}
		else
		{
			throw new RuntimeException("Tried to load profileID=" + profileID
					+ " but not found in DB");
		}

	}

	/**
	 * Used to check a string matches the password of a specified Profile
	 * 
	 * Retrieves the 'password_hash' of the requested profile from the database
	 * and compares it this to the result of hashing the password that is passed
	 * in as a parameter. If they match the requested profile is returned.
	 * 
	 * @param user
	 *            The username of the profile requested
	 * @param pass
	 *            The string to be compared to the password of the requested
	 *            profile
	 * @return The profile requested otherwise null
	 */
	public static Profile authenticate(String user, String pass)
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		Cursor c = sDb.query(DatabaseAdapter.TABLE_PROFILE, new String[]
		{"profileID _id", "display_name", "password_hash"}, "display_name = ?", new String[]
		{user}, null, null, null);

		if (c.getCount() > 0)
		{
			c.moveToFirst();
			if (hashPassword(pass).equals(c.getString(c.getColumnIndex("password_hash"))))
			{
				Profile p = Profile.load(c.getInt(c.getColumnIndex("_id")));
				return p;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}

	//
	/**
	 * Checks a profile with the given username exists in the database
	 * 
	 * @param userName
	 *            the username to be checked.
	 * @return True if a profile with the given username exists otherwise false.
	 */
	public static boolean checkName(String userName)
	{
		boolean exists = false;
		Cursor c;
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		c = sDb.query(DatabaseAdapter.TABLE_PROFILE, new String[]
		{"display_name"}, "display_name = ?", new String[]
		{userName}, null, null, null);

		if (c.getCount() > 0)
		{
			exists = true;
		}
		return exists;
	}

	/**
	 * Hashes a users password for storage in the database.
	 * 
	 * @param password
	 *            the password to be Hashed
	 * @return The hash value of the given password
	 */
	public static String hashPassword(String password)
	{
		MessageDigest md;

		byte[] salt = null;
		byte[] pw = null;

		try
		{
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e)
		{
			Log.e(TAG, "SHA-256 algorithm not available");
			throw new RuntimeException("SHA-256 algorithm not available");
		}

		try
		{
			salt = SALT.getBytes("UTF-8");
			pw = password.getBytes("UTF-8");
		} catch (UnsupportedEncodingException ex)
		{
			ex.printStackTrace();
		}

		byte[] digest;

		md.update(pw);
		digest = md.digest(salt);

		for (int i = 0; i < (HASH_ITERATIONS - 1); i++)
		{
			md.update(digest);
			digest = md.digest(salt);
		}

		return Base64.encodeToString(digest, Base64.NO_PADDING | Base64.NO_WRAP);
	}

	/**
	 * Gets the the level of the currently active profile.
	 * 
	 * Gets the best scores in all tests done by the currently active profile.
	 * Then returns the lowest level where all tests have been passed(achieved a
	 * mark of 75%) + 1. NOTE: Does not include test results for lang_entity 9
	 * because a test for the male/female topic has not been implemented.
	 * 
	 * @return The currently active users level.
	 */
	public static int getUserLevel()
	{
		int userLevel = 0;

		Cursor c;
		SQLiteDatabase sDbb = LanguagetutorActivity.sDBa.getWritableDatabase();

		int[] bestTestScores = new int[10];
		for (int i = 0; i < 10; i++)
		{
			String myQuery = "SELECT MAX(score) as high_score FROM "
					+ DatabaseAdapter.TABLE_TEST_RESULTS
					+ " WHERE profileID = ? AND langsetID = "
					+ Integer.toString(i);
			c = sDbb.rawQuery(myQuery, new String[]
			{Integer.toString(LanguagetutorActivity.currentProfile.profileID)});
			if (c.moveToFirst())
			{
				bestTestScores[i] = c.getInt(c.getColumnIndex("high_score"));
			}
			else
			{
				bestTestScores[i] = 0;
			}
		}

		if ((bestTestScores[0] > 74) && (bestTestScores[1] > 74)
				&& (bestTestScores[2] > 74))
		{
			if ((bestTestScores[3] > 74) && (bestTestScores[4] > 74)
					&& (bestTestScores[5] > 74) && (bestTestScores[6] > 74)
					&& (bestTestScores[7] > 74))
			{
				if ((bestTestScores[8] > 74) /** && (bestTestScores[9] > 74) **/
				)
				{
					userLevel = 4;
				}
				else
				{
					userLevel = 3;
				}
			}
			else
			{
				userLevel = 2;
			}
		}
		else
		{
			userLevel = 1;
		}

		sDbb.close();
		return userLevel;
	}

	@Override
	public String toString()
	{
		return new String("[" + this.getClass().toString() + ": "
				+ "profileID=" + this.profileID + ", display_name="
				+ this.display_name + "]");
	}
}

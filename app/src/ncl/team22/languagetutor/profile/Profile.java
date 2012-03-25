package ncl.team22.languagetutor.profile;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;
import android.util.Log;

import ncl.team22.languagetutor.data.DatabaseAdapter;

public class Profile
{

	private static DatabaseAdapter	dbAdapter;
	private static SQLiteDatabase	db;

	public static final String		TABLE_PROFILE	= "profile";
	public static final String		TAG				= "LT-Profile";
	private static final String		SALT			= "93nsa9j2b0sb0f6v3lstzlpu2n";
	private static int				HASH_ITERATIONS	= 5000;

	// Constructor
	public Profile(Context ctx)
	{
		dbAdapter = new DatabaseAdapter(ctx);
		db = dbAdapter.getWritableDatabase();
		// TODO Profile constructor
	}

	public static Cursor getProfiles(Context ctx)
	{
		DatabaseAdapter sDba = new DatabaseAdapter(ctx);
		SQLiteDatabase sDb = sDba.getWritableDatabase();
		// sDba.close();
		return sDb.query(TABLE_PROFILE, new String[]
		{"profileID _id", "display_name"}, null, null, null, null, null);
	}

	// Creates a new profile in the database with the values given
	// currently crashes the application!
	public static Profile create(String userName, String password,
			String secretQ, String secretA)
	{
		ContentValues cv = new ContentValues();
		// cv.put("profileID", 1);
		cv.put("display_name", userName);
		cv.put("password_hash", hashPassword(password));
		cv.put("secret_q", secretQ);
		cv.put("secret_a", secretA);
		cv.put("theme", 1);

		// from debugging this line seems to be cause a crash null pointer
		// exception reported
		int profileID = (int) db.insert("profile", null, cv);

		return Profile.load(profileID);
	}

	public static Profile load(int profileID)
	{
		// TODO load method body
		return null;
	}

	// Returns a specific users password
	public static String getPass(String userName, Context cxt)
	{
		String pass = "";
		Cursor c;
		DatabaseAdapter sDba = new DatabaseAdapter(cxt);
		SQLiteDatabase sDb = sDba.getWritableDatabase();
		// sDba.close();
		c = sDb.query(TABLE_PROFILE, new String[]
		{"password_hash", "display_name"}, "display_name = '" + userName + "'", null, null, null, null);

		if (c.getCount() > 0)
		{
			int index = c.getColumnIndexOrThrow("password_hash");
			// getString() crashes due to index out of bounds exception...
			pass = c.getString(index);
		}
		return pass;
	}

	// Checks the username given exists in the database
	public static boolean checkName(String userName, Context cxt)
	{
		boolean exists = false;
		Cursor c;
		DatabaseAdapter sDba = new DatabaseAdapter(cxt);
		SQLiteDatabase sDb = sDba.getWritableDatabase();
		// sDba.close();
		c = sDb.query(TABLE_PROFILE, new String[]
		{"display_name"}, "display_name = '" + userName + "'", null, null, null, null);

		if (c.getCount() > 0)
		{
			exists = true;
		}
		return exists;
	}

	private static String hashPassword(String password)
	{
		MessageDigest md;

		byte[] salt = null;
		byte[] pw = null;

		try
		{
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
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

		return Base64.encodeToString(digest, Base64.DEFAULT);
	}

}

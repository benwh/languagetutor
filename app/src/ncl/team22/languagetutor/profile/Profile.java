package ncl.team22.languagetutor.profile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ncl.team22.languagetutor.data.DatabaseAdapter;

public class Profile
{

	private DatabaseAdapter		dbAdapter;
	private SQLiteDatabase		db;

	public static final String	TABLE_PROFILE	= "profile";

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
		sDba.close();
		return sDb.query(TABLE_PROFILE, new String[]
		{"profileID _id", "display_name"}, null, null, null, null, null);
	}

	// Creates a new profile in the database with the values given
	public void create(String userName, String password, String secretQ,
			String secretA)
	{

		ContentValues cv = new ContentValues();
		cv.put("profileID", 1);
		cv.put("display_name", "test");
		cv.put("password_hash", "123");

		db.insert("profile", null, cv);

		dbAdapter.close();
	}

	public void load(int profileID)
	{
		// TODO load method body
	}

	// Returns a specific users password
	public String getPass(String userName)
	{
		String pass = "";
		// TODO getPass method body
		return pass;
	}

	// Checks the username given exists in the database
	public boolean checkName(String userName)
	{
		boolean exists = false;
		// TODO checkName method body
		return exists;
	}

}

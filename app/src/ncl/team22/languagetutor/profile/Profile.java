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

	public static final String	TABLE_PROFILE	= "profile";
	public static final String	TAG				= "LT-Profile";
	private static final String	SALT			= "93nsa9j2b0sb0f6v3lstzlpu2n";
	private static int			HASH_ITERATIONS	= 5000;

	public int					profileID;
	public String				display_name;
	public String				password_hash;
	public String				secret_q;
	public String				secret_a;
	public int					theme;

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

	public static Cursor getProfiles(Context ctx)
	{
		DatabaseAdapter sDba = new DatabaseAdapter(ctx);
		SQLiteDatabase sDb = sDba.getWritableDatabase();
		// sDba.close();
		return sDb.query(TABLE_PROFILE, new String[]
		{"profileID _id", "display_name"}, null, null, null, null, null);
	}

	// Creates a new profile in the database with the values given
	public static Profile create(String userName, String password,
			String secretQ, String secretA, Context ctx)
	{
		DatabaseAdapter sDba = new DatabaseAdapter(ctx);
		SQLiteDatabase sDb = sDba.getWritableDatabase();

		// Don't pass in a profileID, will be inserted automatically
		ContentValues cv = new ContentValues();
		cv.put("display_name", userName);
		cv.put("password_hash", hashPassword(password));
		cv.put("secret_q", secretQ);
		cv.put("secret_a", secretA);
		cv.put("theme", 1);

		int profileID = (int) sDb.insert("profile", null, cv);

		return Profile.load(ctx, profileID);
	}

	public static Profile load(Context ctx, int profileID)
	{
		DatabaseAdapter sDba = new DatabaseAdapter(ctx);
		SQLiteDatabase sDb = sDba.getWritableDatabase();
		Cursor c = sDb.query(TABLE_PROFILE, new String[]
		{"profileID _id", "display_name", "password_hash", "secret_q",
				"secret_a", "theme"}, "_id = ?", new String[]
		{Integer.toString(profileID)}, null, null, null);

		// sDba.close();

		if (c.getCount() > 0)
		{
			c.moveToFirst();
			Profile p = new Profile(profileID, c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getInt(5));
			sDba.close();
			return p;
		}
		else
		{
			return null;
		}

	}

	public static Profile authenticate(Context ctx, String user, String pass)
	{
		DatabaseAdapter sDba = new DatabaseAdapter(ctx);
		SQLiteDatabase sDb = sDba.getWritableDatabase();

		Cursor c = sDb.query(TABLE_PROFILE, new String[]
		{"profileID _id", "display_name", "password_hash"}, "display_name = ?", new String[]
		{user}, null, null, null);

		if (c.getCount() > 0)
		{
			c.moveToFirst();
			if (hashPassword(pass).equals(c.getString(c.getColumnIndex("password_hash"))))
			{
				Profile p = Profile.load(ctx, c.getInt(c.getColumnIndex("_id")));
				sDba.close();
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

	// Checks the username given exists in the database
	public static boolean checkName(String userName, Context ctx)
	{
		boolean exists = false;
		Cursor c;
		DatabaseAdapter sDba = new DatabaseAdapter(ctx);
		SQLiteDatabase sDb = sDba.getWritableDatabase();
		c = sDb.query(TABLE_PROFILE, new String[]
		{"display_name"}, "display_name = ?", new String[]
		{userName}, null, null, null);

		if (c.getCount() > 0)
		{
			exists = true;
		}
		sDba.close();
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

}

package ncl.team22.languagetutor.data;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Topic
{
	protected int				topicID;
	protected String			name;
	protected int				level;
	protected boolean			locked;
	protected boolean			displayable;

	public static final String	TABLE_TOPIC	= "langset";
	public static final String	TAG			= "LT-Topic";

	public Topic(int topicID, String name, int level, boolean locked,
			boolean displayable)
	{
		this.topicID = topicID;
		this.name = name;
		this.level = level;
		this.locked = locked;
		this.displayable = displayable;
	}

	public Cursor getEntities(Context ctx)
	{
		DatabaseAdapter sDba = new DatabaseAdapter(ctx);
		SQLiteDatabase sDb = sDba.getWritableDatabase();

		// TODO: JOIN and return
		Cursor ents = null;
		// sDb.query(TABLE_TOPIC, new String[] {"entityID _id"}, null, null,
		// null, null, null);

		sDba.close();

		return ents;
	}

	// May need to return a Cursor instead
	public static ArrayList<Topic> getTopics(Context ctx, int level)
	{
		DatabaseAdapter sDba = new DatabaseAdapter(ctx);
		SQLiteDatabase sDb = sDba.getWritableDatabase();

		final String levelS = Integer.toString(level);
		Log.d(TAG, "level is: " + levelS);

		ArrayList<Topic> tlist = new ArrayList<Topic>();

		Cursor c = sDb.query(TABLE_TOPIC, new String[]
		{"setID", "name", "level", "locked", "displayable"}, "level = " + "?", new String[]
		{levelS}, null, null, null);
		c.moveToFirst();
		while (!c.isAfterLast())
		{
			// Expressions required on columns 3 + 4 to return boolean instead
			// of int
			tlist.add(new Topic(c.getInt(0), c.getString(1), c.getInt(2), (c.getInt(3) > 0), (c.getInt(4) > 0)));
			c.moveToNext();
		}

		sDba.close();

		return tlist;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}

package ncl.team22.languagetutor.data;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LanguageSetManager
{

	private DatabaseAdapter		dbAdapter;
	private SQLiteDatabase		db;

	public static final String	TABLE_LANGSET	= "langset";

	public LanguageSetManager(Context ctx)
	{
		dbAdapter = new DatabaseAdapter(ctx);
		db = dbAdapter.getWritableDatabase();
	}

	// May need to return a Cursor instead
	public ArrayList<Topic> getTopics()
	{
		ArrayList<Topic> tlist = new ArrayList<Topic>();

		Cursor c = db.query(TABLE_LANGSET, new String[]
		{"setID", "name", "level", "locked", "displayable"}, null, null, null,
				null, null);
		c.moveToFirst();
		while (!c.isAfterLast())
		{
			tlist.add(new Topic(c.getInt(0), c.getString(1), c.getInt(2), (c
					.getInt(3) > 0), (c.getInt(4) > 0)));
			c.moveToNext();
		}

		return tlist;
	}
}

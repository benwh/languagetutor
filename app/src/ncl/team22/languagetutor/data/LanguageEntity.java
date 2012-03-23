package ncl.team22.languagetutor.data;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LanguageEntity
{
	protected int				entityID;
	protected boolean			phrase;
	protected boolean			phrase_partial;
	protected String			source_text;
	protected String			dest_text;
	protected boolean			audio_asset;
	protected String			image_asset;

	public static final String	TABLE_TOPIC	= "langset";
	public static int			setId		= 0;
	public static final String	TAG			= "LT-LanguageEntity";

	public LanguageEntity(int entityID, boolean phrase, boolean phrase_partial,
			String source_text, String dest_text, boolean audio_asset,
			String image_asset)
	{
		entityID = this.entityID;
		phrase = this.phrase;
		phrase_partial = this.phrase_partial;
		source_text = this.source_text;
		dest_text = this.dest_text;
		audio_asset = this.audio_asset;
		image_asset = this.image_asset;
	}

	public static ArrayList<LanguageEntity> getEntities(Context ctx,
			String topicId)
	{
		DatabaseAdapter sDba = new DatabaseAdapter(ctx);
		SQLiteDatabase sDb = sDba.getWritableDatabase();

		ArrayList<LanguageEntity> elist = new ArrayList<LanguageEntity>();

		Cursor c = sDb.query(TABLE_TOPIC, new String[]
		{"setID"}, "name = " + "?", new String[]
		{topicId}, null, null, null);
		c.moveToFirst();
		// Set the id for next query
		setId = c.getInt(0);
		Log.d(TAG, "setID is: " + setId); // TRACE - works fine

		String sqlStatement = "SELECT * "
				+ "FROM langentity, entity_set "
				+ "WHERE langentity.entityId = entity_set.entityId AND entity_set.setID = "
				+ "?";
		Cursor d = sDb.rawQuery(sqlStatement, new String[]
		{Integer.toString(setId)});

		// TRACES
		Log.d(TAG, "Cursor has data? " + d.moveToFirst());
		Log.d(TAG, "Number of columns: " + d.getColumnCount());
		d.moveToFirst();

		while (!d.isAfterLast())
		{
			elist.add(new LanguageEntity(d.getInt(0), (d.getInt(1) > 0), (d.getInt(2) > 0), d.getString(3), d.getString(4), (d.getInt(5) > 0), d.getString(6)));
			d.moveToNext();
		}
		// getting a list of entity values will now be usable in the test

		sDba.close();

		return elist;
	}

	// TODO: Implement/call scoring algorithm
	public void updateScore(int score)
	{

	}

}

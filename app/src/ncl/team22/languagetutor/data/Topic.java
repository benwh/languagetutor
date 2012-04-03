package ncl.team22.languagetutor.data;

import java.io.Serializable;
import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ncl.team22.languagetutor.LanguagetutorActivity;

public class Topic implements Serializable
{
	private static final long	serialVersionUID	= -4417480132232338235L;

	public int					topicID;
	public String				name;
	public int					level;
	public boolean				locked;
	public boolean				displayable;

	public static final String	TABLE_TOPIC			= "langset";
	public static final String	TAG					= "LT-Topic";

	public Topic(int topicID, String name, int level, boolean locked,
			boolean displayable)
	{
		this.topicID = topicID;
		this.name = name;
		this.level = level;
		this.locked = locked;
		this.displayable = displayable;
	}

	// This is no longer used, may be removed if we don't find another use for
	// it
	public static Topic getTopicById(int topicID)
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		Cursor c = sDb.query(TABLE_TOPIC, new String[]
		{"setID", "name", "level", "locked", "displayable"}, "setID = " + "?", new String[]
		{Integer.toString(topicID)}, null, null, null);
		c.moveToFirst();

		Topic t = new Topic(c.getInt(0), c.getString(1), c.getInt(2), (c.getInt(3) > 0), (c.getInt(4) > 0));

		return t;
	}

	public static int getIdByTopic(Topic selectedTopic)
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		Cursor c = sDb.query(TABLE_TOPIC, new String[]
		{"setID"}, "name = " + "?", new String[]
		{selectedTopic.toString()}, null, null, null);
		c.moveToFirst();

		int topicId = c.getInt(0);
		Log.d(TAG, "Topic Id: " + topicId);
		return topicId;
	}

	public ArrayList<LanguageEntity> getEntities()
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		ArrayList<LanguageEntity> entities = new ArrayList<LanguageEntity>();

		Log.d(TAG, "TopicID is: " + this.topicID);
		String sqlStatement = "SELECT langentity.entityID _id, phrase, phrase_partial, source_text, dest_text, audio_asset, image_asset "
				+ "FROM langentity, entity_set "
				+ "WHERE langentity.entityId = entity_set.entityId AND entity_set.setID = "
				+ "?";
		Cursor d = sDb.rawQuery(sqlStatement, new String[]
		{Integer.toString(this.topicID)});

		d.moveToFirst();
		while (!d.isAfterLast())
		{
			entities.add(new LanguageEntity(d.getInt(0), (d.getInt(1) > 0), (d.getInt(2) > 0), d.getString(3), d.getString(4), (d.getInt(5) > 0), d.getString(6)));
			d.moveToNext();
		}

		return entities;
	}

	// May need to return a Cursor instead
	public static ArrayList<Topic> getTopics(int level)
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		ArrayList<Topic> tlist = new ArrayList<Topic>();

		Cursor c = sDb.query(TABLE_TOPIC, new String[]
		{"setID", "name", "level", "locked", "displayable"}, "level = " + "?", new String[]
		{Integer.toString(level)}, null, null, null);
		c.moveToFirst();
		while (!c.isAfterLast())
		{
			// Expressions required on columns 3 + 4 to return boolean instead
			// of int
			tlist.add(new Topic(c.getInt(0), c.getString(1), c.getInt(2), (c.getInt(3) > 0), (c.getInt(4) > 0)));
			c.moveToNext();
		}

		return tlist;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}

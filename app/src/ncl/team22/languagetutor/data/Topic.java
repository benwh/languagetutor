package ncl.team22.languagetutor.data;

import java.io.Serializable;
import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ncl.team22.languagetutor.LanguagetutorActivity;
import ncl.team22.languagetutor.test.LevelSelect;

/**
 * Provides topic structure and useful methods for getting information and data
 * regarding topics from the database, as well as overriding toString
 * 
 * @author ben, james
 * 
 */
public class Topic implements Serializable
{
	private static final long	serialVersionUID	= -4417480132232338235L;

	public int					topicID;
	public String				name;
	public int					level;
	public boolean				locked;
	public boolean				displayable;

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
	// TODO: Still not used, can probably remove?
	public static Topic getTopicById(int topicID)
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		Cursor c = sDb.query(DatabaseAdapter.TABLE_TOPIC, new String[]
		{"setID", "name", "level", "locked", "displayable"}, "setID = " + "?", new String[]
		{Integer.toString(topicID)}, null, null, null);
		c.moveToFirst();

		Topic t = new Topic(c.getInt(0), c.getString(1), c.getInt(2), (c.getInt(3) > 0), (c.getInt(4) > 0));

		return t;
	}

	/**
	 * Get the id of a topic that was selected
	 * 
	 * @param selectedTopic
	 *            - the selected topic
	 * @return the id value from the database
	 */
	public static int getIdByTopic(Topic selectedTopic)
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		Cursor c = sDb.query(DatabaseAdapter.TABLE_TOPIC, new String[]
		{"setID"}, "name = " + "?", new String[]
		{selectedTopic.toString()}, null, null, null);
		c.moveToFirst();

		int topicId = c.getInt(0);
		Log.d(TAG, "Topic Id: " + topicId);
		return topicId;
	}

	/**
	 * Get the arraylist of language entities for a selected topic
	 * 
	 * @return arraylist of language entities
	 */
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
			entities.add(new LanguageEntity(d.getInt(0), (d.getInt(1) > 0), (d.getInt(2) > 0), d.getString(3), d.getString(4), (d.getInt(5) > 0), (d.getInt(6) > 0)));
			d.moveToNext();
		}

		return entities;
	}

	/**
	 * Get all entities from within a level
	 * 
	 * @return the arraylist of entities
	 */
	public static ArrayList<LanguageEntity> getAllEntities()
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		ArrayList<LanguageEntity> entities = new ArrayList<LanguageEntity>();

		Log.d(TAG, "Level is: " + LevelSelect.getLevel());

		String sqlStatement = "SELECT langentity.entityID _id, phrase, phrase_partial, source_text, dest_text, audio_asset, image_asset "
				+ "FROM langentity, entity_set, langset "
				+ "WHERE langentity.entityId = entity_set.entityId AND (langset.level = "
				+ "?" + " AND langset.setId = entity_set.setId)";
		Cursor d = sDb.rawQuery(sqlStatement, new String[]
		{Integer.toString(LevelSelect.getLevel())});

		d.moveToFirst();
		while (!d.isAfterLast())
		{
			entities.add(new LanguageEntity(d.getInt(0), (d.getInt(1) > 0), (d.getInt(2) > 0), d.getString(3), d.getString(4), (d.getInt(5) > 0), (d.getInt(6) > 0)));
			d.moveToNext();
		}

		return entities;
	}

	/**
	 * Gets arraylist of topics on a specific level
	 * 
	 * @param level
	 *            - the level to get topics from
	 * @return arraylist of topics
	 */
	public static ArrayList<Topic> getTopics(int level)
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		ArrayList<Topic> tlist = new ArrayList<Topic>();

		Cursor c = sDb.query(DatabaseAdapter.TABLE_TOPIC, new String[]
		{"setID", "name", "level", "locked", "displayable"}, "level = " + "?", new String[]
		{Integer.toString(level)}, null, null, null);
		c.moveToFirst();
		while (!c.isAfterLast())
		{
			// Expressions required on columns 3 + 4 to return boolean instead
			// of int
			Topic t = new Topic(c.getInt(0), c.getString(1), c.getInt(2), (c.getInt(3) > 0), (c.getInt(4) > 0));
			if ((t.getEntities()).size() > 0)
			{
				tlist.add(t);
			}
			c.moveToNext();
		}

		return tlist;
	}

	// TODO: Method doesn't appear to be used, remove?
	public static ArrayList<Topic> getTopics()
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		ArrayList<Topic> tlist = new ArrayList<Topic>();

		Cursor c = sDb.query(DatabaseAdapter.TABLE_TOPIC, new String[]
		{"setID", "name", "level", "locked", "displayable"}, null, null, null, null, null);
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

	/**
	 * Get the best test result for a given setID
	 * 
	 * @param langsetId
	 *            - the setID
	 * @return string representation of integer value
	 */
	public static String getBestResultById(int langsetId)
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		Log.d("TAG", "Langset id is: " + langsetId);

		String query = "SELECT MAX(score) as best_score FROM "
				+ DatabaseAdapter.TABLE_TEST_RESULTS
				+ " WHERE profileID = ? AND langsetID = ?";
		Cursor c = sDb.rawQuery(query, new String[]
		{Integer.toString(LanguagetutorActivity.currentProfile.profileID),
				Integer.toString(langsetId)});
		if (c.moveToFirst())
		{
			Log.d(TAG, "Returning: "
					+ Integer.toString(c.getInt(c.getColumnIndex("best_score")))); // TRACE
			return Integer.toString(c.getInt(c.getColumnIndex("best_score")));
		}
		else
		{
			return "N/A";
		}
	}

	/**
	 * Get the setID of a topic based on the topic name
	 * 
	 * @param topicName
	 *            - name of the topic
	 * @return the setID of the named topic
	 */
	public static int getLangsetIdByName(String topicName)
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		Cursor c = sDb.query(DatabaseAdapter.TABLE_TOPIC, new String[]
		{"setID"}, "name = " + "?", new String[]
		{topicName}, null, null, null);
		c.moveToFirst();

		int topicId = c.getInt(0);
		Log.d(TAG, "Topic Id: " + topicId);
		return topicId;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}

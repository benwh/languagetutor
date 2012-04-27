package ncl.team22.languagetutor.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ncl.team22.languagetutor.LanguagetutorActivity;

/**
 * Class to represent a LanguageEntity, a word or phrase to be used in the
 * application
 * 
 * @author Ben Wheatley
 * 
 */
public class LanguageEntity
{
	public int					entityID;
	public boolean				phrase;
	public boolean				phrase_partial;
	public String				source_text;
	public String				dest_text;
	public boolean				audio_asset;
	public boolean				image_asset;

	public static final String	TAG	= "LT-LanguageEntity";

	/**
	 * Instantiates a new language entity.
	 * 
	 * @param entityID
	 *            the entity id
	 * @param phrase
	 *            the phrase
	 * @param phrase_partial
	 *            the phrase_partial
	 * @param source_text
	 *            the source_text
	 * @param dest_text
	 *            the dest_text
	 * @param audio_asset
	 *            the audio_asset
	 * @param image_asset
	 *            the image_asset
	 */
	public LanguageEntity(int entityID, boolean phrase, boolean phrase_partial,
			String source_text, String dest_text, boolean audio_asset,
			boolean image_asset)
	{
		this.entityID = entityID;
		this.phrase = phrase;
		this.phrase_partial = phrase_partial;
		this.source_text = source_text;
		this.dest_text = dest_text;
		this.audio_asset = audio_asset;
		this.image_asset = image_asset;
	}

	/**
	 * Get the LanguageEntity as a English word/phrase
	 * 
	 * @return the English word/phrase
	 */
	public String toSourceString()
	{
		return this.source_text;
	}

	/**
	 * Get the LanguageEntity as a Spanish word/phrase
	 * 
	 * @return the Spanish word/phrase
	 */
	public String toDestString()
	{
		return this.dest_text;
	}

	@Override
	public String toString()
	{
		return new String("[" + "LanguageEntity" + ": " + "entityID="
				+ this.entityID + ", src_text=" + this.source_text
				+ ", dst_text=" + this.dest_text + "]");
	}

	/**
	 * Get the easiness factor of this {@link LanguageEntity}
	 * 
	 * @return The easiness factor of the entity
	 */
	public float getEFactor()
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();
		int currentProfId = LanguagetutorActivity.currentProfile.profileID;

		Cursor c = sDb.query(DatabaseAdapter.TABLE_ENTITY_PROGRESS, new String[]
		{"entityID", "efactor"}, "entityID = ? AND profileID = ?", new String[]
		{Integer.toString(this.entityID), Integer.toString(currentProfId)}, null, null, null);

		c.moveToFirst();
		float res = c.getFloat(c.getColumnIndex("efactor"));
		sDb.close();
		return res;
	}

	/**
	 * Gets the current repetition.
	 * 
	 * @return the current repetition
	 */
	public int getCurrentRepetition()
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();
		int currentProfId = LanguagetutorActivity.currentProfile.profileID;

		Cursor c = sDb.query(DatabaseAdapter.TABLE_ENTITY_PROGRESS, new String[]
		{"entityID", "current_repetition"}, "entityID = ? AND profileID = ?", new String[]
		{Integer.toString(this.entityID), Integer.toString(currentProfId)}, null, null, null);

		c.moveToFirst();
		int res = c.getInt(c.getColumnIndex("current_repetition"));
		sDb.close();
		return res;
	}

	/**
	 * Gets the repetition interval.
	 * 
	 * @return the repetition interval
	 */
	public int getRepetitionInterval()
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();
		int currentProfId = LanguagetutorActivity.currentProfile.profileID;

		Cursor c = sDb.query(DatabaseAdapter.TABLE_ENTITY_PROGRESS, new String[]
		{"entityID", "repetition_interval"}, "entityID = ? AND profileID = ?", new String[]
		{Integer.toString(this.entityID), Integer.toString(currentProfId)}, null, null, null);

		c.moveToFirst();
		int res = c.getInt(c.getColumnIndex("repetition_interval"));
		sDb.close();
		return res;
	}

	/**
	 * Gets the repetition next due.
	 * 
	 * @return the repetition next due
	 */
	public int getRepetitionNextDue()
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();
		int currentProfId = LanguagetutorActivity.currentProfile.profileID;

		Cursor c = sDb.query(DatabaseAdapter.TABLE_ENTITY_PROGRESS, new String[]
		{"entityID", "repetition_nextdue"}, "entityID = ? AND profileID = ?", new String[]
		{Integer.toString(this.entityID), Integer.toString(currentProfId)}, null, null, null);

		c.moveToFirst();
		int res = c.getInt(c.getColumnIndex("repetition_nextdue"));
		sDb.close();
		return res;
	}

	/**
	 * Sets the progress.
	 * 
	 * @param efactor
	 *            The new easiness factor
	 * @param currentrep
	 *            The new value of number of repetitions
	 * @param repinterval
	 *            The new repetition interval
	 * @param repnextdue
	 *            The next time this entity will be repeated
	 */
	public void setProgress(float efactor, int currentrep, int repinterval,
			int repnextdue)
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();
		int currentProfId = LanguagetutorActivity.currentProfile.profileID;

		ContentValues cv = new ContentValues();
		cv.put("efactor", efactor);
		cv.put("current_repetition", currentrep);
		cv.put("repetition_interval", repinterval);
		cv.put("repetition_nextdue", repnextdue);

		sDb.update(DatabaseAdapter.TABLE_ENTITY_PROGRESS, cv, "entityID = ? AND profileID = ?", new String[]
		{Integer.toString(this.entityID), Integer.toString(currentProfId)});
		sDb.close();
	}

	/**
	 * If no entry in the entity_progress table exists then create one, with
	 * repetition_nextdue set to 0, signifying the entity has been learnt but
	 * not yet reviewed
	 */
	public void setLearnt()
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();
		int currentProfId = LanguagetutorActivity.currentProfile.profileID;

		Cursor c = sDb.query(DatabaseAdapter.TABLE_ENTITY_PROGRESS, new String[]
		{"entityID", "profileID"}, "entityID = ? AND profileID = ?", new String[]
		{Integer.toString(this.entityID), Integer.toString(currentProfId)}, null, null, null);

		if (c.getCount() > 0)
		{
			// Already exists, do nothing
		}
		else
		{
			// Insert new
			Log.d(TAG, "ne");
			ContentValues cv = new ContentValues();
			cv.put("entityID", this.entityID);
			cv.put("profileID", currentProfId);
			// SM-2 #2: "With all items associate an E-Factor equal to 2.5"
			cv.put("efactor", "2.5");
			cv.put("repetition_nextdue", 0);

			sDb.insert(DatabaseAdapter.TABLE_ENTITY_PROGRESS, null, cv);
		}

		sDb.close();
	}

}

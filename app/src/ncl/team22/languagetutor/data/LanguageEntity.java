package ncl.team22.languagetutor.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ncl.team22.languagetutor.LanguagetutorActivity;

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

	// If no entry in the entity_progress table exists then create one, with
	// repetition_nextdue set to 0, signifying the entity has been learnt but
	// not yet reviewed
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

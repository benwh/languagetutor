package ncl.team22.languagetutor.data;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ncl.team22.languagetutor.LanguagetutorActivity;

/**
 * An implementation of the SuperMemo SM-2 algorithm.
 * 
 * @author Ben Wheatley
 * 
 */
public class SRAlgorithm
{

	private static final String	TAG					= "LT-SRAlgorithm";

	public static final int		REVIEW_MAX_ITEMS	= 15;

	public static final float	EFACTOR_MIN			= 1.3f;
	public static final int		SM_MAXSCORE			= 5;

	public static void updateEntityFromScore(LanguageEntity entity, int rating)
	{
		int currentTimestamp = (int) (new java.util.Date(System.currentTimeMillis()).getTime() / 1000);

		// Subtract 1 from rating, as we want the first star to signify a rating
		// of 0, not 1; and the last star 5 not 6
		int quality = (SM_MAXSCORE - (rating - 1));

		float newEF;

		float eFactor = entity.getEFactor();
		int currentInterval = entity.getRepetitionInterval();
		int repNumber = entity.getCurrentRepetition() + 1;
		int nextDueTimestamp = entity.getRepetitionNextDue();
		// If timestamp is 0 then set it to now
		if (nextDueTimestamp == 0)
		{
			nextDueTimestamp = currentTimestamp;
		}
		// If currentInterval is 0 (i.e. entity has not been reviewed
		// previously) then set to 1, as that's the first increment in SM-2
		if (currentInterval == 0)
		{
			currentInterval = 1;
		}

		// SM-2: EF':=EF+(0.1-(5-q)*(0.08+(5-q)*0.02))
		newEF = (eFactor + (0.1f - quality * (0.08f + quality * 0.02f)));

		// SM-2: "If EF is less than 1.3 then let EF be 1.3"
		if (newEF < EFACTOR_MIN)
		{
			newEF = EFACTOR_MIN;
		}

		// SM-2: "If the quality response was lower than 3 then start
		// repetitions for the item from the beginning without changing the
		// E-Factor"
		if (rating < 3)
		{
			repNumber = 1;
			newEF = eFactor;
		}

		// Set interval for next review
		//
		int daysToAdd = 0;

		if (repNumber == 1)
		{
			daysToAdd = 1;
		}
		else if (repNumber == 2)
		{
			daysToAdd = 6;
		}
		else if (repNumber > 2)
		{
			// SM-2:
			// "If interval is a fraction, round it up to the nearest integer."
			daysToAdd = Math.round(currentInterval * newEF);
		}
		// Returns a negative epoch??
		// cal.setTimeInMillis(currentTimestamp * 1000);
		// cal.add(Calendar.DATE, daysToAdd);
		// nextDueTimestamp = (int) (cal.getTimeInMillis() / 1000);

		nextDueTimestamp = (nextDueTimestamp + (86400 * daysToAdd));

		Log.d(TAG, "Updating entityID:" + entity.entityID + ": EF=" + newEF
				+ " rep=" + repNumber + " interval=" + daysToAdd + " nextdue="
				+ nextDueTimestamp);
		entity.setProgress(newEF, repNumber, daysToAdd, nextDueTimestamp);

	}

	public static ArrayList<LanguageEntity> getEntitiesForReview()
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();
		ArrayList<LanguageEntity> entities = new ArrayList<LanguageEntity>();

		int currentProfId = LanguagetutorActivity.currentProfile.profileID;

		// First get all entities which have been "Learnt", but not yet
		// reviewed. These are entities which have a repetition_nextdue of 0.
		// Sorted in order of ascending entityID, not that it should matter too
		// much
		// JOIN so that we don't have to do another query when we want to add
		// the entity to the arraylist
		Cursor c = sDb.query("langentity le INNER JOIN entity_progress ep ON (le.entityID = ep.entityID)", null, "ep.profileID = ? AND ep.repetition_nextdue = 0", new String[]
		{Integer.toString(currentProfId)}, null, null, "le.entityID ASC");

		Log.d(TAG, "Got " + c.getCount() + " entities for first-time review");

		c.moveToFirst();
		while (!c.isAfterLast() && entities.size() < REVIEW_MAX_ITEMS)
		{
			LanguageEntity e = new LanguageEntity(c.getInt(0), (c.getInt(1) > 0), (c.getInt(2) > 0), c.getString(3), c.getString(4), (c.getInt(5) > 0), (c.getInt(6) > 0));
			entities.add(e);
			c.moveToNext();
		}

		// Next get all entities which have a due date of <= NOW, but don't have
		// a repetition_nextdue of 0 (i.e. have been reviewed previously).
		if (entities.size() < REVIEW_MAX_ITEMS)
		{
			int currentTimestamp = (int) (new java.util.Date(System.currentTimeMillis()).getTime() / 1000);

			Cursor d = sDb.query("langentity le INNER JOIN entity_progress ep ON (le.entityID = ep.entityID)", null, "ep.profileID = ? AND ep.repetition_nextdue != 0 AND ep.repetition_nextdue <= ?", new String[]
			{Integer.toString(currentProfId),
					Integer.toString(currentTimestamp)}, null, null, "ep.repetition_nextdue ASC");

			Log.d(TAG, "Got " + d.getCount() + " entities for another review");

			d.moveToFirst();
			while (!d.isAfterLast() && entities.size() < REVIEW_MAX_ITEMS)
			{
				LanguageEntity e = new LanguageEntity(d.getInt(0), (d.getInt(1) > 0), (d.getInt(2) > 0), d.getString(3), d.getString(4), (d.getInt(5) > 0), (d.getInt(6) > 0));
				entities.add(e);
				d.moveToNext();
			}
		}

		sDb.close();
		return entities;
	}

	public static int getNumOfReviewableEntities(Topic t)
	{
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();
		int currentProfId = LanguagetutorActivity.currentProfile.profileID;

		// Select all entities in the set of the topic passed as a parameter
		// which have been reviewed once or more. We can tell if an entity has
		// been reviewed before because the repetition_nextdue field will be
		// something other than 0
		Cursor c = sDb.query("langentity le INNER JOIN entity_progress ep ON (le.entityID = ep.entityID) INNER JOIN entity_set es ON (es.entityID = le.entityID) JOIN langset ls ON (ls.setID = es.setID)", null, "ep.profileID = ? AND ls.setID = ? AND ep.repetition_nextdue != 0", new String[]
		{Integer.toString(currentProfId), Integer.toString(t.topicID)}, null, null, "le.entityID ASC");

		int num = c.getCount();

		sDb.close();

		return num;
	}
}

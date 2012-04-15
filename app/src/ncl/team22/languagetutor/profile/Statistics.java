package ncl.team22.languagetutor.profile;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import ncl.team22.languagetutor.LanguagetutorActivity;
import ncl.team22.languagetutor.R;

public class Statistics extends Activity
{
	TextView					bannerView;
	TextView					levelView;
	TextView					favView;
	TextView					leastFaveView;
	TextView					testScoreView;
	TextView					gameScoreView;
	TextView					ratingView;
	TextView					noOFWordsView;
	String						myQuery;

	public static final String	TABLE_ENITITY_PROGRESS	= "entity_progress";
	public static final String	TABLE_LANGENTITY		= "langentity";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics);

		Cursor c;
		SQLiteDatabase sDb = LanguagetutorActivity.sDBa.getWritableDatabase();

		bannerView = (TextView) findViewById(R.id.usernameStatsBanner);
		noOFWordsView = (TextView) findViewById(R.id.numOfWords);
		favView = (TextView) findViewById(R.id.fravrite_word_text);
		leastFaveView = (TextView) findViewById(R.id.least_favrite_word_text);

		testScoreView = (TextView) findViewById(R.id.highest_test_score_text);
		gameScoreView = (TextView) findViewById(R.id.highest_game_score_text);
		ratingView = (TextView) findViewById(R.id.spanish_language_rating_text);
		levelView = (TextView) findViewById(R.id.userleveltext);

		bannerView.setText(LanguagetutorActivity.currentProfile.display_name
				+ "'s statistics");

		c = sDb.query(TABLE_ENITITY_PROGRESS, new String[]
		{"last_attempt"}, "profileID = ? AND last_attempt IS NOT NULL", new String[]
		{Integer.toString(LanguagetutorActivity.currentProfile.profileID)}, null, null, null);
		String noOfWordsPracticed = Integer.toString(c.getCount());
		noOFWordsView.setText(noOfWordsPracticed);

		String fWord;
		myQuery = "SELECT source_text FROM "
				+ TABLE_ENITITY_PROGRESS
				+ " NATURAL JOIN "
				+ TABLE_LANGENTITY
				+ " WHERE profileID = ? AND score != 0 AND score >= (SELECT MAX(score) as word FROM "
				+ TABLE_ENITITY_PROGRESS + " WHERE profileID = ? )";
		c = sDb.rawQuery(myQuery, new String[]
		{Integer.toString(LanguagetutorActivity.currentProfile.profileID)});
		if (c.moveToFirst())
		{
			fWord = c.getString(c.getColumnIndex("source_text"));
		}
		else
		{
			fWord = "N/A";
		}
		favView.setText(fWord);

		String lfWord;
		myQuery = "SELECT source_text FROM "
				+ TABLE_ENITITY_PROGRESS
				+ " NATURAL JOIN "
				+ TABLE_LANGENTITY
				+ " WHERE profileID = ? AND score != 0 AND score <= (SELECT MIN(score) as word FROM "
				+ TABLE_ENITITY_PROGRESS + " WHERE profileID = ? )";
		c = sDb.rawQuery(myQuery, new String[]
		{Integer.toString(LanguagetutorActivity.currentProfile.profileID)});
		if (c.moveToFirst())
		{
			lfWord = c.getString(c.getColumnIndex("source_text"));
		}
		else
		{
			lfWord = "N/A";
		}
		leastFaveView.setText(lfWord);
	}
}

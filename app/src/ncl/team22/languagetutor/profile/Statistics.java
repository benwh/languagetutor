package ncl.team22.languagetutor.profile;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import ncl.team22.languagetutor.LanguagetutorActivity;
import ncl.team22.languagetutor.R;
import ncl.team22.languagetutor.data.DatabaseAdapter;

public class Statistics extends Activity
{
	TextView	bannerView;
	TextView	levelView;
	TextView	favView;
	TextView	leastFaveView;
	TextView	testScoreView;
	TextView	gameScoreView;
	TextView	ratingView;
	TextView	noOFWordsView;
	String		myQuery;

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

		c = sDb.query(DatabaseAdapter.TABLE_ENTITY_PROGRESS, new String[]
		{"last_attempt"}, "profileID = ? AND last_attempt IS NOT NULL", new String[]
		{Integer.toString(LanguagetutorActivity.currentProfile.profileID)}, null, null, null);
		String noOfWordsPracticed = Integer.toString(c.getCount());
		noOFWordsView.setText(noOfWordsPracticed);

		String bestTestResult;
		myQuery = "SELECT MAX(score) as high_score FROM "
				+ DatabaseAdapter.TABLE_TEST_RESULTS + " WHERE profileID = ?";
		c = sDb.rawQuery(myQuery, new String[]
		{Integer.toString(LanguagetutorActivity.currentProfile.profileID)});
		if (c.moveToFirst())
		{
			bestTestResult = Integer.toString(c.getInt(c.getColumnIndex("high_score")));
		}
		else
		{
			bestTestResult = "N/A";
		}
		testScoreView.setText(bestTestResult);

		String bestGameResult;
		myQuery = "SELECT MAX(points) as high_score FROM "
				+ DatabaseAdapter.TABLE_GAME_RESULTS + " WHERE profileID = ?";
		c = sDb.rawQuery(myQuery, new String[]
		{Integer.toString(LanguagetutorActivity.currentProfile.profileID)});
		if (c.moveToFirst())
		{
			bestGameResult = Integer.toString(c.getInt(c.getColumnIndex("high_score")));
		}
		else
		{
			bestGameResult = "N/A";
		}
		gameScoreView.setText(bestGameResult);

		String fWord;
		myQuery = "SELECT source_text FROM "
				+ DatabaseAdapter.TABLE_ENTITY_PROGRESS
				+ " NATURAL JOIN "
				+ DatabaseAdapter.TABLE_LANGENTITY
				+ " WHERE profileID = ? AND score != 0 AND score >= (SELECT MAX(score) as word FROM "
				+ DatabaseAdapter.TABLE_ENTITY_PROGRESS
				+ " WHERE profileID = ? )";
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
				+ DatabaseAdapter.TABLE_ENTITY_PROGRESS
				+ " NATURAL JOIN "
				+ DatabaseAdapter.TABLE_LANGENTITY
				+ " WHERE profileID = ? AND score != 0 AND score <= (SELECT MIN(score) as word FROM "
				+ DatabaseAdapter.TABLE_ENTITY_PROGRESS
				+ " WHERE profileID = ? )";
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

		int ranking = 0;
		for (int i = 0; i < 10; i++)
		{
			myQuery = "SELECT MAX(score) as high_score FROM "
					+ DatabaseAdapter.TABLE_TEST_RESULTS
					+ " WHERE profileID = ? AND langsetID = "
					+ Integer.toString(i);
			c = sDb.rawQuery(myQuery, new String[]
			{Integer.toString(LanguagetutorActivity.currentProfile.profileID)});
			if (c.moveToFirst())
			{
				ranking = ranking + c.getInt(c.getColumnIndex("high_score"));
			}
		}
		ranking = ranking / 10;
		if (ranking < 20)
		{
			ratingView.setText("Foreigner");
		}
		else if (ranking < 40)
		{
			ratingView.setText("Tourist");
		}
		else if (ranking < 60)
		{
			ratingView.setText("Resident");
		}
		else if (ranking < 80)
		{
			ratingView.setText("Bilingual");
		}
		else if (ranking < 100)
		{
			ratingView.setText("Fluent");
		}
		else
		{
			ratingView.setText("Native");
		}

		levelView.setText(Integer.toString(Profile.getUserLevel()));
	}
}

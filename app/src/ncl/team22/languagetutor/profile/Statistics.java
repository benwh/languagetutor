package ncl.team22.languagetutor.profile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import ncl.team22.languagetutor.LanguagetutorActivity;
import ncl.team22.languagetutor.R;

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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics);

		bannerView = (TextView) findViewById(R.id.usernameStatsBanner);
		levelView = (TextView) findViewById(R.id.userleveltext);
		favView = (TextView) findViewById(R.id.fravrite_word_text);
		leastFaveView = (TextView) findViewById(R.id.least_favrite_word_text);
		testScoreView = (TextView) findViewById(R.id.highest_test_score_text);
		gameScoreView = (TextView) findViewById(R.id.highest_game_score_text);
		ratingView = (TextView) findViewById(R.id.spanish_language_rating_text);
		noOFWordsView = (TextView) findViewById(R.id.numOfWords);

		bannerView.setText(LanguagetutorActivity.currentProfile.display_name
				+ "'s statistics");

	}
}

package ncl.team22.languagetutor;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.ViewFlipper;

import ncl.team22.languagetutor.data.LanguageEntity;
import ncl.team22.languagetutor.data.SRAlgorithm;
import ncl.team22.languagetutor.data.Topic;

public class RevisionActivity extends Activity
{

	private static final String			TAG					= "LT-Revision";

	private ViewFlipper					flipper;
	private int							currentItemIndex	= -1;
	private ArrayList<LanguageEntity>	entitiesList		= new ArrayList<LanguageEntity>();

	public static final String			SELECTED_TOPIC		= "ncl.team22.languagetutor.RevisionActivity.SELECTED_TOPIC";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review_main);

		if (getCallingActivity() != null
				&& getCallingActivity().getClassName().equals("ncl.team22.languagetutor.TutorialActivity"))
		{
			Bundle extras = getIntent().getExtras();
			Topic selectedTopic = (Topic) extras.getSerializable(SELECTED_TOPIC);
			ArrayList<LanguageEntity> ents = selectedTopic.getEntities();
			entitiesList.addAll(ents);
			TextView headerText = (TextView) findViewById(R.id.review_start_text);
			headerText.setText("You have " + entitiesList.size()
					+ " items to review");
		}
		else
		{
			Log.d(TAG, "Starting rev session for ALL");
			entitiesList.addAll(SRAlgorithm.getEntitiesForReview());

			TextView headerText = (TextView) findViewById(R.id.review_start_text);
			headerText.setText("You have " + entitiesList.size()
					+ " items to review");
		}

		flipper = (ViewFlipper) findViewById(R.id.review_viewflipper);

		// For some reason this code isn't working, so have to use the line
		// below. Need to watch for changes to the ordering of review_main with
		// the hard-coded index
		// LinearLayout startView = (LinearLayout)
		// flipper.getChildAt(flipper.indexOfChild(findViewById(R.layout.review_start)));
		LinearLayout startView = (LinearLayout) flipper.getChildAt(0);
		Button startButton = (Button) startView.findViewById(R.id.review_start_button);

		View.OnClickListener displayNListener = new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				displayNextItem();
			}
		};

		View.OnClickListener returnMenuListener = new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				finish();
			}
		};

		if (entitiesList.size() == 0)
		{
			startButton.setText("Return to main menu");
			startButton.setOnClickListener(returnMenuListener);
		}
		else
		{
			startButton.setOnClickListener(displayNListener);
		}

	}

	private void displayNextItem()
	{
		currentItemIndex++;
		if (currentItemIndex == entitiesList.size())
		{
			displayComplete();
		}
		else
		{
			// flipper.setDisplayedChild(flipper.indexOfChild(findViewById(R.layout.review_item)));
			flipper.setDisplayedChild(1);

			final LanguageEntity currentEnt = entitiesList.get(currentItemIndex);

			final RatingBar rating = (RatingBar) findViewById(R.id.review_item_rating);
			final Button nextBtn = (Button) findViewById(R.id.review_item_nextbutton);
			final TextView srctext = (TextView) findViewById(R.id.review_srctext);
			final TextView dsttext = (TextView) findViewById(R.id.review_dsttext);

			srctext.setText(currentEnt.source_text);
			dsttext.setText("Tap to display");
			rating.setEnabled(false);
			rating.setRating(0);
			nextBtn.setEnabled(false);

			dsttext.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					TextView tv = (TextView) v;
					tv.setText(currentEnt.dest_text);
					tv.setClickable(false);
					rating.setEnabled(true);
				}
			});

			rating.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating,
						boolean fromUser)
				{
					nextBtn.setEnabled(true);
				}
			});

			nextBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					// Cast to int as minimum step size for rating bar is 1
					SRAlgorithm.updateEntityFromScore(currentEnt, (int) rating.getRating());
					// XXX: Score item
					displayNextItem();
				}
			});

		}
	}

	private void displayComplete()
	{
		flipper.setDisplayedChild(2);
		final Button retBtn = (Button) findViewById(R.id.review_end_btn);
		retBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				// A bit redundant now onBackPressed is working
				setResult(RESULT_OK);
				finish();
			}
		});

	}

	@Override
	public void onBackPressed()
	{
		setResult(RESULT_OK);
		finish();
	}
}

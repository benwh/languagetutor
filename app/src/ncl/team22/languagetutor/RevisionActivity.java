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

/**
 * Activity that allows users to review words they have learned
 * 
 * @author Ben
 * 
 */
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

		// If review has been started at the end of a tutorial session then
		// parse the topic that was passed in
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
		// Otherwise call SRAlgorithm.getEntitiesForReview() to get a list of
		// entities that need reviewing
		else
		{
			Log.d(TAG, "Starting rev session for ALL");
			entitiesList.addAll(SRAlgorithm.getEntitiesForReview());

			TextView headerText = (TextView) findViewById(R.id.review_start_text);
			headerText.setText("You have " + entitiesList.size()
					+ " items to review");
		}

		flipper = (ViewFlipper) findViewById(R.id.review_viewflipper);

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

		// If there's no items to review then swap the text and change the
		// listener to return to previous activity
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

	/**
	 * Display next review item.
	 */
	private void displayNextItem()
	{
		currentItemIndex++;
		// Display the completion page if past the last element
		if (currentItemIndex == entitiesList.size())
		{
			displayComplete();
		}
		else
		{
			// Ideally should be:
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

			// When the text is tapped, enable the rating bar
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

			// When the rating is set, enable the next button
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
					// Score the item and display next
					// Cast to int as minimum step size for rating bar is 1
					SRAlgorithm.updateEntityFromScore(currentEnt, (int) rating.getRating());
					displayNextItem();
				}
			});

		}
	}

	/**
	 * Display the 'Review Complete' page
	 */
	private void displayComplete()
	{
		flipper.setDisplayedChild(2);
		final Button retBtn = (Button) findViewById(R.id.review_end_btn);
		retBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
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

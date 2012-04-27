package ncl.team22.languagetutor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import ncl.team22.languagetutor.data.LanguageEntity;
import ncl.team22.languagetutor.data.SRAlgorithm;
import ncl.team22.languagetutor.data.Topic;

/**
 * Activity for taking the user through a tutorial of the selected topic.
 * 
 * @author Ben
 */
public class TutorialActivity extends Activity
{

	private static final String			TAG	= "LT-TutorialActivity";

	private Context						ctx;
	private TutorialPagerAdapter		pagerAdapter;
	private ViewPager					tutPager;
	private Button						yesButton;
	private Button						noButton;

	private ArrayList<Topic>			selectedTopics;
	private ArrayList<LanguageEntity>	entities;
	private int							numPages;
	private AssetManager				assets;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(TAG, "Starting");
		super.onCreate(savedInstanceState);
		ctx = this;
		setContentView(R.layout.tutorial_container);

		Intent topicsel = new Intent(this, TopicSelectionActivity.class);
		topicsel.putExtra(TopicSelectionActivity.ALLOW_ALLTOPICS, true);
		startActivityForResult(topicsel, TopicSelectionActivity.TOPICSELECTION_REQUEST);

	}

	/**
	 * Setup tutorial by retrieving the list of {@link LanguageEntity}s to go
	 * through, setting the header text and displaying a hint if necessary
	 */
	private void setupTutorial()
	{
		entities = new ArrayList<LanguageEntity>();
		Log.d(TAG, "Selected topics: " + selectedTopics.toString());

		for (Topic t : selectedTopics)
		{
			entities.addAll(t.getEntities());
		}

		TextView header = (TextView) findViewById(R.id.tutorial_header);

		if (selectedTopics.size() > 1)
		{
			// If user is taking a mixed review then randomise the order
			Collections.shuffle(entities);
			header.setText("Mixed");
		}
		else
		{
			header.setText(selectedTopics.get(0).name);
		}

		assets = getAssets();

		// Add an extra page for finished screen
		numPages = entities.size() + 1;
		Log.d(TAG, "Entities:" + entities.toString());

		pagerAdapter = new TutorialPagerAdapter();
		tutPager = (ViewPager) findViewById(R.id.tutpager);
		tutPager.setAdapter(pagerAdapter);

		// Display a hint to the user on how to interact with the tutorial
		int currentProfId = LanguagetutorActivity.currentProfile.profileID;
		String hintDispKey = new String("hint_tutswipe_displayed_profile:"
				+ currentProfId);
		SharedPreferences settings = getSharedPreferences(LanguagetutorActivity.PREFS_NAME, MODE_PRIVATE);
		boolean hintAlreadyDisplayed = settings.getBoolean(hintDispKey, false);

		if (!hintAlreadyDisplayed)
		{
			Toast toast = Toast.makeText(getApplicationContext(), "Swipe left to continue", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.BOTTOM, 0, 50);
			toast.show();

			Editor e = settings.edit();
			e.putBoolean(hintDispKey, true);
			e.apply();
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode)
		{
			case TopicSelectionActivity.TOPICSELECTION_REQUEST :
			{
				// If TopicSelection returned ok then pull out the list of
				// topics and set up the tutorial
				if (resultCode == Activity.RESULT_OK)
				{
					@SuppressWarnings("unchecked")
					ArrayList<Topic> topics = (ArrayList<Topic>) data.getSerializableExtra(TopicSelectionActivity.SELECTED_TOPICS);
					selectedTopics = topics;
					setupTutorial();

				}
				else if (resultCode == Activity.RESULT_CANCELED)
				{
					finish();
				}
				break;
			}
		}
	}

	private class TutorialPagerAdapter extends PagerAdapter
	{

		@Override
		public void destroyItem(ViewGroup vp, int pos, Object v)
		{
			((ViewPager) vp).removeView((LinearLayout) v);
		}

		/**
		 * Sets up pages for each page of the ViewPager
		 * 
		 * @see android.support.v4.view.PagerAdapter#instantiateItem(android.view
		 *      .ViewGroup, int)
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			Log.d(TAG, "Instantiating position " + position);
			ViewGroup lv;

			// Display tutorial complete page if on last page
			if (position == numPages - 1)
			{
				// Don't offer to review if user has taken a mixed tutorial
				// OR
				// A topic isn't newly learnt. This is defined by whether it has
				// any entities which have a next repetition date set, meaning
				// they've been reviewed. This doesn't cover the case where only
				// some entities in a set have been reviewed, but in that case
				// the user will review those entities whenever they start a
				// review directly.
				if (selectedTopics.size() > 1
						|| (SRAlgorithm.getNumOfReviewableEntities(selectedTopics.get(0)) > 0))
				{
					lv = (LinearLayout) View.inflate(ctx, R.layout.tutorial_complete_noreview, null);
				}
				else
				{
					lv = (RelativeLayout) View.inflate(ctx, R.layout.tutorial_complete, null);

					yesButton = (Button) lv.findViewById(R.id.tutorial_review_yes);
					noButton = (Button) lv.findViewById(R.id.tutorial_review_no);

					yesButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v)
						{
							// Need to use startActivityForResult so
							// getCallingActivity() works in RevisionActivity. 1
							// is used as the requestCode as we don't care what
							// type of req it is
							Intent i = new Intent(TutorialActivity.this, ncl.team22.languagetutor.RevisionActivity.class);
							i.putExtra(RevisionActivity.SELECTED_TOPIC, selectedTopics.get(0));
							Log.d(TAG, "Putting "
									+ selectedTopics.get(0).toString());
							startActivityForResult(i, 1);
							finish();
						}
					});

					noButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v)
						{
							finish();
						}
					});
				}
			}
			else
			{

				LanguageEntity currentEnt = entities.get(position);
				if (currentEnt.image_asset)
				{
					lv = (LinearLayout) View.inflate(ctx, R.layout.tutorial_image, null);

					InputStream assetStream = null;
					try
					{
						assetStream = assets.open("tut-pics/"
								+ currentEnt.entityID + ".png");

						BitmapDrawable bm = new BitmapDrawable(getResources(), assetStream);
						ImageView img = (ImageView) lv.findViewById(R.id.tutorial_imageview);
						img.setImageDrawable(bm);
					} catch (IOException ex)
					{
						// If couldn't read asset then go back to non-image
						// layout
						lv = (LinearLayout) View.inflate(ctx, R.layout.tutorial, null);
					} finally
					{
						if (assetStream != null)
						{
							try
							{
								assetStream.close();
							} catch (IOException ex)
							{}
						}
					}

				}
				else
				{
					lv = (LinearLayout) View.inflate(ctx, R.layout.tutorial, null);
				}

				// Set up english and spanish text

				TextView src = (TextView) lv.findViewById(R.id.tutorial_srctext);
				TextView dst = (TextView) lv.findViewById(R.id.tutorial_dsttext);

				entities.get(position).setLearnt();

				src.setText(currentEnt.source_text);
				dst.setText(currentEnt.dest_text);

			}

			((ViewPager) container).addView(lv, 0);

			return lv;

		}

		@Override
		public int getCount()
		{
			return numPages;
		}

		@Override
		public boolean isViewFromObject(View v, Object o)
		{
			return v == ((ViewGroup) o);
		}

	}
}

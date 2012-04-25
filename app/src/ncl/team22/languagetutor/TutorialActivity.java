package ncl.team22.languagetutor;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import ncl.team22.languagetutor.data.LanguageEntity;
import ncl.team22.languagetutor.data.Topic;

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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(TAG, "Starting");
		super.onCreate(savedInstanceState);
		ctx = this;
		setContentView(R.layout.tutorial_container);

		Intent topicsel = new Intent(this, TopicSelectionActivity.class);
		topicsel.putExtra(TopicSelectionActivity.ALLOW_ALLTOPICS, false);
		startActivityForResult(topicsel, TopicSelectionActivity.TOPICSELECTION_REQUEST);

	}

	private void setupTutorial()
	{
		entities = new ArrayList<LanguageEntity>();
		Log.d(TAG, "Selected topics: " + selectedTopics.toString());

		for (Topic t : selectedTopics)
		{
			entities.addAll(t.getEntities());
		}

		// Add an extra page for finished screen
		numPages = entities.size() + 1;
		Log.d(TAG, "Entities:" + entities.toString());

		pagerAdapter = new TutorialPagerAdapter();
		tutPager = (ViewPager) findViewById(R.id.tutpager);
		tutPager.setAdapter(pagerAdapter);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode)
		{
			case TopicSelectionActivity.TOPICSELECTION_REQUEST :
			{
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

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			Log.d(TAG, "Instantiating position " + position);
			LinearLayout lv;

			// Display tutorial complete page if on last page
			if (position == numPages - 1)
			{
				lv = (LinearLayout) View.inflate(ctx, R.layout.tutorial_complete, null);

				yesButton = (Button) lv.findViewById(R.id.tutorial_review_yes);
				noButton = (Button) lv.findViewById(R.id.tutorial_review_no);

				yesButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v)
					{
						// Need to use startActivityForResult so
						// getCallingActivity() works in RevisionActivity. 1 is
						// used as the requestCode as we don't care what type of
						// req it is
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
			else
			{
				lv = (LinearLayout) View.inflate(ctx, R.layout.tutorial, null);

				TextView src = (TextView) lv.findViewById(R.id.tutorial_srctext);
				TextView dst = (TextView) lv.findViewById(R.id.tutorial_dsttext);

				entities.get(position).setLearnt();

				src.setText(entities.get(position).source_text);
				dst.setText(entities.get(position).dest_text);

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
			return v == ((LinearLayout) o);
		}

	}
}

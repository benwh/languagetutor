package ncl.team22.languagetutor;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ViewFlipper;

import ncl.team22.languagetutor.data.Topic;
import ncl.team22.languagetutor.profile.Profile;

public class TopicSelectionActivity extends Activity
{

	public static final String	SELECTED_TOPICS		= "ncl.team22.languagetutor.TopicSelectionActivity.SELECTED_TOPICS";
	public static final String	ALLOW_ALLTOPICS		= "ncl.team22.languagetutor.TopicSelectionActivity.ALLOW_ALLTOPICS";

	private ViewFlipper			flipper;
	private boolean				displayMixedButton	= true;
	private static final String	TAG					= "LT-TopicSelection";
	private int					userLevel			= Profile.getUserLevel();

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topicselection_main);

		displayMixedButton = getIntent().getBooleanExtra(ALLOW_ALLTOPICS, true);

		flipper = (ViewFlipper) findViewById(R.id.topicselection_viewflipper);

		OnClickListener buttonListener = new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				displayTopics(Integer.parseInt((String) v.getTag()));
			}
		};

		findViewById(R.id.level1).setOnClickListener(buttonListener);
		findViewById(R.id.level2).setOnClickListener(buttonListener);
		if (userLevel < 2)
		{
			findViewById(R.id.level2).setClickable(false);
		}
		findViewById(R.id.level3).setOnClickListener(buttonListener);
		if (userLevel < 3)
		{
			findViewById(R.id.level3).setClickable(false);
		}
	}

	public void displayTopics(int level)
	{

		// flipper.showNext() ?
		flipper.setDisplayedChild(flipper.indexOfChild(findViewById(R.layout.topicselection_topics)));
		Log.d(TAG, "Displaying topics for level " + level);

		final ArrayList<Topic> topics = Topic.getTopics(level);

		LinearLayout topicmain = (LinearLayout) flipper.findViewById(R.id.topicselection_topics);
		final ListView topiclist = (ListView) topicmain.findViewById(android.R.id.list);
		topiclist.setAdapter(new ArrayAdapter<Topic>(this, R.layout.topicselection_topic_row, topics));

		topiclist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int i, long l)
			{
				ArrayList<Topic> selectedTopics = new ArrayList<Topic>();
				selectedTopics.add((Topic) topiclist.getItemAtPosition(i));

				Intent resultIntent = new Intent();
				resultIntent.putExtra(SELECTED_TOPICS, selectedTopics);

				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});

		Button mixedButton = (Button) findViewById(R.id.mixtest);
		// Don't display the All Topics button if told not to by parent activity
		if (displayMixedButton == false)
		{
			mixedButton.setVisibility(View.GONE);
		}

		mixedButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				ArrayList<Topic> selectedTopics = new ArrayList<Topic>();
				for (int i = 0; i < topiclist.getCount(); i++)
				{
					selectedTopics.add((Topic) topiclist.getItemAtPosition(i));
				}

				Intent resultIntent = new Intent();
				resultIntent.putExtra(SELECTED_TOPICS, selectedTopics);

				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});

	}

	@Override
	public void onBackPressed()
	{
		if (flipper.getDisplayedChild() != 0)
		{
			flipper.showPrevious();
		}
		else
		{
			super.onBackPressed();
		}
	}
}

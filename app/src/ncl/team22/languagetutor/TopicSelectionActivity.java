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

public class TopicSelectionActivity extends Activity
{

	ViewFlipper					flipper;
	public static final String	SELECTED_TOPICS	= "ncl.team22.languagetutor.TopicSelectionActivity.SELECTED_TOPICS";

	private static final String	TAG				= "LT-TopicSelection";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topicselection_main);

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
		findViewById(R.id.level3).setOnClickListener(buttonListener);

	}

	public void displayTopics(int level)
	{

		flipper.setDisplayedChild(flipper.indexOfChild(findViewById(R.layout.topicselection_topics)));
		Log.d(TAG, "Displaying topics for level " + level);

		final ArrayList<Topic> topics = Topic.getTopics(this, level);

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

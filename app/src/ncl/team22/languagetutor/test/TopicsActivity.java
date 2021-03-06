package ncl.team22.languagetutor.test;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import ncl.team22.languagetutor.R;
import ncl.team22.languagetutor.data.Topic;

/**
 * Activity to allow selection of topics from database, show progress of user on
 * specific tests and allow them to do a mixed test
 * 
 * @author james
 */
public class TopicsActivity extends ListActivity
{
	public static final String			intentTopic	= "ncl.team22.languagetutor.test.selectedTopic";
	private static final String			TAG			= "LT-Topics";

	ArrayList<HashMap<String, String>>	lv			= new ArrayList<HashMap<String, String>>();
	ArrayList<Topic>					topics		= Topic.getTopics(LevelSelectActivity.getLevel());

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_topics);

		SimpleAdapter adapter = new SimpleAdapter(this, lv, R.layout.test_topics_list_row, new String[]
		{"Topic", "Score"}, new int[]
		{R.id.test_topics_list_row1, R.id.test_topics_list_row2});

		populateList();

		setListAdapter(adapter);

		final Button mix = (Button) findViewById(R.id.mixtest);

		mix.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				// Ensure test result and counter values are clear
				TestResult.reset();

				TestActivity.setTestLength(14);
				TestActivity.setMixedTest(true);
				Intent intent = new Intent(TopicsActivity.this, TestActivity.class);
				intent.setFlags(100);
				startActivity(intent);

				Log.d(TAG, "Finish reached...");
				finish();
			}
		});
	}

	/**
	 * Populates the gridview with hashmap data values
	 */
	private void populateList()
	{
		for (int i = 0; i < topics.size(); i++)
		{
			HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("Topic", topics.get(i).name);
			temp.put("Score", Topic.getBestResultById(Topic.getLangsetIdByName(topics.get(i).name))
					+ "%");
			lv.add(temp);
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{

		super.onListItemClick(l, v, position, id);
		try
		{
			// Ensure test result and counter values are clear
			TestResult.reset();
			TestActivity.setTestLength(8);

			// As the ListView is a list of 'Topic's can now just pull
			// the selected Topic out and use it for the intent
			Topic selectedTopic = topics.get(position);
			Intent intent = new Intent(getApplicationContext(), TestActivity.class);

			// Use the intentTopic variable as the name of the extra,
			// which can be used across multiple classes. selectedTopic
			// is added as an Intent extra, as Topic is Serializable
			intent.putExtra(intentTopic, selectedTopic);
			intent.setFlags(98);
			startActivity(intent);

			Log.d(TAG, "Finish reached...");
			finish();
		} catch (Exception e)
		{
			Log.e(TAG, e.toString());
		}
	}
}

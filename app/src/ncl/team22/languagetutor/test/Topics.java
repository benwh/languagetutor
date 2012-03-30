package ncl.team22.languagetutor.test;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import ncl.team22.languagetutor.R;
import ncl.team22.languagetutor.data.Topic;

/**
 * Activity to allow selection of topics from level one bank of database, show
 * progress of user on specific tests and (once all individual tests complete)
 * allow them to do a mixed test
 * 
 * @author james
 */
public class Topics extends ListActivity
{
	private boolean				testsComplete	= false;
	public static final String	intentTopic		= "ncl.team22.languagetutor.test.selectedTopic";
	private static final String	TAG				= "LT-Topics";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_topics);

		final ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		final ArrayList<Topic> topics = Topic.getTopics(Setup.getLevel());
		setListAdapter(new ArrayAdapter<Topic>(this, R.layout.test_topics_list_row, topics));

		// Add tests click-through (experimental code)
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int i, long l)
			{
				try
				{
					// As the ListView is a list of 'Topic's can now just pull
					// the selected Topic out and use it for the intent
					Topic selectedTopic = (Topic) lv.getItemAtPosition(i);
					Intent intent = new Intent(getApplicationContext(), Test.class);

					// Use the intentTopic variable as the name of the extra,
					// which can be used across multiple classes. selectedTopic
					// is added as an Intent extra, as Topic is Serializable
					intent.putExtra(intentTopic, selectedTopic);
					startActivity(intent);
				} catch (Exception e)
				{
					Log.e(TAG, e.toString());
				}
			}
		});

		// Disable mixed test option until all tests are complete
		final Button mix = (Button) findViewById(R.id.mixtest);
		if (testsComplete == true)
		{
			mix.setClickable(true);
		}
		else
		{
			mix.setClickable(false);
			// Sets to approx. 40% opacity
			mix.getBackground().setAlpha(102);
		}
	}
}

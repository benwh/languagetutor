package ncl.team22.languagetutor.test;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
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
public class Topics1 extends ListActivity
{
	private boolean	testsComplete	= false;	// temp variable
												// for testing

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_topics);

		final ArrayList<Topic> temp = Topic.getTopics(this, Setup.getLevel());

		System.out.println("Temp size is: " + temp.size());

		final String[] topicNames = new String[temp.size()];
		for (int i = 0; i < temp.size(); i++)
		{
			topicNames[i] = temp.get(i).toString();
		}

		setListAdapter(new ArrayAdapter<String>(this, R.layout.test_topics_list_row, topicNames));

		final ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		// Add tests click-through (experimental code)
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int i, long l)
			{
				try
				{
					// Passes name of topic along as data to Test1.java (this
					// could be useful in order to form a where clause to get
					// the setId, and then use that to return a number of (or
					// all) entities for use in the test)
					String topicId = lv.getItemAtPosition(i).toString();
					Intent intent = new Intent(getApplicationContext(), Test1.class);
					intent.putExtra("ncl.team22.languagetutor.test.topicId", topicId);
					startActivity(intent);
				} catch (Exception e)
				{
					System.out.println("ERROR" + e);
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

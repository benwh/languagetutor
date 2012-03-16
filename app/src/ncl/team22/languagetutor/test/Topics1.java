package ncl.team22.languagetutor.test;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
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
	final static int	level			= 1;
	private boolean		testsComplete	= false;	// temp variable
													// for testing

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_topics);

		ArrayList<Topic> temp = Topic.getTopics(this, level);

		System.out.println("Temp size is: " + temp.size());

		String[] topicNames = new String[temp.size()];
		for (int i = 0; i < temp.size(); i++)
		{
			topicNames[i] = temp.get(i).toString();
		}

		setListAdapter(new ArrayAdapter<String>(this, R.layout.test_topics_list_row, topicNames));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

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

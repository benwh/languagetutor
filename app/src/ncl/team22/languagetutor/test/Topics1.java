package ncl.team22.languagetutor.test;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import ncl.team22.languagetutor.R;
import ncl.team22.languagetutor.data.LanguageSetManager;
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
	final static int			level			= 1;
	public static final String	TABLE_LANGSET	= "langset";
	private LanguageSetManager	lsAdapter;
	private boolean				testsComplete	= false;		// temp variable
																// for testing

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_topics);
		lsAdapter = new LanguageSetManager(this);

		ArrayList<Topic> temp = new ArrayList<Topic>();
		temp.add(new Topic(0, "Test", 1, false, true));
		temp.add(new Topic(1, "Another", 1, false, true));
		temp.add(new Topic(2, "Third", 1, false, true));

		System.out.println("Temp size is: " + temp.size());
		// just trying this hard coded data until stuff implemented
		// I'm thinking of adding toString override for Topic
		// to display the name perhaps?
		// then making the list adapter of type string instead
		String[] temp2 = new String[temp.size()];
		for (int i = 0; i < temp.size(); i++)
		{
			temp2[i] = temp.get(i).toString();
		}

		setListAdapter(new ArrayAdapter<String>(this, R.layout.test_topics_list_row, temp2));
		lsAdapter.getDb().close();

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

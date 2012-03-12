package ncl.team22.languagetutor.test;

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

		setListAdapter(new ArrayAdapter<Topic>(this,
				R.layout.test_topics_list_row, lsAdapter.getTopics()));

		ListView lv = getListView();

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

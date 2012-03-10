package ncl.team22.languagetutor.test;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

import ncl.team22.languagetutor.R;
import ncl.team22.languagetutor.data.DatabaseAdapter;

/**
 * Activity to allow selection of topics from level one bank of database, show
 * progress of user on specific tests and (once all individual tests complete)
 * allow them to do a mixed test
 * 
 * @author james
 */
public class Topics1 extends ListActivity
{
	final static int level = 1;
	public static final String TABLE_LANGSET = "langset";
	private DatabaseAdapter dbAdapter;
	private SQLiteDatabase db;
	private boolean testsComplete = false; // temp variable for testing

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_topics);
		dbAdapter = new DatabaseAdapter(this);
		db = dbAdapter.getWritableDatabase();
		// populateListView();

		// Disable mixed test option until all tests are complete
		final Button mix = (Button) findViewById(R.id.mixtest);
		if (testsComplete == true) {
			mix.setClickable(true);
		} else {
			mix.setClickable(false);
			// Sets to approx. 40% opacity
			mix.getBackground().setAlpha(102);
		}
	}

	private Cursor getSetName()
	{
		return db.query(TABLE_LANGSET, new String[]
		{"setID _id", "name"}, "level = 1", null, null, null, null);

	}

	private void populateListView()
	{
		Cursor c = getSetName();
		startManagingCursor(c);

		String[] from = new String[]
		{"setID, name"};
		int[] to = new int[]
		{R.id.test_topics_list_row};

		SimpleCursorAdapter tests = new SimpleCursorAdapter(this,
				R.layout.test_topics, c, from, to);
		setListAdapter(tests);
		dbAdapter.close();
	}

}

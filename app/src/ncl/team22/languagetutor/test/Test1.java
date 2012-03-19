package ncl.team22.languagetutor.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import ncl.team22.languagetutor.R;
import ncl.team22.languagetutor.data.DatabaseAdapter;

/**
 * Activity for first style of test, multiple choice questions
 * 
 * @author james
 */
public class Test1 extends Activity
{
	public static final String	TABLE_TOPIC	= "langset";
	// public static final String TABLE_ENTITY_SET = "entity_set";
	public static int			setId		= 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test1);

		Intent i = getIntent();
		Bundle extras = i.getExtras();

		String topicId = i.getStringExtra("ncl.team22.languagetutor.test.topicId");

		// At the moment this just shows which topic you selected to go to the
		// test, but eventually it will formulate some kind of question -
		// however, this value could be used to show the kind of test you are
		// doing as well
		final TextView question = (TextView) findViewById(R.id.multi_question);
		question.setText("You picked a test on " + topicId);
	}

	// Think this could be needed in a class of it's own called Entity perhaps,
	// similar to topics (putting stuff in a nicer data structure to work with?
	public void getEntities(Context ctx, String topicId)
	{
		DatabaseAdapter sDba = new DatabaseAdapter(ctx);
		SQLiteDatabase sDb = sDba.getWritableDatabase();

		Cursor c = sDb.query(TABLE_TOPIC, new String[]
		{"setID"}, "name = " + "?", new String[]
		{topicId}, null, null, null);
		c.moveToFirst();
		// Set the id for next query
		setId = c.getInt(0);

		// NEED TO DO JOIN
		// then return data based on set id of entities, further methods to
		// break up or work with this data perhaps

		sDba.close();

		// return (if applicable)
	}
}

package ncl.team22.languagetutor.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ncl.team22.languagetutor.R;
import ncl.team22.languagetutor.data.Topic;

/**
 * Activity for first style of test, multiple choice questions
 * 
 * @author james
 */
public class Test1 extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test1);

		// Pull the selected topic out of the intent from the previous activity
		Intent i = getIntent();
		Topic selectedTopic = (Topic) i.getSerializableExtra(Topics1.intentTopic);

		// At the moment this just shows which topic you selected to go to the
		// test, but eventually it will formulate some kind of question -
		// however, this value could be used to show the kind of test you are
		// doing as well
		final TextView question = (TextView) findViewById(R.id.multi_question);
		question.setText("You picked a test on " + selectedTopic.toString());
	}
}

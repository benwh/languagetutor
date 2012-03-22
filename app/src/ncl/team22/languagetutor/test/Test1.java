package ncl.team22.languagetutor.test;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ncl.team22.languagetutor.R;
import ncl.team22.languagetutor.data.LangEntity;

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

		final ArrayList<LangEntity> temp = LangEntity.getEntities(this, topicId);

	}
}

package ncl.team22.languagetutor.test;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import ncl.team22.languagetutor.R;

public class TestReviewGrid extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_review_grid);

		final ArrayList<String> review = TestData.getReviewTest();
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ArrayAdapter<String>(this, R.layout.test_review_grid_row, review));
	}

}

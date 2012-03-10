package ncl.team22.languagetutor.profile;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ncl.team22.languagetutor.R;

public class ProfileSelection extends ListActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_profile_list);
		populateListView();
	}

	private void populateListView()
	{
		Cursor c = Profile.getProfiles(this);
		startManagingCursor(c);

		String[] from = new String[]
		{"display_name"};
		int[] to = new int[]
		{R.id.profile_list_rowtext};

		SimpleCursorAdapter profiles = new SimpleCursorAdapter(this,
				R.layout.login_profile_list_row, c, from, to);
		setListAdapter(profiles);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		Cursor c = Profile.getProfiles(this);
		c.moveToPosition(position);
		Intent i = new Intent(this, ReplacePassword.class);
		startActivity(i);
	}

}

package ncl.team22.languagetutor.profile;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ncl.team22.languagetutor.LanguagetutorActivity;
import ncl.team22.languagetutor.R;

/**
 * Activity used to allow the user to select their profile.
 * 
 * @author Misha
 */
public class ProfileSelection extends ListActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_profile_list);
		populateListView();
	}

	/**
	 * Populates the list with the usernames of all the profiles in the
	 * database.
	 */
	private void populateListView()
	{
		Cursor c = Profile.getProfiles();
		startManagingCursor(c);

		String[] from = new String[]
		{"display_name"};
		int[] to = new int[]
		{R.id.profile_list_rowtext};

		SimpleCursorAdapter profiles = new SimpleCursorAdapter(this, R.layout.login_profile_list_row, c, from, to);
		setListAdapter(profiles);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		Cursor c = Profile.getProfiles();
		c.moveToPosition(position);

		LanguagetutorActivity.currentProfile = Profile.load(c.getPosition() + 1);
		SharedPreferences settings = getSharedPreferences(LanguagetutorActivity.PREFS_NAME, MODE_PRIVATE);
		Editor e = settings.edit();
		e.putInt(LanguagetutorActivity.ACTIVE_PROFILE_ID, LanguagetutorActivity.currentProfile.profileID);
		e.apply();

		Intent i = new Intent(this, ReplacePassword.class);
		i.putExtra("user verified", 1);
		startActivity(i);
		finish();
	}
}

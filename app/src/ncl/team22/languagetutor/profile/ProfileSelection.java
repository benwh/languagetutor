package ncl.team22.languagetutor.profile;

import ncl.team22.languagetutor.R;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

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
		ProfileManager pm = new ProfileManager(this);

		Cursor c = pm.getProfiles();
		startManagingCursor(c);

		String[] from = new String[]{"display_name"};
		int[] to = new int[]{R.id.profile_list_rowtext};

		SimpleCursorAdapter profiles = new SimpleCursorAdapter(this,
				R.layout.login_profile_list_row, c, from, to);
		setListAdapter(profiles);
	}

}

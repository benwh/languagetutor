package ncl.team22.languagetutor;

import ncl.team22.languagetutor.data.DatabaseAdapter;
import android.app.Activity;
import android.os.Bundle;

public class LanguagetutorActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        DatabaseAdapter db = new DatabaseAdapter(this);
        db.getWritableDatabase();
        db.close();
    }
}
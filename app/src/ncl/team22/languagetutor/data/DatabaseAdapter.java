package ncl.team22.languagetutor.data;

import ncl.team22.languagetutor.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAdapter extends SQLiteOpenHelper {
	final static String NAME = "languagetutor";
	final static int VERSION = 1;
	Context ctx;

	public DatabaseAdapter(Context context) {
		super(context, NAME, null, VERSION);
		ctx = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		InputStream sqlfile = ctx.getResources().openRawResource(R.raw.create);
		
		final char[] buffer = new char[0x10000];
		StringBuilder out = new StringBuilder();
		Reader in = new InputStreamReader(sqlfile);
		int read;
		
		try {
			do {
				read = in.read(buffer, 0, buffer.length);
				if (read > 0) {
					out.append(buffer, 0, read);
				}
			} while (read >= 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String result = out.toString();

		db.execSQL(result);
	}

	@Override
	public synchronized SQLiteDatabase getReadableDatabase() {
		// TODO Auto-generated method stub
		return super.getReadableDatabase();
	}

	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		// TODO Auto-generated method stub
		return super.getWritableDatabase();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}

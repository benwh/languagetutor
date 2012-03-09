package ncl.team22.languagetutor.data;

import ncl.team22.languagetutor.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAdapter extends SQLiteOpenHelper {

	public static final String DBNAME = "languagetutor";
	public static final int DBVERSION = 1;

	private static Context ctx;
	private static int SQL_CREATE = R.raw.create;
	private static int SQL_TESTDATA = R.raw.testdata;
	private static final String TAG = "DatabaseAdapter";

	public DatabaseAdapter(Context context) {
		super(context, DBNAME, null, DBVERSION);
		ctx = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String creationSQL = sqlResourceToString(SQL_CREATE);
		String testDataSQL = sqlResourceToString(SQL_TESTDATA);
		try {
			db.execSQL(" PRAGMA foreign_keys = ON ");
			db.execSQL(creationSQL);
			db.execSQL(testDataSQL);
		} catch (SQLException ex) {
			Log.e(TAG, ex.getMessage());
		}
	}

	@Override
	public synchronized SQLiteDatabase getReadableDatabase() {
		return super.getReadableDatabase();
	}

	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		return super.getWritableDatabase();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Add code for dropping tables

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	private String sqlResourceToString(int resource) {
		InputStream sqlfile = null;

		try {
			sqlfile = ctx.getResources().openRawResource(resource);
		} catch (NotFoundException ex) {
			Log.e(TAG, "Failed to open resource " + resource);
		}

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
		return result;
	}
}

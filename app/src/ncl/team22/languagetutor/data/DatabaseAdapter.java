package ncl.team22.languagetutor.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ncl.team22.languagetutor.R;

/**
 * A helper class to manage database creation and version management
 */
public class DatabaseAdapter extends SQLiteOpenHelper
{

	public static final String	DBNAME					= "languagetutor";
	public static final int		DBVERSION				= 7;

	public static final String	TABLE_PROFILE			= "profile";
	public static final String	TABLE_TOPIC				= "langset";
	public static final String	TABLE_TEST_RESULTS		= "test_results";
	public static final String	TABLE_GAME_RESULTS		= "game_results";
	public static final String	TABLE_ENTITY_PROGRESS	= "entity_progress";
	public static final String	TABLE_LANGENTITY		= "langentity";

	private static Context		ctx;
	private static int			SQL_CREATE				= R.raw.create;
	private static int			SQL_TESTDATA			= R.raw.testdata;
	private static int			SQL_DROP				= R.raw.drop;
	private static final String	TAG						= "LT-DatabaseAdapter";

	public DatabaseAdapter(Context context)
	{
		super(context, DBNAME, null, DBVERSION);
		ctx = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String creationSQL = sqlResourceToString(SQL_CREATE);
		String testDataSQL = sqlResourceToString(SQL_TESTDATA);
		try
		{
			db.execSQL(" PRAGMA foreign_keys = ON ");
			String[] tables = Pattern.compile("-- -+[\\r\\n]+--\\sTable.+[\\r\\n]+.+", Pattern.MULTILINE).split(creationSQL);
			Log.w(TAG, "Got " + tables.length + " tables from SQL string");
			for (int i = 0; i < tables.length; i++)
			{
				if (!tables[i].isEmpty())
				{
					Log.w(TAG, "Inserting: " + tables[i].substring(0, 40)
							+ "...");
					db.execSQL(tables[i]);
				}
			}

			String[] testdata = testDataSQL.split(";");
			for (int i = 0; i < testdata.length; i++)
			{
				String curStatement = testdata[i].replace("\n", "");
				if (!curStatement.isEmpty())
				{
					db.execSQL(curStatement);
				}
			}
		} catch (SQLException ex)
		{
			Log.e(TAG, ex.getMessage());
		}
	}

	@Override
	public synchronized SQLiteDatabase getReadableDatabase()
	{
		return super.getReadableDatabase();
	}

	@Override
	public synchronized SQLiteDatabase getWritableDatabase()
	{
		return super.getWritableDatabase();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.i(TAG, "Dropping all tables for DB upgrade from " + oldVersion
				+ " to " + newVersion);
		String dropSQL = sqlResourceToString(SQL_DROP);
		String[] dropstrings = dropSQL.split("\n");
		for (int i = 0; i < dropstrings.length; i++)
		{
			if (!dropstrings[i].isEmpty())
			{
				Log.w(TAG, "Dropping: " + dropstrings[i]);
				db.execSQL(dropstrings[i]);
			}
		}
		onCreate(db);

	}

	@Override
	public void onOpen(SQLiteDatabase db)
	{
		super.onOpen(db);
		if (!db.isReadOnly())
		{
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	/**
	 * Converts resource from raw folder into a String
	 * 
	 * @param resource
	 *            - the int value of the raw resource
	 * @return the result as a String
	 */
	private String sqlResourceToString(int resource)
	{
		InputStream sqlfile = null;

		try
		{
			sqlfile = ctx.getResources().openRawResource(resource);
		} catch (NotFoundException ex)
		{
			Log.e(TAG, "Failed to open resource " + resource);
		}

		final char[] buffer = new char[0x10000];
		StringBuilder out = new StringBuilder();
		Reader in = new InputStreamReader(sqlfile);
		int read;

		try
		{
			do
			{
				read = in.read(buffer, 0, buffer.length);
				if (read > 0)
				{
					out.append(buffer, 0, read);
				}
			} while (read >= 0);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		String result = out.toString().trim();
		return result;
	}
}

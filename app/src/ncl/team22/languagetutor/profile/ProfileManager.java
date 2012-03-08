package ncl.team22.languagetutor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProfileManager extends SQLiteOpenHelper {

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	//Constructor
	public ProfileManager (Context con)
	{
		//TO DO
	}

	//Opens the database probaly with a database handler so values within it can be altered.
	public void open()
	{
		//TO DO
	}
	
	//Closes the database handler
	public void close()
	{
			
	}
	
	//Creates a new profile in the database with the values given
	public void createProfile(String userName, String password, String secrateQ, String secrateA)
	{
		//TO DO
	}
		
	//Returns a specific users password
	public String getPass(String userName)
	{
		String pass ="";
		//TO DO
		return pass;
	}
		
	//Returns a specific users ID
	public int getID(String userName)
	{
		int id =0;
		//TO DO
		return id;
	}
		
	//Checks the username given exists in the database
	public boolean cheackName(String userName)
	{
		boolean exists = false;
		//TO DO
		return exists;
	}
}

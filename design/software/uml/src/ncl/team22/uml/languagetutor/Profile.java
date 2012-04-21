package ncl.team22.uml.languagetutor;

import java.util.ArrayList;

public class Profile
{

	public int					profileID;
	public String				display_name;
	public String				password_hash;
	public String				secret_q;
	public String				secret_a;
	public int					theme;

	public Profile(int profileID, String display_name, String password_hash,
			String secret_q, String secret_a, int theme)
	{
		this.profileID = profileID;
		this.display_name = display_name;
		this.password_hash = password_hash;
		this.secret_q = secret_q;
		this.secret_a = secret_a;
		this.theme = theme;
	}

	public static ArrayList<Profile> getProfiles()
	{
		return null;
	}

	// Creates a new profile in the database with the values given
	public static Profile create(String userName, String password,
			String secretQ, String secretA)
	{
		return null;
	}

	public static Profile load(int profileID)
	{
		return null;
	}

	public static Profile authenticate(String user, String pass)
	{
		return null;
	}

	// Checks the username given exists in the database
	public static boolean checkName(String userName)
	{
		return false;
	}

	public static String hashPassword(String password)
	{
		return null;
	}

}

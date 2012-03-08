package ncl.team22.languagetutor.profile;

import ncl.team22.languagetutor.data.DatabaseAdapter;

public class ProfileManager {

	private DatabaseAdapter dbAdapter;

	// Constructor
	public ProfileManager() {
		// TODO ProfileManager constructor
	}

	// Creates a new profile in the database with the values given
	public void createProfile(String userName, String password, String secretQ,
			String secretA) {
		// TODO createProfile method body
	}

	// Returns a specific users password
	public String getPass(String userName) {
		String pass = "";
		// TODO getPass method body
		return pass;
	}

	// Checks the username given exists in the database
	public boolean checkName(String userName) {
		boolean exists = false;
		// TODO checkName method body
		return exists;
	}
}

package ncl.team22.uml.languagetutor;

public class Profile
{
	public boolean passworded;
	public OptionsManager optMan;
	public GameResultManager gameResMan;
	public TestResultManager testResMan;
	public boolean authenticate(String password) 	{ return true; }
	/**
	 * @uml.property  name="testResultManager"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="profile:ncl.team22.uml.languagetutor.TestResultManager"
	 */
	private TestResultManager testResultManager = new ncl.team22.uml.languagetutor.TestResultManager();
	/**
	 * Getter of the property <tt>testResultManager</tt>
	 * @return  Returns the testResultManager.
	 * @uml.property  name="testResultManager"
	 */
	public TestResultManager getTestResultManager()
	{
		return testResultManager;
	}
	/**
	 * Setter of the property <tt>testResultManager</tt>
	 * @param testResultManager  The testResultManager to set.
	 * @uml.property  name="testResultManager"
	 */
	public void setTestResultManager(TestResultManager testResultManager)
	{
		this.testResultManager = testResultManager;
	}
	/**
	 * @uml.property  name="gameResultManager"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="profile:ncl.team22.uml.languagetutor.GameResultManager"
	 */
	private GameResultManager gameResultManager = new ncl.team22.uml.languagetutor.GameResultManager();
	/**
	 * Getter of the property <tt>gameResultManager</tt>
	 * @return  Returns the gameResultManager.
	 * @uml.property  name="gameResultManager"
	 */
	public GameResultManager getGameResultManager()
	{
		return gameResultManager;
	}
	/**
	 * Setter of the property <tt>gameResultManager</tt>
	 * @param gameResultManager  The gameResultManager to set.
	 * @uml.property  name="gameResultManager"
	 */
	public void setGameResultManager(GameResultManager gameResultManager)
	{
		this.gameResultManager = gameResultManager;
	}
	/**
	 * @uml.property  name="profileManager"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="profile1:ncl.team22.uml.languagetutor.ProfileManager"
	 */
	private ProfileManager profileManager = new ncl.team22.uml.languagetutor.ProfileManager();
	/**
	 * Getter of the property <tt>profileManager</tt>
	 * @return  Returns the profileManager.
	 * @uml.property  name="profileManager"
	 */
	public ProfileManager getProfileManager()
	{
		return profileManager;
	}
	/**
	 * Setter of the property <tt>profileManager</tt>
	 * @param profileManager  The profileManager to set.
	 * @uml.property  name="profileManager"
	 */
	public void setProfileManager(ProfileManager profileManager)
	{
		this.profileManager = profileManager;
	}
	/**
	 * @uml.property  name="optionsManager"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="profile:ncl.team22.uml.languagetutor.OptionsManager"
	 */
	private OptionsManager optionsManager = new ncl.team22.uml.languagetutor.OptionsManager();
	/**
	 * Getter of the property <tt>optionsManager</tt>
	 * @return  Returns the optionsManager.
	 * @uml.property  name="optionsManager"
	 */
	public OptionsManager getOptionsManager()
	{
		return optionsManager;
	}
	/**
	 * Setter of the property <tt>optionsManager</tt>
	 * @param optionsManager  The optionsManager to set.
	 * @uml.property  name="optionsManager"
	 */
	public void setOptionsManager(OptionsManager optionsManager)
	{
		this.optionsManager = optionsManager;
	}

}

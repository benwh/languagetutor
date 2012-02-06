package ncl.team22.uml.languagetutor;

public class Option
{

	/**
	 * @uml.property  name="optionsManager"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="option:ncl.team22.uml.languagetutor.OptionsManager"
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

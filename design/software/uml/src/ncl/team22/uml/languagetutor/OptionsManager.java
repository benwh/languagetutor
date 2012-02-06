package ncl.team22.uml.languagetutor;

import java.util.Collection;

public class OptionsManager
{
	public Option getOpt(String key) { return null; }
	public void setOpt(Option o) { }
	/**
	 * @uml.property  name="profile"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="optionsManager:ncl.team22.uml.languagetutor.Profile"
	 */
	private Profile profile = new ncl.team22.uml.languagetutor.Profile();
	/**
	 * Getter of the property <tt>profile</tt>
	 * @return  Returns the profile.
	 * @uml.property  name="profile"
	 */
	public Profile getProfile()
	{
		return profile;
	}
	/**
	 * Setter of the property <tt>profile</tt>
	 * @param profile  The profile to set.
	 * @uml.property  name="profile"
	 */
	public void setProfile(Profile profile)
	{
		this.profile = profile;
	}
	/**
	 * @uml.property  name="option"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="optionsManager:ncl.team22.uml.languagetutor.Option"
	 */
	private Collection<Option> option;
	/**
	 * Getter of the property <tt>option</tt>
	 * @return  Returns the option.
	 * @uml.property  name="option"
	 */
	public Collection<Option> getOption()
	{
		return option;
	}
	/**
	 * Setter of the property <tt>option</tt>
	 * @param option  The option to set.
	 * @uml.property  name="option"
	 */
	public void setOption(Collection<Option> option)
	{
		this.option = option;
	};

}

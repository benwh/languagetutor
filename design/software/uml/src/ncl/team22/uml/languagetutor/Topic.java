package ncl.team22.uml.languagetutor;

import java.util.Collection;

public class Topic
{

	/**
	 * @uml.property  name="languageSetManager"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="topic:ncl.team22.uml.languagetutor.LanguageSetManager"
	 */
	private LanguageSetManager languageSetManager = new ncl.team22.uml.languagetutor.LanguageSetManager();

	/**
	 * Getter of the property <tt>languageSetManager</tt>
	 * @return  Returns the languageSetManager.
	 * @uml.property  name="languageSetManager"
	 */
	public LanguageSetManager getLanguageSetManager()
	{
		return languageSetManager;
	}

	/**
	 * Setter of the property <tt>languageSetManager</tt>
	 * @param languageSetManager  The languageSetManager to set.
	 * @uml.property  name="languageSetManager"
	 */
	public void setLanguageSetManager(LanguageSetManager languageSetManager)
	{
		this.languageSetManager = languageSetManager;
	}

	/**
	 * @uml.property  name="languageEntity"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="topic:ncl.team22.uml.languagetutor.LanguageEntity"
	 */
	private Collection<LanguageEntity> languageEntity;

	/**
	 * Getter of the property <tt>languageEntity</tt>
	 * @return  Returns the languageEntity.
	 * @uml.property  name="languageEntity"
	 */
	public Collection<LanguageEntity> getLanguageEntity()
	{
		return languageEntity;
	}

	/**
	 * Setter of the property <tt>languageEntity</tt>
	 * @param languageEntity  The languageEntity to set.
	 * @uml.property  name="languageEntity"
	 */
	public void setLanguageEntity(Collection<LanguageEntity> languageEntity)
	{
		this.languageEntity = languageEntity;
	}

	/** 
	 * @uml.property name="unlocked"
	 */
	boolean unlocked;

	/** 
	 * Getter of the property <tt>unlocked</tt>
	 * @return  Returns the unlocked.
	 * @uml.property  name="unlocked"
	 */
	public boolean isUnlocked()
	{
		return unlocked;
	}

	/** 
	 * Setter of the property <tt>unlocked</tt>
	 * @param unlocked  The unlocked to set.
	 * @uml.property  name="unlocked"
	 */
	public void setUnlocked(boolean unlocked)
	{
		this.unlocked = unlocked;
	}

}

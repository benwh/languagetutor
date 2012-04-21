package ncl.team22.uml.languagetutor;

import java.util.ArrayList;
import java.util.Collection;

public class Topic
{

	public int					topicID;
	public String				name;
	public int					level;
	public boolean				locked;
	public boolean				displayable;

	public Topic(int topicID, String name, int level, boolean locked,
			boolean displayable)
	{
		this.topicID = topicID;
		this.name = name;
		this.level = level;
		this.locked = locked;
		this.displayable = displayable;
	}

	// This is no longer used, may be removed if we don't find another use for
	// it
	public static Topic getTopicById(int topicID)
	{
		return null;
	}

	public static int getIdByTopic(Topic selectedTopic)
	{
		return 0;
	}

	public ArrayList<LanguageEntity> getEntities()
	{
		return null;
	}

	public static ArrayList<LanguageEntity> getAllEntities()
	{
		return null;
	}

	public static ArrayList<Topic> getTopics(int level)
	{
		return null;
	}

	public static ArrayList<Topic> getTopics()
	{
		return null;
	}

	@Override
	public String toString()
	{
		return this.name;
	}

	/** 
	 * @uml.property name="languageEntity"
	 * @uml.associationEnd multiplicity="(1 -1)" inverse="topic:ncl.team22.uml.languagetutor.LanguageEntity"
	 */
	private Collection<LanguageEntity>	languageEntity;

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
}

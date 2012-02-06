package ncl.team22.uml.languagetutor;

import java.util.ArrayList;
import java.util.Collection;


public class LanguageSetManager
{
	public ArrayList<Topic> getTopics() { return null; }
	public ArrayList<LanguageEntity> getEntities(ArrayList<Topic> topicList) { return null; }
	/**
	 * @uml.property  name="topic"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="languageSetManager:ncl.team22.uml.languagetutor.Topic"
	 */
	private Collection<Topic> topic;
	/**
	 * Getter of the property <tt>topic</tt>
	 * @return  Returns the topic.
	 * @uml.property  name="topic"
	 */
	public Collection<Topic> getTopic()
	{
		return topic;
	}
	/**
	 * Setter of the property <tt>topic</tt>
	 * @param topic  The topic to set.
	 * @uml.property  name="topic"
	 */
	public void setTopic(Collection<Topic> topic)
	{
		this.topic = topic;
	}

}

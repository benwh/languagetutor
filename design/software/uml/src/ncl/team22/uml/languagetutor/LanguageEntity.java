package ncl.team22.uml.languagetutor;

public class LanguageEntity
{
	public void updateScore(int score) { }

	/**
	 * @uml.property  name="topic"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="languageEntity:ncl.team22.uml.languagetutor.Topic"
	 */
	private Topic topic = new ncl.team22.uml.languagetutor.Topic();

	/**
	 * Getter of the property <tt>topic</tt>
	 * @return  Returns the topic.
	 * @uml.property  name="topic"
	 */
	public Topic getTopic()
	{
		return topic;
	}

	/**
	 * Setter of the property <tt>topic</tt>
	 * @param topic  The topic to set.
	 * @uml.property  name="topic"
	 */
	public void setTopic(Topic topic)
	{
		this.topic = topic;
	}

}

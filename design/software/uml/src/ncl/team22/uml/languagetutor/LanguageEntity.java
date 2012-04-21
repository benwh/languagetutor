package ncl.team22.uml.languagetutor;

import java.util.Collection;

public class LanguageEntity
{
	public int					entityID;
	public boolean				phrase;
	public boolean				phrase_partial;
	public String				source_text;
	public String				dest_text;
	public boolean				audio_asset;
	public boolean				image_asset;

	public static final String	TAG	= "LT-LanguageEntity";

	public LanguageEntity(int entityID, boolean phrase, boolean phrase_partial,
			String source_text, String dest_text, boolean audio_asset,
			boolean image_asset)
	{
		this.entityID = entityID;
		this.phrase = phrase;
		this.phrase_partial = phrase_partial;
		this.source_text = source_text;
		this.dest_text = dest_text;
		this.audio_asset = audio_asset;
		this.image_asset = image_asset;
	}

	/**
	 * Get the LanguageEntity as a English word/phrase
	 * 
	 * @return the English word/phrase
	 */
	public String toSourceString()
	{
		return this.source_text;
	}

	/**
	 * Get the LanguageEntity as a Spanish word/phrase
	 * 
	 * @return the Spanish word/phrase
	 */
	public String toDestString()
	{
		return this.dest_text;
	}

	public String toString()
	{
		return this.entityID + " " + this.phrase + " " + this.phrase_partial
				+ " " + this.source_text + " " + this.dest_text + " "
				+ this.audio_asset + " " + this.image_asset;
	}

	// TODO: Implement/call scoring algorithm
	public void updateScore(int score)
	{

	}

	/** 
	 * @uml.property name="topic"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="languageEntity:ncl.team22.uml.languagetutor.Topic"
	 */
	private Collection<Topic>	topic;

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

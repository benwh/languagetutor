package ncl.team22.languagetutor.data;

public class LanguageEntity
{
	protected int				entityID;
	protected boolean			phrase;
	protected boolean			phrase_partial;
	protected String			source_text;
	protected String			dest_text;
	protected boolean			audio_asset;
	protected String			image_asset;

	public static final String	TABLE_TOPIC	= "langset";
	public static final String	TAG			= "LT-LanguageEntity";

	public LanguageEntity(int entityID, boolean phrase, boolean phrase_partial,
			String source_text, String dest_text, boolean audio_asset,
			String image_asset)
	{
		entityID = this.entityID;
		phrase = this.phrase;
		phrase_partial = this.phrase_partial;
		source_text = this.source_text;
		dest_text = this.dest_text;
		audio_asset = this.audio_asset;
		image_asset = this.image_asset;
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

}

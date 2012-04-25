package ncl.team22.languagetutor.data;

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

	@Override
	public String toString()
	{
		return new String("[" + "LanguageEntity" + ": " + "entityID="
				+ this.entityID + ", src_text=" + this.source_text
				+ ", dst_text=" + this.dest_text + "]");
	}

	// TODO: Implement/call scoring algorithm
	public void updateScore(int score)
	{

	}

}

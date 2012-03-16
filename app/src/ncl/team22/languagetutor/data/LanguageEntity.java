package ncl.team22.languagetutor.data;

public class LanguageEntity
{
	protected int		entityID;
	protected boolean	phrase;
	protected boolean	phrase_partial;
	protected String	source_text;
	protected String	dest_text;
	protected boolean	audio_asset;
	protected boolean	image_asset;

	// TODO: Implement/call scoring algorithm
	public void updateScore(int score)
	{

	}

}

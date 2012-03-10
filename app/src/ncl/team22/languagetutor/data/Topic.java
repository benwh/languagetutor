package ncl.team22.languagetutor.data;

public class Topic
{
	protected int		topicID;
	protected String	name;
	protected int		level;
	protected boolean	locked;
	protected boolean	displayable;

	public Topic(int topicID, String name, int level, boolean locked,
			boolean displayable)
	{
		this.topicID = topicID;
		this.name = name;
		this.level = level;
		this.locked = locked;
		this.displayable = displayable;
	}

}

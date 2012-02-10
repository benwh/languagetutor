package ncl.team22.uml.languagetutor;


public class GameResult extends Result
{
	/** 
	 * @uml.property name="score"
	 */
	int score;
	/**
	 * @uml.property  name="gameResultManager"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="gameResult:ncl.team22.uml.languagetutor.GameResultManager"
	 */
	private GameResultManager gameResultManager = new ncl.team22.uml.languagetutor.GameResultManager();

	/**
	 * Getter of the property <tt>gameResultManager</tt>
	 * @return  Returns the gameResultManager.
	 * @uml.property  name="gameResultManager"
	 */
	public GameResultManager getGameResultManager()
	{
		return gameResultManager;
	}

	/**
	 * Setter of the property <tt>gameResultManager</tt>
	 * @param gameResultManager  The gameResultManager to set.
	 * @uml.property  name="gameResultManager"
	 */
	public void setGameResultManager(GameResultManager gameResultManager)
	{
		this.gameResultManager = gameResultManager;
	}

}

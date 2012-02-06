package ncl.team22.uml.languagetutor;

import java.util.ArrayList;
import java.util.Collection;

public class GameResultManager implements ResultManager<GameResult>
{

	@Override
	public ArrayList<GameResult> getResults()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addResult(GameResult result)
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * @uml.property  name="profile"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="gameResultManager:ncl.team22.uml.languagetutor.Profile"
	 */
	private Profile profile = new ncl.team22.uml.languagetutor.Profile();

	/**
	 * Getter of the property <tt>profile</tt>
	 * @return  Returns the profile.
	 * @uml.property  name="profile"
	 */
	public Profile getProfile()
	{
		return profile;
	}

	/**
	 * Setter of the property <tt>profile</tt>
	 * @param profile  The profile to set.
	 * @uml.property  name="profile"
	 */
	public void setProfile(Profile profile)
	{
		this.profile = profile;
	}

	/**
	 * @uml.property  name="gameResult"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="gameResultManager:ncl.team22.uml.languagetutor.GameResult"
	 */
	private Collection<GameResult> gameResult;

	/**
	 * Getter of the property <tt>gameResult</tt>
	 * @return  Returns the gameResult.
	 * @uml.property  name="gameResult"
	 */
	public Collection<GameResult> getGameResult()
	{
		return gameResult;
	}

	/**
	 * Setter of the property <tt>gameResult</tt>
	 * @param gameResult  The gameResult to set.
	 * @uml.property  name="gameResult"
	 */
	public void setGameResult(Collection<GameResult> gameResult)
	{
		this.gameResult = gameResult;
	}

}

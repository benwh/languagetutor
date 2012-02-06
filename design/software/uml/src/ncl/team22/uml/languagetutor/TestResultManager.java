package ncl.team22.uml.languagetutor;

import java.util.ArrayList;
import java.util.Collection;

public class TestResultManager implements ResultManager<TestResult>
{

	@Override
	public ArrayList<TestResult> getResults()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addResult(TestResult result)
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * @uml.property  name="profile"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="testResultManager:ncl.team22.uml.languagetutor.Profile"
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
	 * @uml.property  name="testResult"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="testResultManager:ncl.team22.uml.languagetutor.TestResult"
	 */
	private Collection<TestResult> testResult;

	/**
	 * Getter of the property <tt>testResult</tt>
	 * @return  Returns the testResult.
	 * @uml.property  name="testResult"
	 */
	public Collection<TestResult> getTestResult()
	{
		return testResult;
	}

	/**
	 * Setter of the property <tt>testResult</tt>
	 * @param testResult  The testResult to set.
	 * @uml.property  name="testResult"
	 */
	public void setTestResult(Collection<TestResult> testResult)
	{
		this.testResult = testResult;
	}

}

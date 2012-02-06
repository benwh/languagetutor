package ncl.team22.uml.languagetutor;

import java.util.ArrayList;


public interface ResultManager<ResultType>
{
	public ArrayList<ResultType> getResults();
	public void addResult(ResultType result);

}

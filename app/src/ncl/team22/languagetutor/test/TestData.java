package ncl.team22.languagetutor.test;

import java.util.ArrayList;

import ncl.team22.languagetutor.data.LanguageEntity;
import ncl.team22.languagetutor.data.Topic;

/**
 * Class containing static methods and ArrayLists for LanguageEntity, for use in
 * tests
 * 
 * @author james
 * 
 */
public class TestData
{
	private static ArrayList<LanguageEntity>	entitiesList;
	// Prevent asked questions being asked again but enable them to be fed as
	// incorrect options
	private static ArrayList<LanguageEntity>	entitiesRemoved	= new ArrayList<LanguageEntity>();
	// For test review
	private static ArrayList<String>			reviewTest		= new ArrayList<String>();

	public static void setEntitiesListByTopic(Topic selectedTopic)
	{
		entitiesList = selectedTopic.getEntities();
	}

	public static ArrayList<LanguageEntity> getEntitiesList()
	{
		return entitiesList;
	}

	public static ArrayList<LanguageEntity> getRemovedList()
	{
		return entitiesRemoved;
	}

	public static ArrayList<String> getReviewTest()
	{
		return reviewTest;
	}

	public static void addToRemovedList(LanguageEntity entity)
	{
		entitiesRemoved.add(entity);
	}

	public static void removeFromEntitiesList(int entityID)
	{
		entitiesList.remove(entityID);
	}

	public static void addToReviewTest(String addition)
	{
		reviewTest.add(addition);
	}

	public static void resetData()
	{
		for (int i = 0; i < entitiesList.size(); i++)
		{
			entitiesList.remove(i);
		}
		for (int j = 0; j < entitiesRemoved.size(); j++)
		{
			entitiesRemoved.remove(j);
		}
		for (int k = 0; k < reviewTest.size(); k++)
		{
			reviewTest.remove(k);
		}
	}
}

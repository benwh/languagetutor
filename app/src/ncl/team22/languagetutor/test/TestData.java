package ncl.team22.languagetutor.test;

import java.util.ArrayList;

import ncl.team22.languagetutor.data.LanguageEntity;
import ncl.team22.languagetutor.data.Topic;

/**
 * Class containing static methods and ArrayLists for LanguageEntity, for use in
 * tests
 * 
 * @author james
 */
public class TestData
{

	private static ArrayList<LanguageEntity>	entitiesList;
	// Prevent asked questions being asked again but enable them to be fed as
	// incorrect options
	private static ArrayList<LanguageEntity>	entitiesRemoved	= new ArrayList<LanguageEntity>();
	// For test review
	private static ArrayList<String>			reviewTest		= new ArrayList<String>();

	/**
	 * Sets the entitiesList based on an arraylist from a selected topic
	 * 
	 * @param selectedTopic
	 *            - the topic that was selected through topic select
	 */
	public static void setEntitiesListByTopic(Topic selectedTopic)
	{
		entitiesList = selectedTopic.getEntities();
	}

	/**
	 * Sets the entitiesList using another arraylist of entities
	 * 
	 * @param entities
	 *            - an arraylist of language entities
	 */
	public static void setEntitiesList(ArrayList<LanguageEntity> entities)
	{
		entitiesList = entities;
	}

	/**
	 * Get the entitiesList
	 * 
	 * @return - arraylist of language entities
	 */
	public static ArrayList<LanguageEntity> getEntitiesList()
	{
		return entitiesList;
	}

	/**
	 * Get the removedList, the arraylist of questions that have already been
	 * asked
	 * 
	 * @return - arraylist of used language entities
	 */
	public static ArrayList<LanguageEntity> getRemovedList()
	{
		return entitiesRemoved;
	}

	/**
	 * Gets the arraylist used for test review
	 * 
	 * @return - arraylist of string values showing original, translations and
	 *         user answers
	 */
	public static ArrayList<String> getReviewTest()
	{
		return reviewTest;
	}

	/**
	 * Adds a language entity to the removedList
	 * 
	 * @param entity
	 *            - language entity to add
	 */
	public static void addToRemovedList(LanguageEntity entity)
	{
		entitiesRemoved.add(entity);
	}

	/**
	 * Removed an entity from the entities list, based on the entityID value
	 * 
	 * @param entityID
	 *            - the ID of the entity to remove
	 */
	public static void removeFromEntitiesList(int entityID)
	{
		entitiesList.remove(entityID);
	}

	/**
	 * Adds a string value to the reviewTest arraylist
	 * 
	 * @param addition
	 *            - the string value to be added
	 */
	public static void addToReviewTest(String addition)
	{
		reviewTest.add(addition);
	}

	/**
	 * Clears all arraylists in preparation for the next test, or if the test
	 * has been quit midway through
	 */
	public static void resetData()
	{
		entitiesList.removeAll(entitiesList);
		entitiesRemoved.removeAll(entitiesRemoved);
		reviewTest.removeAll(reviewTest);
	}
}

package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.usParlament.misc.State;

/**
 * Created by ondrej on 1.6.15.
 */
public interface DomainController {

	/**
	 * remove all data related to Community manager tab
	 */
	void cleanCommunityManager();

	/**
	 * remove all data related to Compare manager tab
	 */
	void cleanCompareManager();



	// Congress

	/**
	 * Create new empty current congress. Previous current congress will be lost.
	 */
	void newCongress();

	/**
	 * save congress into persistent memory. If cogress with the same identificator already exists it will be rewritten.
	 * @param name  unique identificator of congress
	 * @return  Exception string If there is exception "{}" string otherwise.
	 */
	String saveCurrentCongress(String name);

	/**
	 * load congress from persistent memory
	 * @param name  identificator of congress
	 * @return  JSON representation of congress
	 */
	String loadCongressAsCurrent(String name);

	/**
	 * load names of all saved congresses
	 * @return JSON list of all congresses
	 */
	String loadAllCongressesNames();

	/**
	 * @return current congress name if it is saved. empty string otherwise.
	 */
	String getCurrentCongressName();

	/**
	 * Compute new relationships in current congress by MP attributes
	 */
	void computeRelationships();



	// MP

	/**
	 * @return String with the State and District values for each MP at the current congress.
	 */
	String getMPsShort();

	/**
	 * @return It returns an String with Name, State, District and attributes values for each MP at the current congress.
	 */
	String getMPs();

	/**
	 * @param state State of the MP
	 * @param district District of the MP
	 * @return all the saved information about the MP (name,state,district, attributes).
	 */
	String getMP(State state, int district);

	/**
	 * Add mp into current congress
	 * @pre jMP doesn't belong to the current congress.
	 * @post jMP belongs to the current congress.
	 * @param mp JSON Object defining the new MP.
	 */
	String addMP(String mp);

	/**
	 * remove MP from current congress
	 * @param state first identificaro of MP
	 * @param district second identificator of MP
	 */
	void removeMP(State state, int district);

	/**
	 * remove MP from current congress
	 * @param mp MP to be removed (contains state and district)
	 */
	void removeMP(String mp);



	// Attributes

	/**
	 * @return Returns an String with all the information about the AttrDefinitions defined in the current congress.
	 */
	String getAttrDefs();

	/**
	 * @pre true.
	 * @post The AttrDefinition defined by jAttrDef is added/modified to/from the current congress.
	 * @param attrDef JSON representation of the relative AttrDefinition.
	 */
	void addOrModifyAttrDef(String attrDef);

	/**
	 * Deletes the AttrDefinition attrDefName from the current congress.
	 * @param attrDefName name of attribute definition to be deleted
	 */
	public void deleteAttrDef(String attrDefName);

	/**
	 * Adds the attribute to the MP specified. If the MP already has that attribute it is modified instead of added.
	 * @param mp JSON representation of MP (contains state and district)
	 * @param attr JSON representation of attribute to add with all information.
	 */
	void addOrModifyAttribute(String mp, String attr);

	/**
	 * Adds the attributes to the MP specified. If the MP already has that attribute it is modified instead of added.
	 * @param mp JSON representation of MP (contains state and district)
	 * @param attrs JSON representation of attributes to add with all information.
	 */
	void addOrModifyAttributes(String mp, String attrs);

	/**
	 * @pre The specified MP has a value defined for the specified attribute.
	 * @post The specified MP has not any value defined for the (@pre) specified attribute.
	 * @param mp MP represented by JSON.
	 * @param attrDefName name of attribute definition of attribute to be removed.
	 */
	void removeAttribute(String mp, String attrDefName);

	/**
	 * check if current congress contains attribute deffinition
	 * @param name unique name of attribute definition
	 * @return true if attribute deffiniton with the name exists in current congress
	 */
	boolean existsAttrDef(String name);



	// Partitions

	/**
	 * save main partition into persistent memory. If partition with same identificators already exists
	 * it will be overwritten.
	 * @param partitionName  unique identificator in congressName scope.
	 * @return  Exception string If there is exception "{}" string otherwise.
	 */
	String saveMainPartition(String partitionName);

	/**
	 * load partition from persistent memory as main partition or partitions to compare. Depends on param.
	 * @param partitionName  unique identificator in congressName scope.
	 * @param into  name of field where to put loaded partition (mainPartion, partition1, partition2)
	 * @return JSON representation of partition or exception.
	 */
	String loadPartitionInto(String partitionName, String into);

	/**
	 * load all saved partitions of current congress.
	 * @return  JSON representation of array of partitions.
	 */
	String loadAllPartitionsInCurrentCongress();

	/**
	 * get IDs of partition defined by param
	 * @param partition  name of field where to get IDs (mainPartion, partition1, partition2)
	 * @return JSON representation of array of partition IDs or exception.
	 */
	String getCommunityIDs(String partition);

	/**
	 * @return size (number of communities) of main partition
	 */
	String getMainPartitionSize();

	/**
	 * @param communityID unique id of community
	 * @return return JSON representation of all MPs in main partition
	 */
	String getMPsInMainPartition(String communityID);

	/**
	 * @param communityID unique id of community
	 * @return return JSON representation of all MPs in partition 1
	 */
	String getMPsInPartition1(String communityID);

	/**
	 * @param communityID unique id of community
	 * @return return JSON representation of all MPs in partition 2
	 */
	String getMPsInPartition2(String communityID);

	/**
	 * set Current partition as partition 1 for compare
	 */
	void setMainToPartition1();

	/**
	 * set Current partition as partition 2 for compare
	 */
	void setMainToPartition2();

	/**
	 * @return all results of comparation as JSON
	 */
	String compare2partitions();

	/**
	 * load all saved partition names of current congress.
	 * @return  JSON representation of array of names.
	 */
	String loadAllPartitionNamesInCurrentCongress();

	/**
	 * Compute partitions with given algorithm name and save it to the current partition
	 * @param algorithm  identificator of algorithm (N Clique Percolation, Four Clique Percolation, Louvian, Newmann Girvan).
	 * @param argument  argument for algorithm if it need any.
	 */
	void computePartition(String algorithm, String argument);



	// Communities

	/**
	 * add new empty community into main Partition
	 */
	void addNewCommunity();

	/**
	 * remove community from main partition
	 * @param communityID unique ID of community
	 */
	void removeCommunity(String communityID);

	/**
	 * Adds the MP (state, district) to the community (communityID) of main partition.
	 * If the MP(st, distr) is in another community different from (cNumb), the MP is moved.
	 * @param communityID Community identifier number.
	 * @param state State of the MP we want to add.
	 * @param district District of the MP we want to add.
	 */
	void addMPToCommunity(String communityID, State state, int district);

	/**
	 * remove MP from community in main partition
	 * @param communityID Community identifier number.
	 * @param state State of the MP we want to add.
	 * @param district District of the MP we want to add.
	 */
	void removeMPFromCommunity(String communityID, State state, int district);

}

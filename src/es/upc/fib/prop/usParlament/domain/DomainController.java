package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.usParlament.data.DataController;
import es.upc.fib.prop.usParlament.misc.JSONArray;
import es.upc.fib.prop.usParlament.misc.JSONObject;
import es.upc.fib.prop.usParlament.misc.State;

/**
 * Created by ondrej on 1.6.15.
 */
public interface DomainController {

	void setDataController(DataController dataController);

	void cleanCommunityManager();

	void cleanCompareManager();


	// Congress

	void newCongress();

	String saveCurrentCongress(String name);

	String loadAllCongressesNames();

	String loadCongressAsCurrent(String name);

	String getCurrentCongressName();

	void computeRelationships();


	// MP

	String getShortMPList();

	String getMPList();

	String getMPInfo(State state, int district);

	void addMP(JSONObject mp);

	void deleteMP(State state, int district);


	// Attributes

	String getAttrDefs();

	void addOrModifyAttrDef(JSONObject obj);

	void addOrModifyAttribute(JSONObject mp, JSONArray attr);

	void deleteAttribute(JSONObject jo, JSONObject ja);

	boolean existsAttrDef(String name);


	// Partitions

	String saveCurrentPartition(String trim);

	String loadPartitionAs(String pName, String as);

	String getCommunityIDs(String partition);

	String getMPsMainPartition(String s);

	String getMainPartitionSize();

	String getMPsPartition1(String s);

	String getMPsPartition2(String s);

	void setCurrentToPartition1();

	void setCurrentToPartition2();

	String compare2partitions();

	String loadAllPartitionNamesInCurrentCongress();

	void computeCommunities(String algorithm, String argument);


	// Communities

	void addNewCommunity();

	void deleteSelectedCommunity(Integer cNumb);

	void addMPToCommunity(Integer cNumb, State st, Integer dt);

	void deleteMPFromCommunity(Integer cNumb, State st, Integer dt);

}

package es.upc.fib.prop.usParlament.data;

/**
 * Created by ondrej on 20.5.15.
 */
public interface DataController {

	/**
	 * save congress into persistent memory. If cogress with the same identificator already exists it will be rewritten.
	 * @param name  unique identificator of congress
	 * @param congress  JSON representation of congress object
	 * @return  Exception string If there is exception "{}" string otherwise.
	 */
	 String saveCongress(String name, String congress);

	/**
	 * load congress from persistent memory
	 * @param name  identificator of congress
	 * @return  JSON representation of congress
	 */
	 String loadCongress(String name);

	/**
	 * load names of all saved congresses
	 * @return JSON list of all congresses
	 */
	 String loadAllCongressesNames();


	/**
	 * save partition into persistent memory. If partition with same identificators already exists it will be rewritten.
	 * @param congressName  unique identificator of congress. It has to already exists in persistent memory.
	 * @param partitionName  unique identificator in congressName scope.
	 * @param partition  JSON representation of partition object
	 * @return  Exception string If there is exception "{}" string otherwise.
	 */
	 String savePartition(String congressName, String partitionName, String partition);

	/**
	 * load saved partition from persistent memory.
	 * @param congressName  unique identificator of congress.
	 * @param partitionName  unique identificator in congressName scope.
	 * @return JSON representation of partition.
	 */
	 String loadPartition(String congressName, String partitionName);

	/**
	 * remove saved partition of congress from persistent memory.
	 * @param congressName  unique identificator of congress.
	 * @param partitionName  unique identificator in congressName scope.
	 * @return Exception if there is someone "{}" otherwise
	 */
	String removePartition(String congressName, String partitionName);

	/**
	 * remove all saved partitions of congress from persistent memory.
	 * @param congressName  unique identificator of congress.
	 * @return Exception if there is someone "{}" otherwise
	 */
	 String removeAllPartitions(String congressName);

	/**
	 * load all saved partitions of congress.
	 * @param congressName  unique identificator of congress.
	 * @return  JSON representation of array of partitions.
	 */
	 String loadAllPartitionsOfCongress(String congressName);


	/**
	 * load all saved partition names of congress.
	 * @param congressName  unique identificator of congress.
	 * @return  JSON representation of array of names.
	 */
	 String loadAllPartitionNamesOfCongress(String congressName);




}

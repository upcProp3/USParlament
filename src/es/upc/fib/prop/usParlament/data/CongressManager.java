package es.upc.fib.prop.usParlament.data;

/**
 * Created by ondrej on 20.5.15.
 */
public interface CongressManager {

	/**
	 * save congress into persistent memory. If cogress with the same identificator already exists it will be rewritten.
	 * @param name  unique identificator of congress
	 * @param congress  JSON representation of congress object
	 * @return  Exception string If there is exception "{}" string otherwise.
	 */
	public String saveCongress(String name, String congress);

	/**
	 * load congress from persistent memory
	 * @param name  identificator of congress
	 * @return  JSON representation of congress
	 */
	public String loadCongress(String name);

	/**
	 * load names of all saved congresses
	 * @return JSON list of all congresses
	 */
	public String loadAllCongressesNames();


	/**
	 * save partition into persistent memory. If partition with same identificators already exists it will be rewritten.
	 * @param congressName  unique identificator of congress. It has to already exists in persistent memory.
	 * @param partitionName  unique identificator in congressName scope.
	 * @param partition  JSON representation of partition object
	 * @return  Exception string If there is exception "{}" string otherwise.
	 */
	public String savePartition(String congressName, String partitionName, String partition);

	/**
	 * load saved partition from persistent memory.
	 * @param congressName  unique identificator of congress.
	 * @param partitionName  unique identificator in congressName scope.
	 * @return JSON representation of partition.
	 */
	public String loadPartition(String congressName, String partitionName);

	/**
	 * load all saved partitions of congress.
	 * @param congressName  unique identificator of congress.
	 * @return  JSON representation of array of partitions.
	 */
	public String loadAllPartitionsOfCongress(String congressName);





}

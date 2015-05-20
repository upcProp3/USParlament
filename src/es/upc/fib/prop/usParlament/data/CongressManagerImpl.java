package es.upc.fib.prop.usParlament.data;

import java.io.*;

/**
 * Class which using one file for each congress saving information about it. It is using JSON format.
 * {@code
 *  {
 *      "value":"<congressValue>",
 *      "partitions":[
 *          {
 *              "name":"<nameOfPartition>",
 *              "value":"<partitionValue>"
 *          },
 *          ...
 *      ]
 *  }
 * }
 *
 * Created by ondrej on 20.5.15.
 */
public class CongressManagerImpl implements CongressManager {

	private String path;

	public CongressManagerImpl(String path) {
		this.path = path;
	}

	@Override
	public String saveCongress(String name, String congress) {
		try {
			return saveCongress(name, congress, getBufferedWriter(name));
		} catch (IOException e) {
			return exceptionMaker(e);
		} catch (SecurityException e) {
			return exceptionMaker(e);
		}
	}
	private String saveCongress(String name, String congress, BufferedWriter bw) {
		return null;
	}


	@Override
	public String loadCongress(String name) {
		try {
			return loadCongress(name, getBufferedReader(name));
		} catch (IOException e) {
			return exceptionMaker(e);
		}
	}
	private String loadCongress(String name, BufferedReader br) {
		return null;
	}


	@Override
	public String loadAllCongressesNames() {

		return null;
	}


	@Override
	public String savePartition(String congressName, String partitionName, String partition) {
		try {
			return savePartition(congressName, partitionName, partition, getBufferedWriter(congressName));
		} catch (IOException e) {
			return exceptionMaker(e);
		} catch (SecurityException e) {
			return exceptionMaker(e);
		}
	}
	private String savePartition(String congressName, String partitionName, String partition, BufferedWriter bw) {
		return null;
	}


	@Override
	public String loadPartition(String congressName, String partitionName) {
		try {
			return loadPartition(congressName, partitionName, getBufferedReader(congressName));
		} catch (IOException e) {
			return exceptionMaker(e);
		}
	}
	private String loadPartition(String congressName, String partitionName, BufferedReader br) {
		return null;
	}


	@Override
	public String loadAllPartitionsOfCongress(String congressName) {
		try {
			return savePartition(congressName, getBufferedReader(congressName));
		} catch (IOException e) {
			return exceptionMaker(e);
		}
	}
	private String savePartition(String congressName, BufferedReader br) {
		return null;
	}


	private BufferedWriter getBufferedWriter(String fileName) throws IOException, SecurityException {
		File dir = new File(path);
		try {
			dir.mkdirs();
		} catch(SecurityException e){
			throw new SecurityException("doesn't have enough privileges to create folder.", e);
		}
		File file = new File(dir, fileName);
		try (FileWriter fw = new FileWriter(file)) {
			return new BufferedWriter(fw);
		}
	}
	private BufferedReader getBufferedReader(String fileName) throws IOException {
		File dir = new File(path);
		if (!dir.exists()) {
			throw new IOException("Folder with congresses doesn't exists");
		}
		File file = new File(dir, fileName);
		try (FileReader fr = new FileReader(file)) {
			return new BufferedReader(fr);
		}
	}


	private String exceptionMaker(Exception e) {
		return null;
	}
}

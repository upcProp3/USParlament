package es.upc.fib.prop.usParlament.data;

import java.io.*;

/**
 * Class which using one file for each congress saving information about it.
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
		try (BufferedWriter bw = getBufferedWriter(name)) {
			return saveCongress(name, congress, bw);
		} catch (IOException e) {
			return exceptionMaker(e);
		} catch (SecurityException e) {
			return exceptionMaker(e);
		}
	}
	private String saveCongress(String name, String congress, BufferedWriter bw) throws IOException {
		bw.write(congress);
		bw.flush();
		return "{}";
	}


	@Override
	public String loadCongress(String name) {
		try (BufferedReader br = getBufferedReader(name)) {
			return loadCongress(name, br);
		} catch (IOException e) {
			return exceptionMaker(e);
		}
	}
	private String loadCongress(String name, BufferedReader br) throws IOException {
		return br.readLine();
	}


	@Override
	public String loadAllCongressesNames() {
		try {
			File dir = new File(path);
			if (!dir.exists()) {
				throw new IOException("Folder with congresses doesn't exists");
			}
			boolean first = true;
			String res = "{\"congressesNames\":[";
			for (File f : dir.listFiles()) {
				if (!first) {
					res += ",";
				}
				res += "\"" +f.getName()+ "\"";
				first = false;
			}
			res += "]}";
			return res;
		} catch (FileNotFoundException e) {
			return exceptionMaker(e);
		} catch (IOException e) {
			return exceptionMaker(e);
		}
	}


	@Override
	public String savePartition(String congressName, String partitionName, String partition) {
		try (BufferedWriter bw = getBufferedWriter(congressName)) {
			return savePartition(congressName, partitionName, partition, bw);
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
		try (BufferedReader br = getBufferedReader(congressName)) {
			return loadPartition(congressName, partitionName, br);
		} catch (IOException e) {
			return exceptionMaker(e);
		}
	}
	private String loadPartition(String congressName, String partitionName, BufferedReader br) {
		return null;
	}


	@Override
	public String loadAllPartitionsOfCongress(String congressName) {
		try (BufferedReader br = getBufferedReader(congressName)) {
			return savePartition(congressName, br);
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
		FileWriter fw = new FileWriter(file);
		return new BufferedWriter(fw);
	}
	private BufferedReader getBufferedReader(String fileName) throws IOException {
		File dir = new File(path);
		if (!dir.exists()) {
			throw new IOException("Folder with congresses doesn't exists");
		}
		File file = new File(dir, fileName);
		FileReader fr = new FileReader(file);
		return new BufferedReader(fr);
	}


	private String exceptionMaker(Exception e) {
		return e.toString();
	}
}

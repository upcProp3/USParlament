package es.upc.fib.prop.usParlament.data;

import javax.sql.DataSource;
import java.io.*;

/**
 * Created by ondrej on 20.5.15.
 */
public class CongressManagerImpl implements CongressManager {

	private final String path = "data/congress.data";


	@Override
	public String saveCongress(String name, String congress) {
		try {
			return saveCongress(name, congress, getBufferedWriter());
		} catch (IOException e) {
			return exceptionMaker(e);
		}
	}
	private String saveCongress(String name, String congress, BufferedWriter bw) {
		return null;
	}


	@Override
	public String loadCongress(String name) {
		try {
			return loadCongress(name, getBufferedReader());
		} catch (IOException e) {
			return exceptionMaker(e);
		}
	}
	private String loadCongress(String name, BufferedReader br) {
		return null;
	}


	@Override
	public String loadAllCongressesNames() {
		try {
			return loadAllCongressesNames(getBufferedReader());
		} catch (IOException e) {
			return exceptionMaker(e);
		}
	}
	private String loadAllCongressesNames(BufferedReader br) {
		return null;
	}


	@Override
	public String savePartition(String congressName, String partitionName, String partition) {
		try {
			return savePartition(congressName, partitionName, partition, getBufferedWriter());
		} catch (IOException e) {
			return exceptionMaker(e);
		}
	}
	private String savePartition(String congressName, String partitionName, String partition, BufferedWriter bw) {
		return null;
	}


	@Override
	public String loadPartition(String congressName, String partitionName) {
		try {
			return loadPartition(congressName, partitionName, getBufferedReader());
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
			return savePartition(congressName, getBufferedReader());
		} catch (IOException e) {
			return exceptionMaker(e);
		}
	}
	private String savePartition(String congressName, BufferedReader br) {
		return null;
	}


	private BufferedWriter getBufferedWriter() throws IOException {
		File file = new File(path);
		try (FileWriter fw = new FileWriter(file)) {
			return new BufferedWriter(fw);
		}
	}
	private BufferedReader getBufferedReader() throws IOException {
		File file = new File(path);
		try (FileReader fr = new FileReader(file)) {
			return new BufferedReader(fr);
		}
	}

	private String exceptionMaker(IOException e) {
		return "";
	}
}

package es.upc.fib.prop.usParlament.data;

import es.upc.fib.prop.usParlament.domain.MP;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by ondrej on 15.5.15.
 */
public class MPsManagerImpl implements MPsManager {

	private DataSource ds;

	public MPsManagerImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void createMP(MP mp) {

	}

	@Override
	public MP findMPByID(Long id) {
		return null;
	}

	@Override
	public List<MP> findAllMPs() {
		return null;
	}

	@Override
	public void deleteMP(MP mp) {

	}

	@Override
	public void updateMP(MP mp) {

	}
}

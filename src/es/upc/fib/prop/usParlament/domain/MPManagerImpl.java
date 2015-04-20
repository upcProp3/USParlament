package es.upc.fib.prop.usParlament.domain;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by ondrej on 17.4.15.
 */
public class MPManagerImpl implements MPManager {

	private DataSource dataSource;

	public MPManagerImpl(DataSource ds)
	{
		this.dataSource = ds;
	}

	@Override
	public void createMP(MP mp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<MP> getAllMPs() {
		throw new UnsupportedOperationException();
	}

	@Override
	public MP getMPbyId(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteMP(MP mp) {
		throw new UnsupportedOperationException();
	}
}

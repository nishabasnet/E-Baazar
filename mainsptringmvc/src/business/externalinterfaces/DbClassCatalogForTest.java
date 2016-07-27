package business.externalinterfaces;

import java.util.List;

import business.exceptions.BackendException;

public interface DbClassCatalogForTest {
	public List<Catalog> getCatalogList() throws BackendException;


}

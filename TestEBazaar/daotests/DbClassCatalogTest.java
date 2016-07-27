package daotests;


import java.util.List;
import java.util.logging.Logger;

import business.externalinterfaces.Catalog;
import business.externalinterfaces.DbClassCatalogForTest;
import business.externalinterfaces.ProductSubsystem;
import business.productsubsystem.ProductSubsystemFacade;
import dbsetup.DbQueries;
import junit.framework.TestCase;
import alltests.AllTests;

public class DbClassCatalogTest extends TestCase {
	
	static String name = "Product Subsystem Test";
	static Logger log = Logger.getLogger(name);
	
	static {
		AllTests.initializeProperties();
	}
	
	public void testReadAllCatalogs() {
		List<Catalog> expected = DbQueries.readCustCatalogs();
		System.out.println(expected.size());
		
		ProductSubsystem pss = new ProductSubsystemFacade();
		DbClassCatalogForTest dbclass = pss.getGenericDbClassCatalogs();
		
		try {
			System.out.println(dbclass.getCatalogList().size());
			dbclass.getCatalogList();
			List<Catalog> found = dbclass.getCatalogList();
			assertTrue(expected.size() == found.size());
			
		} catch(Exception e) {
			fail("Catalog Lists don't match");
		}
		
	}
}

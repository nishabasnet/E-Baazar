package presentation.control;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import business.BusinessConstants;
import business.SessionCache;
import business.exceptions.BackendException;
import business.externalinterfaces.Catalog;
import business.externalinterfaces.CustomerSubsystem;
import business.externalinterfaces.Product;
import business.externalinterfaces.ProductSubsystem;
import business.productsubsystem.CatalogTypesImpl;
import business.productsubsystem.ProductSubsystemFacade;
import business.usecasecontrol.ManageProductsController;
import business.util.DataUtil;
import presentation.data.BrowseSelectData;
import presentation.data.CartItemPres;
import presentation.data.CatalogPres;
import presentation.data.ManageProductsData;
import presentation.data.ProductPres;

@Controller

public class ManageProductCatalogConrol {
	private String resourcePath = "C:\\Users\\Owner\\Desktop\\NewWorkSpace\\MainProjectSpringMVC_MERGED_WITH_SASHIKSHYA\\MainProjectSpringMVC\\mainsptringmvc\\WebContent\\resources\\images\\products\\";

	@RequestMapping(value = "/cataloglist")
	public String viewCatalogsHandler(ModelMap modelMap) {
		// System.out.println("in catlog");

		if (!DataUtil.custIsAdmin()) {
			modelMap.addAttribute("message", "Admin login required");
			modelMap.addAttribute("callback", "cataloglist");
			return "login";
		}

		List<CatalogPres> catalogs = null;
		try {
			catalogs = BrowseSelectData.INSTANCE.getCatalogList();
		} catch (BackendException e) {
			e.printStackTrace();
		}
		modelMap.addAttribute("catalogs", catalogs);
		return "cataloglist";
	}

	@RequestMapping(value = "/addnewcatalog", method = RequestMethod.GET)
	public String newcatalogHandler(ModelMap modelMap) {
		if (!DataUtil.custIsAdmin()) {
			modelMap.addAttribute("message", "Admin login required");
			modelMap.addAttribute("callback", "cataloglist");
			return "login";
		}
		return "newcatalog";
	}

	@RequestMapping(value = "/addnewproduct", method = RequestMethod.GET)
	public String newproductHandler(@RequestParam("id") String catid, @RequestParam("value") String catName,
			ModelMap modelMap) {
		if (!DataUtil.custIsAdmin()) {
			modelMap.addAttribute("message", "Admin login required");
			modelMap.addAttribute("callback", "productlist");
			return "login";
		}
		modelMap.addAttribute("catId", catid);
		modelMap.addAttribute("catName", catName);
		return "newproduct";
	}

	@RequestMapping(value = "/savecatalog", method = RequestMethod.POST)
	public String savecatalogHandler(@RequestParam Map<String, String> params, ModelMap modelMap) {

		try {
			ProductSubsystem pss = new ProductSubsystemFacade();
			pss.saveNewCatalog(params.get("catName"));
		} catch (BackendException e) {
			e.printStackTrace();
		}
		/// modelMap.addAttribute("products", products);
		// modelMap.addAttribute("catalogName", name);
		return "redirect:/cataloglist";
	}

	private void saveImage(String filename, MultipartFile image) throws RuntimeException, IOException {
		image.transferTo(new File(filename));
	}

	@RequestMapping(value = "/imageController/{pname}")
	@ResponseBody
	public byte[] image(@PathVariable String pname) throws IOException  {
		File file = new File(resourcePath+pname+".jpg");
		FileInputStream fis=new FileInputStream(file);
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		int b;
		byte[] buffer = new byte[1024];
		while((b=fis.read(buffer))!=-1){
		   bos.write(buffer,0,b);
		}
		byte[] fileBytes=bos.toByteArray();
		fis.close();
		bos.close();
		return fileBytes;
	}
	
	private Boolean uploadImage(MultipartFile file, ModelMap modelMap, String productName) {
		if (!file.isEmpty()) {
			try {
				String directoryPath = resourcePath + productName + ".jpg";
				saveImage(directoryPath, file);

			} catch (IOException e) {
				modelMap.addAttribute("message", "Unsupported Image");
				return false;
			}
		}
		return true;
	}

	@RequestMapping(value = "/saveproduct", method = RequestMethod.POST)
	public String saveproductHandler(@RequestParam Map<String, String> params, @RequestParam("file") MultipartFile file,
			ModelMap modelMap) throws BackendException {

		String catid = params.get("catId");
		String catname = params.get("catName");
		String name = params.get("pName");
		String date = params.get("mDate");
		String quantity = params.get("num");
		String unitprice = params.get("price");
		String description = params.get("des");
		uploadImage(file, modelMap, name);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate formatedDate = LocalDate.parse(date, formatter);

		Catalog catalog = ProductSubsystemFacade.createCatalog(Integer.parseInt(catid), catname);
		Product product = ProductSubsystemFacade.createProduct(catalog, 0, name, Integer.parseInt(quantity),
				Integer.parseInt(unitprice), formatedDate, description);
		ManageProductsController.INSTANCE.saveNewProduct(product);

		return "redirect:/productlist";
	}

	@RequestMapping(value = "/productlist")
	public String viewProductListHandler(@RequestParam(value = "catId", required = false) String id,
			@RequestParam(value = "name", required = false) String name, ModelMap modelMap) {
		if (!DataUtil.custIsAdmin()) {
			modelMap.addAttribute("message", "Admin login required");
			modelMap.addAttribute("callback", "productlist");
			return "login";
		}
	
		List<CatalogPres> catalogs = null;
		List<ProductPres> products = null;

		try {
			catalogs = BrowseSelectData.INSTANCE.getCatalogList();
			if (id == null) {
				products = BrowseSelectData.INSTANCE.getProductList(catalogs.get(0));
			} else {
				Catalog cat = ProductSubsystemFacade.createCatalog(Integer.parseInt(id), "");
				CatalogPres catPress = new CatalogPres();
				catPress.setCatalog(cat);
				products = BrowseSelectData.INSTANCE.getProductList(catPress);

			}
		} catch (BackendException e) {
			e.printStackTrace();
		}

		modelMap.addAttribute("products", products);
		modelMap.addAttribute("catalogs", catalogs);
		modelMap.addAttribute("selectedId", id);
		modelMap.addAttribute("name", name);

		return "manageproduct";
	}
}

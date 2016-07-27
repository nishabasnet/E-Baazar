package presentation.control;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import business.BusinessConstants;
import business.Login;
import business.SessionCache;
import business.exceptions.BackendException;
import business.exceptions.UserException;
import business.externalinterfaces.CustomerSubsystem;
import presentation.data.BrowseSelectData;
import presentation.data.CartItemPres;
import presentation.data.CatalogPres;
import presentation.data.CheckoutData;
import presentation.data.DataUtil;
import presentation.data.LoginData;
import presentation.data.ManageProductsData;
import presentation.data.ProductPres;
import presentation.gui.GuiUtils;

@Controller
public class LoginControl {
	LoginData loginData = new LoginData();
	String returnPath = null;
	@RequestMapping("/login/callback/{name}")
	public String viewLoginHandler(@PathVariable String name, ModelMap modelMap) {
		modelMap.addAttribute("callback", name);
		return "login";
	}
	@RequestMapping("/logout")
	public String logoutHandler( ModelMap modelMap) {
	
		SessionCache cache = SessionCache.getInstance();
		boolean loggedIn = (Boolean)cache.get(BusinessConstants.LOGGED_IN);
	
		if(!loggedIn) {
			modelMap.addAttribute("message", "You are already logged out");
		} else {
			cache.add(BusinessConstants.LOGGED_IN, Boolean.FALSE);
			cache.remove(BusinessConstants.CUSTOMER);
			modelMap.addAttribute("message", "Logout Successful");
		}
		return "redirect:/";
	}
	
	@RequestMapping("/loginsubmit/callback/{name}")
	public String submitLoginHandler(@PathVariable String name, @RequestParam Map<String, String> allRequestParams,
			ModelMap modelMap) {
		if (DataUtil.isLoggedIn()) {
			modelMap.addAttribute("message", "You are already Logged In");
			return "redirect:/";
		}
		try {
			Login login = extractLogin(allRequestParams);
			int authorizationLevel = loginData.authenticate(login);
			loginData.loadCustomer(login, authorizationLevel);
			getCustomerCheckoutData();
			modelMap.addAttribute("message", "Successfully Logged In");
			modelMap.addAttribute("redirect", name);
			String redirectlocation = "redirect:/"+ urlManager(name);
			
			return redirectlocation;
		} catch (UserException e) {
			modelMap.addAttribute("callback", name);
			modelMap.addAttribute("message", e.getMessage());
			return "redirect:/login/callback/login";
		} catch (BackendException e) {
			modelMap.addAttribute("callback", name);
			modelMap.addAttribute("message", e.getMessage());
			return "redirect:/login/callback/login";
		}

	}
	public void getCustomerCheckoutData() throws BackendException{
		CheckoutData.INSTANCE.loadBillAddresses();
		CheckoutData.INSTANCE.loadShipAddresses();
		CheckoutData.INSTANCE.loadDefaultBillAddress();
		CheckoutData.INSTANCE.loadDefaultPaymentInfo();
		CheckoutData.INSTANCE.loadDefaultShipAddress();
	}
	private Login extractLogin(Map<String, String> allRequestParams) throws UserException {
		try {
			return new Login(GuiUtils.toInteger(allRequestParams.get("custId")),
					(String) allRequestParams.get("password"));
		} catch (Exception ex) {
			throw new UserException("CustomerId is not in proper format");
		}
	}
	
	private String urlManager(String path){
		switch(path){
		case "login":
			path = "login/callback/login";
		break;
		default:
			
		break;
		}
		return path;
	}

}

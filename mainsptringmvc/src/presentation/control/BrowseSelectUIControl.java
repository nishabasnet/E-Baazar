package presentation.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.xml.internal.ws.api.ha.HaInfo;

import business.exceptions.BackendException;
import business.exceptions.BusinessException;
import business.exceptions.RuleException;
import business.externalinterfaces.Product;
import business.shoppingcartsubsystem.ShoppingCartSubsystemFacade;
import business.usecasecontrol.BrowseAndSelectController;
import business.util.DataUtil;
import presentation.data.BrowseSelectData;
import presentation.data.CartItemPres;
import presentation.data.CatalogPres;
import presentation.data.ManageProductsData;
import presentation.data.ProductPres;

@Controller
public class BrowseSelectUIControl {
	CartItemPres cartPres;
	
	@RequestMapping(value="/retrievesavecart")
	public String retrieveSaveCartHandler(ModelMap modelMap) {
		if (!DataUtil.isLoggedIn()) {
			modelMap.addAttribute("callback", "retrievesavecart");
			return "login";
		}
		List<CartItemPres> listCartItem = null;
		try {
			listCartItem = BrowseSelectData.INSTANCE.retrieveSavedcart();
			for(CartItemPres p: listCartItem){
				System.out.println("--------------------------------------------------------");
				System.out.println(p.getItemName());
				System.out.println("--------------------------------------------------------");
			}
			
		} catch (BackendException e) {
			modelMap.addAttribute("message", e.getMessage());
			return "catalog";
		}
		modelMap.addAttribute("cartItems", listCartItem);
		return "cart";
	}
	
	@RequestMapping(value="/")
	public String viewCatalogsHandler(ModelMap modelMap) {
	
		List<CatalogPres> catalogs = null;
		try {
			catalogs = BrowseSelectData.INSTANCE.getCatalogList();
		} catch (BackendException e) {
			e.printStackTrace();
		}
		modelMap.addAttribute("catalogs", catalogs);
		return "catalog";
	}

	@RequestMapping(value = "/viewProductList/catalogId/{id}/catalogName/{name}", method = RequestMethod.GET)
	public String viewProductListHandler(@PathVariable int id, @PathVariable String name, ModelMap modelMap) {
		System.out.println("id: " + id + ", name: " + name);
		List<ProductPres> products = null;
		try {
			products = BrowseSelectData.INSTANCE
					.getProductList(ManageProductsData.INSTANCE.createCatalogPres(id, name));
		} catch (BackendException e) {
			e.printStackTrace();
		}
		modelMap.addAttribute("products", products);
		modelMap.addAttribute("catalogName", name);
		return "product";
	}

	@RequestMapping("/viewProductDetail")
	public String viewProductDetailHandler(@RequestParam("id") int id, ModelMap modelMap) {
		System.out.println("viewProductsHandler2 - id: " + id);
		ProductPres prodPres = null;
		try {
			prodPres = ManageProductsData.INSTANCE.createProductPresByName(id);
		} catch (BackendException e) {
			e.printStackTrace();
		}
		modelMap.addAttribute("product", prodPres);
		return "productdetail";
	}

	@RequestMapping(value = "/addToCart", method = RequestMethod.POST)
	public String addToCartHandler(@RequestParam Map<String, String> allRequestParams, ModelMap modelMap) {
		System.out.println("addToCartHandler.....");
		String name = allRequestParams.get("prodName");
		double unitPrice = Double.valueOf(allRequestParams.get("unitPrice"));
		int quanAvailable=Integer.valueOf(allRequestParams.get("quantityAvail"));
		cartPres = BrowseSelectData.INSTANCE.cartItemPresFromData(name, unitPrice, quanAvailable);
		/*BrowseSelectData.INSTANCE.addToCart2(cartPres);
		List<CartItemPres> cartItems = BrowseSelectData.INSTANCE.getCartData2();
		modelMap.addAttribute("cartItems", cartItems);*/
		modelMap.addAttribute("quantity",1);
		return "changeQuantity";
	}
	@RequestMapping(value = "/addToCart", method = RequestMethod.GET)
	public String editQuantityHandler(@RequestParam("quan") int quan, ModelMap modelMap) {
		modelMap.addAttribute("quantity", 1);
		return "changeQuantity";
	}
	
	@RequestMapping(value = "/savecart")
	public String addToCartHandler(ModelMap modelMap) {
		System.out.println("save cart.....");
		if (!DataUtil.isLoggedIn()) {
			modelMap.addAttribute("callback", "shoppingCart");
			return "login";
		}
			
		try {
			ShoppingCartSubsystemFacade.INSTANCE.saveCart();
		} catch (BackendException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/shoppingCart";
	}
	@RequestMapping(value="/shoppingCart", method=RequestMethod.GET)
	public String loadShoppingCart(ModelMap modelMap){
		List<CartItemPres> cartItems = BrowseSelectData.INSTANCE.getCartData2();
		modelMap.addAttribute("cartItems", cartItems);
		return "cart";
	}
	@RequestMapping(value="/deleteItem/{cartItemPres}", method=RequestMethod.GET)
	public String deleteCartItem(@PathVariable String cartItemPres,ModelMap modelMap){
		System.out.println("cartItem pres = "+cartItemPres);
		List<CartItemPres> cartItems = BrowseSelectData.INSTANCE.deleteAndUpdateCart(cartItemPres);
		modelMap.addAttribute("cartItems", cartItems);
		return "cart";
	}
	@RequestMapping(value = "/saveQuantity", method = RequestMethod.POST)
	public String saveQuantityHandler(@RequestParam Map<String, String> allRequestParams, ModelMap modelMap) {
		String quantityRequested = (allRequestParams.get("quantity"));
		//if rule pass
		String message="";
		Product product;
		boolean rulesok=true;
		try {
			product = BrowseSelectData.INSTANCE.getProductForProductName(cartPres.getItemName());
			BrowseAndSelectController.INSTANCE.runQuantityRules(product, quantityRequested);
			
		} catch (RuleException e) {
			rulesok=false;
			message=e.getMessage();
		} catch (BackendException e) {
			rulesok=false;
			message=e.getMessage();
		} catch (BusinessException e) {
			rulesok=false;
			message=e.getMessage();
		}
		if (rulesok) {
			cartPres.setQuantity(Integer.valueOf(quantityRequested));
			BrowseSelectData.INSTANCE.addToCart2(cartPres);
			List<CartItemPres> cartItems = BrowseSelectData.INSTANCE.getCartData2();
			modelMap.addAttribute("cartItems", cartItems);
			return "cart";
		}else{
			modelMap.addAttribute("message",message);
			return "changeQuantity";
		}
		
		
	}

}

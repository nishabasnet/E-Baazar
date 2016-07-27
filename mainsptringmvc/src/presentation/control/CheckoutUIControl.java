package presentation.control;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import business.BusinessConstants;
import business.SessionCache;
import business.exceptions.BackendException;
import business.exceptions.BusinessException;
import business.exceptions.RuleException;
import business.externalinterfaces.Address;
import business.externalinterfaces.CreditCard;
import business.shoppingcartsubsystem.ShoppingCartSubsystemFacade;
import business.usecasecontrol.CheckoutController;
import javafx.collections.ObservableList;
import presentation.data.AddressPres;
import presentation.data.BrowseSelectData;
import presentation.data.CartItemPres;
import presentation.data.CheckoutData;
import presentation.data.CreditCardPres;
import presentation.data.CustomerPres;
import presentation.data.DataUtil;
import presentation.gui.GuiConstants;

@Controller
public class CheckoutUIControl {
	Address userBillAdd;

	@RequestMapping(value = "/shippingbilling", method = RequestMethod.GET)
	public String proceedFromCartToShipBill(ModelMap modelMap) {
		
		List<CartItemPres> cartItems = BrowseSelectData.INSTANCE.getCartData2();
		
		try {
			CheckoutController.INSTANCE.runShoppingCartRules(cartItems);
		} catch (RuleException e1) {
			modelMap.addAttribute("cartItems", cartItems);
			modelMap.addAttribute("message",e1.getMessage());
			return "cart";
		} catch (BusinessException e1) {
			modelMap.addAttribute("cartItems", cartItems);
			modelMap.addAttribute("message",e1.getMessage());
			return "cart";
		}
		if (!(Boolean) (SessionCache.getInstance().get(BusinessConstants.LOGGED_IN))) {
			modelMap.addAttribute("callback", "shippingbilling");
			return "login";
		}

		CheckoutData data = CheckoutData.INSTANCE;
		Address defaultShipAddress;
		try {
			defaultShipAddress = data.getDefaultShippingData();
			AddressPres uiShipAddress = new AddressPres();
			AddressPres uiBillAddress = new AddressPres();
			Address defaultBillAddress = data.getDefaultBillingData();
			uiBillAddress.setAddress(defaultBillAddress);
			uiShipAddress.setAddress(defaultShipAddress);
			System.out.println(uiBillAddress.getStreet());
			System.out.println(uiShipAddress.getStreet());
			modelMap.addAttribute("addressFields", GuiConstants.DISPLAY_ADDRESS_FIELDS);
			modelMap.addAttribute("shippingAddress", uiShipAddress);
			modelMap.addAttribute("billingAddress", uiBillAddress);
		} catch (BackendException e) {
			modelMap.addAttribute("message", e.getMessage());
		}

		return "shippingbilling";
	}

	@RequestMapping(value = "/selectshipaddress", method = RequestMethod.GET)
	public String selectshipAddress(ModelMap modelMap) {
		//System.out.println("select address = "+add);
		CheckoutData data = CheckoutData.INSTANCE;
		try {
			ObservableList<CustomerPres> shipList = data.getCustomerShipAddresses();
			modelMap.addAttribute("addressFields", GuiConstants.DISPLAY_ADDRESS_FIELDS);
			modelMap.addAttribute("addressList", shipList);
		} catch (BackendException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// modelMap.addAttribute("addressList",DefaultData.ADDRESSES_ON_FILE);
		modelMap.addAttribute("title", "Shipping Address");
		return "selectaddress";
	}

	@RequestMapping(value = "/selectbilladdress", method = RequestMethod.GET)
	public String selectbillAddress(ModelMap modelMap) {
		CheckoutData data = CheckoutData.INSTANCE;
		try {
			ObservableList<CustomerPres> billList = data.getCustomerBillAddresses();
			modelMap.addAttribute("addressFields", GuiConstants.DISPLAY_ADDRESS_FIELDS);
			modelMap.addAttribute("addressList", billList);
		} catch (BackendException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// modelMap.addAttribute("addressList",DefaultData.ADDRESSES_ON_FILE);
		modelMap.addAttribute("title", "Billing Address");
		return "selectaddress";
	}

	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public String proceedToPayment(ModelMap modelMap) {
		CheckoutData data = CheckoutData.INSTANCE;
		try {
			CreditCardPres cc = data.getDefaultPaymentInfo();
			modelMap.addAttribute("ccard", cc);
			return "payment";
		} catch (BackendException e) {
			// TODO Auto-generated catch block
			System.out.println("back end exception =" + e.getMessage());
			return "payment";

		}

	}

	@RequestMapping(value = "/selectAdd/{title}/{id}", method = RequestMethod.GET)
	public String selectAddress(@PathVariable("title") String title, @PathVariable("id") String id, ModelMap modelMap) {
		CheckoutData data = CheckoutData.INSTANCE;
		
		Address selectedAddress;
		AddressPres shipAddress = new AddressPres();
		AddressPres billAddress = new AddressPres();
		try {
			selectedAddress = DataUtil.readCustFromCache().getAllAddresses().stream()
					.filter(a -> (a.getId()) == Integer.parseInt(id)).findFirst().get();
			if (title.matches("Billing Address")) {
				
					Address defaultShipAddress = data.getDefaultShippingData();
					shipAddress.setAddress(defaultShipAddress);
					billAddress.setAddress(selectedAddress);

				

			} else {
				Address defaultBillAddress;
				defaultBillAddress = data.getDefaultBillingData();
				billAddress.setAddress(defaultBillAddress);
				shipAddress.setAddress(selectedAddress);
				
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			modelMap.addAttribute("message", e.getMessage());

		} catch (BackendException e) {
			// TODO Auto-generated catch block
			modelMap.addAttribute("message", e.getMessage());

		}
		
		modelMap.addAttribute("addressFields", GuiConstants.DISPLAY_ADDRESS_FIELDS);
		modelMap.addAttribute("shippingAddress", shipAddress);
		modelMap.addAttribute("billingAddress", billAddress);
		return "shippingbilling";
	}

	@RequestMapping(value = "/deleteAdd/{title}/{id}", method = RequestMethod.GET)
	public String deleteAddress(@PathVariable("title") String title, @PathVariable("id") String id, ModelMap modelMap) {
		System.out.println("selected title = " + title);
		CheckoutData data = CheckoutData.INSTANCE;
		try {
			Address selectedAddress = DataUtil.readCustFromCache().getAllAddresses().stream()
					.filter(a -> (a.getId()) == Integer.parseInt(id)).findFirst().get();
			ObservableList<CustomerPres> billList = data.deleteCustomerAddressesAndUpdate(title, selectedAddress);
			modelMap.addAttribute("addressFields", GuiConstants.DISPLAY_ADDRESS_FIELDS);
			modelMap.addAttribute("addressList", billList);
		} catch (BackendException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// modelMap.addAttribute("addressList",DefaultData.ADDRESSES_ON_FILE);
		modelMap.addAttribute("title", title);
		return "selectaddress";
	}

	/** Returns the user-filled Billing Address data */
	public Address createAddress(String street, String city, String state, String zip, boolean isShip) {
		return CheckoutData.INSTANCE.createAddress(street, city, state, zip, isShip, !isShip);
	}

	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public String verifyAddress(@RequestParam Map<String, String> allRequestParams, ModelMap modelMap) {
		String shipstreet = allRequestParams.get("shipStreet");
		String shipState = allRequestParams.get("shipState");
		String shipCity = allRequestParams.get("shipCity");
		String shipZip = allRequestParams.get("shipZip");
		Address userShipAdd = createAddress(shipstreet, shipCity, shipState, shipZip, true);
		String billStreet = allRequestParams.get("billStreet");
		String billCity = allRequestParams.get("billCity");
		String billState = allRequestParams.get("billState");
		String billZip = allRequestParams.get("billZip");
		userBillAdd = createAddress(billStreet, billCity, billState, billZip, false);
		String saveBill = null, saveShip = null;
		saveBill = allRequestParams.get("saveBillingAdd");
		saveShip = allRequestParams.get("saveShippingAdd");
		String sameShipBill = allRequestParams.get("sameShipBill");

		boolean rulesOk = true;
		Address cleansedShipAddress = null;
		Address cleansedBillAddress = null;
		String message = "";

		// save ship add
		
			try {
				cleansedShipAddress = CheckoutController.INSTANCE.runAddressRules(userShipAdd);
			} catch (RuleException e) {
				rulesOk = false;
				message = e.getMessage();
			} catch (BusinessException e) {
				rulesOk = false;
				message = e.getMessage();
			}
		
		if (rulesOk) {
			// save bill add

			try {
				cleansedBillAddress = CheckoutController.INSTANCE.runAddressRules(userBillAdd);
			} catch (RuleException e) {
				rulesOk = false;
				message = e.getMessage();
			} catch (BusinessException e) {
				rulesOk = false;
				message = e.getMessage();
			}

		} else {
			modelMap.addAttribute("shippingAddress", userShipAdd);
			modelMap.addAttribute("billingAddress", userBillAdd);
			modelMap.addAttribute("message", message);
			return "shippingbilling";
		}
		if (rulesOk) {
			System.out.println("Address Rules passed!");
			if (cleansedShipAddress != null) {
				try {
					if (saveShip != null) {
						if (saveShip.matches("saveShippingAdd")) {
							CheckoutController.INSTANCE.saveNewAddress(cleansedShipAddress);
						}
					}

				} catch (BackendException e) {
					message = e.getMessage();
					modelMap.addAttribute("shippingAddress", userShipAdd);
					modelMap.addAttribute("billingAddress", userBillAdd);
					modelMap.addAttribute("message", message);
					return "shippingbilling";
				}
			} else {

				modelMap.addAttribute("shippingAddress", userShipAdd);
				modelMap.addAttribute("billingAddress", userBillAdd);
				modelMap.addAttribute("message", message);
				return "shippingbilling";

			}
			if (cleansedBillAddress != null) {
				try {
					if (saveBill != null) {
						if (saveBill.matches("saveBillingAdd")) {
							CheckoutController.INSTANCE.saveNewAddress(cleansedBillAddress);
						}
					}
				} catch (BackendException e) {
					message = e.getMessage();
					modelMap.addAttribute("shippingAddress", userShipAdd);
					modelMap.addAttribute("billingAddress", userBillAdd);
					modelMap.addAttribute("message", message);
					return "shippingbilling";
				}
			} else {
				modelMap.addAttribute("shippingAddress", userShipAdd);
				modelMap.addAttribute("billingAddress", userBillAdd);
				modelMap.addAttribute("message", message);
				return "shippingbilling";
			}

		} else {
			modelMap.addAttribute("shippingAddress", userShipAdd);
			modelMap.addAttribute("billingAddress", userBillAdd);
			modelMap.addAttribute("message", message);
			return "shippingbilling";
		}
		CheckoutController.INSTANCE.setShippingAddress(userShipAdd);
		CheckoutController.INSTANCE.setBillingAddress(userBillAdd);
		return "redirect:/payment";

	}

	@RequestMapping(value = "/backShoppingCart", method = RequestMethod.GET)
	public String backToShoppingCart(ModelMap modelMap) {
		List<CartItemPres> cartItems = BrowseSelectData.INSTANCE.getCartData2();
		modelMap.addAttribute("cartItems", cartItems);
		return "cart";
	}

	@RequestMapping(value = "/terms", method = RequestMethod.POST)
	public String proceedToTermsAndCondition(@RequestParam Map<String, String> allRequestParams, ModelMap modelMap) {
		String name = allRequestParams.get("name");
		String ccType = allRequestParams.get("ccType");
		String ccNumber = allRequestParams.get("ccNumber");
		String ccExpDate = allRequestParams.get("ccExpDate");
		CreditCard cc = CheckoutData.INSTANCE.createCreditCard(name, ccExpDate, ccNumber, ccType);
		boolean rulesok = true;
		String message = "";
		try {
			CheckoutController.INSTANCE.runPaymentRules(userBillAdd, cc);
			CheckoutController.INSTANCE.verifyCreditCard(cc);
		} catch (RuleException e) {
			rulesok = false;
			message = e.getMessage();
		} catch (BusinessException e) {
			rulesok = false;
			message = e.getMessage();
		}
		if (rulesok) {
			CheckoutController.INSTANCE.setPaymentInfoInCart(cc);
			return "redirect:/terms";
		} else {
			modelMap.addAttribute("message", message);
			CreditCardPres ccPress = new CreditCardPres(cc);
			modelMap.addAttribute("ccard", ccPress);
			return "payment";
		}

	}

	@RequestMapping(value = "terms", method = RequestMethod.GET)
	public String proceedToTermsAndConditionget(ModelMap modelMap) {
		modelMap.addAttribute("terms", GuiConstants.TERMS_MESSAGE);
		return "termsncondition";
	}

	@RequestMapping(value = "finalOrder", method = RequestMethod.GET)
	public String proceedToFinalOrder(ModelMap modelMap) {
		List<CartItemPres> cartItems = BrowseSelectData.INSTANCE.getCartData2();
		modelMap.addAttribute("cartItems", cartItems);
		return "finalOrder";
	}

	@RequestMapping(value = "finalOrder", method = RequestMethod.POST)
	public String submitFinalOrder(@RequestParam Map<String, String> allRequestParams, ModelMap modelMap) {
		boolean rulesok=true;
		String message="";
		try {
			CheckoutController.INSTANCE.runFinalOrderRules();
		} catch (RuleException e) {
			rulesok=false;
			message=e.getMessage();
		} catch (BusinessException e) {
			rulesok=false;
			message=e.getMessage();
		}
		if (rulesok) {
			try {
				CheckoutController.INSTANCE.submitFinalOrder();
			} catch (BackendException e) {
				List<CartItemPres> cartItems = BrowseSelectData.INSTANCE.getCartData2();
				modelMap.addAttribute("cartItems", cartItems);
				modelMap.addAttribute("message",e.getMessage());
				return "finalOrder";
			}
			BrowseSelectData.INSTANCE.clearCart();
			return "redirect:/successSubmission";
		}else{
			List<CartItemPres> cartItems = BrowseSelectData.INSTANCE.getCartData2();
			modelMap.addAttribute("cartItems", cartItems);
			modelMap.addAttribute("message",message);
			return "finalOrder";
		}
	
	}
	@RequestMapping(value = "cancelOrder", method = RequestMethod.GET)
	public String cancelOrder() {
		BrowseSelectData.INSTANCE.clearCart();
		return "redirect:/";
	}

	@RequestMapping(value = "successSubmission", method = RequestMethod.GET)
	public String successScreen(ModelMap modelMap) {
		modelMap.addAttribute("message", GuiConstants.SUCCESS_MESSAGE);
		return "successSubmission";
	}
}

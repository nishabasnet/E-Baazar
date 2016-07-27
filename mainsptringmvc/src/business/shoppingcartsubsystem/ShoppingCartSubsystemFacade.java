package business.shoppingcartsubsystem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import middleware.exceptions.DatabaseException;
import presentation.data.DataUtil;
import business.customersubsystem.AddressImpl;
import business.exceptions.BackendException;
import business.exceptions.BusinessException;
import business.exceptions.RuleException;
import business.externalinterfaces.Address;
import business.externalinterfaces.CartItem;
import business.externalinterfaces.CreditCard;
import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.CustomerSubsystem;
import business.externalinterfaces.Rules;
import business.externalinterfaces.ShoppingCart;
import business.externalinterfaces.ShoppingCartSubsystem;

public enum ShoppingCartSubsystemFacade implements ShoppingCartSubsystem {
	INSTANCE;
	
	ShoppingCartImpl liveCart = new ShoppingCartImpl(new LinkedList<CartItem>());
	ShoppingCartImpl savedCart;
	Integer shopCartId;
	CustomerProfile customerProfile;
	Logger log = Logger.getLogger(this.getClass().getPackage().getName());

	// interface methods
	public void setCustomerProfile(CustomerProfile customerProfile) {
		this.customerProfile = customerProfile;
	}
	
	
	public void makeSavedCartLive() {
	System.out.println("saved cart total price: "+savedCart.getTotalPrice());
		liveCart = savedCart;
	}
	
	public ShoppingCart getLiveCart() {
		return liveCart;
	}
	

	public void retrieveSavedCart() throws BackendException {
		try {
			DbClassShoppingCart dbClass = new DbClassShoppingCart();
			ShoppingCartImpl cartFound = dbClass.retrieveSavedCart(customerProfile);
			if(cartFound == null) {
				savedCart = new ShoppingCartImpl(new ArrayList<CartItem>());
			} else {
				savedCart = cartFound;
			}
		} catch(DatabaseException e) {
			throw new BackendException(e);
		}

	}
	
	public void saveCart() throws BackendException {
		try {
			DbClassShoppingCart dbClass = new DbClassShoppingCart();
			
			CustomerSubsystem css = DataUtil.readCustFromCache();
			ShoppingCartImpl shopingCart = (ShoppingCartImpl) getLiveCart();
			
			shopingCart.setShipAddress(css.getDefaultShippingAddress());
			shopingCart.setBillAddress(css.getDefaultBillingAddress());
			shopingCart.setPaymentInfo(css.getDefaultPaymentInfo());
			
			dbClass.saveCart(css.getCustomerProfile(), shopingCart);
			
		} catch(DatabaseException e) {
			throw new BackendException(e);
		}

	}
	
	public void updateShoppingCartItems(List<CartItem> list) {
		liveCart.setCartItems(list);
	}
	
	public List<CartItem> getCartItems() {
		return liveCart.getCartItems();
	}
	
	//static methods
	public static CartItem createCartItem(String productName, String quantity,
            String totalprice) {
		try {
			return new CartItemImpl(productName, quantity, totalprice);
		} catch(BackendException e) {
			throw new RuntimeException("Can't create a cartitem because of productid lookup: " + e.getMessage());
		}
	}

	public static ShoppingCart createShoppingCart(List<CartItem> cartItemsList){
		return new ShoppingCartImpl(cartItemsList);
	}

	public void runShippingRules(ShoppingCart shoppingCart) throws RuleException, BusinessException {

		Rules transferObject = new RulesShoppingCart(shoppingCart);
		transferObject.runRules();

	}
	public void runFinalOrderRules(ShoppingCart shoppingCart) throws RuleException, BusinessException {

		Rules transferObject = new RulesFinalOrder(shoppingCart);
		transferObject.runRules();

	}

	//interface methods for testing
	
	public ShoppingCart getEmptyCartForTest() {
		return new ShoppingCartImpl();
	}

	
	public CartItem getEmptyCartItemForTest() {
		return new CartItemImpl();
	}

	@Override
	public void clearLiveCart() {
		liveCart.clearCart();;
		
	}

	@Override
	public void setShippingAddress(Address addr) {
		liveCart.setShipAddress(addr);
	}

	@Override
	public void setBillingAddress(Address addr) {
		liveCart.setBillAddress(addr);
	}

	@Override
	public void setPaymentInfo(CreditCard cc) {
		liveCart.setPaymentInfo(cc);;
	}

}

package business.ordersubsystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;import java.util.stream.Collector;
import java.util.stream.Collectors;

import business.exceptions.BackendException;
import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.Order;
import business.externalinterfaces.OrderItem;
import business.externalinterfaces.OrderSubsystem;
import business.externalinterfaces.ShoppingCart;
import middleware.exceptions.DatabaseException;

public class OrderSubsystemFacade implements OrderSubsystem {
	private static final Logger LOG = 
			Logger.getLogger(OrderSubsystemFacade.class.getPackage().getName());
	CustomerProfile custProfile;
	    
    public OrderSubsystemFacade(CustomerProfile custProfile){
        this.custProfile = custProfile;
    }
	
	/** 
     *  Used by customer subsystem at login to obtain this customer's order history from the database.
	 *  Assumes cust id has already been stored into the order subsystem facade 
	 *  This is created by using auxiliary methods at the bottom of this class file.
	 *  First get all order ids for this customer. For each such id, get order data
	 *  and form an order, and with that order id, get all order items and insert
	 *  into the order.
	 */
    public List<Order> getOrderHistory() throws BackendException {
    	List<Order> orderList=new ArrayList<>();
    	try {
    		List<Integer> idOfOrders=getAllOrderIds();
        	for (Integer id:idOfOrders) {
        		// get each order data
    			Order order=getOrderData(id);
    
        		System.out.println(order.getOrderId());
    			
    			// get each orderitem of each order
    			List<OrderItem> listOrderItem = getOrderItems(id);
    			order.setOrderItems(listOrderItem);
    			orderList.add(order);
    		}
    	} catch(DatabaseException e) {
    		throw new BackendException("database exception view Order History");
    	}
    	return orderList;
    	
    }
    
    public void submitOrder(ShoppingCart cart) throws BackendException {
    	//implement
    	Order order =new OrderImpl();
    	System.out.println("quantity = "+cart.getCartItems().stream().map(cartItem->cartItem.getQuantity()).collect(Collectors.joining(",")));
    	List<OrderItem> orderItemList=cart.getCartItems().stream()
    			.map(cartItem->{OrderItemImpl orderItem=new OrderItemImpl(cartItem.getProductName(), Integer.valueOf(cartItem.getQuantity()), Double.valueOf(cartItem.getTotalprice()));
    					orderItem.setProductId(cartItem.getProductid());
    					return orderItem;
    			})
    			.collect(Collectors.toList());
    	order.setOrderItems(orderItemList);
    	order.setBillAddress(cart.getBillingAddress());
    	order.setShipAddress(cart.getShippingAddress());
    	order.setPaymentInfo(cart.getPaymentInfo());
    	LocalDate d = LocalDate.now();


    	order.setDate(d);
    	
    	DbClassOrder dbClass=new DbClassOrder();
    	try {
			dbClass.submitOrder(custProfile, order);
		} catch (DatabaseException e) {
			throw new BackendException(e);
		}
    	LOG.warning("The method submitOrder(ShoppingCart cart) in OrderSubsystemFacade has not been implemented");
    }
	
	/** Used whenever an order item needs to be created from outside the order subsystem */
    public static OrderItem createOrderItem(
    		Integer prodId, Integer orderId, String quantityReq, String totalPrice) {
    	//implement
        LOG.warning("Method createOrderItem(prodid, orderid, quantity, totalprice) still needs to be implemented");
    	return null;
    }
    
    /** to create an Order object from outside the subsystem */
    public static Order createOrder(Integer orderId, String orderDate, String totalPrice) {
    	//implement
        LOG.warning("Method  createOrder(Integer orderId, String orderDate, String totalPrice) still needs to be implemented");
    	return null;
    }
    
    ///////////// Methods internal to the Order Subsystem -- NOT public
    List<Integer> getAllOrderIds() throws DatabaseException {
        DbClassOrder dbClass = new DbClassOrder();
        System.out.println("get all order ids "+ custProfile.getCustId());
              return dbClass.getAllOrderIds(custProfile);
        
    }
    
    /** Part of getOrderHistory */
    List<OrderItem> getOrderItems(Integer orderId) throws DatabaseException {
        DbClassOrder dbClass = new DbClassOrder();
        System.out.println("getorderitems"+ orderId);
        return dbClass.getOrderItems(orderId);
    }
    
    /** Uses cust id to locate top-level order data for customer -- part of getOrderHistory */
    OrderImpl getOrderData(Integer orderId) throws DatabaseException {
    	DbClassOrder dbClass = new DbClassOrder();
    	return dbClass.getOrderData(orderId);
    }
}

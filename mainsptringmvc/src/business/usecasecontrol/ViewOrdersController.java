
package business.usecasecontrol;

import java.util.List;

import business.externalinterfaces.CustomerSubsystem;
import business.externalinterfaces.Order;
import business.externalinterfaces.OrderItem;

/**
 * @author pcorazza
 */
public enum ViewOrdersController   {
	INSTANCE;
	
	public List<Order> getOrderHistory(CustomerSubsystem cust) {
		return cust.getOrderHistory();
	}
	
	public List<OrderItem> getOrderHistoryDetail(CustomerSubsystem cust, int orderId){
		Order order = cust.getOrderHistory().stream().filter(x->x.getOrderId()==orderId).iterator().next();
	
		return  order.getOrderItems();
	}
	
	
}

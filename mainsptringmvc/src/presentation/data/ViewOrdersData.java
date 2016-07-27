package presentation.data;

import java.util.List;
import java.util.stream.Collectors;

import business.BusinessConstants;
import business.SessionCache;
import business.exceptions.BackendException;
import business.externalinterfaces.CustomerSubsystem;
import business.externalinterfaces.Order;
import business.usecasecontrol.BrowseAndSelectController;
import business.usecasecontrol.ViewOrdersController;
import presentation.util.Util;

public enum ViewOrdersData {
	INSTANCE;
	private OrderPres selectedOrder;

	public OrderPres getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder(OrderPres so) {
		selectedOrder = so;
	}

	public List<OrderPres> getOrderList() throws BackendException {

		CustomerSubsystem cust = (CustomerSubsystem) SessionCache.getInstance().get(BusinessConstants.CUSTOMER);
	
				return ViewOrdersController.INSTANCE.getOrderHistory(cust)
				.stream().map(order -> Util.orderToOrderPres(order))
				.collect(Collectors.toList());

	}

	 public List<OrderItemPres> getOrderListDetail(int orderid) throws BackendException{
		 CustomerSubsystem cust = (CustomerSubsystem) SessionCache.getInstance().get(BusinessConstants.CUSTOMER);
			
			return ViewOrdersController.INSTANCE.getOrderHistoryDetail(cust, orderid)
			.stream().map(orderitem -> Util.orderitemToOrderItemPres(orderitem))
			.collect(Collectors.toList());
		 
	 }
}

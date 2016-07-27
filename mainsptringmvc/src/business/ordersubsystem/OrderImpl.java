package business.ordersubsystem;

import java.time.LocalDate;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import business.externalinterfaces.Address;
import business.externalinterfaces.CreditCard;
import business.externalinterfaces.Order;
import business.externalinterfaces.OrderItem;

public class OrderImpl implements Order {
	private List<OrderItem> orderItems;
	private int orderId;
	private LocalDate date;
	private double totalPrice;
	public OrderImpl() {
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public void setTotalPrice(double price){
		this.totalPrice = price;
	}
	
	public double getTotalPrice() {
		if(orderItems == null) {
			return totalPrice;
		} else {
			 DoubleSummaryStatistics summary 
			    = orderItems.stream().collect(
				    Collectors.summarizingDouble(
					   (OrderItem item) -> item.getUnitPrice() * item.getQuantity()));
			 return summary.getSum();
		}
	}
	
	public LocalDate getOrderDate() {
		//note that LocalDates are immutable
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	private Address shipAddress;
	private Address billAddress;
	private CreditCard cc;

	

	@Override
	public Address getShipAddress() {
		// TODO Auto-generated method stub
		return shipAddress;
	}

	@Override
	public Address getBillAddress() {
		// TODO Auto-generated method stub
		return billAddress;
	}

	@Override
	public CreditCard getPaymentInfo() {
		// TODO Auto-generated method stub
		return cc;
	}

	@Override
	public void setShipAddress(Address add) {
		this.shipAddress=add;
	}

	@Override
	public void setBillAddress(Address add) {
		this.billAddress=add;
	}

	@Override
	public void setPaymentInfo(CreditCard cc) {
		this.cc=cc;
	}

}

package org.drools.devguide.eshop.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<OrderItem> items;
	private OrderState state;
	
	public Order() {
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	
	public OrderState getState() {
		return state;
	}
	
	public void setState(OrderState state) {
		this.state = state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (state != other.state)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [items=" + items + ", state=" + state + "]";
	}
}
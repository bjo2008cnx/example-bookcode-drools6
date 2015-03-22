package org.drools.devguide.eshop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Client client;
	private Date date;
	private List<OrderItem> items;
	private OrderState state;
	
	public Order() {
	}

        public Client getClient() {
            return client;
        }

        public void setClient(Client client) {
            this.client = client;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
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
        
        public double getTotal(){
            return this.getItems().stream()
                    .mapToDouble(item -> item.getProduct().getSalePrice() * item.getQuantity())
                    .sum();
        }

        @Override
        public int hashCode() {
                int hash = 5;
                return hash;
        }

        @Override
        public boolean equals(Object obj) {
                if (obj == null) {
                    return false;
                }
                if (getClass() != obj.getClass()) {
                    return false;
                }
                final Order other = (Order) obj;
                if (!Objects.equals(this.client, other.client)) {
                    return false;
                }
                if (!Objects.equals(this.date, other.date)) {
                    return false;
                }
                if (!Objects.equals(this.items, other.items)) {
                    return false;
                }
                if (this.state != other.state) {
                    return false;
                }
                return true;
        }

        @Override
        public String toString() {
                return "Order [" + "client=" + client + ", date=" + date + ", items=" + items + ", state=" + state + ']';
        }
}
package org.drools.devguide.eshop.model;

import java.io.Serializable;
import java.util.Date;

public class Discount implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Provider provider;
	private Double percentage;
	private Integer minimumQuantity;
	private Date start;
	private Date end;
	
	public Discount() {
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Integer getMinimumQuantity() {
		return minimumQuantity;
	}

	public void setMinimumQuantity(Integer minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result
				+ ((minimumQuantity == null) ? 0 : minimumQuantity.hashCode());
		result = prime * result
				+ ((percentage == null) ? 0 : percentage.hashCode());
		result = prime * result
				+ ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
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
		Discount other = (Discount) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (minimumQuantity == null) {
			if (other.minimumQuantity != null)
				return false;
		} else if (!minimumQuantity.equals(other.minimumQuantity))
			return false;
		if (percentage == null) {
			if (other.percentage != null)
				return false;
		} else if (!percentage.equals(other.percentage))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Discount [provider=" + provider + ", percentage=" + percentage
				+ ", minimumQuantity=" + minimumQuantity + ", start=" + start
				+ ", end=" + end + "]";
	}
	
	
}

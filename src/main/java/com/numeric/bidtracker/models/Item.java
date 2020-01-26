package com.numeric.bidtracker.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Item implements IItem, Serializable {
	public static enum BID_STATUS {Pending, Started, Closed, Cancelled}

	private static final long serialVersionUID = 3428998467177668155L;

	private static int MINIMUM_BID_DURATION = 7;  // in days
	private static int MAXIMUN_BID_DURATION = 30; // in days

	private String category;
	private String title;
	private String decsription;

	private BigDecimal startPrice, reservePrice;

	private int startDate = Integer.MIN_VALUE;
	private long startTime = Long.MIN_VALUE;
	private int duration = MINIMUM_BID_DURATION;

	private BID_STATUS status = BID_STATUS.Pending;

	// this would be allocated after persistence beyond the scope of this exercise,
	// we will randomly allocate in the BidTracker.
	private int itemId;

	private Long id; // persistence scope only - beyond scope

	/**
	 * Item holds information on the item to be auctioned.
	 *
	 *@param category     - the class or type of the item
	 *@param title        - Simple item description
	 *@param description  - detail item description
	 *@param startPrice   - price to start the auction at
	 *@param reservePrice - minimum the item must sell for
	 *@param startDate    - when the auction should start
	 *@param reserveTime  - what time the auction should start on the day
	 *@param duration     - how many days the auction should be on for between 7 - 30
	 */
	public Item(String category, String title, String description,
				BigDecimal startPrice, BigDecimal reservePrice,
				int startDate, long startTime, int duration) {

		this.category = category;
		this.title = title;
		this.decsription = description;

		this.startPrice = startPrice == null ? BigDecimal.ZERO : startPrice;
		this.reservePrice = reservePrice == null ? BigDecimal.ZERO : reservePrice;

		this.startDate = Integer.MIN_VALUE;
		this.startTime = new Date().getTime();;
		this.duration = duration;
		if (duration < MINIMUM_BID_DURATION && duration > MAXIMUN_BID_DURATION)
			throw new IllegalArgumentException("Error: Your set duration is not within permitted 7 - 30 days");

		status = BID_STATUS.Pending;
	}

	@Override
	public String getCategory() { return category; }
	@Override
	public String getTitle() { return title; }
	@Override
	public String getDecsription() { return decsription; }
	@Override
	public BigDecimal getStartPrice() { return startPrice; }
	@Override
	public BigDecimal getReservePrice() { return reservePrice; }
	@Override
	public int getStartDate() { return startDate; }
	@Override
	public long getStartTime() { return startTime; }
	@Override
	public int getDuration() { return duration; }

	@Id
	@GeneratedValue
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id;}

	@Override
	public BID_STATUS getStatus() { return status; }

	@Override
	public int getItemId() { return itemId; }

	@Override
	public void setCategory(String category) { this.category = category; }
	@Override
	public void setTitle(String title) { this.title = title; }
	@Override
	public void setDecsription(String decsription) { this.decsription = decsription; }
	@Override
	public void setReservePrice(BigDecimal reservePrice) { this.reservePrice = reservePrice; }
	@Override
	public void setStartDate(int startDate) { this.startDate = startDate; }
	@Override
	public void setStartTime(long startTime) { this.startTime = startTime; }
	@Override
	public void setDuration(int duration) { this.duration = duration; }
	@Override
	public void setStatus(BID_STATUS status) { this.status = status; }
	@Override
	public void setItemId(int itemId) { this.itemId = itemId; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((decsription == null) ? 0 : decsription.hashCode());
		result = prime * result + duration;
		result = prime * result + itemId;
		result = prime * result + ((startPrice == null) ? 0 : startPrice.hashCode());
		result = prime * result + ((reservePrice == null) ? 0 : reservePrice.hashCode());
		result = prime * result + startDate;
		result = prime * result + (int) (startTime ^ (startTime >>> 32));
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Item other = (Item) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (decsription == null) {
			if (other.decsription != null)
				return false;
		} else if (!decsription.equals(other.decsription))
			return false;
		if (duration != other.duration)
			return false;
		if (startPrice == null) {
			if (other.startPrice != null)
				return false;
		} else if (reservePrice == null) {
			if (other.reservePrice != null)
				return false;
		} else if (!reservePrice.equals(other.reservePrice))
			return false;
		if (startDate != other.startDate)
			return false;
		if (startTime != other.startTime)
			return false;
		if (status != other.status)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item [category=" + category + ", title=" + title +
				", startPrice=" + startPrice + ", reservePrice=" + reservePrice +
				", startDate=" + startDate + ", startTime=" + startTime +
				", duration=" + duration + ", status=" + status + ", itemId=" + itemId + "]";
	}
}

package com.numeric.bidtracker.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Bid implements IBid, Serializable, Comparable<Bid> {
	private static final long serialVersionUID = -161196594335877800L;

	private User bidder;
	private Item item;
	private BigDecimal bidAmount;

	private Long id; // persistence scope, beyond scope!

    /**
     * Bid - is a user's bid at given value on an item.
     *
     * @param bidder      - user of the bidding on item
     * @param item      - item the user
     * @param bidAmount - how much the bidder is bidding for the item
     */
	public Bid(User bidder, Item item, BigDecimal bidAmount) {
		this.bidder = bidder;
		this.item = item;
		this.bidAmount = bidAmount;
	}

	@Id
	@GeneratedValue
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id;}

	@Override
	@ManyToOne
	public User getBidder() { return bidder; }
	@Override
	@OneToOne
	public Item getItem() { return item; }
	@Override
	public BigDecimal getBidAmount() { return bidAmount; }

	@Override
	public void setBidder(User bidder) { this.bidder = bidder; }
	@Override
	public void setItem(Item item) { this.item = item; }
	@Override
	public void setBidAmount(BigDecimal bidAmount) { this.bidAmount = bidAmount; }

	@Override
	public String toString() {
		return "Bid [bidder=" + bidder + ", item=" + item + ", bidAmount=" + bidAmount + "]";
	}

	// compare two bids by price used when in a priority queue so highest is on top
	@Override
	public int compareTo(Bid o) {
		return o.bidAmount.compareTo(this.bidAmount);
	}

	public boolean isValidBidAmount(final Item item) {
        return bidAmount.compareTo(item.getStartPrice()) == 1;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bidAmount == null) ? 0 : bidAmount.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((bidder == null) ? 0 : bidder.hashCode());
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
		Bid other = (Bid) obj;
		if (bidAmount == null) {
			if (other.bidAmount != null)
				return false;
		} else if (!bidAmount.equals(other.bidAmount))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (bidder == null) {
			if (other.bidder != null)
				return false;
		} else if (!bidder.equals(other.bidder))
			return false;
		return true;
	}
}

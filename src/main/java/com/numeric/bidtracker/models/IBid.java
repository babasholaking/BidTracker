package com.numeric.bidtracker.models;

import java.math.BigDecimal;

public interface IBid {

	User getBidder();
	Item getItem();
	BigDecimal getBidAmount();

	void setBidder(User bidder);
	void setItem(Item item);
	void setBidAmount(BigDecimal bidPrice);

	boolean isValidBidAmount(final Item item);
}
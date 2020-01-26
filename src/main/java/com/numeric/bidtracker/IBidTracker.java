package com.numeric.bidtracker;

import java.util.Collection;
import java.util.List;

import com.numeric.bidtracker.models.Bid;
import com.numeric.bidtracker.models.Item;
import com.numeric.bidtracker.models.User;

public interface IBidTracker {
	void postAnAuction(final User owner, final Item item) throws BidTrackerException;

    void registerBid(final User user, final Item item, final Bid bid) throws InvalidBidException, BidTrackerException;

    Bid getLargestBid(final Item item);
    Bid getCurrentWinningBid(final Item item);

	boolean isReserveMet(final Item item);

    List<Bid> getAllBids(final Item item);

    Collection<Item> getAllItems(final User user);

}

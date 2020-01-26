package com.numeric.bidtracker;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import com.numeric.bidtracker.models.Bid;
import com.numeric.bidtracker.models.Item;
import com.numeric.bidtracker.models.User;

public class BidTracker implements IBidTracker {

    private final Map<Item, Queue<Bid>> itemMap;
    private final Map<User, Queue<Item>> userMap;

	public BidTracker() {
		itemMap = new ConcurrentHashMap<>(100, 0.75f);
		userMap = new ConcurrentHashMap<>(100, 0.75f);
	}

    @Override
    public void postAnAuction(final User owner, final Item item) throws BidTrackerException {
		if (owner == null || item == null)
			throw new BidTrackerException("Unable to registerBid as the owner or item is null.");

    	// prepare the storages for the item in context
    	prepareStorages(owner, item);
        userMap.get(owner).add(item);
    }

    @Override
    public void registerBid(final User bidder, final Item item, final Bid bid) throws InvalidBidException, BidTrackerException {
		if (bidder == null)
			throw new BidTrackerException("Unable to registerBid as the bidder is null.");
		if (item == null)
			throw new BidTrackerException("Unable to registerBid as the item is null.");
		if (bid == null)
			throw new BidTrackerException("Unable to registerBid as the bid is null.");

		// prepare the storages for the item in context
		prepareStorages(bidder, item);

		// check to see if the bid value is sufficient
		if (!bid.isValidBidAmount(item))
			throw new InvalidBidException("Error: bid amount less than minimum!");

		// check item map is not empty and that the last bid value is lower
		// than the new bid
		if (itemMap.get(item).size() == 0 ||
			bid.getBidAmount().compareTo(itemMap.get(item).peek().getBidAmount()) == 1) {
			itemMap.get(item).add(bid);
			userMap.get(bidder).add(item);
		}
    }

	/**
     * Get the largest bid for an item.
     *
     * @param item
     */
    @Override
    public Bid getLargestBid(final Item item) {
        return itemMap.get(item).peek();
    }


	/**
     * Get the current winning bid for an item. The winning bid has to be equal or
     * bigger than the reserve amount. If no reserve set, then the winning bid is the
     * largest bid amount.
     *
     * @param item
     */
    @Override
    public Bid getCurrentWinningBid(final Item item) {
    	Bid largestBid = getLargestBid(item);
   		return largestBid.getBidAmount().compareTo(item.getReservePrice()) >= 0 ? largestBid : null;
    }

    /**
     * Get the largest bid for an item which is bigger than any preset reserve.
     *
     * Note: if the largest bid is less than the preset reserve, return null.
     * @param item
     */
    @Override
    public boolean isReserveMet(final Item item) {
    	BigDecimal bidAmount = itemMap.get(item).peek().getBidAmount();
        if (item.getReservePrice().compareTo(bidAmount) <= 0)
        	return true;
        else
        	return false;
    }

    /**
     * Get all the bids placed on an item.
     *
     * @param item
     */
    @Override
    public List<Bid> getAllBids(final Item item) {
        return new LinkedList<>(itemMap.get(item));
    }

    /**
     * Get all the items on which a user bid on.
     *
     * @param user
     */
    @Override
    public List<Item> getAllItems(final User user) {
        return new LinkedList<>(userMap.get(user));
    }

    /**
     * Prepare the maps storages to ensure that the keys are in-place.
     *
     * @param user the current owner or bidder
     * @param item the item the bid is against
     */
    private void prepareStorages(final User user, final Item item) {
        if (itemMap.get(item) == null)
        	itemMap.put(item, new PriorityQueue<Bid>());

        if (userMap.get(user) == null)
        	userMap.put(user, new LinkedList<Item>());
    }
}

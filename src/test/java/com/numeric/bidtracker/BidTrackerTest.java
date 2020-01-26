package com.numeric.bidtracker;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.numeric.bidtracker.models.Bid;
import com.numeric.bidtracker.models.Item;
import com.numeric.bidtracker.models.User;

public class BidTrackerTest {

	private BidTracker bidTracker = new BidTracker();
	private User owner, bidder, defaultBidder;
	private Item item, item2, noReserveOrStartItem, noReserveItem;

    @Before
    public void setup() {
		item = new Item("Technology", "32inch Monitor",
						 "A long description of the 32inches monitor",
						 new BigDecimal(225), new BigDecimal(350), 20200123, 9876l, 30);

		item2 = new Item("Furniture", "Dinning Table",
						 "A long description of the dinning table with any distinct features to attract bidders",
						 new BigDecimal(45), new BigDecimal(120), 20200123, 9876l, 7);

		noReserveItem = new Item("Technology", "Keyboard", "brand new wireless keyboard",
								 new BigDecimal(25), null, 20200123, 9876l, 7);

		noReserveOrStartItem = new Item("Technology", "Mouse", "brand new wireless mouse",
									    null, null, 20200123, 9876l, 7);

		owner = new User("Adam King", "AdamKing@gmail.com", "07970448787");

		defaultBidder = new User("Daily Thompson", "dthompson@gmail.com", "07970687558");
		bidder = new User("Doreen Lane", "AdamKing@gmail.com", "07970448787");
    }

	@Test
	public void shouldBeAbleToPostAnAuction() throws BidTrackerException {
	   bidTracker.postAnAuction(owner, item);

		List<Item> ownersItems = bidTracker.getAllItems(owner);
    	assertThat(ownersItems.size(), is(1));
	}

    @Test
    public void bidOnItemWithAmountLowerThanTheStartValueShouldFail() {
		Bid bid = new Bid(defaultBidder, item, new BigDecimal(100));

        assertThat(bid.isValidBidAmount(item), equalTo(false));
    }

    @Test
    public void bidOnItemWithTheSameAmountAsTheStartValueShouldFail() {
		Bid bid = new Bid(defaultBidder, item, new BigDecimal(225));

        assertThat(bid.isValidBidAmount(item), equalTo(false));
    }

    @Test
    public void bidOnItemWithAHigherAmountThanTheStartValueSucceeds() {
		Bid bid = new Bid(defaultBidder, item, new BigDecimal(275));

        assertThat(bid.isValidBidAmount(item), equalTo(true));
    }

    @Test(expected = InvalidBidException.class)
	public void shouldThrowsExceptionWhenBidAmountLessThanMinimum() throws InvalidBidException, BidTrackerException {

		Bid bid = new Bid(bidder, item, new BigDecimal(100.10));

		// should throw an exception as the minimum price is bigger than the bid
		bidTracker.registerBid(bidder, item, bid);

        assertThat(bidTracker.getAllItems(bidder).isEmpty(), is(true));
	}

	@Test
	public void shouldRecordUsersBidOnAnItem() throws InvalidBidException, BidTrackerException {
        // Item - startPrice: 225, reservePrice:250
		Bid bid = new Bid(defaultBidder, item, new BigDecimal(260));

		// should NOT throw an exception if the minimum price is bigger than the bid
		bidTracker.registerBid(defaultBidder, item, bid);

		assertThat(bidTracker.getLargestBid(item), is(bid));
	}

    @Test
    public void testTwoBidsWithEqualValueOnlyRegisterOnce() throws Exception {
        // Item2 - startPrice: 45, reservePrice:120
        Bid
			bid1 = new Bid(bidder, noReserveOrStartItem, new BigDecimal(65)),
			bid2 = new Bid(bidder, noReserveOrStartItem, new BigDecimal(65));

        bidTracker.registerBid(bidder, noReserveOrStartItem, bid1);
        bidTracker.registerBid(bidder, noReserveOrStartItem, bid2);

        assertThat(bidTracker.getAllItems(bidder).size(), is(1));
        assertThat(bidTracker.getLargestBid(item2), is(bid1));
    }

	@Test
	public void shouldObtainAllItemsUserBidOn() throws InvalidBidException, BidTrackerException {
        // Item2 - startPrice: 45, reservePrice:120
		Bid
			bid = new Bid(bidder, item2, new BigDecimal(50)),
			bid2 = new Bid(bidder, item2, new BigDecimal(65)),
		    bid3 = new Bid(bidder, item2, new BigDecimal(85)),
		    bid4 = new Bid(bidder, item2, new BigDecimal(105));

		bidTracker.registerBid(bidder, item2, bid);
		bidTracker.registerBid(bidder, item2, bid2);
		bidTracker.registerBid(bidder, item2, bid3);
		bidTracker.registerBid(bidder, item2, bid4);

		List<Item> allItems = bidTracker.getAllItems(bidder);
		assertThat(allItems.size(), is(4));
	}

	@Test
	public void shouldGetTheLastBidAsWinningBid() throws InvalidBidException, BidTrackerException {
        // noReserveItem - startPrice: 25, reservePrice:null
		Bid
			bid = new Bid(bidder, noReserveItem, new BigDecimal(45.10)),
			bid2 = new Bid(bidder, noReserveItem, new BigDecimal(50)),
			bid3 = new Bid(bidder, noReserveItem, new BigDecimal(65.00)),
		    bid4 = new Bid(bidder, noReserveItem, new BigDecimal(70.50));

		bidTracker.registerBid(bidder, noReserveItem, bid);
		bidTracker.registerBid(bidder, noReserveItem, bid2);
		bidTracker.registerBid(bidder, noReserveItem, bid3);
		bidTracker.registerBid(bidder, noReserveItem, bid4);

		assertThat(bidTracker.getLargestBid(noReserveItem), is(new BigDecimal(70.50)));
	}

    @Test
    public void currentWinningBidGivenMultipleBidsOnAnItem() throws InvalidBidException, BidTrackerException {
        // Item2 - startPrice: 45, reservePrice:120
		Bid
			bid = new Bid(bidder, item2, new BigDecimal(100.10)),
			bid2 = new Bid(defaultBidder, item2, new BigDecimal(110.10)),
			bid3 = new Bid(bidder, item2, new BigDecimal(120.00)),
		    bid4 = new Bid(defaultBidder, item2, new BigDecimal(130.00)),
	        bid5 = new Bid(defaultBidder, item2, new BigDecimal(140.10));

		bidTracker.registerBid(bidder, item2, bid);
		bidTracker.registerBid(defaultBidder, item2, bid2);
		bidTracker.registerBid(bidder, item2, bid3);
		bidTracker.registerBid(defaultBidder, item2, bid4);
		bidTracker.registerBid(defaultBidder, item2, bid5);

        assertThat(bidTracker.getCurrentWinningBid(item2), is(bid5));

        // there should only be four valid bids
        assertThat(bidTracker.getAllBids(item2).size(), is(5));
    }
}

package com.numeric.bidtracker.models;

import java.math.BigDecimal;

import com.numeric.bidtracker.models.Item.BID_STATUS;

public interface IItem {

	String getCategory();
	String getTitle();
	String getDecsription();
	BigDecimal getStartPrice();
	BigDecimal getReservePrice();
	int getStartDate();
	long getStartTime();
	int getDuration();
	BID_STATUS getStatus();
	int getItemId();

	void setCategory(String category);
	void setTitle(String title);
	void setDecsription(String decsription);
	void setReservePrice(BigDecimal reservePrice);
	void setStartDate(int startDate);
	void setStartTime(long startTime);
	void setDuration(int duration);
	void setStatus(BID_STATUS status);
	void setItemId(int itemId);
}
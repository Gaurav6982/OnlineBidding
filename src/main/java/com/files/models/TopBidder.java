package com.files.models;

public class TopBidder {
	private User user;
	private int amount;
	public TopBidder(User user, int amount) {
		super();
		this.user = user;
		this.amount = amount;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}

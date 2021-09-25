package com.files.models;

public class Auction {
	private int id,min_value,user_id;
	public Auction(int id, int min_value, int user_id, String auction_name, String item_name, String start_timestamp,
			String end_timestamp, String created_at, String updated_at, boolean is_active) {
		super();
		this.id = id;
		this.min_value = min_value;
		this.user_id = user_id;
		this.auction_name = auction_name;
		this.item_name = item_name;
		this.start_timestamp = start_timestamp;
		this.end_timestamp = end_timestamp;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.is_active = is_active;
	}
	private String auction_name,item_name,start_timestamp,end_timestamp,created_at,updated_at;
	private boolean is_active;
	public Auction(int id, int min_value, String auction_name, String item_name, String start_timestamp,
			String end_timestamp,boolean is_active) {
		super();
		this.id = id;
		this.min_value = min_value;
		this.auction_name = auction_name;
		this.item_name = item_name;
		this.start_timestamp = start_timestamp;
		this.end_timestamp = end_timestamp;
		this.is_active = is_active;
	}
	public int getId() {
		return id;
	}
	public boolean isIs_active() {
		return is_active;
	}
	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMin_value() {
		return min_value;
	}
	public void setMin_value(int min_value) {
		this.min_value = min_value;
	}
	public String getAuction_name() {
		return auction_name;
	}
	public void setAuction_name(String auction_name) {
		this.auction_name = auction_name;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getStart_timestamp() {
		return start_timestamp;
	}
	public void setStart_timestamp(String start_timestamp) {
		this.start_timestamp = start_timestamp;
	}
	public String getEnd_timestamp() {
		return end_timestamp;
	}
	public void setEnd_timestamp(String end_timestamp) {
		this.end_timestamp = end_timestamp;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	@Override
	public String toString() {
		return "Auction [id=" + id + ", min_value=" + min_value + ", auction_name=" + auction_name + ", item_name="
				+ item_name + ", start_timestamp=" + start_timestamp + ", end_timestamp=" + end_timestamp
				+ ", created_at=" + created_at + ", updated_at=" + updated_at + ", is_active=" + is_active + "]";
	}
	
}

package com.gmail.jbosworth2.japanese_studies;

import java.util.Date;

public class Review {
	private Item item;
	private Date nextReview;
	
	public Review(Item item, Date nextReview){
		this.item = item;
		this.nextReview = nextReview;
	}
	
	public Item getItem(){
		return item;
	}
	
	public void setItem(Item item){
		this.item = item;
	}
	
	public Date getNextReview(){
		return nextReview;
	}
	
	public void setNextReview(Date nextReview){
		this.nextReview = nextReview;
	}
}

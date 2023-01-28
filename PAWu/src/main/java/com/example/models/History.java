package com.example.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class History {
	@Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 private Long id;
	 private String winner;
	 private int winner_points;
	 private String losser;
	 private int losser_points;
	 
	public History() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public int getWinnerPoints() {
		return winner_points;
	}

	public void setWinnerPoints(int winner_points) {
		this.winner_points = winner_points;
	}

	public String getLosser() {
		return losser;
	}

	public void setLosser(String losser) {
		this.losser = losser;
	}

	public int getLosserPoints() {
		return losser_points;
	}

	public void setLosserPoints(int losser_points) {
		this.losser_points = losser_points;
	}
	
	
}

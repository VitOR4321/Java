package com.example.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Matches {
	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 private Long id;
	 private int round;
	 private String first_nick;
	 private String secound_nick;
	 private String result;
	 private boolean end_game;
	 
	public Matches() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public String getFirstNick() {
		return first_nick;
	}

	public void setFirstNick(String first_nick) {
		this.first_nick = first_nick;
	}

	public String getSecoundNick() {
		return secound_nick;
	}

	public void setSecoundNick(String secound_nick) {
		this.secound_nick= secound_nick;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public boolean isEndGame() {
		return end_game;
	}

	public void setEndGame(boolean end_game) {
		this.end_game = end_game;
	}
	 
}

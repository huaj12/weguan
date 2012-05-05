package com.juzhai.search.controller.form;

import java.util.List;

public class SearchProfileForm {
	long city;
	long town;
	Integer gender;
	int minYear;
	int maxYear;
	List<String> educations;
	int minMonthlyIncome;
	String home;
	List<Long> constellationId;
	String house;
	String car;
	int minHeight;
	int maxHeight;

	public SearchProfileForm(long city, long town, Integer gender, int minYear,
			int maxYear, List<String> educations, int minMonthlyIncome,
			String home, List<Long> constellationId, String house, String car,
			int minHeight, int maxHeight) {
		this.city = city;
		this.town = town;
		this.gender = gender;
		this.minYear = minYear;
		this.maxYear = maxYear;
		this.educations = educations;
		this.minMonthlyIncome = minMonthlyIncome;
		this.home = home;
		this.constellationId = constellationId;
		this.house = house;
		this.car = car;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
	}

	public long getCity() {
		return city;
	}

	public void setCity(long city) {
		this.city = city;
	}

	public long getTown() {
		return town;
	}

	public void setTown(long town) {
		this.town = town;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public int getMinYear() {
		return minYear;
	}

	public void setMinYear(int minYear) {
		this.minYear = minYear;
	}

	public int getMaxYear() {
		return maxYear;
	}

	public void setMaxYear(int maxYear) {
		this.maxYear = maxYear;
	}

	public List<String> getEducations() {
		return educations;
	}

	public void setEducations(List<String> educations) {
		this.educations = educations;
	}

	public int getMinMonthlyIncome() {
		return minMonthlyIncome;
	}

	public void setMinMonthlyIncome(int minMonthlyIncome) {
		this.minMonthlyIncome = minMonthlyIncome;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public List<Long> getConstellationId() {
		return constellationId;
	}

	public void setConstellationId(List<Long> constellationId) {
		this.constellationId = constellationId;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

}

package es.uv.prnr.p3.spring.p3_spring.model;

public class MonthlyBudget {

	private int year;
	private int month;
	private float amount;

	public MonthlyBudget(int year, int month, float amount) {
		super();
		this.year = year;
		this.month = month;
		this.amount = amount;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public float getAmount() {
		return amount;
	}

}

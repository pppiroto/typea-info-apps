package info.typea.sample.restservice.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AIRLINES database table.
 * 
 */
@Entity
@Table(name="AIRLINES")
public class Airline implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String airline;

	@Column(name="AIRLINE_FULL")
	private String airlineFull;

	@Column(name="BASIC_RATE")
	private double basicRate;

	@Column(name="BUSINESS_LEVEL_FACTOR")
	private double businessLevelFactor;

	@Column(name="BUSINESS_SEATS")
	private int businessSeats;

	@Column(name="DISTANCE_DISCOUNT")
	private double distanceDiscount;

	@Column(name="ECONOMY_SEATS")
	private int economySeats;

	@Column(name="FIRSTCLASS_LEVEL_FACTOR")
	private double firstclassLevelFactor;

	@Column(name="FIRSTCLASS_SEATS")
	private int firstclassSeats;

    public Airline() {
    }

	public String getAirline() {
		return this.airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getAirlineFull() {
		return this.airlineFull;
	}

	public void setAirlineFull(String airlineFull) {
		this.airlineFull = airlineFull;
	}

	public double getBasicRate() {
		return this.basicRate;
	}

	public void setBasicRate(double basicRate) {
		this.basicRate = basicRate;
	}

	public double getBusinessLevelFactor() {
		return this.businessLevelFactor;
	}

	public void setBusinessLevelFactor(double businessLevelFactor) {
		this.businessLevelFactor = businessLevelFactor;
	}

	public int getBusinessSeats() {
		return this.businessSeats;
	}

	public void setBusinessSeats(int businessSeats) {
		this.businessSeats = businessSeats;
	}

	public double getDistanceDiscount() {
		return this.distanceDiscount;
	}

	public void setDistanceDiscount(double distanceDiscount) {
		this.distanceDiscount = distanceDiscount;
	}

	public int getEconomySeats() {
		return this.economySeats;
	}

	public void setEconomySeats(int economySeats) {
		this.economySeats = economySeats;
	}

	public double getFirstclassLevelFactor() {
		return this.firstclassLevelFactor;
	}

	public void setFirstclassLevelFactor(double firstclassLevelFactor) {
		this.firstclassLevelFactor = firstclassLevelFactor;
	}

	public int getFirstclassSeats() {
		return this.firstclassSeats;
	}

	public void setFirstclassSeats(int firstclassSeats) {
		this.firstclassSeats = firstclassSeats;
	}

}
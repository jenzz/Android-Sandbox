package de.jensdriller.dvd4uCatalogue;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

	private int id;
	private String name;
	private double price;
	private int categoryId;
	private String description;
	private String director;
	private String actors;
	private String certificate;
	private String screen;
	private int year;

	public Product(int id, String name, double price, int categoryId,
			String description, String director, String actors,
			String certificate, String screen, int year) {

		this.id = id;
		this.name = name;
		this.price = price;
		this.categoryId = categoryId;
		this.description = description;
		this.director = director;
		this.actors = actors;
		this.certificate = certificate;
		this.screen = screen;
		this.year = year;

	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getDescription() {
		return description;
	}

	public String getDirector() {
		return director;
	}

	public String getActors() {
		return actors;
	}

	public String getCertificate() {
		return certificate;
	}

	public String getScreen() {
		return screen;
	}

	public int getYear() {
		return year;
	}

	
	/* 
	 * Parcelable methods for passing object between activities 
	 */
	
	public Product(Parcel in) {
		readFromParcel(in);
	}
	
	public int describeContents() {
		return 0;
	}
 
	public void writeToParcel(Parcel dest, int flags) { 
		
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeDouble(price);
		dest.writeInt(categoryId);
		dest.writeString(description);
		dest.writeString(director);
		dest.writeString(actors);
		dest.writeString(certificate);
		dest.writeString(screen);
		dest.writeInt(year);

	}

	
	private void readFromParcel(Parcel in) {
		
		id = in.readInt();
		name = in.readString();
		price = in.readDouble();
		categoryId = in.readInt();
		description = in.readString();
		director = in.readString();
		actors = in.readString();
		certificate = in.readString();
		screen = in.readString();
		year = in.readInt();
		
	}
	
	public static final Parcelable.Creator<Product> CREATOR =
	    	new Parcelable.Creator<Product>() {
	            public Product createFromParcel(Parcel in) {
	                return new Product(in);
	            }
	 
	            public Product[] newArray(int size) {
	                return new Product[size];
	            }
	        };
	
}
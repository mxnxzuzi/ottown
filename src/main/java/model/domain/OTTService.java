package model.domain;

import java.util.ArrayList;
import java.util.List;

public enum OTTService {
	NETFLIX(1, "Netflix"),
    TVING(2, "Tving"),
    COUPANG_PLAY(3, "Coupang Play"),
    DISNEY_PLUS(4, "Disney+"),
    Wavve(5, "Wavve"),
    Watch(6, "Watcha");
    

	private int id;
	private String name;
	private String image;
	
	private OTTService(int id, String name) {
        this.id = id;
        this.name = name;
    }
	
	private OTTService(int id, String name, String image) {
		this.id = id;
		this.name = name;
		this.image = image;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
	    this.image = image;
	}
}

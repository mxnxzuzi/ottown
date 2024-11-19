package model.domain;

import java.util.ArrayList;
import java.util.List;

public enum OTTService {
	NETFLIX(1, "Netflix", "netflix_image_url"),
    TVING(2, "Tving", "tving_image_url"),
    COUPANG_PLAY(3, "Coupang Play", "coupang_play_image_url"),
    DISNEY_PLUS(4, "Disney+", "disney_plus_image_url"),
    LAFTEL(5, "Laftel", "url");

	private int id;
	private String name;
	private String image;
	private List<Content> contentList;
	
	private OTTService(int id, String name, String image) {
		this.id = id;
		this.name = name;
		this.image = image;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public List<Content> getContentList() {
		return contentList;
	}
	public void setContentList(List<Content> contentList) {
		this.contentList = contentList;
	}
	
}

package android.com.skyh.entity;

public class AdEntity {
	private int image;
	private String text;
	
	public AdEntity(int image) {
		super();
		this.image = image;

	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}

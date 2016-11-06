package Texture;

import android.graphics.drawable.Drawable;

public  class Texture {
	private String image_name;
	private Drawable image;
	public Texture (Drawable _image){
		image=_image;

	}
	public Drawable getImage() {
		return image;
	}

	public void setImage(Drawable image) {
		this.image = image;
	}

	/**
	 * @return the image_name
	 */
	public String getImage_name() {
		return image_name;
	}
	/**
	 * @param image_name the image_name to set
	 */
	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}
	
}

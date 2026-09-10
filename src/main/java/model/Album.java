package model;

import java.io.Serializable;

public class Album implements Serializable {
	private static final long serialVersionUID = 1L;
	private String productId;
    private String title;

    public Album(String productId, String title) {
        this.productId = productId;
        this.title = title;
    }

    public String getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }
}


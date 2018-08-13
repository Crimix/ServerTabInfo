package com.black_dog20.servertabinfo.client.objects;

public interface IRenderable {

	int getWidth();
	
	int[] getWidthArray();
	
	void render(int x, int y, int width);
	
	void render(int x, int y, int[] width);

	int getWidthOfElement(int n);
}

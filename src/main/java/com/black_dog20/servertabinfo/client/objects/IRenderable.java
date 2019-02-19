package com.black_dog20.servertabinfo.client.objects;

public interface IRenderable {
	
	int[] getWidthArray();
	
	void render(int x, int y, int[] width);

	int getWidthOfElement(int n);
}

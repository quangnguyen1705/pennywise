package sjsu.edu.application;

import java.util.ArrayList;

public interface ListInterface<T> {
	void add(T item);
	void reload();
	ArrayList<T> getList();
	
}

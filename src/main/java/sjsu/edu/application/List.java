package sjsu.edu.application;

import java.util.ArrayList;

public class List<T> {
	private ListInterface<T> list;
	
	public List(ListInterface<T> list) {
		this.list = list;
	}
	
	public void add(T item) {
		list.add(item);
	}
	public ArrayList<T> getList(){
		return list.getList();
	}
	public void reload() {
		list.reload();
	}
	public void update(T item) {
		if(list instanceof TransactionListInterface) {
			((TransactionListInterface<T>) list).update(item);
		}
	}
		

}

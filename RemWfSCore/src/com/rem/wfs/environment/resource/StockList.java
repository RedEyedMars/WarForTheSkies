package com.rem.wfs.environment.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.rem.core.storage.Storable;
import com.rem.core.storage.StorageHandler;
import com.rem.core.storage.handler.HandlerListStorageHandler;
import com.rem.core.storage.handler.StorableListStorageHandler;
import com.rem.wfs.Creatable;

public class StockList<Type extends Creatable> extends SpaceResource<StockList<Type>> implements Storable, List<Type>{

	private List<Type> list = new ArrayList<Type>();
	private StockType<Type> stockType;
	public StockList(ResourceContainer container, StockType<Type> stockType) {
		super(container, stockType);
		this.stockType = stockType;
	}

	@Override
	public void onCreate(){
		super.onCreate();
		int size = (int)(float)getValue();
		setValue(getValue()-size);
		for(int i=0;i<size;++i){
			Type element = stockType.createObjectPlaceHolder();
			element.onCreate();
			add(element);
		}
	}
	@Override
	public StorageHandler getStorageHandler() {
		return new HandlerListStorageHandler(
				new ResourceStorageHandler<StockList<Type>>(this),
				new StorableListStorageHandler<Type>(list,(int)(float)getValue()){
					@Override
					public Type createPlaceHolder() {
						return stockType.createObjectPlaceHolder();
					}
				});
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<Type> iterator() {
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean add(Type e) {
		this.setValue(this.getValue()+1);
		return list.add(e);
	}

	@Override
	public boolean remove(Object o) {
		this.setValue(this.getValue()-1);
		return list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Type> c) {
		return list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Type> c) {
		return list.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public Type get(int index) {
		return list.get(index);
	}

	@Override
	public Type set(int index, Type element) {
		return list.set(index, element);
	}

	@Override
	public void add(int index, Type element) {
		this.setValue(this.getValue()+1);
		list.add(index,element);
	}

	@Override
	public Type remove(int index) {
		this.setValue(this.getValue()-1);
		return list.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {		
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<Type> listIterator() {
		return this.listIterator();
	}

	@Override
	public ListIterator<Type> listIterator(int index) {
		return list.listIterator(index);
	}

	@Override
	public List<Type> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

}

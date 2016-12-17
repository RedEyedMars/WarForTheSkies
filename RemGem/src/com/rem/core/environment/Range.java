package com.rem.core.environment;

import java.util.Iterator;

public class Range implements Iterable<Integer>{
	protected int first;
	protected int last;
	protected int size;

	public Range(int first, int last){
		this.first = first;
		this.last = last;
		this.size = last+1-first;
	}
	
	
	public int get(int index){
		return index+first;
	}
	public int getRandomElement(){
		return get((int) (Math.random()*(size)));
	}
	public int getRandomIndex(){
		return (int) (Math.random()*(size));
	}
	
	public static class List extends Range {
		private Range[] ranges;

		public List(Range...ranges){
			super(0,findSize(ranges));
			this.ranges = ranges;
		}
		private static int findSize(Range[] ranges){
			int ret = 0;
			for(int i=0;i<ranges.length;++i){
				ret += ranges[i].size;
			}
			return ret-1;
		}
		@Override
		public int get(int index){
			int i=0;
			for(;i<ranges.length;++i){
				if(ranges[i].size>index){
					break;
				}
				else {
					index-=ranges[i].size;
				}
			}
			return ranges[i].get(index);
		}
	}

	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>(){
			private int i=0;
			@Override
			public boolean hasNext() {
				return i<size;
			}

			@Override
			public Integer next() {
				return get(i++);
			}

			@Override
			public void remove() {
				
			}};
	}
}

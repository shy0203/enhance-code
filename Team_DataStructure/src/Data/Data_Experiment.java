package Data;

import java.util.List;

import Lists.CopyList00;

public class Data_Experiment extends Data_Tool implements Data_Structure {
	// add(), get(), 기타 동작을 실험하기 위해 필요한 조건 동작들을 추가 (갈비지컬렉터 실행, get()동작에 필요한 데이터가 들어있는 list 생성...)
	private Data_Experiment() {}
	
	public static Data_Experiment getInstance(){
		return LazyHolder.INSTANCE;
	}
	
	private static class LazyHolder {
		private static final Data_Experiment INSTANCE = new Data_Experiment();
	}
	
	@Override
	public void Analysis_add(List<Integer> list, String name) {
		
		System.gc();
		
		start = System.nanoTime();	
		for(int i=0; i<size; i++){
			list.add(i);
		}
		end = System.nanoTime();
		
		Print_result(start, end, 1, name);
	}
	
	@Override
	public void Analysis_get(List<Integer> list, String name) {
			
		start = System.nanoTime();
		for(int i=0; i<size; i++){
			 list.get(i);
		}
		end = System.nanoTime();
		
		Print_result(start, end, 2, name);
	}
	
	public void Analysis_SequentialGet(List<Integer> list, String name){
			
		start = System.nanoTime();
		for(int i=0; i<size; i++){
			((CopyList00<Integer>) list).SequentialGet();
		}
		end = System.nanoTime();
		
		Print_result(start, end, 3, name);
	}
}

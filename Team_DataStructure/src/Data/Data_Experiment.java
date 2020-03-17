package Data;

import java.util.List;

import Lists.CopyList00;

public class Data_Experiment extends Data_Tool implements Data_Structure {
	// add(), get(), 기타 동작을 실험하기 위해 필요한 조건 동작들을 추가 (갈비지컬렉터 실행, get()동작에 필요한 데이터가 들어있는 list 생성...)
	private Data_Experiment() {}	// 생성자 private
	
	public static Data_Experiment getInstance(){	// LazyHolder 싱글톤 생성
		return LazyHolder.INSTANCE;
	}
	
	private static class LazyHolder {	// LazyHolder  싱글톤을 위한 내부 클래스
		private static final Data_Experiment INSTANCE = new Data_Experiment();
	}
	
	@Override
	public void Analysis_add(List<Integer> list, String name) {
		// add() 실험
		System.gc();
		methodName = new Object(){}.getClass().getEnclosingMethod().getName();	// 현재 실행된 메서드명 호출
		reName(methodName);
		
		start = System.nanoTime();	
		for(int i=0; i<size; i++){
			list.add(i);
		}
		end = System.nanoTime();
		
		print_result(start, end, name, methodName);
	}
	
	@Override
	public void Analysis_get(List<Integer> list, String name) {
		// get() 실험
		methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		reName(methodName);
		
		start = System.nanoTime();
		for(int i=0; i<size; i++){
			 list.get(i);
		}
		end = System.nanoTime();
		
		print_result(start, end, name, methodName);
	}
	
	@Override
	public void Analysis_sequentialGet(List<Integer> list, String name) {
		// sequentialGet() 실험
		methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		reName(methodName);
				
		start = System.nanoTime();
		for(int i=0; i<size; i++){
			((CopyList00<Integer>) list).sequentialGet();
		}
		end = System.nanoTime();
				
		print_result(start, end, name, methodName);
	}
	
	@Override
	public void Analysis_rotationGet(List<Integer> list, String name) {
		// rotationGet() 실험
		methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		reName(methodName);
		
		start = System.nanoTime();
		for(int i=0; i<size; i++){
			((CopyList00<Integer>) list).rotationGet(i);
		}
		end = System.nanoTime();
		
		print_result(start, end, name, methodName);
	}
}

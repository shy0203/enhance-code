package Data;

import java.util.List;

import Lists.LinkedList_sg;

public abstract class Data_Measure extends Data_Tool {
	// add(), get(), 기타 동작에 대한 세부적인 부분 (시간측정 및 올바른 결과 출력, 동작 수정 및 추가 가능)
	
	public void Measure_add(List list, String name) {
		
		start = System.nanoTime();
		
		Produce(list);
		
		end = System.nanoTime();
		
		Print_result(start, end, 1, name);
	}
	
	public void Measure_get(List list, String name) {
		
		start = System.nanoTime();
		
		for(int i=0; i<size; i++){
			list.get(i);
		}
		
		end = System.nanoTime();
		
		Print_result(start, end, 2, name);
	}
	
	public void Measure_SequentialGet(List list, String name) {
		
		start = System.nanoTime();
		
		for(int i=0; i<size; i++){
			((LinkedList_sg) list).SequentialGet();
		}
		
		end = System.nanoTime();
		
		Print_result(start, end, 3, name);
	} 
}

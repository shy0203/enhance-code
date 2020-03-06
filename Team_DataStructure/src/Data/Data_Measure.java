package Data;

import java.util.List;

public abstract class Data_Measure extends Data_Tool {
	
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
	
}

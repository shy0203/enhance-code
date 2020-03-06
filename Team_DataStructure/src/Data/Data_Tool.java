package Data;

import java.util.List;

public abstract class Data_Tool {

	public static final int size = 100000;
	
	protected long start;
	protected long end;
	
	public void Produce(List list) {
		for(int i=0; i<size; i++){
			list.add(i);
		}
	}
	
	public static void Print_result(long start, long end, int num, String name) {
		
		if(num == 1){
			System.out.println(name + " add() 걸린 시간 : " + (end - start) + " ns");
		}
		else if(num == 2){
			System.out.println(name + " get() 걸린 시간 : " + (end - start) + " ns");
		}
		else if(num == 3){
			System.out.println(name + " SequentialGet() 걸린 시간 : " + (end - start) + " ns");
		}
		else{
			System.out.println(name + " 새로운 실험 걸린 시간 : " + (end - start) + " ns");
		}
	}
}

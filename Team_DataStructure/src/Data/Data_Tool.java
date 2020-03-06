package Data;

import java.util.List;

public abstract class Data_Tool {
	// 실험하는데 필요한 필수적이고 공통적인 변수나 반복되는 메소드를 도구로 묶음
	
	public static final int size = 100000;	// list 크기
	
	protected long start;	// ns단위의 처음 측정 시간
	protected long end;		// ns단위의 마지막 측정 시간
	
	public void Produce(List list) {
		// get()에 필요한 데이터가 들어있는 list 생성
		
		for(int i=0; i<size; i++){
			list.add(i);
		}
	}
	
	public static void Print_result(long start, long end, int num, String name) {
		// 실행된 실험에 맞게 알맞은 출력을 위함 (새로운 동작 추가시 출력문 추가 및 수정 가능)
		
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

package Data;

import java.util.List;

public class Data_Experiment extends Data_Measure implements Data_Structure {
	// add(), get(), 기타 동작을 실험하기 위해 필요한 조건 동작들을 추가 (ex. 갈비지컬렉터 실행, get()동작에 필요한 데이터가 들어있는 list 생성...)

	@Override
	public void Analysis_add(List list, String name) {
		System.gc();
		Measure_add(list, name);
	}

	@Override
	public void Analysis_get(List list, String name) {
		System.gc();
		Produce(list);
		Measure_get(list, name);
	}
	
	public void Analysis_SequentialGet(List list, String name){
		System.gc();
		Produce(list);
		Measure_SequentialGet(list, name);
	}
}

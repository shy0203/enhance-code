package Data;

import java.util.List;

public class Data_Experiment extends Data_Measure implements Data_Structure {
	// 멤버 변수

	// 생성자
	private Data_Experiment() {
	}

	// 메소드
	// lazyhoder 싱클톤 생성
	public static Data_Experiment getInstanceRemoteControl() {
		return LazyHolder.INSTANCE;
	}

	// 내부 클래스: lazyhoder 싱클톤을 위한 내부 클래스
	private static class LazyHolder {
		private static final Data_Experiment INSTANCE = new Data_Experiment();
	}

	// add(), get(), 기타 동작을 실험하기 위해 필요한 조건 동작들을 추가 (갈비지컬렉터 실행, get()동작에 필요한 데이터가
	// 들어있는 list 생성...)

	@Override
	public void Analysis_add(List<Integer> list, String name) {
		System.gc();
		Measure_add(list, name);
	}

	@Override
	public void Analysis_get(List<Integer> list, String name) {
		System.gc();
		Produce(list);
		Measure_get(list, name);
	}

	public void Analysis_SequentialGet(List<Integer> list, String name) {
		System.gc();
		Produce(list);
		Measure_SequentialGet(list, name);
	}
}

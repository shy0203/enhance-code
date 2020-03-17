package Data;

import java.util.List;

public interface Data_Structure {
	// 실험에 필요한 공통적인 동작들을 인터페이스로 묶음 (새로운 동작 추가 가능)
	public abstract void Analysis_add(List<Integer> list, String name);	// add() 동작 실험
	public abstract void Analysis_get(List<Integer> list, String name);	// get() 동작 실험
	public abstract void Analysis_sequentialGet(List<Integer> list, String name);	// sequentialGet() 동작 실험
	public abstract void Analysis_rotationGet(List<Integer> list, String name);	// rotationGet() 동작 실험
}

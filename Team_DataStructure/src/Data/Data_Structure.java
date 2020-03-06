package Data;

import java.util.List;

public interface Data_Structure {
	// 실험에 필요한 공통적인 동작들을 인터페이스로 묶음 (새로운 동작 추가 가능)
	
	public abstract void Analysis_add(List list, String name);
	public abstract void Analysis_get(List list, String name);
}

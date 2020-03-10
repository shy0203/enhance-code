package Control;

public interface Control_Tool {
	// Remote_Control의 동작들을 인터페이스로 묶음
	
	public abstract Class[] getClass(String packageName);	// 패키지 내, 클래스들을 탐색하고 알맞은 클래스들을 list화 하는 동작
	public abstract int getCaseNum();	// 실험번호를 입력받고, 해당 실험번호를 return하는 동작
	public abstract void Experiment_select();	// 실험번호를 받고 그에 해당하는 클래스를 실험하는 동작
}

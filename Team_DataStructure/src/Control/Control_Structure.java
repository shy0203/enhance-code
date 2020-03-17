package Control;

public interface Control_Structure {
	// Remote_Control의 동작들을 인터페이스로 묶음
	@SuppressWarnings("rawtypes")
	public abstract Class[] getClass(String packageName);	// 패키지 내, 클래스들을 탐색하고 알맞은 클래스들을 list화
	public abstract void insertNum();	// 실험번호를 입력받는 동작
	public abstract int getCaseNum();	// 해당 실험번호를 return하는 동작
	public abstract String getClassName();	// 해당 클래스명을 return하는 동작
	public abstract void experiment_select();	// 실험번호를 받아 그에 해당하는 클래스를 실험하는 동작
	public abstract void getManual();	// 실험번호 입력 전, 실험번호의 설명을 출력하는 동작
}

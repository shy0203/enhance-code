package Control;

public abstract class Control_Structure {
	// Remote_Control의 동작들을 인터페이스로 묶음
	protected abstract void insertNum();	// 실험번호를 입력받는 동작
	protected abstract void addSettings();	// 각 클래스별 추가적으로 필요한 동작이 있을 때 추가적인 세팅 동작
	public abstract int getCaseNum();	// 해당 실험번호를 return하는 동작
	public abstract String getClassName();	// 해당 클래스명을 return하는 동작
	public abstract void experiment_select();	// 실험번호를 받아 그에 해당하는 클래스를 실험하는 동작
	public abstract int dataSize();	// 실험 데이터의 범위를 설정할 수 있는 동작
	protected abstract void getManual(int listCount, int startList);	// 메뉴얼 출력 동작
	public abstract void paging();	// 메뉴얼 출력을 페이징 처리하는 동작
}

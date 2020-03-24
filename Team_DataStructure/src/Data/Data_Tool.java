package Data;

class Data_Tool {
	// 실험하는데 필요한 필수적이고 공통적인 변수나 반복되는 메소드를 도구로 묶음
	public int size = 100000;	// 데이터 크기
	
	protected long start;	// ns단위의 처음 측정 시간
	protected long end;		// ns단위의 마지막 측정 시간
	
	protected String methodName;	// 최종 출력될 메소드명
	
	protected String reName(String method_name){	// 현재 실행된 메소드명을 받아 print_result()에서 출력하기 위해 동작 명으로 변경
		String[] methodN = method_name.split("_");
		methodName = methodN[1];
		return methodName;
	}
	
	protected static void print_result(long start, long end, String analysis_name, String method_name) {	// 실행된 실험의 올바른 결과값 출력
		System.out.println(analysis_name + " " + method_name + "() 걸린 시간 : " + (end - start) + " ns");
	}
}

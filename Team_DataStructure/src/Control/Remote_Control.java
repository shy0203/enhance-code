package Control;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Data.Data_Experiment;
import Data.Data_Lists;

public class Remote_Control extends Control_Structure {
	// 입력한 실험번호에 맞는 실험 class파일들을 호출하고 실행하는 클래스
	int length = 0;	// 입력 가능한 숫자 범위
	int research_num = -1;	// 입력받은 실험번호를 담아 리턴할 변수
	String className;	// 입력받은 실험번호에 맞는 클래스명
	
	@SuppressWarnings("rawtypes")
	Class[] classes;	// getClass()로 인하여 생성된 Lists 패키지 내 클래스들
	Data_Lists copyList = null;	// 각 클래스들을 Data_Lists 인터페이스로 캐스팅 하기 위한 변수
	Scanner scanNum;
	Data_Experiment e;
	Paging_Control pageClass;
	
	public Remote_Control() {
		classes = getClass("Lists");	// 번호를 지정하기 위해 getClass()를 실행하여 클래스들을 미리 담음
		// 필요한 객체 생성
		e = new Data_Experiment();
		pageClass = new Paging_Control(classes);
		// 초기 세팅
		paging();
		e.size = dataSize();	// 입력된 값을 실험 데이터의 범위로 설정
		insertNum();
		addSettings();
		getClassName();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class[] getClass(String packageName) {
		// 실험하고자 하는 패키지 내 클래스들을 모두 호출하여 copylists에 담아 리턴
		List<Class<?>> copylists = new ArrayList<Class<?>>();	// 패키지 내 클래스들을 담을 변수
		
		String path = "./" + packageName.replace('.', '/');	// URL을 호출하기 위해 클래스의 구분자 '.'을 '/'로 바꾸는 과정
		URL resources = Thread.currentThread().getContextClassLoader().getResource(path);	// 패키지의 URL 가져오기
		
		String resource = resources.getFile();	// 위에서 구한 URL을 이용하여 디렉토리의 전체 경로를 가져온다.
		File directory = new File(resource); // 전체 경로를 담는 변수 초기화
		
		if(directory.exists()){	// 파일이 존재하는지 여부 조사
			
			Class<?> classlist; // 로드한 클래스들 리스트화
			boolean isName;
			String[] files = directory.list();	// directory에 담겨 있는 목록을 String 배열로 반환
			
			for(String file : files){
				if(file.endsWith(".class")){	// .class로 문자열을 끝나는 경우
					file = file.substring(0, file.length() - 6);	// 확장자 삭제
					
					try {
						classlist = Class.forName(packageName + "." + file);	// 동적로딩 : 해당 클래스를 메모리로 로드
						
						isName = classlist.getName().contains("$");	// 동적로딩한 클래스의 이름에서 '$'문자 포함 확인
						
						if(!isName) {
							copylists.add(classlist);	// return할 변수 copylists에 클래스리스트들을 담음
						}
					} 
					catch (ClassNotFoundException e) {	// 클래스가 없을 때 예외처리
						e.printStackTrace();
					}
				}
			}
			length = copylists.size();	// 입력 가능한 숫자범위 지정을 위해 copylists의 크기를 담음
		}
		return copylists.toArray(new Class[length]);	// copylists 리턴
	}
	
	@Override
	protected void insertNum() {
		// 실험 번호를 입력받고 잘못된 번호 입력시 예외처리
		scanNum = new Scanner(System.in);
		System.out.print("0 ~ " + (length - 1) + " 까지 ");	// 입력 가능 숫자범위 조건 출력
		System.out.print("실험번호 입력 : ");
		
		do{
			while(!scanNum.hasNextInt()){	// int형이 아닌 값을 입력했을 경우 예외처리
				System.out.print("잘못된 입력입니다. 다시 입력하세요 : ");
				scanNum.next();
			}
			research_num = scanNum.nextInt();
			
			if(research_num < 0 || research_num > length - 1){	// 입력 가능 숫자범위 조건 불만족 예외처리
				System.out.println("없는 실험번호입니다.");
				this.insertNum();
			}		
		}while(research_num == -1);	
	}
	
	@Override
	protected void addSettings(){
		// 각 클래스별 추가적으로 필요한 동작이 있을 때 추가적인 세팅 동작
		try {
			copyList = (Data_Lists) classes[research_num].newInstance();	// Data_Lists 인터페이스 캐스팅 후, 인스턴스 생성 
			copyList.addSetting();	// 해당 메서드의 addSetting() 실행
		} 
		catch (InstantiationException | IllegalAccessException e) {	// newInstance 메소드로 객체 생성하려는 대상이 추상클래스일 때 예외처리
			e.printStackTrace();									// 접근제한자에 의해 접근할 수 없을 때 예외처리
		}
	}
	
	@Override
	public int getCaseNum() {
		// 실험번호 리턴
		return research_num;
	}

	@Override
	public String getClassName() {	
		// 입력받은 번호에 대한 클래스명 리턴
		className = classes[research_num].getName().replace("Lists.", "");	// 클래스명을 보기 좋게 변경
		return className;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void experiment_select() {
		// 실험번호를 받아 실험번호를 받아 그에 해당하는 클래스를 실험
		List<Integer> copyList = null;
		
		try {
			// Data_Experiment를 실행하기 위해 캐스팅 후, 인스턴스 생성
			copyList = (List<Integer>) classes[research_num].newInstance();
			// 실험부
			e.Analysis_add(copyList, className);
			e.Analysis_get(copyList, className);
			// LinkedList(=CopyList00)만 실행가능한 동작 예외처리
			if(research_num == 0){
				e.Analysis_sequentialGet(copyList, className);
				e.Analysis_rotationGet(copyList, className);
			}
		} 
		catch (InstantiationException | IllegalAccessException e) {	// newInstance 메소드로 객체 생성하려는 대상이 추상클래스일 때 예외처리
			e.printStackTrace();									// 접근제한자에 의해 접근할 수 없을 때 예외처리
		}
	}

	@Override
	public int dataSize(){
		// 실험 데이터의 범위를 설정할 수 있는 동작
		int dataNum = 0;	
		scanNum = new Scanner(System.in); 
		System.out.print("실험 데이터의 범위를 입력하세요(기본값 100000) : ");
		
		do{
			while(!scanNum.hasNextInt()){	// int형이 아닌 값을 입력했을 경우 예외처리
				System.out.print("잘못된 입력입니다. 다시 입력하세요 : ");
				scanNum.next();
			}
			dataNum = scanNum.nextInt();
			
			if(dataNum == 0){	// 0 입력 예외처리
				System.out.print("0보다 큰 수를 입력하세요 : ");
			}
			
		}while(dataNum == 0);
			
		return dataNum;	
	}
	
	@Override
	protected void getManual(int listCount, int startList) {
		// 실험번호 입력 전, 실험번호의 설명을 출력하는 동작
		
		System.out.println("-----------------  실험 목록    "
				+ "<" + pageClass.getCurrentPage() + " / "+ pageClass.getTotalPage() + "> --------------------------------");
		
		for(int i=0; i<listCount; i++){	
			try {
				copyList = (Data_Lists) classes[startList+i].newInstance();	// Data_Lists 캐스팅 후, 인스턴스 생성
				
				System.out.print(" [" + (startList+i) + "] " + classes[(startList+i)].getName().replace("Lists.", ""));
				((Data_Lists) copyList).printManual();	// 추가적인 설명에 대한 출력
				
			} 
			catch (InstantiationException | IllegalAccessException e1) {	// newInstance 메소드로 객체 생성하려는 대상이 추상클래스일 때 예외처리
				e1.printStackTrace();										// 접근제한자에 의해 접근할 수 없을 때 예외처리
			}
		}
		pageClass.pageCheck();	// 페이징 시, 어느 페이지인지 체크
	}
	
	@Override
	public void paging() {
		// 메뉴얼 출력을 페이징 처리하는 동작
		int listCount; // 한 페이지에 몇개를 읽을지
		int startList; // 각 페이지 시작 번호
		boolean commandCheck = false;
		
		while (!commandCheck) {
			pageClass.pagingControl();	// 전체 페이징 설정
			// 페이지변수 재설정
			listCount = pageClass.getListCount();
			startList = pageClass.getStartList();
			
			this.getManual(listCount, startList);	// getManual() 실행
			commandCheck = pageClass.inputChar(commandCheck);	// 입력값 검사
			
			if(commandCheck == false){	// 종료하지 않을 때만 clear
				pageClass.clear();
			}
		}
	}
}

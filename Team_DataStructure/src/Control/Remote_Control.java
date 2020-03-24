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
	private Scanner scanNum;	// 실험번호 입력하기 위한 스캐너 변수
	private int researchNum = -1;	// 입력받은 실험번호를 담아 리턴할 변수
	private int length = 0;	// 입력 가능한 숫자 범위
	private String className;	// 입력받은 실험번호에 맞는 클래스명
	
	@SuppressWarnings("rawtypes")
	private Class[] classes;	// getClass()로 인하여 생성된 Lists 패키지 내 클래스들
	private Data_Lists copyList = null;	// 각 클래스들을 Data_Lists 인터페이스로 캐스팅 하기 위한 변수
	
	// 필요한 객체 생성을 위한 변수
	private Paging_Control pagingC;
	private Data_Experiment e;
	
	public Remote_Control() {
		// 순서대로 정리된 클래스리스트와 리스트길이를 가져옴
		classes = ClassList_Control.getClass("Lists");
		length =  ClassList_Control.getLength();
		// 페이징과 실험에 필요한 객체 생성
		pagingC = new Paging_Control(classes);
		e = new Data_Experiment();
		// 초기세팅
		paging();
		e.setSize(dataSize());	// 입력된 값을 실험 데이터의 범위로 설정
		insertNum();
		addSettings();
		getClassName();
	}
	
	@Override
	protected void insertNum() {
		// 실험 번호를 입력받고 잘못된 번호 입력시 예외처리
		scanNum = new Scanner(System.in);
		boolean isNum = researchNum < 0 || researchNum > (length-1);
		do{
			System.out.print("0 ~ " + (length - 1) + " 까지 ");	// 입력 가능 숫자범위 조건 출력
			System.out.print("실험번호 입력 : ");
			
			while(!scanNum.hasNextInt()){	// int형이 아닌 값을 입력했을 경우 예외처리
				System.out.print("잘못된 입력입니다. 다시 입력하세요 : ");
				scanNum.next();
			}
			researchNum = scanNum.nextInt();
			
			if(isNum){	// 입력 가능 숫자범위 조건 불만족 예외처리
				System.out.println("없는 실험번호입니다.");
			}		
		}while(isNum);
	}
	
	@Override
	protected void addSettings(){
		// 각 클래스별 추가적으로 필요한 동작이 있을 때 추가적인 세팅 동작
		try {
			copyList = (Data_Lists) classes[researchNum].newInstance();	// Data_Lists 인터페이스 캐스팅 후, 인스턴스 생성 
			copyList.addSetting();	// 해당 메서드의 addSetting() 실행
		} 
		catch (InstantiationException | IllegalAccessException e) {	// newInstance 메소드로 객체 생성하려는 대상이 추상클래스일 때 예외처리
			e.printStackTrace();									// 접근제한자에 의해 접근할 수 없을 때 예외처리
		}
	}
	
	@Override
	public int getCaseNum() {
		// 실험번호 리턴
		return researchNum;
	}

	@Override
	public String getClassName() {	
		// 입력받은 번호에 대한 클래스명 리턴
		className = classes[researchNum].getName().replace("Lists.", "");	// 클래스명을 보기 좋게 변경
		return className;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void experiment_select() {
		// 실험번호를 받아 실험번호를 받아 그에 해당하는 클래스를 실험
		List<Integer> dataList = null;
		
		try {
			// Data_Experiment를 실행하기 위해 캐스팅 후, 인스턴스 생성
			dataList = (List<Integer>) classes[researchNum].newInstance();
			// 실험부
			e.Analysis_add(dataList, className);
			e.Analysis_get(dataList, className);
			// LinkedList(=CopyList00)만 실행가능한 동작 예외처리
			if(researchNum == 0){
				e.Analysis_sequentialGet(dataList, className);
				e.Analysis_rotationGet(dataList, className);
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
			
			if(dataNum <= 0){	// 0 입력 예외처리
				System.out.print("0보다 큰 수를 입력하세요 : ");
			}
			
		}while(dataNum <= 0);
			
		return dataNum;
	}
	
	@Override
	protected void getManual(int listCount, int startList) {
		// 실험번호 입력 전, 실험번호의 설명을 출력하는 동작
		System.out.println("-----------------  실험 목록    "
				+ "<" + pagingC.getCurrentPage() + " / "+ pagingC.getTotalPage() + "> --------------------------------");
		
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
		pagingC.pageCheck();	// 페이징 시, 어느 페이지인지 체크
	}
	
	@Override
	public void paging() {
		// 메뉴얼 출력을 페이징 처리하는 동작
		int listCount; // 한 페이지에 몇개를 읽을지
		int startList; // 각 페이지 시작 번호
		boolean commandCheck = false;
		
		while (!commandCheck) {
			pagingC.pagingControl();	// 전체 페이징 설정
			// 페이지변수 재설정
			listCount = pagingC.getListCount();
			startList = pagingC.getStartList();
			
			this.getManual(listCount, startList);	// getManual() 실행
			commandCheck = pagingC.inputChar(commandCheck);	// 입력값 검사
			
			if(commandCheck == false){	// 종료하지 않을 때만 clear
				pagingC.clear();
			}
		}
	}
}
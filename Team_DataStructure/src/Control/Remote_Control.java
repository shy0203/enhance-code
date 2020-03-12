package Control;
import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import Data.Data_Experiment;
import Lists.*;

public class Remote_Control implements Control_Tool {
	// 입력한 실험번호에 맞는 실험 class파일들을 호출하고 실행하는 클래스
	int length = 0;	// 입력 가능한 숫자 범위
	Scanner scanNum;	// 입력받은 실험번호
	
	int research_num;	// 입력받은 실험번호를 담아 리턴할 변수
	String className;	// 입력받은 실험번호에 맞는 클래스명
	
	@SuppressWarnings("rawtypes")
	Class[] classes;	// getClass()로 인하여 생성된 Lists 패키지 내 클래스들
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class[] getClass(String packageName) {
		// 실험하고자 하는 패키지 내 클래스들을 모두 호출하여 copylists에 담아 리턴
		List<Class<?>> copylists = new ArrayList<Class<?>>();	// 패키지 내 클래스들을 담을 변수
		
		String path = "./" + packageName.replace('.', '/');	// URL을 호출하기 위해 클래스의 구분자 '.'을 '/'로 바꾸는 과정
		URL resources = Thread.currentThread().getContextClassLoader().getResource(path);	// 패키지의 URL 가져오기
		
		String resource = resources.getFile();	// 위에서 구한 URL을 이용하여 디렉토리의 전체 경로를 가져온다.
		File directory = new File(resource); // 전체 경로를 담는 변수 초기화
		
		if(directory.exists()){	// 파일이 존재하는지 여부 조사
			
			Class<?> classlist;
			boolean isName;
			String[] files = directory.list();	// directory에 담겨 있는 목록을 String 배열로 반환
			
			for(String file : files){
				if(file.endsWith(".class")){	// .class로 문자열을 끝나는 경우
					file = file.substring(0, file.length() - 6);	// 확장자 삭제
					
					try {
						classlist = Class.forName(packageName + "." + file);	// 동적로딩 : 해당 클래스를 메모리로 로드
						
						isName = classlist.getName().contains("$");	// 동적로딩한 클래스의 이름에서 '$'문자 제거
						
						if(!isName) {
							copylists.add(classlist);	// return할 변수 copylists에 클래스리스트들을 담음
						}
					} 
					catch (ClassNotFoundException e) {	// 클래스가 없을 때 예외처리
						e.printStackTrace();
					}
				}
				length = copylists.size();	// 입력 가능한 숫자범위 지정을 위해 copylists의 크기를 담음
			}
		}
		return copylists.toArray(new Class[length]);	// copylists 리턴
	}
	
	@Override
	public void insertNum() {
		// 실험 번호를 입력받고 잘못된 번호 입력시 예외처리
		classes = this.getClass("Lists");	// 번호를 지정하기 위해 getClass()를 실행하여 클래스들을 미리 담음
		
		scanNum = new Scanner(System.in);
		System.out.print("0 ~ " + (length - 1) + " 까지 ");	// 입력 가능 숫자범위 조건 출력
		System.out.print("실험번호 입력 : ");
		research_num = scanNum.nextInt();
		
		if(research_num < 0 || research_num > length - 1){	// 입력 가능 숫자범위 조건 불만족 예외처리
			System.out.println("없는 실험번호입니다.");
			this.insertNum();
		}
	}

	@Override
	public int getCaseNum() {
		// 입력받은 번호에 대한 실험번호 리턴
		insertNum();
		return research_num;
	}

	@Override
	public String getClassName() {	
		// 입력받은 번호에 대한 클래스명 리턴
		insertNum();
		className = classes[research_num].getName().replace("Lists.", "");
		
		return className;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void experiment_select() {
		// 실험번호를 받아 실험번호를 받아 그에 해당하는 클래스를 실험
		List<Integer> copyList;
		Data_Experiment e = Data_Experiment.getInstance();
		
		this.getClassName();	// 이 동작으로 인해 getClass(), insertNum() 실험하기 위한 초기 조건 세팅
		
		try {
			copyList = (List<Integer>) classes[research_num].newInstance();	// 해당 번호에 맞는 클래스의 인스턴스 생성
			
			e.Analysis_add(copyList, className);
			e.Analysis_get(copyList, className);
			
			if(research_num == 0){	// LinkedList(=CopyList00)만 실행가능한 동작 예외처리
				e.Analysis_SequentialGet(copyList, className);
			}
		} 
		catch (InstantiationException | IllegalAccessException e1) {	// newInstance 메소드로 객체 생성하려는 대상이 추상클래스일 때 예외처리
			e1.printStackTrace();										// 접근제한자에 의해 접근할 수 없을 때 예외처리
		}
	}

	@Override
	public void getManual() {
		// 실험번호 입력 전, 실험번호의 설명을 출력하는 동작
		classes = this.getClass("Lists");
		System.out.println("-----------  실험목록    --------------------");
		
		for(int i=0; i<length; i++){
			
			className = classes[i].getName().replace("Lists.", "");
			
			switch (i) {
			case 0:
				System.out.println(i + "." + className + " : LinkedList.class");
				break;
			case 1:
				System.out.println(i + "." + className + " : Vector 참고 및 배열 추가");
				break;
			case 2:
				System.out.println(i + "." + className + " : ArrayList 참고 및 배열 추가");
				break;
			case 3:
				System.out.println(i + "." + className + " : Entry의 element를 ArrayList로 변경, ArrayList 공간 확장");
				break;
			case 4:
				System.out.println(i + "." + className + " : Entry의 element를 ArrayList로 변경, ArrayList 객체 생성");
				break;
			case 5:
				System.out.println(i + "." + className + " : 특정 기준을 잡고 탐색하는 구조");
				break;
			case 6:
				System.out.println(i + "." + className + " : 특정 기준을 자동으로 잡고 탐색하는 구조");
				break;
			case 7:
				System.out.println(i + "." + className + " : DoublyCircularLinkedList");
				break;
			case 8:
				System.out.println(i + "." + className + " : DoublyLinkedList");
				break;
			case 9:
				System.out.println(i + "." + className + " : SinglyCircularLinkedList");
				break;
			case 10:
				System.out.println(i + "." + className + " : SinglyLinkedList");
				break;
			default:
				break;
			}
		}
		System.out.println("----------------------------------------");
	}
}

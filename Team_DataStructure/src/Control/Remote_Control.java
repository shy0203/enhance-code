package Control;

import java.util.List;
import java.util.Scanner;

import Data.Data_Experiment;

public class Remote_Control {
	// 멤버 변수
	private boolean commandCheck = false;
	private String command;
	private Scanner keyboard;
	private String className;
	private String[] classNames = Class_List.getClassList();
	// 인스턴스 변수
	private Paging pageClass;
	private Class_List classList;
	private Data_Experiment dataEx;

	// 생성자
	public Remote_Control() {
		pageClass = new Paging();
		classList = new Class_List();
		dataEx = Data_Experiment.getInstanceRemoteControl();
	}

	// 메소드
	public void execute() {
		String[] classNames = Class_List.getClassList(); // 클래스이름을 문자열 배열로 저장
		int listCount; // 한 페이지에 몇개를 읽을지
		int startList; // 각 페이지 시작 번호

		// 키보드 명령과 프린트
		while (!commandCheck) {
			// 전체 페이징 설정
			pageClass.paging();

			// 페이지변수 재설정
			listCount = pageClass.getListCount();
			startList = pageClass.getStartList();
			// 프린트
			toPrint(classNames, listCount, startList);
			// 명령어
			inputChar();
			// 공백
			blank();
		}
		// 실험 체크할 명령어 체크
		commandCheck(command);
		// 실험 시작
		Experiment_select();
	}

	public void toPrint(String[] classNames, int listCount, int startList) {
		System.out.println("현재페이지: "+pageClass.getCurrentPage()+", 총페이지: "+pageClass.getTotalPage());
		// Lists 패키지 클래스 목록 불러오기
		for (int i = 0; i < listCount; i++) {
			System.out.printf("[" + (startList + i) + "] " + classNames[(startList + i)] + " : " + classList.getClassScript()[(startList + i)] + "\n");
		}
		System.out.println("---------------------------------------------");
		// 이전, 다음 명령어 프린트
		pageCheck();
		System.out.print("명령을 입력해주세요(기본값:Enter - LinkedList.class): ");
	}

	public void pageCheck() {
		if (1 == pageClass.getCurrentPage()) {
			System.out.println("[n]: next");
		} else if (pageClass.getTotalPage() == pageClass.getCurrentPage()) {
			System.out.println("[p]: preview");
		} else {
			System.out.println("[p]: preview, [n]: next");
		}
	}

	public void inputChar() {
		String inputChar;
		keyboard = new Scanner(System.in);
		inputChar = keyboard.nextLine();

		// 스캐너를 통해 입력 받은 명령어 구별
		if (inputChar.matches("")) {
			command = "enter";
			commandCheck = true;
		} else if (inputChar.matches("n")) {
			pageClass.incrementCurrentPage();
		} else if (inputChar.matches("p")) {
			pageClass.decrementCurrentPage();
		} else {
			command = inputChar;
			commandCheck = true;
		}
		System.out.println("----------------------------------------");
	}

	public void blank() {
		for (int i = 0; i < 30; ++i)
			System.out.println();
	}

	public void commandCheck(String command) {
		if (command.equals("enter")) {
			className = "java.util.LinkedList";	// enter 입력 시 기본값으로 출력
		} else {
			className = "Lists." + classNames[Integer.parseInt(command)];
		}
	}

	@SuppressWarnings("unchecked")
	public void Experiment_select() {
		try {
			Class<?> testClass = Class.forName(className);
			dataEx.Analysis_add((List<Integer>) testClass.newInstance(), className);
			dataEx.Analysis_get((List<Integer>) testClass.newInstance(), className);
		} catch (Exception s) {
			s.printStackTrace();
		} finally {
			keyboard.close();
		}

	}
}

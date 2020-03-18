package Control;

import java.io.File;
import java.util.Arrays;

public class Class_List {
	// 멤버 변수
	private String[] classScript = new String[getClassList().length];
	// 생성자
	public Class_List(){
		script();
	}
	// Lists 패키지의 모든 클래스 이름 읽음
	public static String[] getClassList() {
		File file = new File("./src/Lists/"); // 상대 경로를 통해 Lists를 가져옴
		String[] fileList = file.list(); // 경로아래 클래스를 문자열 배열로 저장
		Arrays.sort(fileList); // 순정렬
		for (int i = 0; i < fileList.length; i++) { // 클래스 파일 이름만 사용하게 끔해서
			fileList[i] = fileList[i].substring(0, fileList[i].length() - 5); // filelist재배열
		}
		return fileList;
	}

	public String[] script() {
		classScript[0] = "Sequential";
		classScript[1] = "Vector 참고 및 배열 추가";
		classScript[2] = "ArrayList 참고 및 배열 추가";
		classScript[3] = "Entry의 element를 ArrayList로 변경, ArrayList 공간 확장";
		classScript[4] = "Entry의 element를 ArrayList로 변경, ArrayList 객체 생성";
		classScript[5] = "특정 기준을 잡고 탐색하는 구조";
		classScript[6] = "특정 기준을 자동으로 잡고 탐색하는 구조";
		classScript[7] = "DoublyCircularLinkedLis";
		classScript[8] = "DoublyLinkedList";
		classScript[9] = "SinglyCircularLinkedList";
		classScript[10] = "SinglyLinkedList";
		return classScript;
	}

	public String[] getClassScript() {
		return classScript;
	}
	
	
	
	
}

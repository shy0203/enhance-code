package Control;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Search_Class {
	// 멤버 변수
	private int index = 0;
	private String[] classScript;

	// 생성자
	private Search_Class() {
	}

	// 메소드
	// lazyhoder 싱클톤 생성
	public static Search_Class getInstanceSeachClass() {
		return LazyHolder.INSTANCE;
	}

	// 내부 클래스: lazyhoder 싱클톤을 위한 내부 클래스
	private static class LazyHolder {
		private static final Search_Class INSTANCE = new Search_Class();
	}

	// Lists 패키지의 모든 클래스 이름 읽음
	public String[] getClassList() {
		File file = new File("./src/Lists/");
		String[] fileList = file.list();
		Arrays.sort(fileList);
		for (int i = 0; i < fileList.length; i++) {
			fileList[i] = fileList[i].substring(0, fileList[i].length() - 5);
		}
		return fileList;
	}

	public void toPrint() {
		script();
		for (String name : getClassList()) {
			System.out.printf("[" + index + "]" + "\t : " + name + " - " + classScript[index] +"\n");
			index++;
		}
		System.out.printf("기본값(Enter)" + ": LinkedList" + "\n");
	}

	public void script() {
		classScript = new String[getClassList().length];
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
	}
}

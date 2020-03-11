import java.util.Scanner;

import Control.Search_Class;
import Control.Remote_Control;

public class Main_Test {


	public static void main(String[] args) {

		Search_Class printout = Search_Class.getInstanceSeachClass();
		printout.toPrint();
		
		Remote_Control rc = Remote_Control.getInstanceRemoteControl();
		rc.Experiment_select();
		


	}

	public static void List_Manual(int a) {
		// 실험 번호별 설명문

		switch (a) {
		case 0:
			System.out.println("LinkedList<Integer> (LinkedList.class)");
			break;

		case 1:
			System.out.println("CopyList<Integer> (Vector 참고 및 배열 추가)");
			break;

		case 2:
			System.out.println("CopyList2<Integer> (ArrayList 참고 및 배열 추가)");
			break;

		case 3:
			System.out.println("CopyList3<Integer> (Entry의 element를 ArrayList로 변경, ArrayList 공간 확장)");
			break;

		case 4:
			System.out.println("CopyList4<Integer> (Entry의 element를 ArrayList로 변경, ArrayList 객체 생성)");
			break;

		case 5:
			System.out.println("CopyList5<Integer> (특정 기준을 고정으로 잡고 탐색하는 구조)");
			break;

		case 6:
			System.out.println("CopyList6<Integer> (특정 기준을 자동으로 잡고 탐색하는 구조)");
			break;

		case 7:
			System.out.println("CopyList7<Integer> (DoublyCircularLinkedList)");
			break;

		case 8:
			System.out.println("CopyList8<Integer> (DoublyLinkedList)");
			break;

		case 9:
			System.out.println("CopyList9<Integer> (SinglyCircularLinkedList)");
			break;

		case 10:
			System.out.println("CopyList10<Integer> (SinglyLinkedList)");
			break;

		default:
			break;
		}
	}
}

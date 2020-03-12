package Control;

import java.util.List;
import java.util.Scanner;

import Data.Data_Experiment;

public class Remote_Control {
	// 멤버 변수
	private int index = 0;

	// 생성자
	private Remote_Control() {
	}

	// 메소드
	// lazyhoder 싱클톤 생성
	public static Remote_Control getInstanceRemoteControl() {
		return LazyHolder.INSTANCE;
	}

	// 내부 클래스: lazyhoder 싱클톤을 위한 내부 클래스
	private static class LazyHolder {
		private static final Remote_Control INSTANCE = new Remote_Control();
	}

	@SuppressWarnings("unchecked")
	public void Experiment_select() {
		int number = Integer.parseInt(insertNum());
		Data_Experiment e = Data_Experiment.getInstanceRemoteControl();
		String[] classNames = Search_Class.getInstanceSeachClass().getClassList();
		String className;

		if (number == 99) {
			className = "java.util.LinkedList";
		} else {
			className = "Lists." + classNames[number];
		}

		try {
			Class<?> testClass = Class.forName(className);
			e.Analysis_add((List<Integer>) testClass.newInstance(), className);
			e.Analysis_get((List<Integer>) testClass.newInstance(), className);
		} catch (Exception s) {
			s.printStackTrace();
		}

	}

	public String insertNum() {
		String number;
		Scanner sc = new Scanner(System.in);
		System.out.print("선택?");
		number = sc.nextLine();
		if (number.matches(""))
			number = "99";
		sc.close();
		return number;
	}
}

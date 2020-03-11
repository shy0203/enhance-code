package Control;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import Data.Data_Experiment;
import Lists.CopyList01;
import Lists.CopyList02;
import Lists.CopyList03;
import Lists.CopyList04;
import Lists.CopyList05;
import Lists.CopyList06;
import Lists.CopyList07;
import Lists.CopyList08;
import Lists.CopyList09;
import Lists.CopyList10;
import Lists.CopyList00;

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

	public void Experiment_select() {
		
		int number = insertNum();
		Data_Experiment e = Data_Experiment.getInstanceRemoteControl();

		String[] ab = Search_Class.getInstanceSeachClass().getClassList();
		String Testclass = "Lists." + ab[number];
		try {
			Class<?> testClass = Class.forName(Testclass);
			e.Analysis_add((List<Integer>) testClass.newInstance(), Testclass);
			e.Analysis_get((List<Integer>) testClass.newInstance(), Testclass);
		} catch (Exception s) {
			s.printStackTrace();
		}

	}
	
	public int insertNum(){
		int number;
		Scanner sc = new Scanner(System.in);
		System.out.print("선택?");
		number = sc.nextInt();
		return number;
	}

}

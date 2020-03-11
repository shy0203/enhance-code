import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import Control.Remote_Control;

public class Main_Test {
	
	public static void main(String[] args) {
		// 최종 실행문 결과출력!!
		Remote_Control rc = new Remote_Control();
		rc.getManual();	// 메뉴얼 호출 (필수 X)
		rc.experiment_select();
	}
}
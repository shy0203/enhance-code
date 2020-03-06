import java.util.Scanner;


public class Main_Test {
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		System.out.print("실험번호 입력 : ");		
		int a = scan.nextInt();
		
		Remote_Control rc = new Remote_Control();
		rc.Experiment_select(a);
	}
}

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
}
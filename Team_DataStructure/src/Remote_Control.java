import java.util.LinkedList;

import Data.Data_Experiment;
import Lists.*;

public class Remote_Control {
	
	public static void Experiment_select(int a) {
		// 실험번호별 분류 (해당 List에 대한 add(), get() 등의 동작들을 묶어 번호별로 분류)
		
		Data_Experiment e = new Data_Experiment();
		
		switch (a) {
		case 0:
			e.Analysis_add(new LinkedList<Integer>(), "LinkedList");
			e.Analysis_get(new LinkedList<Integer>(), "LinkedList");
			e.Analysis_SequentialGet(new LinkedList_sg<Integer>(), "LinkedList");
			
			break;
		
		case 1:
			e.Analysis_add(new CopyList<Integer>(), "CopyList");
			e.Analysis_get(new CopyList<Integer>(), "CopyList");
			
			break;

		case 2:
			e.Analysis_add(new CopyList2<Integer>(), "CopyList2");
			e.Analysis_get(new CopyList2<Integer>(), "CopyList2");
			
			break;

		case 3:
			e.Analysis_add(new CopyList3<Integer>(), "CopyList3");
			e.Analysis_get(new CopyList3<Integer>(), "CopyList3");
			
			break;

		case 4:
			e.Analysis_add(new CopyList4<Integer>(), "CopyList4");
			e.Analysis_get(new CopyList4<Integer>(), "CopyList4");
			
			break;

		case 5:
			e.Analysis_add(new CopyList5<Integer>(), "CopyList5");
			e.Analysis_get(new CopyList5<Integer>(), "CopyList5");
			
			break;
			
		case 6:
			e.Analysis_add(new CopyList6<Integer>(), "CopyList6");
			e.Analysis_get(new CopyList6<Integer>(), "CopyList6");
			
			break;
		
		case 7:
			e.Analysis_add(new CopyList7<Integer>(), "CopyList7");
			e.Analysis_get(new CopyList7<Integer>(), "CopyList7");
			
			break;
			
		case 8:
			e.Analysis_add(new CopyList8<Integer>(), "CopyList8");
			e.Analysis_get(new CopyList8<Integer>(), "CopyList8");
			
			break;
		
		case 9:
			e.Analysis_add(new CopyList9<Integer>(), "CopyList9");
			e.Analysis_get(new CopyList9<Integer>(), "CopyList9");
			
			break;

		default:
			break;
		}
	}

}

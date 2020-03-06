import java.util.LinkedList;

import Experiment.Experiment;
import Lists.*;


public class Remote_Control {

	public void Experiment_select(int a) {
		
		Experiment e = new Experiment();
		
		switch (a) {
		case 0:
			e.Analysis_add(new LinkedList<Integer>(), "LinkedList");
			e.Analysis_get(new LinkedList<Integer>(), "LinkedList");
			
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

		default:
			break;
		}
	}

}

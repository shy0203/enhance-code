package Experiment;

import java.util.List;
import Data.Data_Measure;
import Data.Data_Structure;

public class Experiment extends Data_Measure implements Data_Structure {

	@Override
	public void Analysis_add(List list, String name) {
		System.gc();
		Measure_add(list, name);
	}

	@Override
	public void Analysis_get(List list, String name) {
		System.gc();
		Produce(list);
		Measure_get(list, name);
	}
}

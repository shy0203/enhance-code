import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Lists.CopyList;



public class SearchClass {
	
	// Lists 패키지의 모든 클래스 이름 읽음
	public List getClassList(String packagePath) throws IOException{
		List classList = new ArrayList();
		Class<CopyList> a = CopyList.class;
		String targetPackage = Package.getPackage(packagePath).getName();
		System.out.println(targetPackage);
		if(targetPackage ==null){
			System.err.printf("패키지 경로(%s)를 입력해주세요!", packagePath);
			return null;
		}
		
		
		
		File[] files = new File(packagePath).listFiles();
		for (int i = 0; i < files.length; i++) {
			String path = files[i].getName().replace("/", ".");
			if(path.endsWith(".java")){
				classList.add(packagePath + "." + path.replace(".java",""));
				System.out.println(packagePath + "." + path.replace(".java",""));
			}
		}
		return classList;
	}
}

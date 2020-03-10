package Control;
import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import Data.Data_Experiment;
import Lists.*;

public class Remote_Control implements Control_Tool {
	// 사용자가 입력한 실험번호에 맞는 실험 class파일들을 호출하고 실행하는 클래스

	@Override
	public Class[] getClass(String packageName) {
		
		List classes = new ArrayList();
		
		String path = "./" + packageName.replace('.', '/');		
		URL resources = Thread.currentThread().getContextClassLoader().getResource(path);
		
		String resource = resources.getFile();	
		File directory = new File(resource);
		
		if(directory.exists()){
			
			String[] files = directory.list();
			
			try {
				classes.add(Class.forName("java.util.LinkedList"));
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			for(String file : files){
				if(file.endsWith(".class")){
					
					file = file.substring(0, file.length() - 6);
					
					Class copylist;
					boolean isName1, isName2;
					
					try {
						copylist = Class.forName(packageName + "." + file);
						
						isName1 = copylist.getName().contains("$");
						isName2 = copylist.getName().contains("CopyList");
						
						if(((!isName1) && isName2)) {
							classes.add(copylist);
						}
					} 
					catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return (Class[])classes.toArray(new Class[classes.size()]);
	}

	@Override
	public int getCaseNum() {
		
		int research_num;
		
		Scanner scanNum = new Scanner(System.in);
		System.out.print("실험번호 입력 : ");
		research_num = scanNum.nextInt();
		
		return research_num;
	}

	@Override
	public void Experiment_select() {
		
		int caseNum = this.getCaseNum();
		Class[] classes = this.getClass("Lists");
		
		List copyList;
		String className;
		
		try {
			copyList = (List) classes[caseNum].newInstance();
			className = classes[caseNum].getName();
			
			Data_Experiment e = new Data_Experiment();
			
			// 캐스팅 문제 있음!!
//			if(className.equals("java.util.LinkedList")){
//				e.Analysis_SequentialGet(copyList, className);
//			}
			
			e.Analysis_add(copyList, className.replace("Lists.", ""));
			e.Analysis_get(copyList, className.replace("Lists.", ""));
			
		} 
		catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}
}

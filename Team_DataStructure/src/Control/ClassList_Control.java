package Control;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class ClassList_Control implements Comparator<Class<?>> {

	private static int length = 0;	// 입력 가능한 숫자범위 지정을 위해 copylists의 크기를 담음
	
	@SuppressWarnings("rawtypes")
	public static Class[] getClass(String packageName) {
		// 실험하고자 하는 패키지 내 클래스들을 모두 호출하여 copylists에 담아 리턴
		List<Class<?>> copyLists = new ArrayList<Class<?>>();	// 패키지 내 클래스들을 담을 변수
		
		String path = "./" + packageName.replace('.', '/');	// URL을 호출하기 위해 클래스의 구분자 '.'을 '/'로 바꾸는 과정
		URL resources = Thread.currentThread().getContextClassLoader().getResource(path);	// 패키지의 URL 가져오기
		
		String resource = resources.getFile();	// 위에서 구한 URL을 이용하여 디렉토리의 전체 경로를 가져온다.
		File directory = new File(resource); // 전체 경로를 담는 변수 초기화
		
		if(directory.exists()){	// 파일이 존재하는지 여부 조사
			
			Class<?> classList; // 로드한 클래스들 리스트화
			String[] files = directory.list();	// directory에 담겨 있는 목록을 String 배열로 반환
			boolean isName;
			
			for(String file : files){	
				if(file.endsWith(".class")){	// .class로 문자열을 끝나는 경우
					
					file = file.substring(0, file.length() - 6);	// 확장자 삭제
					
					try {
						classList = Class.forName(packageName + "." + file);	// 동적로딩 : 해당 클래스를 메모리로 로드
						isName = classList.getName().contains("$");	// 동적로딩한 클래스의 이름에서 '$'문자 포함 확인
						
						if(!isName) {
							copyLists.add(classList);	// return할 변수 copylists에 클래스리스트들을 담음
						}
					} 
					catch (ClassNotFoundException e) {	// 클래스가 없을 때 예외처리
						e.printStackTrace();
					}
				}
			}
			length = copyLists.size();	// 입력 가능한 숫자범위 지정을 위해 copylists의 크기를 담음
		}
		Collections.sort(copyLists, new ClassList_Control());
		
		return copyLists.toArray(new Class[length]);	// copylists 리턴
	}
	
	public static int getLength(){
		return length;
	}
	
	@Override
	public int compare(Class<?> o1, Class<?> o2) {
		return o1.getName().replace("Lists.", "").compareTo(o2.getName().replace("Lists.", ""));
	}
}

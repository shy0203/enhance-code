package Control;

import java.util.Scanner;

public class Paging_Control {

	@SuppressWarnings("rawtypes")
	private Class[] list;
	private int totalCount;
	private int totalPage;
	private int listCount;
	private int changeListCount;
	private int currentPage;
	private int startList;
	
	public Paging_Control() {
		Remote_Control rc = new Remote_Control();
		list = rc.getClass("Lists");
		this.totalCount = list.length;	// 불러온 클래스들을 담은 리스트 길이
		this.listCount = 5;	// 한 페이지에 보여줄 개수
		this.totalPage = (int) Math.ceil((double) totalCount / listCount);	// 전체 페이지를 담는 변수
		this.currentPage = 1;	// 현재 페이지를 담는 변수
	}
	
	public Paging_Control(Class[] list, int listCount, int currentPage) {
		this.list = list;
		this.totalCount = list.length;	// 불러온 클래스들을 담은 리스트 길이
		this.listCount = listCount;	// 한 페이지에 보여줄 개수
		this.totalPage = (int) Math.ceil((double) totalCount / listCount);	// 전체 페이지를 담는 변수
		this.currentPage = currentPage;	// 현재 페이지를 담는 변수
	}
	
	public int getTotalCount() {
		return totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getListCount() {	// 보여지는 개수에 따른 페이지 인덱스를 변경하기 위함
		if(currentPage == totalPage){
			this.changeListCount = totalCount % listCount;	// 마지막 페이지 인덱스 변경 5->남은 갯수 만큼
		} else{
			this.changeListCount = listCount;
		}
		return changeListCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}
	
	public int getStartList() {
		this.startList = (currentPage - 1) * listCount;
		return startList;
	}

	public void pagingControl(){
		//currentPage = 현재 보고있는 페이지
		if (totalPage < currentPage)	currentPage = totalPage;// 현재 페이지를 마지막 페이지로
		if (1 > currentPage) currentPage = 1;				// 1페이지에서 이전으로 갈때 인덱스 장애 조치
	}

	public void incrementCurrentPage(){
		this.currentPage++;
	}
	
	public void decrementCurrentPage(){
		this.currentPage--;
	}
	
	public void pageCheck() {
		// 페이지 다음/이전 표시
		if (1 == this.getCurrentPage()) {
			System.out.print("\t\t\t\t\t[n]: next (종료 필요시 q 입력)...");
		} 
		else if (this.getTotalPage() == this.getCurrentPage()) {
			System.out.print("[p]: preview\t\t\t\t\t  (종료 필요시 q 입력)...");
		}
		else {
			System.out.print("[p]: preview\t\t\t\t[n]: next (종료 필요시 q 입력)...");
		}
	}
	
	@SuppressWarnings("resource")
	public boolean inputChar(boolean commandCheck) {
		// 입력 받은 명령어에 대한 구별 동작
		Scanner scan = new Scanner(System.in);
		String inputChar = scan.nextLine();
		
		// 입력 받은 명령어 구별
		if(inputChar.matches("n")){
			this.incrementCurrentPage();	// 페이지 증가
		} 
		else if(inputChar.matches("p")){
			this.decrementCurrentPage();	// 페이지 감소
		}
		else if(inputChar.matches("q")){
			commandCheck = true;			// 종료
		}
		System.out.println("-----------------------------------------------------------------");
		return commandCheck;
	}
	
	@SuppressWarnings("static-method")
	public void clear() {	// 콘솔창 클리어
		for (int i = 0; i < 80; ++i)
			System.out.println();
	}
}

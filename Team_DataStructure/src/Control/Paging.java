package Control;


public class Paging {
	private String[] list;
	private int totalCount;
	private int totalPage;
	private int listCount;
	private int changeListCount;
	private int currentPage;
	private int startList;

	// 생성자
	public Paging() {
		this.list = Class_List.getClassList();
		this.totalCount = list.length;
		this.listCount = 5;
		this.totalPage = (int) Math.ceil((double) totalCount / listCount);
		this.currentPage = 1;
	}

	// 메소드

	public int getTotalCount() {
		return totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getListCount() {
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
	
	public String[] getList() {
		return list;
	}

	public int getStartList() {
		this.startList = (currentPage - 1) * listCount;
		return startList;
	}

	public void paging(){
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
	
}

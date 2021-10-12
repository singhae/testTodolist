package com.todo.menu;
public class Menu {

    public static void displaymenu() // 메모리할당을 안해도 사용할 수 잇는 함수  Static , void: 반환할게 없는 
    {
        System.out.println(); //출력 
        System.out.println("<Todolist 관리 명령어 사용법>");
        System.out.println("add - 항목추가");  //새로운 아이쳄을 추가하라는글 출력 
        System.out.println("del - 항목제거"); //ㅈ있던 아이템을 삭제하라는글 
        System.out.println("edit - 항목수정"); //아이템을 업데이트 
        System.out.println("ls - 전체목록"); //모든 리스트보여주기 
        System.out.println("ls_name_asc - 제목순으로 정렬"); //이름을 오름차순으로 정렬 
        System.out.println("ls_name_desc - 제목역순 정렬"); // 내림차순으로 정렬 
        System.out.println("ls_date - 날짜순 정렬"); //날짜순으로 정렬 
        System.out.println("find - 제목, 내용에서 키워드 찾기 ");
        System.out.println("find_cate - 카테고리 키워드 찾기 ");
        System.out.println("ls_cate - 카테고리로 정렬");
        System.out.println("exit - 종료 "); //종료키를 선택 
        System.out.println("Enter your choice >"); //너의 선택을 입력해라 
    }
    public static void prompt() {
    	System.out.print("\nCommand > ");
    }
}

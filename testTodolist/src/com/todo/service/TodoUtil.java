package com.todo.service;

import java.util.*;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.io.FileOutputStream;
import java.io.IOException;



public class TodoUtil {
	
	private static final char[] String = null;
	
	

	public static void createItem(TodoList l) { // ㅇ아이템 만드는 함수 
		
		String category,title, desc,due_date,place,important;  //타이틀 디스크립션 선언 
		Scanner sc = new Scanner(System.in); // 입력 받고 
		
		System.out.println("\n" //출력 
				+ "[항목추가]\n" 
				+ "제목을 입력하십시오 >");
		title = sc.nextLine().trim();
		
		if (l.isDuplicate(title)) { //만약 타이틀을 이즈듀플리케이트 함수에서 실행 된다면 
			System.out.printf("제목이 중복됩니다!"); // 타이틀은 복사되지못한다는 출력 
			return;
		}
		
		System.out.println("카테고리를 입력하십시오 >");
		category = sc.next().trim(); //타이틀에 스트링 입력 
		
		
		sc.nextLine();
		System.out.println("내용을 입력하십시오 >"); // 디스크립션을 쳐라. 출력 
		desc = sc.nextLine().trim(); //받은거 디스크립에 넣기 
		
		System.out.println("장소를 입력하십시오 >");
		place= sc.nextLine().trim(); //타이틀에 스트링 입력 
		
		System.out.println("중요도를 입력하십시오 (*****)으로 >");
		important= sc.nextLine().trim(); //타이틀에 스트링 입력 
		
		System.out.println("마감일을 입력하십시오 >");
		due_date = sc.nextLine().trim(); 
		
		TodoItem t = new TodoItem(title, desc, category, due_date, place, important);  //투두아이템 타입의 티가 투두아이템 메소드 생성 
		if(l.addItem(t) > 0) //리스트 에드아이템 메소드에 티 값을 실행..?
			System.out.println("추가되었습니다");
	}

	public static void deleteItem(TodoList l) {  //수정 
		
		Scanner sc = new Scanner(System.in); //입력 
		
		
		System.out.println("[항목삭제]\n"
				+"삭제할 항목의 번호를 입력하시오 > ");
		int num =sc.nextInt();
		
		
		
		if(l.deleteItem(num)>0)
			System.out.println("삭제되었습니다. ");
	}


	public static void updateItem(TodoList l) { //수정  
		
		Scanner sc = new Scanner(System.in); //입력 
		
		System.out.println("[항목수정]\n"
				+ "수정할 항목의 번호를 입력하시오 > ");
		int num = sc.nextInt();
		
		
		System.out.println("새 제목을 입력하시오 > ");  //출력 
		String new_title = sc.next().trim(); //뉴타이틀 입력받기 
		sc.nextLine(); 
		
		System.out.println("새 카테고리를 입력하시오 > "); 
		String new_category = sc.next().trim(); 
		
		sc.nextLine(); //중요,,,,,
		
		System.out.println("새로운 내용을 입력하십시오. > "); //출력 
		String new_desc = sc.nextLine().trim();  // 얘를 못받고 있네 지금...
		
		System.out.println("새 장소를 입력하십시오 >");
		String new_place= sc.next().trim(); //장소추가 
		
		System.out.println("새 중요도를 입력하십시오 (*****)으로 >");
		String new_important= sc.next().trim(); //중요도 추가  
		
		sc.nextLine(); 
		
		System.out.println("새 마감일 입력하시오 > "); 
		String new_due_date = sc.nextLine().trim(); 
		
		TodoItem t = new TodoItem( new_title, new_desc,new_category, new_due_date,new_place,new_important);
		t.setId(num);
		System.out.print(l.updateItem(t));
		
		if (l.updateItem(t)>0) 
				System.out.println("업데이트 되었습니다."); //출력 
				
		

	}

	public static void listAll(TodoList l) { //수정 하기 
		
		System.out.println("[전체목록, 총" + l.getList().size() + "개]");
		
		
		for (TodoItem item : l.getList()) { 
			
			System.out.println(item.toString()); //수정 
		}
		
		
		
	}
	public static void listAll(TodoList l,String orderby, int ordering) { //수정 하기 
		
		System.out.println("[전체목록, 총" + l.getList().size() + "개]");
		
		
		for (TodoItem item : l.getOrderedList(orderby, ordering)) { 
			
			System.out.println(item.toString()); //수정 
		}
		
		
		
	}
	
	public static void saveList(TodoList l, String filename) {
		
			try {
				FileWriter fw = new FileWriter(filename);
				for (TodoItem item : l.getList()) { 
					fw.write(item.toSaveString()); //왜 이렇게 나오는데 
				}

				fw.close();
				
				
				
				System.out.println("파일에 저장되었습니다.");
				
			}catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static void loadList(TodoList l,String filename){
		
		  try {
			  BufferedReader reader = new BufferedReader(new FileReader(filename));
			  String str;
			  
			  int i = 0;
			  while((str=reader.readLine())!=null){
				  StringTokenizer st = new StringTokenizer(str, "-");  // ##이 나오는 곳 까지 자른다 .
				  String title = st.nextToken();
				  String desc = st.nextToken();
				  String current_date = st.nextToken();
				  
				  String category = st.nextToken(); 
				  String due_date = st.nextToken();
				  
				  TodoItem item = new TodoItem(category, title, desc,current_date, due_date);
				  //item.setCurrent_date(current_date);
				  l.addItem(item);
				  i++;
				  
			  }
			 reader.close();
			 //System.out.println(i);
			 System.out.println(i + "개의 정보 읽기 완료");
		  	}catch (FileNotFoundException e) {
		  		System.out.println(filename + "파일이없습니다.");
				//e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
		  
		 }

	public static void findList(TodoList l, String word) {
		// TODO Auto-generated method stub
		int count = 0;
	
		for(TodoItem item : l.getList(word)) {
			System.out.println(item.toString());
			count++;
		}	
		
		
		System.out.printf("총 %d개의 항복을 찾았습니다.\n", count);
	}
	
	
	public static void findCategory(TodoList l, String word) {
		// TODO Auto-generated method stub
		int count = 0;
		for (TodoItem item : l.getListCategory(word)) {  //TodoItem > String 으로 고치래 
				System.out.println(item.toString());
				count++;
			
		}	
		System.out.printf("총 %d개의 항복을 찾았습니다.\n", count);
	}

	
		public static void listCateAll(TodoList l) {
		
			int count =0;
			for(String item : l.getCategories()) {
				System.out.print(item + " ");
				count++;
			}
		
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다. \n", count);
	}

		public static void completeItem(TodoList l,int value) {
			// TODO Auto-generated method stub
			//Scanner sc = new Scanner(System.in);
			
			if(l.completeItem(value)>0) {
				
				System.out.println("완료하였습니다. ");
			}
			
			
		}
		public static void listCompAll(TodoList l) { //수정 하기 
						
			System.out.println("[전체목록, 총" + l.getCompList().size() + "개]");
								
							
			for (TodoItem item : l.getCompList()) {  //TodoItem > String 으로 고치래 
						System.out.println(item.toCompString());
			}
				
				
		}
		public static void listNotCompAll(TodoList l) { //수정 하기 
			
			System.out.println("[전체목록, 총" + l.getNotCompList().size() + "개]");
								
							
			for (TodoItem item : l.getNotCompList()) {  //TodoItem > String 으로 고치래 
						System.out.println(item.toNotCompString());
			}
				
				
		}
	
		public static void listPlace(TodoList l) {
			
			int count =0;
			for(String item : l.getPlace()) {
				System.out.println(item + " ");
				count++;
			}
		
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다. \n", count);
	}

		public static void findPlace(TodoList l,String word) {
			// TODO Auto-generated method stub
			int count = 0;
			for (TodoItem item : l.getListPlace(word)) {  //TodoItem > String 으로 고치래 
					System.out.println(item.toString());
					count++;
				
			}	
			System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
			
		}
			
		public static void listImport_Not(TodoList l,String impor) { //수정 하기 
			
			
			System.out.println("[전체목록, 총" + l.getListImport_Notcomp(impor).size() + "개]");
			
			int count = 0;
			for (TodoItem item : l.getListImport_Notcomp(impor)) { 
				
				System.out.println(item.toImpoNotCompString()); //수정 
				//count++;
			}
			//System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
			
			
		}
	
		
}

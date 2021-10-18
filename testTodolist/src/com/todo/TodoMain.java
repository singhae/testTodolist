package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in); 
		TodoList l = new TodoList();  
		//l.importData("todolist.txt");
		
		boolean isList = false; 
		boolean quit = false;  
		
		//TodoUtil.loadList(l, "todolist.txt");
		
		Menu.displaymenu();
		
		do { 
			Menu.prompt(); 
			isList = false; 
			String choice = sc.next(); 
			
			switch (choice) { 

			case "add": 
				TodoUtil.createItem(l);  
				
				break; 
			
			case "del": 
				TodoUtil.deleteItem(l); 
				break;
				
			case "edit":
				TodoUtil.updateItem(l);  
				break;
				
			case "ls":
				TodoUtil.listAll(l); 
				
				break;

			case "ls_name_asc": 
				System.out.println("제목순으로 정렬하였습니다.");
				TodoUtil.listAll(l,"title",1); 
				
				break; 

			case "ls_name_desc": 
				System.out.println("제목역순으로 정렬하였습니다.");
				TodoUtil.listAll(l,"title",0); 
				
				break; 
				
			case "ls_date": 
				
				System.out.println("날짜순으로 정렬합니다."); //추가 
				TodoUtil.listAll(l,"due_date",1); 
				break;
				
			case "ls_date_desc": 
				System.out.println("날짜를 역순으로 정렬합니다."); //추가 
				TodoUtil.listAll(l,"due_date",0); 
				break;
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
			case "ls_impor": 
				System.out.println("중요도순으로 정렬합니다.."); //추가 
				TodoUtil.listAll(l,"important",0); 
				break;
			case "ls_impor_desc": 
				System.out.println("덜 중요한 순으로 정렬합니다.."); //추가 
				TodoUtil.listAll(l,"important",1); 
				break;
			case "help":
				Menu.displaymenu();
				break;
				
			case "find":
				String word = sc.next().trim();
				TodoUtil.findList(l,word);
				break;
			case "find_cate": //
				String wordofcate = sc.next();
				TodoUtil.findCategory(l,wordofcate);
				break;
				
			case "comp":
				int value = sc.nextInt();
				TodoUtil.completeItem(l,value);
				break;
			case "ls_comp":
				TodoUtil.listCompAll(l);
				break;
			case "ls_notcomp":
				TodoUtil.listNotCompAll(l);
				break;
			case "ls_place":
				TodoUtil.listPlace(l);
				break;
			case "find_place":
				String wordplace = sc.next();
				TodoUtil.findPlace(l,wordplace);
				break;
			case "import_notcomp":
				String impor = sc.next().trim();
				TodoUtil.listImport_Not(l,impor);
				break;
			case "exit": 
				quit = true; 
				
				break; 

			default: 
				System.out.println("정확한 명령어를 입력하십시오. (도움말 : help) "); 
				break;
			}
			
			if(isList) TodoUtil.listAll(l); 
		} while (!quit);
		TodoUtil.saveList(l,"todolist.txt");
	} 
}

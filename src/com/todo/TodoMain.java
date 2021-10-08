package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		l.importData("todolist.txt");
		
		boolean isList = false;
		boolean quit = false;
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
				
			case "find":
				String findStr = sc.nextLine().trim();
				TodoUtil.findItem(l, findStr);
				break;	
				
			case "find_cate":
				String cate = sc.nextLine().trim();
				TodoUtil.findcateItem(l, cate);
				break;	
				
			case "ls":
				TodoUtil.listAll(l);
				break;
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;

			case "ls_name":
				System.out.println("\n**제목순으로 정렬된 리스트**");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("\n**제목역순으로 정렬된 리스트**");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("\n**날짜순으로 정렬된 리스트**");
				TodoUtil.listAll(l, "due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("\n**날짜역순으로 정렬된 리스트**");
				TodoUtil.listAll(l, "due_date", 0);
				break;
			
			case "ls_comp":
				System.out.println("\n**완료된 할일 리스트**");
				TodoUtil.listAll(l,1);
				break;
				
			case "comp":
				int comp_index = sc.nextInt();
				TodoUtil.completeItem(l, comp_index);
				break;
				
			case "help":
				Menu.displaymenu();
				break;

			case "exit":
				quit = true;
				System.out.println("종료되었습니다...");
				TodoUtil.saveList(l, "todolist.txt");
				break;

			default:
				System.out.println("!명령어 리스트 가운데 하나를 입력하세요!");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
	}
}

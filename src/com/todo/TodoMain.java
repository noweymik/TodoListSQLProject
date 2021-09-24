package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		Menu.displaymenu();
		if("todolist.txt" != null) {
			TodoUtil.loadList(l, "todolist.txt");
		}
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
				String findStr = sc.next().trim();
				TodoUtil.findItem(l, findStr);
				break;	
				
			case "find_cate":
				String findcate = sc.next().trim();
				TodoUtil.findcateItem(l, findcate);
				break;	
				
			case "ls_cate":
				TodoUtil.lscate(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				l.sortByName();
				System.out.println("\n**제목순으로 정렬된 리스트**");
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				System.out.println("\n**제목역순으로 정렬된 리스트**");
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				System.out.println("\n**날짜순으로 정렬된 리스트**");
				isList = true;
				break;
				
			case "ls_date_desc":
				l.sortByDate();
				l.reverseList();
				System.out.println("\n**날짜역순으로 정렬된 리스트**");
				isList = true;
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

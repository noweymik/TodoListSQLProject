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
				System.out.println("완료체크할 항목의 개수 입력 >> ");
				int compCount = sc.nextInt();
				int[] compnum = new int[compCount];
				System.out.printf(compCount+"개 만큼 완료체크할 항목을 입력 >> ");
				for(int j=0; j<compCount; j++) {
					compnum[j] = sc.nextInt();
					TodoUtil.completeItem(l, compnum[j]);
				}
				break;
				
			case "uncomp":
				System.out.println("\n**완료된 할일 리스트**");
				TodoUtil.listAll(l,1);
				System.out.println("완료체크한 항목 중 취소할 항목 번호 입력 >> ");
				int uncompnum = sc.nextInt();
				TodoUtil.uncompleteItem(l, uncompnum);
				break;
				
			case "favorite": 
				System.out.println("즐겨찾기할 항목 입력 >> ");
				int fav = sc.nextInt();
				TodoUtil.importantItem(l, fav);
				break;
				
			case "ls_favorite":
				System.out.println("\n**즐겨찾기 리스트**");
				TodoUtil.listImportant(l,1);
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

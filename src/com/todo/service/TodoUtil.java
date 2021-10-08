package com.todo.service;

import java.io.FileWriter;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	
	public static void createItem(TodoList l) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"+ "**항목 추가**\n");
		System.out.print("제목 입력 >> ");
		
		title = sc.next();
		if (l.isDuplicate(title)) {
			System.out.printf("!기존 항목의 제목과 중복됩니다!\n");
			return;
		}
		sc.nextLine();
		System.out.print("카테고리 입력 >> ");
		category = sc.next();
		sc.nextLine();
		System.out.print("내용 입력 >> ");
		desc = sc.nextLine().trim();
		//sc.nextLine();
		System.out.print("마감일자 입력 >> ");
		due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		if(l.addItem(t)>0)
			System.out.println("\n추가되었습니다. ^^\n");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("\n"
				+ "**항목 삭제**\n");
		System.out.print("삭제할 항목의 번호 입력 >> ");
		
		int index = sc.nextInt();
		if(l.deleteItem(index)>0) {
			System.out.println("삭제되었습니다.");
		}
	}


	public static void updateItem(TodoList l) {
		//String new_title, new_desc, new_category, new_due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "**항목 수정**\n"
				+ "수정하고 싶은 항목의 번호 입력 >> ");
		int index = sc.nextInt();
		
		System.out.print("새 제목 입력 >> ");
		String new_title = sc.next().trim();
		System.out.print("새 카테고리 입력 >> ");
		String new_category = sc.next();
		sc.nextLine();
		System.out.print("새 내용 입력 >> ");
		String new_description = sc.nextLine().trim();
		System.out.print("새 마감일자 입력 >> ");
		String new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
		t.setId(index);
		if(l.updateItem(t) > 0)
			System.out.println("\n항목 업데이트를 완료하였습니다. ^^\n");

	}
	public static void completeItem(TodoList l, int index) {
		TodoItem t = l.getList(index);
		if(t != null) {
			t.setIscompleted(1);
			if(l.completeitem(t)>0)
				System.out.println("\n완료 체크하였습니다. ^^\n");
			}
		else {
			System.out.println("\n없는 번호입니다. ^^\n");
		}
			
	}



	public static void listAll(TodoList l) {
		System.out.printf("\n** 전체 리스트, 총 "+"%d"+" 개 **\n", l.getCount());
		for(TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("\n** 전체 리스트, 총 "+"%d"+" 개 **\n", l.getCount());
		for(TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	public static void listAll(TodoList l, int comp) {
		int count=0;
		for(TodoItem item : l.getList()) {
			if(item.getIscompleted()==1) {
				System.out.println(item.toString());
				count++;
			}
		}
		System.out.printf("\n** 총 "+"%d"+" 개의 항목이 완료되었습니다. **\n", count);
	}
	
	public static void saveList(TodoList l, String filename) {
		
		try (FileWriter fw = new FileWriter(filename)) {
			
			for (TodoItem item : l.getList()) {
				fw.write(item.toSaveString());
			}

		} catch (Exception e) {			
			//System.out.println("예외처리");
		}
	}

	public static void findItem(TodoList l, String findStr) {
		int fcount = 0;
		for (TodoItem item : l.getList(findStr)) {
				System.out.println(item.toString());
				fcount++;
			}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n",fcount);
	}
	
	public static void findcateItem(TodoList l, String cate) {
		int count =0;
		
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		for(String item : l.getCategories()) {
			System.out.print(item + " ");
			count ++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n",count);
	}
}

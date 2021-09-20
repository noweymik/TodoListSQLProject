package com.todo.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {

	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "**항목 추가**\n"
				+ "제목 입력 >> ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("!기존 항목의 제목과 중복됩니다!\n");
			return;
		}
		sc.nextLine();
		System.out.println("내용 입력 >> ");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("\n추가되었습니다. ^^\n");
	}

	public static void deleteItem(TodoList l) {
		
		System.out.println("\n"
				+ "**항목 삭제**\n"
				+ "삭제할 항목의 제목 입력 >> ");
		
		Scanner sc = new Scanner(System.in);
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("\n삭제되었습니다. ^^\n");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "**항목 수정**\n"
				+ "수정하고 싶은 항목의 제목 입력 >> ");
		
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("!해당 제목을 찾을 수 없습니다!\n");
			return;
		}
		
		System.out.println("새 제목 입력 >> ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("!제목은 중복될 수 없습니다!\n");
			return;
		}
		sc.nextLine();
		System.out.println("새 내용 입력 >> ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("\n항목 업데이트를 완료하였습니다. ^^\n");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("\n**전체 리스트**");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		
		try (FileWriter fw = new FileWriter("todolist.txt")) {
			
			for (TodoItem item : l.getList()) {
				fw.write(item.toSaveString());
			}

		} catch (Exception e) {			
			//System.out.println("예외처리");
		}
	}
	public static void loadList(TodoList l, String filename) {
		
		try {
			FileReader fr = new FileReader("todolist.txt");
			BufferedReader br = new BufferedReader(fr);
			String str;
			while((str = br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(str, "##", false);
				TodoItem t = new TodoItem(st.nextToken(), st.nextToken(), st.nextToken());
				l.addItem(t);
			}
			br.close();
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

package com.todo.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	public static void findItem(TodoList l, String findStr) {
		int fcount =0;
		int index = 1;
		for (TodoItem item : l.getList()) {
			if(item.getTitle().contains(findStr) || item.getDesc().contains(findStr)) {
				System.out.println(index + ". " + item.toString());
				fcount++;
			}
			index++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n",fcount);
	}
	
	public static void findcateItem(TodoList l, String findStr) {
		int fcount =0;
		int index = 1;
		for (TodoItem item : l.getList()) {
			if(item.getCategory().contains(findStr)) {
				System.out.println(index + ". " + item.toString());
				fcount++;
			}
			index++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n",fcount);
	}
	
	public static void lscate(TodoList l) {
		HashSet<String> cateset = new HashSet<String>();
		for (TodoItem item : l.getList()) {
			cateset.add(item.getCategory());
		}
		Iterator<String> iter = cateset.iterator();	// Iterator 사용
		
		while(iter.hasNext()) {//값이 있으면 true 없으면 false
		    System.out.print(iter.next());
		    
		    if(iter.hasNext()) {
		    	System.out.print(" / ");
		    }
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n",cateset.size());
	}
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"+ "**항목 추가**\n");
		System.out.print("제목 입력 >> ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("!기존 항목의 제목과 중복됩니다!\n");
			return;
		}
		sc.nextLine();
		System.out.print("카테고리 입력 >> ");
		category = sc.next().trim();
		
		sc.nextLine();
		System.out.print("내용 입력 >> ");
		desc = sc.nextLine().trim();
		
		System.out.print("마감일자 입력 >> ");
		due_date = sc.next().trim();
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		list.addItem(t);
		System.out.println("\n추가되었습니다. ^^\n");
	}

	public static void deleteItem(TodoList l) {
		
		System.out.println("\n"
				+ "**항목 삭제**\n");
		System.out.print("삭제할 항목의 번호 입력 >> ");
		
		Scanner sc = new Scanner(System.in);
		int dNum = sc.nextInt();
		dNum -= 1;
		if(dNum >= l.getList().size()) {
			System.out.println("!선택할 수 있는 항목 번호 범위를 넘었습니다!");
			return;
		}
		if(l.getList().get(dNum) != null) {	//true라면!
			System.out.printf("%d. " + l.getList().get(dNum)+"\n", dNum+1);
		}
		else {
			System.out.println("!해당 항목을 찾을 수 없습니다!\n");
			return;
		}
		System.out.print("위 항목을 삭제하시겠습니까? (y/n) > ");
		String delYes = sc.next();
		if(delYes.equals("y")) {
			TodoItem item = l.getList().get(dNum);
			l.deleteItem(item);
			System.out.println("\n삭제되었습니다. ^^\n");
		}
		else return;
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "**항목 수정**\n"
				+ "수정하고 싶은 항목의 번호 입력 >> ");
		int eNum = sc.nextInt();
		eNum -= 1;
		if(eNum >= l.getList().size()) {
			System.out.println("!선택할 수 있는 항목 번호 범위를 넘었습니다!");
			return;
		}
		if(l.getList().get(eNum) != null) {	//true라면!
			System.out.printf("%d. " + l.getList().get(eNum)+"\n", eNum+1);
		}
		else {
			System.out.println("!해당 항목을 찾을 수 없습니다!\n");
			return;
		}
		
		System.out.print("새 제목 입력 >> ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("!제목은 중복될 수 없습니다!\n");
			return;
		}
		sc.nextLine();
		System.out.print("새 카테고리 입력 >> ");
		String new_category = sc.next().trim();
		
		sc.nextLine();
		System.out.print("새 내용 입력 >> ");
		String new_description = sc.nextLine().trim();
		
		TodoItem item = l.getList().get(eNum);
		l.deleteItem(item);
		
		System.out.print("새 마감일자 입력 >> ");
		String new_due_date = sc.next().trim();
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
		l.addItem(t);
		System.out.println("\n항목 업데이트를 완료하였습니다. ^^\n");

	}

	public static void listAll(TodoList l) {
		System.out.printf("\n** 전체 리스트, 총 "+"%d"+" 개 **\n", l.getList().size());
		
		int index=1;
		for (TodoItem item : l.getList()) {
			System.out.print(index++);
			System.out.println(". "+item.toString());
		}
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
	public static void loadList(TodoList l, String filename) {
		
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String str;
			while((str = br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(str, "##", false);
				
				String category= st.nextToken();
				String title = st.nextToken();
				String desc= st.nextToken();
				String due_date= st.nextToken();
				String current_date= st.nextToken();
				TodoItem t = new TodoItem(title, desc, category, due_date, current_date);
				l.addItem(t);
			}
			br.close();
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

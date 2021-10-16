package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println(" <TodoList 명령어 사용법> ");
        System.out.println("add : 항목 추가하기 ");
        System.out.println("del : 항목 삭제하기 ");
        System.out.println("edit : 항목 수정하기 ");
        System.out.println("ls : 전체 리스트 보기 ");
        System.out.println("ls_cate : 카테고리 전체 항목 보기 ");
        System.out.println("ls_name : 리스트 제목순으로 보기 ");
        System.out.println("ls_name_desc : 리스트 제목역순으로 보기 ");
        System.out.println("ls_date : 리스트 날짜순으로 보기 ");
        System.out.println("ls_date_desc : 리스트 날짜역순으로 보기 ");
        System.out.println("find <키워드> : 제목, 내용에서 키워드를 포함하고 있는 항목 보기 ");
        System.out.println("find_cate <키워드> : 카테고리에서 키워드를 포함하고 있는 항목 보기 ");
        System.out.println("comp : 완료된 거 체크하기 ");
        System.out.println("uncomp : 완료 체크 해제하기 ");
        System.out.println("ls_comp : 할일 완료된 항목 보기 ");
        System.out.println("favorite : 즐겨찾기하기 ");
        System.out.println("ls_favorite : 즐겨찾기한 항목 보기 ");
        System.out.println("exit : 종료하기 ");
    
    }
    public static void prompt()
    {
    	System.out.print("\n명령어 입력 (명령어 리스트보기: help) >> ");
    }
}

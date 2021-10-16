package com.todo.dao;

import java.io.BufferedReader;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	Connection conn;
	private List<TodoItem> list;
	
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, category, current_date, due_date, difficulty, estimated_time)"
						+ " values (?,?,?,?,?,?,?);";
			int records = 0;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				String difficulty = st.nextToken();
				String estimated_time = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				pstmt.setString(6, difficulty);
				pstmt.setString(7, estimated_time);
				
				int count = pstmt.executeUpdate();
				if(count > 0) records++;
				pstmt.close();
			}
			System.out.println(records + " records read!!");
			br.close();
		}	catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public TodoList() {
		this.list = new ArrayList<TodoItem>();
		this.conn = DbConnect.getConnection();
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list(title, memo, category, current_date, due_date, difficulty, estimated_time)"
					+ " values (?,?,?,?,?,?,?);";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setString(6, t.getDifficulty());
			pstmt.setString(7, t.getEstimated_time());
			count = pstmt.executeUpdate();
			pstmt.close();
		}	catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	public int getCount() {
		Statement stmt;
		int count = 0;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?" 
					+ " where id = ?;";	
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4,  t.getCurrent_date());
			pstmt.setString(5,  t.getDue_date());
			pstmt.setInt(6, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String difficulty = rs.getString("difficulty");
				String estimated_time = rs.getString("estimated_time");
				int important = rs.getInt("important");
				TodoItem t = new TodoItem(title, description, category, due_date, current_date, is_completed, difficulty, estimated_time, important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		
		keyword = "%"+keyword+"%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or memo like ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String difficulty = rs.getString("difficulty");
				String estimated_time = rs.getString("estimated_time");
				int important = rs.getInt("important");
				TodoItem t = new TodoItem(title, description, category, due_date, current_date, is_completed, difficulty, estimated_time, important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String category = rs.getString("category");
				list.add(category);
			}
			stmt.close();
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<TodoItem> getListCategory(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		
		try {
			String sql = "SELECT * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String difficulty = rs.getString("difficulty");
				String estimated_time = rs.getString("estimated_time");
				int important = rs.getInt("important");
				TodoItem t = new TodoItem(title, description, category, due_date, current_date, is_completed, difficulty, estimated_time, important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if(ordering ==0) sql+= " desc";
			
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String difficulty = rs.getString("difficulty");
				String estimated_time = rs.getString("estimated_time");
				TodoItem t = new TodoItem(title, description, category, due_date, current_date, is_completed, difficulty, estimated_time);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void listAll() {
		int index=1;
		for (TodoItem item : list) {
			System.out.print(index++);
			System.out.println(". "+item.toString());
		}
	}

	public Boolean isDuplicate(String enter_title) {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT title FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String title = rs.getString("title");
				list.add(title);
			}
			stmt.close();
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		for (String title : list) {
			if (enter_title.equals(title)) return true;
		}
		return false;
	}

	public int completeitem(TodoItem t) {
		String sql = "update list set is_completed = ? WHERE id = ?;";
		
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, t.getIscompleted());
			pstmt.setInt(2, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		}	catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	public int importantItem(TodoItem t) {
		String sql = "update list set important = ? WHERE id = ?;";
		
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, t.getImportant());
			pstmt.setInt(2, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		}	catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public TodoItem getList(int index) {
		TodoItem t = null;// = new TodoItem();
		PreparedStatement pstmt;
		
		try {
			String sql = "SELECT * FROM list WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String difficulty = rs.getString("difficulty");
				String estimated_time = rs.getString("estimated_time");
				int important = rs.getInt("important");
				t = new TodoItem(title, description, category, due_date, current_date, is_completed, difficulty, estimated_time, important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
				System.out.println(t.toString());
				
			}
			pstmt.close();
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}
	
}

package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;
import com.todo.service.DbConnect;

public class TodoList {
	Connection conn;

	private List<TodoItem> list;

	public TodoList() { //새로운 어레이 투두아이템의 형식으로 만드는 메소
		this.list = new ArrayList<TodoItem>();
		this.conn = DbConnect.getConnection(); // 얘 클라스 있느데 왜 안ㅇ돼 
	}
	public int updateItem(TodoItem t) {
		String sql = "update list set title =?, memo=?, category=?, current_date=?, due_date=?, place=?, important=?"
					+ " where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setString(6, t.getPlace());
			pstmt.setString(7, t.getImportant());
			pstmt.setInt(8, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	/*public void addItem(TodoItem t) { //리스트에 티(내용)를 추가해주는 함
		list.add(t);
	}
	*/
	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, current_date, due_date, place, important)"
					+ " values (?,?,?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setString(6, t.getPlace());
			pstmt.setString(7, t.getImportant());
			count = pstmt.executeUpdate();
			pstmt.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	public int deleteItem(int num) { //리스트에 티를 제거해주는 함수
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/*public void editItem(TodoItem t, TodoItem updated) { //
		int index = list.indexOf(t); 
		list.remove(index);
		list.add(updated);
	}*/
	/*public int editItem(int num) {
		String sql = "update 
	}*/


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
				String place= rs.getString("place");
				String important= rs.getString("important");
				TodoItem t = new TodoItem(title, description, category, due_date, place, important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
				//add
				
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String word) {
		// TODO Auto-generated method stub
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt; //initialize 하라고해서 함!!! 
		word = "%" + word + "%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, word);
			pstmt.setString(2, word);
			ResultSet rs= pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				String place= rs.getString("place");
				String important= rs.getString("important");
				TodoItem t = new TodoItem(title, description, category, due_date,place, important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
				
				
			}
			rs.close();
			pstmt.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/*private void setString(int i, String word) {
		// TODO Auto-generated method stub
		
	}*/
	public void sortByName() {
		//System.out.println("제목순으로 정렬하였습니다.");
		Collections.sort(list, new TodoSortByName()); //정렬함수 리스o랑,comparator로 구현한 클래스의 인스턴스

	}

	public void listAll() { //모든 리스트불러오는 함수 
		System.out.println("\n"
				+ "inside list_All method\n");  //출력 리스트안에있는 모든 메소
		for (TodoItem myitem : list) {  //반복문 : 리스트에 있는 값을 myitem에 넣기 
			System.out.println(myitem.getTitle() + myitem.getDesc()); //타이틀함수 + 디스크립션 출
		}
	}
	
	public void reverseList() { 
		Collections.reverse(list);  //list안의 값을 역순으로
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate()); //comparator투두솔트바이데이트 객체로 정렬하는 함
	}

	public int indexOf(TodoItem t) { //전달된 인자가 리스트에 존재한다면 해당 객체의 인덱스를 리턴. 아니면 -1
		return list.indexOf(t);
	}

	/*public Boolean isDuplicate(String title) {   //
		for (TodoItem item : list) { //리스트를 아이템에 넣는 반복
			if (title.equals(item.getTitle())) return true; //만약 타이틀이 리스트에 있는 타이틀이랑 같으면 true 리
		}
		return false; // 같지 않으면 거짓 리턴 
	}*/
	public boolean isDuplicate(String title) {  // 제목 중복되는지 ㅏ안되는지 확인 
		PreparedStatement pstmt;
		int count = 0;
			try {
				
				String sql = "SELECT title, COUNT(*) FROM list GROUP BY title;"; //숫자 반환해줌..중복된거만큼 숫자! 
				pstmt = conn.prepareStatement(sql);
				
				ResultSet rs = pstmt.executeQuery(); //sql 왜 뺴줘야하노 
				
				//System.out.print(rs);
				
				while(rs.next()) {
					
					count =rs.getInt("count(*)");
				
					//count++;
				}
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(count >= 2)
				return true;
			else	
				return false;
		
		
	}
	
	public TodoItem getItem(int num) {
		return list.get(num);
	}
	public int getCount(){
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
		
	}
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, category, current_date, due_date)"
					+ "values (?,?,?,?,?);";
			int records =0;
			while((line = br.readLine())!= null) {
				StringTokenizer st = new StringTokenizer(line, "-");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String current_date = st.nextToken();
				String due_date = st.nextToken();
				String place= st.nextToken();
				String important= st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, desc);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				pstmt.setString(6,place);
				pstmt.setString(7,important);
				int count = pstmt.executeUpdate();
				if(count > 0) records++;
				pstmt.close();
				
				
			}
			System.out.println(records + " records read !! ");
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public ArrayList<String> getCategories() {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql= "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				
				String category = rs.getString("category");
			
				list.add(category);
			}
			rs.close();
			stmt.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String word) { //여기 에러 걸림...
		// TODO Auto-generated method stub
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql= "SELECT * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, word);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				//String current_date = rs.getString("current_date");
				String place = rs.getString("place");
				String important= rs.getString("important");
				
				TodoItem t = new TodoItem(title, description, category, due_date,place, important);
				list.add(t); //이부분 왜.... 
			}
			rs.close();
			pstmt.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		// TODO Auto-generated method stub
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		
		try {
			stmt= conn.createStatement();
			String sql="SELECT * FROM list ORDER BY " + orderby;
			
			if(ordering == 0)
				sql +=" desc";
			ResultSet rs = stmt.executeQuery(sql); //에러?!!!!!!!
			
			
			while(rs.next()) {
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				//String current_date = rs.getString("current_date");
				String place = rs.getString("place");
				String important= rs.getString("important");
				TodoItem t = new TodoItem(title, description, category, due_date, place, important);
				list.add(t);
			}
			rs.close();
		}catch (Exception e) {
				e.printStackTrace();
			}
		return list;
	}
	public int completeItem(int value) { //업데이트만 해주면 된다 , 사실 아이디의 이즈 컴플리티트를 1로 바까주는거야 ! 
		// TODO Auto-generated method stub
		String sql = "UPDATE list SET is_completed = 1 WHERE id = ?"; //??id 값 안받아와도 되겟지?
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, value);
			//pstmt.setInt(1, id);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count; //1이 되면 
		
	}
	public ArrayList<TodoItem> getCompList() {
		// TODO Auto-generated method stub
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt; //initialize 하라고해서 함!!! 
		
		try {
			String sql = "SELECT * FROM list WHERE is_completed=1";
			pstmt = conn.prepareStatement(sql);
			
			
			ResultSet rs= pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				String place = rs.getString("place");
				String important = rs.getString("important");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title, desc, category, due_date,is_completed,place, important); //todoitem 수정 
				t.setId(id);
				t.setCurrent_date(current_date);
				
				list.add(t);
				
				
			}
			rs.close();
			pstmt.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<TodoItem> getNotCompList() {
		// TODO Auto-generated method stub
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt; //initialize 하라고해서 함!!! 
		
		try {
			String sql = "SELECT * FROM list WHERE is_completed=0";
			pstmt = conn.prepareStatement(sql);
			
			
			ResultSet rs= pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				String place = rs.getString("place");
				String important = rs.getString("important");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title, desc, category, due_date,is_completed,place, important); //todoitem 수정 
				t.setId(id);
				t.setCurrent_date(current_date);
				
				list.add(t);
				
				
			}
			rs.close();
			pstmt.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListPlace(String word) {
		// TODO Auto-generated method stub
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt; //initialize 하라고해서 함!!! 
		
		try {
			String sql = "SELECT * FROM list WHERE place = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, word);
			//System.out.print(sql);

			ResultSet rs= pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				String place = rs.getString("place");
				String important = rs.getString("important");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title, desc, category, due_date,is_completed,place, important); //todoitem 수정 
				t.setId(id);
				t.setCurrent_date(current_date);
				//t.setIs_completed(is_completed);
				list.add(t);
				
				
			}
			rs.close();
			pstmt.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<String> getPlace() {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql= "SELECT DISTINCT place FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				
				String place = rs.getString("place");
			
				list.add(place);
			}
			rs.close();
			stmt.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
		public ArrayList<TodoItem> getListImport_Notcomp(String word) { //중요한데, 미완료한 목록정렬할 메소드! 

			ArrayList<TodoItem> list = new ArrayList<TodoItem>();
			PreparedStatement pstmt; 
			
			try {
				String sql = "SELECT title, memo, due_date, important, is_completed FROM list WHERE important = ? AND is_completed = 0;"; //select title, memo, due_date, case when important='****' then is_completed=0 end from list;
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, word);
				
				ResultSet rs= pstmt.executeQuery();
				
				while(rs.next()) {
					
					String title = rs.getString("title");
					String desc = rs.getString("memo");
					String due_date = rs.getString("due_date");
					
					String important = rs.getString("important");
					int is_completed = rs.getInt("is_completed");
					TodoItem t = new TodoItem(title, desc, due_date,is_completed, important); //todoitem 수정 
					
					list.add(t);
		
		
				}
				rs.close();
				pstmt.close();
	
			}catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		
	
}


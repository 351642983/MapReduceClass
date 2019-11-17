package mpc;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 通用类Dao
 * Dao层操作数据
 * @author HDQ
 *
 */
public class ClassDao {

	/**
	 * 添加
	 * @return
	 */
	public <T> boolean add(String table,String []strList,String []strList1) {
		if(strList.length==0)
			return false;
		String sql = "insert into "+table+"(";
		for(int i=0;i<strList.length;i++)
		{
			if(i!=strList.length-1)
				sql+=strList[i]+",";
			else sql+=strList[i]+")";
		}
		sql+=" values('";
		for(int i=0;i<strList1.length;i++)
		{
			if(i!=strList1.length-1)
				sql+=strList1[i]+"','";
			else sql+=strList1[i]+"')";
		}
		//创建数据库链接
		Connection conn = DBUtil.getConn();
		Statement state = null;
		boolean f = false;
		int a = 0;
		
		try {
			state = conn.createStatement();
			a=state.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			DBUtil.close(state, conn);
		}
		
		if (a > 0) {
			f = true;
		}
		return f;
	}
	
	/**
	 * 添加T
	 * @return
	 */
	public <T> boolean add(String table,T obj) {
		StringHandle sh=new StringHandle();
		EntityToString ets=new EntityToString();
		String []strList=sh.StringListToStringNlist(ets.getNameList(obj.getClass()));
		String []strList1=sh.StringListToStringNlist(ets.getStringListSingle(obj));
		
		if(strList.length==0)
			return false;
		String sql = "insert into "+table+"(";
		for(int i=0;i<strList.length;i++)
		{
			if(i!=strList.length-1)
				sql+=strList[i]+",";
			else sql+=strList[i]+")";
		}
		sql+=" values('";
		for(int i=0;i<strList1.length;i++)
		{
			if(i!=strList1.length-1)
				sql+=strList1[i]+"','";
			else sql+=strList1[i]+"')";
		}
		//创建数据库链接
		Connection conn = DBUtil.getConn();
		Statement state = null;
		boolean f = false;
		int a = 0;
		
		try {
			state = conn.createStatement();
			a=state.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			DBUtil.close(state, conn);
		}
		
		if (a > 0) {
			f = true;
		}
		return f;
	}
	
	
	
	

	/**
	 * 删除
	 * 
	 * @return
	 */
	
	public boolean delete (String table,String zhixing,String biaoshi) {
		boolean f = false;
		String sql = "delete from "+table+" where "+zhixing+"='" + biaoshi + "'";
		
		Connection conn = DBUtil.getConn();
		Statement state = null;
		int a = 0;
		
		try {
			state = conn.createStatement();
			a = state.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(state, conn);
		}
		
		if (a > 0) {
			f = true;
		}
		return f;
	}

	/**
	 * 修改T
	 * @param pass
	 */
	public <T> boolean update(String table,T obj,String qian,String hou) {
		StringHandle sh=new StringHandle();
		EntityToString ets=new EntityToString();
		String []strlist=sh.StringListToStringNlist(ets.getNameList(obj.getClass()));
		String []strlist1=sh.StringListToStringNlist(ets.getStringListSingle(obj));
		
		String sql = "update "+table+" set ";
		for(int i=0;i<strlist.length;i++)
		{
			if(i!=strlist.length-1)
				sql+=strlist[i]+"='" + strlist1[i] + "',";
			else sql+=strlist[i]+"='" + strlist1[i] + "' where "+qian+"='" + hou + "'";
		}

		Connection conn = DBUtil.getConn();
		Statement state = null;
		boolean f = false;
		int a = 0;

		try {
			state = conn.createStatement();
			a = state.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(state, conn);
		}
		
		if (a > 0) {
			f = true;
		}
		return f;
	}
	
	/**
	 * 修改T多key
	 * @param pass
	 */
	public <T> boolean update(String table,T obj,String[] qian,String[] hou) {
		
		if(qian.length!=hou.length||qian.length==0)
			return false;
		StringHandle sh=new StringHandle();
		EntityToString ets=new EntityToString();
		String []strlist=sh.StringListToStringNlist(ets.getNameList(obj.getClass()));
		String []strlist1=sh.StringListToStringNlist(ets.getStringListSingle(obj));
		
		String sql = "update "+table+" set ";
		for(int i=0;i<strlist.length;i++)
		{
			if(i!=strlist.length-1)
				sql+=strlist[i]+"='" + strlist1[i] + "',";
			else
			{
				sql+=strlist[i]+"='" + strlist1[i] + "' " ;
				for(int j=0;j<qian.length;j++)
				{
					if(j==0)
					{
						sql+="where "+qian[j]+"='" + hou[j] + "' ";
					}
					else
					{
						sql+="and "+qian[j]+"='" + hou[j] + "' ";
					}
				}
			}
			
		}

		Connection conn = DBUtil.getConn();
		Statement state = null;
		boolean f = false;
		int a = 0;

		try {
			state = conn.createStatement();
			a = state.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(state, conn);
		}
		
		if (a > 0) {
			f = true;
		}
		return f;
	}
	
	
	/**
	 * 修改
	 * @param pass
	 */
	public boolean update(String table,String []strlist,String []strlist1,String qian,String hou) {
		String sql = "update "+table+" set ";
		for(int i=0;i<strlist.length;i++)
		{
			if(i!=strlist.length-1)
				sql+=strlist[i]+"='" + strlist1[i] + "',";
			else sql+=strlist[i]+"='" + strlist1[i] + "' where "+qian+"='" + hou + "'";
		}

		Connection conn = DBUtil.getConn();
		Statement state = null;
		boolean f = false;
		int a = 0;

		try {
			state = conn.createStatement();
			a = state.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(state, conn);
		}
		
		if (a > 0) {
			f = true;
		}
		return f;
	}
	
	/**
	 * 修改多key
	 * @param pass
	 */
	public boolean update(String table,String []strlist,String []strlist1,String []qian,String[] hou) {
		String sql = "update "+table+" set ";
		for(int i=0;i<strlist.length;i++)
		{
			if(i!=strlist.length-1)
				sql+=strlist[i]+"='" + strlist1[i] + "',";
			else 
			{
				sql+=strlist[i]+"='" + strlist1[i] + "' ";
				for(int j=0;j<qian.length;j++)
				{
					if(j==0)
					{
						sql+="where "+qian[j]+"='" + hou[j] + "' ";
					}
					else
					{
						sql+="and "+qian[j]+"='" + hou[j] + "' ";
					}
				}
			}
		}

		Connection conn = DBUtil.getConn();
		Statement state = null;
		boolean f = false;
		int a = 0;

		try {
			state = conn.createStatement();
			a = state.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(state, conn);
		}
		
		if (a > 0) {
			f = true;
		}
		return f;
	}
	
	
	/**
	 * 验证通用类名称是否唯一
	 * true --- 不唯一
	 
	 * @return
	 */
	public boolean name(String table,String zhi,String weiyi) {
		boolean flag = false;
		String sql = "select "+zhi+" from "+table+" where "+zhi+" = '" + weiyi + "'";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		
		try {
			state = conn.createStatement();
			flag = state.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(state, conn);
		}
		return flag;
	}
	
	//判断表是否存在
	public boolean isExistTable(String tablename)
	{
		boolean flag = false;
		String sql ="SELECT table_name FROM information_schema.TABLES WHERE table_name ='"+tablename+"'";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return flag;
	}
	
	
	/**
	 * 查找
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("deprecation")
	public <T> List<T> search(String table,String []strList,String []strList1,Class<T> clazz)  {
		String sql = "select * from "+table;
		int i=0,k=0;
		for(String it:strList1)
		{
			if(it!=null&&!it.equals(""))
			{
				if(k==0)
					sql +=" where "+ strList[i]+" like '%" + it + "%'";
				else sql +=" and "+ strList[i]+" like '%" + it + "%'";
				++k;
			}
			++i;
		}
		List<T> list = new ArrayList<>();
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;

		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			T bean = null;
			while (rs.next()) {
				bean=clazz.newInstance();
				for(String it:strList)
				{
					Field fs=getDeclaredField(bean, it);
					if(fs==null){
						throw new IllegalArgumentException("Could not find field["+ 
								it+"] on target ["+bean+"]");
					}
					makeAccessiable(fs);
				    try{
				        fs.set(bean, rs.getObject(it));
				    }
				    catch(IllegalAccessException e){
				        System.out.println("不可能抛出的异常");
				    }
				}
				list.add(bean);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		
		return list;
	}
	
	/**
	 * 单列表信息获取
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getInfoAllList(String table,String rowInfo,Class<T> clazz)
	{
		List<T> infoList=new ArrayList<T>();
		String sql="select "+rowInfo+" from "+table;
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			T bean = null;
			while (rs.next()) {
				bean=(T)rs.getObject(rowInfo);
				infoList.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return infoList;
	}
	
	/**
	 * 键对值信息获取
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	public <T> Map<T,Object> getInfoMapList(String table,String key,String value,Class<T> clazz)
	{
		Map<T,Object> infoList=new HashMap<T,Object>();
		String sql="select "+key+","+value+" from "+table;
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			T bean = null;
			Object otemp = null;
			while (rs.next()) {
				bean=(T)rs.getObject(key);
				otemp=rs.getObject(value);
				infoList.put(bean,otemp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return infoList;
	}
	
	/**
	 * 精确查找
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("deprecation")
	public <T> List<T> searchBy(String table,String []strList,String []strList1,Class<T> clazz){
		String sql = "select * from "+table;
		int i=0,k=0;
		for(String it:strList1)
		{
			if(it!=null&&!it.equals(""))
			{
				if(k==0)
					sql +=" where "+ strList[i]+" = '" + it + "'";
				else sql +=" and "+ strList[i]+" = '" + it + "'";
				++k;
			}
			++i;
		}
		List<T> list = new ArrayList<>();
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;

		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			T bean = null;
			while (rs.next()) {
				bean=clazz.newInstance();
				for(String it:strList)
				{
					Field fs=getDeclaredField(bean, it);
					if(fs==null){
						throw new IllegalArgumentException("Could not find field["+ 
								it+"] on target ["+bean+"]");
					}
					makeAccessiable(fs);
				    try{
				        fs.set(bean, rs.getObject(it));
				    }
				    catch(IllegalAccessException e){
				        System.out.println("不可能抛出的异常");
				    }
				}
				list.add(bean);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		
		return list;
	}
	
	
	
	/**
	 * 由时间和条件查找
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("deprecation")
	public <T> List<T> searchByTime(String table,String []strList,String []strList1,String biaoshi,String qian,String hou,Class<T> clazz)  {
		String sql = "select * from "+table+" where ";
		int i=0,k=0;
		for(String it:strList1)
		{
			if(it!=null&&!it.equals(""))
			{
				sql += strList[i]+" like '%" + it + "%'";
				++k;
			}
			++i;
		}
		if(qian!=null&&!qian.equals(""))
		{
			if(k>0)
				sql+=" and "+biaoshi+" Between '"+qian+"' AND '"+hou+"'";
			else sql+=biaoshi+" Between '"+qian+"' AND '"+hou+"'";
		}
		//and shijian Between '"+request.getParameter("shijian1")+"' AND '"+request.getParameter("shijian2")+"'"
		//查询的时间格式例如:2015-10-27 24:00:0
		List<T> list = new ArrayList<>();
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;

		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			T bean = null;
			while (rs.next()) {
				bean=clazz.newInstance();
				for(String it:strList)
				{
					Field fs=getDeclaredField(bean, it);
					if(fs==null){
						throw new IllegalArgumentException("Could not find field["+ 
								it+"] on target ["+bean+"]");
					}
					makeAccessiable(fs);
				    try{
				        fs.set(bean, rs.getObject(it));
				    }
				    catch(IllegalAccessException e){
				        System.out.println("不可能抛出的异常");
				    }
				}
				list.add(bean);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		
		return list;
	}
	//得到数据库中所有的表
	public List<String> getTableName(String database)
	{
		String sql="select table_name from information_schema.tables where table_schema='"+database+"'";
		List<String> list = new ArrayList<String>();
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		
		return list;
	}
	
	//得到表中所有列名
	public List<String> getTableColumn(String table)
	{
		String sql="select * from "+table;
		List<String> list = new ArrayList<String>();
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			ResultSetMetaData data = rs.getMetaData();
			int colnum=data.getColumnCount();
			for(int i=1;i<=colnum;i++)
				list.add(data.getColumnName(i));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		
		return list;
	}
	
	/**
	 * 创建数据库
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public boolean createTable(String table,String []info,String []type,Integer []size)
	{
		String sql = "CREATE TABLE "+table+"(";
		String lei[]=new String[] {"char","varchar"};
		int i=0;
		for(String it:info)
		{
			if(!it.equals(""))
			{
				boolean g_trit=false;
				for(String sit:lei)
				{
					if(type[i].toLowerCase().contains(sit.toLowerCase()))
					{
						g_trit=true;
					}
				}
				if(g_trit)
					sql += it+" "+type[i]+"("+size[i]+")";
				else sql += it+" "+type[i];
			}
			
			if(i!=info.length-1)
				sql+=",";
			++i;
		}
		sql+=")";
		//and shijian Between '"+request.getParameter("shijian1")+"' AND '"+request.getParameter("shijian2")+"'"
		//查询的时间格式例如:2015-10-27 24:00:0
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		int a=0;
		boolean f=false;
		try {
			state = conn.createStatement();
			a = state.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		if(a>0)
			f=true;
		return f;
	}
	
	
	/**
	 * 删除表格
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public boolean deleteTable(String table)
	{
		String sql = "drop table "+table;
		Connection conn = DBUtil.getConn();
		Statement state = null;
		boolean g_ok = true;
		
		try {
			state = conn.createStatement();
			g_ok = state.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(state, conn);
		}
		return !g_ok;


	}
			
	/**
	 * 全部数据
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("deprecation")
	public <T> List<T> list(String table,String []strList,Class<T> clazz){
		String sql = "select * from "+table;
		List<T> list = new ArrayList<>();
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			T bean = null;
			
			while (rs.next()) {
				bean=clazz.newInstance();
				for(String it:strList)
				{
					Field fs=getDeclaredField(bean, it);
					if(fs==null){
						throw new IllegalArgumentException("Could not find field["+ 
								it+"] on target ["+bean+"]");
					}
					makeAccessiable(fs);
				    try{
				        fs.set(bean, rs.getObject(it));
				    }
				    catch(IllegalAccessException e){
				        System.out.println("不可能抛出的异常");
				    }
				}
				
				list.add(bean);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		
		return list;
	}
	
	
	/**
	 * 全部数据
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("deprecation")
	public <T> List<T> list(String table,Class<T> clazz){
		StringHandle sh=new StringHandle();
		EntityToString ets=new EntityToString();
		String []strList=sh.StringListToStringNlist(ets.getNameList(clazz));
		
		String sql = "select * from "+table;
		List<T> list = new ArrayList<>();
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			T bean = null;
			
			while (rs.next()) {
				bean=clazz.newInstance();
				for(String it:strList)
				{
					Field fs=getDeclaredField(bean, it);
					if(fs==null){
						throw new IllegalArgumentException("Could not find field["+ 
								it+"] on target ["+bean+"]");
					}
					makeAccessiable(fs);
				    try{
				        fs.set(bean, rs.getObject(it));
				    }
				    catch(IllegalAccessException e){
				        System.out.println("不可能抛出的异常");
				    }
				}
				
				list.add(bean);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		
		return list;
	}
	
	
	//获取field属性，属性有可能在父类中继承 
	public static Field getDeclaredField(Object obj,String fieldName){
	    for (Class<?> clazz=obj.getClass(); clazz!=Object.class; clazz=clazz.getSuperclass()){
	        try{
	            return clazz.getDeclaredField(fieldName);
	        }
	        catch(Exception e){
	        }
	    }
	    return null;
	}
	//判断field的修饰符是否是public,并据此改变field的访问权限 
	public static void makeAccessiable(Field field){
	    if(!Modifier.isPublic(field.getModifiers())){
	        field.setAccessible(true);
	    }
	}
}

package mpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListTools {


	//subList：不改变原数组，但是改变其中值会改变原数组。end值(subList)是开区间
	//把一维数组当做循环数组，除去指定部分从以尾开始到头（保留from和end元素）
	/*
	 * 	type值
	 * 	1：不删除末尾元素
	 * 	2或其他：删除末尾元素
	 * 
	 */
	public <T> List<T> reList(List<T> objList,int from,int end,int type)
	{
		if(from<0||from>end||end>objList.size()-1)
			return null;
		List<T> objListResult=new ArrayList<T>();
		List<T> objListTemp=new ArrayList<T>(objList);
		List<T> objListTemp_1=new ArrayList<T>(objList);
		if(objList.size()==1)
			return objListTemp;
		if(type!=1)
			objListTemp.remove(objListTemp.size()-1);
		objListResult.addAll(objListTemp.subList(end, objListTemp.size()));
		objListResult.addAll(objListTemp_1.subList(0, from+1));
		return objListResult;
	}
	
	//删除数组中的重复元素
	public <T> void minList(List<T> objList)
	{
		Iterator<T> it = objList.iterator();
		Map<T,Integer> a=new HashMap<T,Integer>();
		while(it.hasNext())
		{
		    T x = it.next();
		    if(a.get(x)==null)
		    {
		        a.put(x, 1);
		    }
		    else
		    {
		    	it.remove();
		    }
		}
	}
	//获得List中第n个x元素的位置,如果没有第n个x元素的位置则返回-1
	public <T> int getListIndexBy(List<T> objList,T x,int n)
	{
		if(!objList.contains(x))
			return -1;
		Map<T,Integer> map=new HashMap<T,Integer>();
		int num=objList.size();
		for(int i=0;i<num;i++)
		{
			if(objList.get(i).equals(x))
			{
				if(map.get(x)==null)
					map.put(x, 1);
				else
				{
					map.put(x, map.get(x)+1);
				}
				if(map.get(x)==n)
					return i;
			}
		}
		return -1;
	}
	
	
	
	//根据双数组容器统计出对应的整型容器的大小
	public <T> List<Integer> getListListSize(List<List<T>> objlistlist)
	{
		List<Integer> listsize=new ArrayList<Integer>();
		for(int i=0;i<objlistlist.size();i++)
		{
			listsize.add(objlistlist.get(i).size());
		}
		return listsize;
	}
	
	//根据数组对前数组进行排序
	public <T> void sortFromList(List<T> list,List<Integer> array,boolean isMaxToMin)
	{
		int temp;
		T tTemp;
		int size =array.size();
		for(int i=1; i<size; i++) {
			for(int j=0; j<size-i; j++) {
				int jarray=array.get(j);
				int j1array=array.get(j+1);
				T jlist=list.get(j);
				T j1list=list.get(j+1);
				if(isMaxToMin)
				{
					if(jarray < j1array) {
						temp = array.get (j);
						array.set(j, j1array) ;
						array.set(j+1,temp);		
						tTemp=jlist;
						list.set(j,j1list);
						list.set(j+1,tTemp);
					}
				}
				else
				{
					if(jarray > j1array) {
						temp = jarray;
						array.set(j, j1array) ;
						array.set(j+1,temp);
						tTemp=jlist;
						list.set(j, j1list);
						list.set(j+1, tTemp);

					}

				}
			}
		}
	}
	
	//将T数组转换为T容器
	public <T> List<T> TNlistToTList(T []tlist)
	{
		List<T> list = java.util.Arrays.asList(tlist);
		return list;
	}

	
	//将Object容器转换为String容器
	public <T> List<T> ObjectListToTList(List<Object> objList,T it)
	{
		List<T> tList=new ArrayList<T>();
		for(int i=0;i<objList.size();i++)
		{
			tList.add((T)objList.get(i));
		}
		return tList;
	}
	
	//获得Map中的值的最大或最小的n个位置
	public List<Integer> getMtoM(List<Integer> listinteger,int count,boolean isMaxToMin)
	{
		List<Integer> numlist=new ArrayList<Integer>();
		List<Integer> clist=new ArrayList<Integer>();
		int g_size=listinteger.size();
		if(g_size==0||count<=0)
			return numlist;
		if(isMaxToMin)
		{
			for(int j=0;j<count;j++)
			{
				Integer point=null;
				int g_m=Integer.MIN_VALUE;
				for(int i=0;i<g_size;i++)
				{
					if(clist.contains(i))
						continue;
					int number=listinteger.get(i);
					if(number>g_m)
					{
						point=i;
						g_m=number;
					}
				}
				clist.add(point);
				numlist.add(g_m);
				
			}
		}
		else
		{
			for(int j=0;j<count;j++)
			{
				Integer point=null;
				int g_m=Integer.MAX_VALUE;
				for(int i=0;i<g_size;i++)
				{
					if(clist.contains(i))
						continue;
					int number=listinteger.get(i);
					if(number<g_m)
					{
						point=i;
						g_m=number;
					}
				}
				clist.add(point);
				numlist.add(g_m);
				
			}
		}
		return clist;
	}
	
	//获得Map中的键值List
	public <T> List<T> getMapTList(Map<T,Integer> maplist)
	{
		List<T> tList=new ArrayList<T>();
		Set<T> s = maplist.keySet();//获取KEY集合
		for (T t : s) 
		{
			tList.add(t);
		}
		return tList;
	}
	
	//获得Map中的值List
	public <T> List<Integer> getMapValueList(Map<T,Integer> maplist)
	{
		List<Integer> numList=new ArrayList<Integer>();
		Set<T> s = maplist.keySet();//获取KEY集合
		for (T str : s) 
		{
			numList.add(maplist.get(str));
		}
		return numList;
	}

}

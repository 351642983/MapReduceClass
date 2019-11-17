package mpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class MapReduceDriver3 {

	static ListTools lts=new ListTools();
	static Map<String,Integer> mps=new HashMap<String,Integer>();
	static List<Integer> info=new ArrayList<Integer>();
	public static class MyMapper extends Mapper<LongWritable, Text, Text/*map��Ӧ������*/, Text/*map��Ӧֵ����*/>
    {
         protected void map(LongWritable key, Text value,Context context)throws IOException, InterruptedException
         {
              String[] strNlist = value.toString().split(",");//��ηָ�
              //LongWritable��IntWritable,Text��
              context.write(new Text(strNlist[5]+","+strNlist[0])/*map��Ӧ������*/,new Text(strNlist[3])/*map��Ӧֵ����*/);
              //System.out.println(strNlist[0]);
              //context.write(new Text(strNlist[5])/*map��Ӧ������*/,new Text("time:"+strNlist[1]+",day:"+strNlist[2]+",traffic:"+strNlist[3]+",type:"+strNlist[4]+",id:"+strNlist[5])/*map��Ӧֵ����*/);
         }
    }
    public static class MyReducer extends Reducer<Text/*map��Ӧ������*/, Text/*map��Ӧֵ����*/, Text/*reduce��Ӧ������*/, Text/*reduce��Ӧֵ����*/>
    {
    	
         protected void reduce(Text key, Iterable<Text/*map��Ӧֵ����*/> values,Context context)throws IOException, InterruptedException
         {
        	 //int sum=0;
        	 String[] strNlist =key.toString().split(",");
        	 String ip=strNlist[1];
        	 System.out.println(ip);
        	 for (/*map��Ӧֵ����*/Text init : values)
             {
        		 
        		 Integer tra=Integer.parseInt(init.toString().replaceAll("\\s", ""));
        		 System.out.println(tra);
        		 //String[] strNlist = init.toString().split(",");
        		 if(mps.get( ip)!=null)
               		 mps.put( ip, (mps.get( ip)+tra));
               	 else mps.put( ip,1);
            	 //context.write( key/*reduce��Ӧ������*/, new Text(init)/*reduce��Ӧֵ����*/);
             }
        	 //context.write( key/*reduce��Ӧ������*/, new Text(sum+"")/*reduce��Ӧֵ����*/);
        	 //info.add(sum);
         }
    }
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		
		//���������еĲ����Զ����õ�����conf��
//		String[] otherArgs = new GenericOptionsParser(conf,args).getRemainingArgs();
//		if (otherArgs.length != 2) {
//			System.err.println("Usage: wordcount <in> <out>");
//			System.exit(2);
//		}
		
		Job job = Job.getInstance();
		//job.setJar("MapReduceDriver.jar");
		job.setJarByClass(MapReduceDriver3.class);
		// TODO: specify a mapper
		job.setMapperClass(MyMapper.class);
		job.setMapOutputKeyClass(/*map��Ӧ������*/Text.class);
        job.setMapOutputValueClass( /*map��Ӧֵ����*/Text.class);
		
		// TODO: specify a reducer
		job.setReducerClass(MyReducer.class);
		job.setOutputKeyClass(/*reduce��Ӧ������*/Text.class);
		job.setOutputValueClass(/*reduce��Ӧֵ����*/Text.class);

		// TODO: specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.57.128:9000/testhdfs1026/run/input/result.txt"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.57.128:9000/testhdfs1026/run/output/result2_2"));

		boolean flag = job.waitForCompletion(true);
		List<Integer> ln=lts.getMtoM(lts.getMapValueList(mps), 10, true);
		List<String> ls=lts.getMapTList(mps);
		//StringHandle sh=new StringHandle();
		ClassService cs=new ClassService();
		cs.createTable("data4", new String[] {"id","traffic"}, new String[] {"text","text"}, new Integer[] {});
		for(int i=0;i<ln.size();i++)
			cs.add("data4", new String[] {"id","traffic"} , new String[] {ls.get(ln.get(i)),""+mps.get(ls.get(ln.get(i)))});
		
		System.out.println("SUCCEED!"+flag);	//���������ʾ
		System.exit(flag ? 0 : 1);
		System.out.println();
	}

}

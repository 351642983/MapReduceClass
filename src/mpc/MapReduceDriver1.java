package mpc;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class MapReduceDriver1 {

	static ClassService service=new ClassService();
	public static class MyMapper extends Mapper<LongWritable, Text, Text/*map��Ӧ������*/, Text/*map��Ӧֵ����*/>
    {
         protected void map(LongWritable key, Text value,Context context)throws IOException, InterruptedException
         {
              String[] strNlist = value.toString().split(",");//��ηָ�
              //LongWritable��IntWritable,Text��
              
              context.write(new Text(strNlist[0])/*map��Ӧ������*/,new Text(strNlist[1]+","+strNlist[2]+","+strNlist[3]+","+strNlist[4]+","+strNlist[5])/*map��Ӧֵ����*/);
         }
    }
    public static class MyReducer extends Reducer<Text/*map��Ӧ������*/, Text/*map��Ӧֵ����*/, Text/*reduce��Ӧ������*/, Text/*reduce��Ӧֵ����*/>
    {
    	static No1Info info=new No1Info();
         protected void reduce(Text key, Iterable<Text/*map��Ӧֵ����*/> values,Context context)throws IOException, InterruptedException
         {
        	 for (/*map��Ӧֵ����*/Text init : values)
             {
        		 String[] strNlist = init.toString().split(",");
        		 info.setTime(strNlist[0]);
        		 info.setDay(strNlist[1]);
        		 info.setTraffic(strNlist[2]);
        		 info.setType(strNlist[3]);
        		 info.setId(strNlist[4]);
        		 service.add("data", info);
            	 context.write( key/*reduce��Ӧ������*/, new Text(init)/*reduce��Ӧֵ����*/);
             }
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
		job.setJarByClass(MapReduceDriver1.class);
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
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.57.128:9000/testhdfs1026/run/output/result1"));

		boolean flag = job.waitForCompletion(true);
		System.out.println("SUCCEED!"+flag);	//���������ʾ
		System.exit(flag ? 0 : 1);
		System.out.println();
	}

}

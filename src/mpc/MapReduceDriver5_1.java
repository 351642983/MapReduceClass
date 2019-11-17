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

public class MapReduceDriver5_1 {

	public static class MyMapper extends Mapper<LongWritable, Text, Text/*map对应键类型*/, Text/*map对应值类型*/>
    {
         protected void map(LongWritable key, Text value,Context context)throws IOException, InterruptedException
         {
              String[] strNlist = value.toString().split(",");//如何分隔
              //LongWritable，IntWritable,Text等
              
              context.write(new Text(strNlist[5])/*map对应键类型*/,new Text("1")/*map对应值类型*/);
         }
    }
    public static class MyReducer extends Reducer<Text/*map对应键类型*/, Text/*map对应值类型*/, Text/*reduce对应键类型*/, Text/*reduce对应值类型*/>
    {
         protected void reduce(Text key, Iterable<Text/*map对应值类型*/> values,Context context)throws IOException, InterruptedException
         {
        	 int sum=0;
             for (/*map对应值类型*/Text init : values)
             {
            	 sum++;
             }
           //LongWritable，IntWritable,Text等
             context.write( new Text(key)/*reduce对应键类型*/, new Text(sum+"")/*reduce对应值类型*/);
         }
    }
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		
		//将命令行中的参数自动设置到变量conf中
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
		job.setMapOutputKeyClass(/*map对应键类型*/Text.class);
        job.setMapOutputValueClass( /*map对应值类型*/Text.class);
		
		// TODO: specify a reducer
		job.setReducerClass(MyReducer.class);
		job.setOutputKeyClass(/*reduce对应键类型*/Text.class);
		job.setOutputValueClass(/*reduce对应值类型*/Text.class);

		// TODO: specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.57.128:9000/testhdfs1026/run/input/result.txt"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.57.128:9000/testhdfs1026/run/output/result5_1"));

		boolean flag = job.waitForCompletion(true);
		System.out.println("SUCCEED!"+flag);	//任务完成提示
		System.exit(flag ? 0 : 1);
		System.out.println();
	}

}

package mpc;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MapReduceDriver5 {
	public static class IntPair implements WritableComparable<IntPair> 
	{ 
		int first; 
		//int second; 

		public void set(int left/*, int right*/) 
		{ 
			first = left; 
			//second = right; 
		} 
		public int getFirst() 
		{ 
			return first; 
		} 
//		public int getSecond() 
//		{ 
//			return second; 
//		} 
		@Override 

		public void readFields(DataInput in) throws IOException 
		{ 
			// TODO Auto-generated method stub 
			first = in.readInt(); 
			//second = in.readInt(); 
		} 
		@Override 

		public void write(DataOutput out) throws IOException 
		{ 
			// TODO Auto-generated method stub 
			out.writeInt(first); 
			//out.writeInt(second); 
		} 
		@Override 

		public int compareTo(IntPair o) 
		{ 
			// TODO Auto-generated method stub 
			if (first != o.first) 
			{ 
				//�Ӵ�С����
				return first < o.first ? 1 : -1; 
			} 
//			else if (second != o.second) 
//			{ 
//				//��С��������
//				return second > o.second ? 1 : -1; 
//			} 
			else 
			{ 
				return 0; 
			} 
		} 
		@Override 
		public int hashCode() 
		{ 
			return first * 157 /*+ second*/; 
		} 
		@Override 
		public boolean equals(Object right) 
		{ 
			if (right == null) 
				return false; 
			if (this == right) 
				return true; 
			if (right instanceof IntPair) 
			{ 
				IntPair r = (IntPair) right; 
				return r.first == first/* && r.second == second*/; 
			} 
			else 
			{ 
				return false; 
			} 
		} 
	} 

	public static class FirstPartitioner extends Partitioner<IntPair, IntWritable> 
	{ 
		@Override 
		public int getPartition(IntPair key, IntWritable value,int numPartitions) 
		{ 
			return Math.abs(key.getFirst() * 127) % numPartitions; 
		} 
	} 
	public static class GroupingComparator extends WritableComparator 
	{ 
		protected GroupingComparator() 
		{ 
			super(IntPair.class, true); 
		} 
		@Override 
		//Compare two WritableComparables. 
		public int compare(WritableComparable w1, WritableComparable w2) 
		{ 
			IntPair ip1 = (IntPair) w1; 
			IntPair ip2 = (IntPair) w2; 
			int l = ip1.getFirst(); 
			int r = ip2.getFirst(); 
			return l == r ? 0 : (l < r ? -1 : 1); 
		} 
	} 


	public static class MyMapper extends Mapper<LongWritable, Text, IntPair/*map��Ӧ������*/, IntWritable/*map��Ӧֵ����*/>
	{
		private final IntPair intkey = new IntPair(); 
		private final IntWritable intvalue = new IntWritable(); 
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException 
		{ 
			
             //LongWritable��IntWritable,Text��
             //context.write(new Text(strNlist[4]+":"+strNlist[5]+","+strNlist[0])/*map��Ӧ������*/,new Text(strNlist[4])/*map��Ӧֵ����*/);
             //context.write(new Text(strNlist[5])/*map��Ӧ������*/,new Text("time:"+strNlist[1]+",day:"+strNlist[2]+",traffic:"+strNlist[3]+",type:"+strNlist[4]+",id:"+strNlist[5])/*map��Ӧֵ����*/);
             
			String line = value.toString(); 
			String[] strNlist = value.toString().split("\t");//��ηָ�
			//StringTokenizer tokenizer = new StringTokenizer(line); 
			int left = Integer.parseInt(strNlist[0]); 
			int right = Integer.parseInt(strNlist[1]); 
//			if (tokenizer.hasMoreTokens()) 
//			{ 
//				left = Integer.parseInt(tokenizer.nextToken()); 
//				if (tokenizer.hasMoreTokens()) 
//					right = Integer.parseInt(tokenizer.nextToken()); 
				intkey.set(right/*, left*/); 
				intvalue.set(left); 
				//�����Ǹ���map��key��������ģ����Ǹ���reduce�ģ�����
				context.write(intkey, intvalue); 
//			} 
		} 
	}
	
	public static class MyReducer extends Reducer<IntPair/*map��Ӧ������*/, IntWritable/*map��Ӧֵ����*/, Text/*reduce��Ӧ������*/, IntWritable/*reduce��Ӧֵ����*/>
	{
		private final Text left = new Text(); 
		private static final Text SEPARATOR = new Text("------------------------------------------------"); 

		public void reduce(IntPair key, Iterable<IntWritable> values,Context context) throws IOException, InterruptedException 
		{ 
			//context.write(SEPARATOR, null); 
			left.set(Integer.toString(key.getFirst())); 
			System.out.println(left); 
			for (IntWritable val : values) 
			{ 
				context.write(left, val); 
				//System.out.println(val); 
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

		Job job = Job.getInstance(conf, "JobName");
		//job.setJar("MapReduceDriver.jar");
		job.setJarByClass(MapReduceDriver5.class);
		// TODO: specify a mapper
		job.setMapperClass(MyMapper.class);
		job.setMapOutputKeyClass(/*map��Ӧ������*/IntPair.class);
		job.setMapOutputValueClass( /*map��Ӧֵ����*/IntWritable.class);

		// TODO: specify a reducer
		job.setReducerClass(MyReducer.class);
		job.setOutputKeyClass(/*reduce��Ӧ������*/Text.class);
		job.setOutputValueClass(/*reduce��Ӧֵ����*/IntWritable.class);

		// TODO: specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.57.128:9000/testhdfs1026/run/input/part5_1.txt"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.57.128:9000/testhdfs1026/run/output/result5_1_1"));

		boolean flag = job.waitForCompletion(true);
		System.out.println("SUCCEED!"+flag);	//���������ʾ
		System.exit(flag ? 0 : 1);
		System.out.println();
	}

}

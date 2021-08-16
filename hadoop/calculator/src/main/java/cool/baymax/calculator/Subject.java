package cool.baymax.calculator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class Subject {

    public static class PersonMapper extends Mapper<Object, Text, Text, Text> {
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] line = value.toString().split(",");
            context.write(new Text(line[1]), new Text(String.format("1,%s", line[2])));
        }
    }

    public static class AverageCombiner extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            int count = 0;
            int total = 0;
            for (Text value : values) {
                String[] line = value.toString().split(",");
                count += Integer.parseInt(line[0]);
                total += Integer.parseInt(line[1]);
            }
            context.write(key, new Text(String.format("%d,%d", count, total)));
        }
    }

    public static class AverageReducer extends Reducer<Text, Text, Text, DoubleWritable> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            int count = 0;
            int total = 0;
            for (Text value : values) {
                String[] line = value.toString().split(",");
                count += Integer.parseInt(line[0]);
                total += Integer.parseInt(line[1]);
            }
            context.write(key, new DoubleWritable(total * 1.0 / count));
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "subject average");
        job.setJarByClass(Subject.class);
        job.setMapperClass(Subject.PersonMapper.class);
        job.setCombinerClass(Subject.AverageCombiner.class);
        job.setReducerClass(Subject.AverageReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

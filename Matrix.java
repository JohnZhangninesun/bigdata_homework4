import java.io.IOException;
 
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;


public class Matrix{
	public static class MatrixMapper extends Mapper<LongWritable, Text, Text, Text> {
 
    private int columnN = 0;
    private int rowM = 0;
    private Text mapKey = new Text();
    private Text mapValue = new Text();
 
    protected void setup(Context context) throws IOException, InterruptedException {
        Configuration conf = context.getConfiguration();
        columnN = Integer.parseInt(conf.get("columnN"));
        rowM = Integer.parseInt(conf.get("rowM"));
    };
 
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        FileSplit file = (FileSplit) context.getInputSplit();
        String fileName = file.getPath().getName();
        String line = value.toString();
        String[] tuple = line.split(",");
        if (tuple.length != 2) {
            throw new RuntimeException("MatrixMapper tuple error");
        }
        int row = Integer.parseInt(tuple[0]);
        String[] tuples = tuple[1].split("\t");
        if (tuples.length != 2) {
            throw new RuntimeException("MatrixMapper tuples error");
        }
        if (fileName.contains("M")) {
            matrixM(row, Integer.parseInt(tuples[0]), Integer.parseInt(tuples[1]), context);
        } else {
            matrixN(row, Integer.parseInt(tuples[0]), Integer.parseInt(tuples[1]), context);
        }
 
    };
 
    private void matrixM(int row, int column, int value, Context context) throws IOException, InterruptedException {
        for (int i = 1; i < columnN + 1; i++) {
            mapKey.set(row + "," + i);
            mapValue.set("M," + column + "," + value);
            context.write(mapKey, mapValue);
        }
    }
 
    private void matrixN(int row, int column, int value, Context context) throws IOException, InterruptedException {
        for (int i = 1; i < rowM + 1; i++) {
            mapKey.set(i + "," + column);
            mapValue.set("N," + row + "," + value);
            context.write(mapKey, mapValue);
        }
    }
    }
	public class MatrixReducer extends Reducer<Text, Text, Text, Text> {
 
    private int columnM = 0;
 
    protected void setup(Context context) throws IOException, InterruptedException {
        Configuration conf = context.getConfiguration();
        columnM = Integer.parseInt(conf.get("columnM"));
    };
 
    protected static void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        int[] m = new int[columnM + 1];
        int[] n = new int[columnM + 1];
        for (Text val : values) {
            String[] tuple = val.toString().split(",");
            if (tuple.length != 3) {
                throw new RuntimeException("MatrixReducer tuple error");
            }
            if ("M".equals(tuple[0])) {
                m[Integer.parseInt(tuple[1])] = Integer.parseInt(tuple[2]);
            } else {
                n[Integer.parseInt(tuple[1])] = Integer.parseInt(tuple[2]);
            }
        }
        for (int i = 1; i < columnM + 1; i++) {
            sum += m[i] * n[i];
        }
        context.write(key, new Text(sum + ""));
    };
    }
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args == null || args.length != 5) {
            throw new RuntimeException("请输入输入路径、输出路径、矩阵M的行数、矩阵M的列数、矩阵N的列数");
        }
        Configuration conf = new Configuration();
        conf.set("rowM", args[2]);
        conf.set("columnM", args[3]);
        conf.set("columnN", args[4]);
        Job job = Job.getInstance(conf);
        job.setJobName("Matrix");
        job.setJarByClass(Matrix.class);
        job.setMapperClass(MatrixMapper.class);
        job.setReducerClass(MatrixReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPaths(job, args[0]);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

package com.mr.handle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.LazyOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

/**
 * Created with Kaylina
 * Time: 2017/5/24 14:33
 * Description: mr框架解析日志
 * <KEYIN, VALUEIN, KEYOUT, VALUEOUT>
 */
public class LogHandle {

    private static final String PAGEVIEW_FILE = "pageview";
    private static final String CUSEVENT_FILE = "cusevent";
    private static final String EVENT_FILE = "event";
    private static final String HEATMAP_FILE = "heatmap";
    private static final String OTHER_FILE = "other";
    private static final String INPUT_FILE = "master.hadoop.node:9000";

    public static final Log log = LogFactory.getLog("MyLog");

    public static class HandleMapper extends Mapper<Object, Text, Text, Text> {

        private MultipleOutputs<Text, Text> mos;
        private Text word = new Text();

        @Override
        public void setup(Context context) throws IOException, InterruptedException {
            mos = new MultipleOutputs(context);
        }

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();
            try {
                if (line != null) {
                    if (line.contains("t=heatmap")) {
                        word.set(line);
                        mos.write(word, new Text(), HEATMAP_FILE);
                    } else if (line.contains("t=pageview")) {
                        word.set(line);
                        mos.write(word, new Text(), PAGEVIEW_FILE);
                    } else if (line.contains("t=event") && line.contains("&caid=")) {
                        word.set(line);
                        mos.write(word, new Text(), CUSEVENT_FILE);
                    } else if (line.contains("t=event") && !line.contains("&caid=")) {
                        word.set(line);
                        mos.write(word, new Text(), EVENT_FILE);
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }

        @Override
        public void cleanup(Context context) throws IOException, InterruptedException {
            mos.close();
        }
    }


    static class HandleReducer extends Reducer<Text, Text, Text, Text> {

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            context.write(key, null);

        }
    }

    static class PageViewMapper extends Mapper<Object, Text, Text, Text> {

        private MultipleOutputs<Text, Text> mos;
        private Text word = new Text();

        @Override
        public void setup(Context context) throws IOException, InterruptedException {
            mos = new MultipleOutputs(context);
        }

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();
            if (!PubMethod.isEmpty(line)) {
                try {
                    PageView pv = PageView.analyzeLog(line);
                    if (!PubMethod.isEmpty(pv)) {
                        word.set(pv.toString());
                        mos.write(word, new Text(), PAGEVIEW_FILE);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    word.set(line);
                    mos.write(word, new Text(), OTHER_FILE);
                }
            }
        }

        @Override
        public void cleanup(Context context) throws IOException, InterruptedException {
            mos.close();
        }
    }

    static class EventMapper extends Mapper<Object, Text, Text, Text> {

        private MultipleOutputs<Text, Text> mos;
        private Text word = new Text();

        @Override
        public void setup(Context context) throws IOException, InterruptedException {
            mos = new MultipleOutputs(context);
        }

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();
            if (!PubMethod.isEmpty(line)) {
                try {
                    Event e = Event.analyzeLog(line);
                    if (!PubMethod.isEmpty(e) && !PubMethod.isEmpty(e.getPageView())) {

                        word.set(e.toString());
                        mos.write(word, new Text(), EVENT_FILE);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    word.set(line);
                    mos.write(word, new Text(), OTHER_FILE);
                }
            }
        }

        @Override
        public void cleanup(Context context) throws IOException, InterruptedException {
            mos.close();
        }

    }

    static class CusEventMapper extends Mapper<Object, Text, Text, Text> {

        private MultipleOutputs<Text, Text> mos;
        private Text word = new Text();

        @Override
        public void setup(Context context) throws IOException, InterruptedException {
            mos = new MultipleOutputs(context);
        }

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();
            if (!PubMethod.isEmpty(line)) {
                try {
                    CusEvent ce = CusEvent.analyzeLog(line);
                    if (!PubMethod.isEmpty(ce) && !PubMethod.isEmpty(ce.getPageView())) {

                        word.set(ce.toString());
                        mos.write(word, new Text(), CUSEVENT_FILE);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    word.set(line);
                    mos.write(word, new Text(), OTHER_FILE);
                }
            }
        }

        @Override
        public void cleanup(Context context) throws IOException, InterruptedException {
            mos.close();
        }

    }

    static class HeatMapMapper extends Mapper<Object, Text, Text, Text> {

        private MultipleOutputs<Text, Text> mos;
        private Text word = new Text();

        @Override
        public void setup(Context context) throws IOException, InterruptedException {
            mos = new MultipleOutputs(context);
        }

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();
            if (!PubMethod.isEmpty(line)) {
                try {
                    HeatMap hm = HeatMap.analyzeLog(line);
                    if (!PubMethod.isEmpty(hm) && !PubMethod.isEmpty(hm.getPageView())) {
                        word.set(hm.toString());
                        mos.write(word, new Text(), HEATMAP_FILE);
                    }

                } catch (Exception e) {
                    log.error(e.getMessage());
                    word.set(line);
                    mos.write(word, new Text(), OTHER_FILE);
                }
            }
        }

        @Override
        public void cleanup(Context context) throws IOException, InterruptedException {
            mos.close();
        }
    }

    static class MyReducer extends Reducer<Text, Text, Text, Text> {

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            context.write(key, null);

        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        Configuration conf = new Configuration();
        //String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs(); //hadoop命令行
        String input = INPUT_FILE + args[0];
        String output = INPUT_FILE + args[1];
        if (args.length != 2) {
            System.exit(2); //不是两个参数就退出
        }
        Job job1 = new Job(conf, "loghandle");
        job1.setJarByClass(LogHandle.class);
        job1.setMapperClass(HandleMapper.class);
        job1.setCombinerClass(HandleReducer.class);
        job1.setReducerClass(HandleReducer.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job1, new Path(input));
        FileOutputFormat.setOutputPath(job1, new Path(output));
        LazyOutputFormat.setOutputFormatClass(job1, TextOutputFormat.class);
        job1.setNumReduceTasks(12);

        //判断源文件是否存在
        FileSystem fs = FileSystem.get(conf);
        Path outPath1 = new Path(output);
        if (fs.exists(outPath1)) {
            fs.delete(outPath1, true);
        }

        Job job2 = new Job(conf, "logHandle");
        job2.setJarByClass(LogHandle.class);
        job2.setReducerClass(MyReducer.class);
        job2.setCombinerClass(MyReducer.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(Text.class);
        MultipleInputs.addInputPath(job2, new Path(output + "pageview*"), TextInputFormat.class, PageViewMapper.class);
        MultipleInputs.addInputPath(job2, new Path(output + "event*"), TextInputFormat.class, EventMapper.class);
        MultipleInputs.addInputPath(job2, new Path(output + "cusevent*"), TextInputFormat.class, CusEventMapper.class);
        MultipleInputs.addInputPath(job2, new Path(output + "heatmap*"), TextInputFormat.class, HeatMapMapper.class);
        FileOutputFormat.setOutputPath(job2, new Path(output + "output"));
        LazyOutputFormat.setOutputFormatClass(job2, TextOutputFormat.class);
        job2.setNumReduceTasks(12);

        Path outPath2 = new Path(output + "output");
        if (fs.exists(outPath2)) {
            fs.delete(outPath2, true);
        }

        if (job1.waitForCompletion(true)) {
            System.exit(job2.waitForCompletion(true) ? 0 : 1);
        }
    }
}
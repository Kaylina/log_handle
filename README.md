## 该项目主要是用 mapreduce实现日志清洗的ETL流程，运行环境为hadoop2.7.3，jdk1.8；
#### 输入的文本格式为4个文件，对应4种格式的日志，根据所需字段分为: event、cusevent、heatmap、pageview;
#### 项目把4种格式的日志用4个map分开处理，输出结果为包括other在内的5种日志，异常日志写出到other文件下，其余的按类别输出；
#### Test为本地测试类。
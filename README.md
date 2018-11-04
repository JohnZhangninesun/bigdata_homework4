# bigdata_homework4
2018-2019年第一学期 金融大数据作业4
## wordcount2.0
### 1.配置环境变量：
* vim /etc/profile  
```
export JAVA_HOME=/usr/java/default  
export PATH=${JAVA_HOME}/bin:${PATH}  
export HADOOP_CLASSPATH=${JAVA_HOME}/lib/tools.jar  
```
### 2.编译 WordCount.java:  
* bin/hadoop com.sun.tools.javac.Main WordCount.java  
* jar cf wc.jar WordCount*.class  
### 3.上传文本I_Hava_a_Dream.txt:  
* bin/hadoop fs -put I_Hava_a_Dream.txt /user/zx/wordcount/input  
### 4.运行程序,查看结果：  
* bin/hadoop jar wc.jar WordCount /user/zx/wordcount/input /user/zx/wordcount/output  
* bin/hadoop fs -cat /user/joe/wordcount/output/part-r-00000  
![](https://github.com/JohnZhangninesun/bigdata_homework4/blob/master/screenshot/wordcount.PNG)
### 5.编辑patterns.txt，重新运行
* vim patterns.txt  
* bin/hadoop jar wc.jar WordCount2 -Dwordcount.case.sensitive=false /user/zx/wordcount/input /user/zx/wordcount/output -skip /user/zx/wordcount/patterns.txt  
* bin/hadoop fs -cat /user/joe/wordcount/output/part-r-00000    
![](https://github.com/JohnZhangninesun/bigdata_homework4/blob/master/screenshot/patterns.PNG)  
![](https://github.com/JohnZhangninesun/bigdata_homework4/blob/master/screenshot/wordcount2.PNG)

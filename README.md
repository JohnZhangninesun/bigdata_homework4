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
## 矩阵乘法mapreduce实现：  
### 1.编译 Matrix.java:  
* bin/hadoop com.sun.tools.javac.Main Matrix.java  
* jar cf mr.jar Matrix*.class  
### 2.上传M_10_15和N_15_20文件  
* bin/hadoop fs -put M_10_15 /user/zx/matrix/input  
* bin/hadoop fs -put N_15_20 /user/zx/matrix/input  
### 3.运行程序,查看结果：  
* bin/hadoop jar mr.jar Matrix /user/zx/matrix/input /user/zx/matrix/output  
* bin/hadoop fs -cat /user/zx/matrix/output/part-r-00000  
![](https://github.com/JohnZhangninesun/bigdata_homework4/blob/master/screenshot/matrix1.PNG)
![](https://github.com/JohnZhangninesun/bigdata_homework4/blob/master/screenshot/matrix2.PNG)
## 关系代数mapreduce实现：  
### 1.在Ra.txt上对属性name进行投影：
* bin/hadoop jar pjt.jar projection /user/zx/relation/input /user/zx/relation/output
* bin/hadoop fs -cat /user/zx/relation/output/part-m-00000  
![](https://github.com/JohnZhangninesun/bigdata_homework4/blob/master/screenshot/projection0.PNG)
![](https://github.com/JohnZhangninesun/bigdata_homework4/blob/master/screenshot/projection.PNG)
### 2. 在Ra.txt上选择age=18的记录；在Ra.txt上选择age>18的记录：
![](https://github.com/JohnZhangninesun/bigdata_homework4/blob/master/screenshot/relation.PNG)
![](https://github.com/JohnZhangninesun/bigdata_homework4/blob/master/screenshot/relation2.PNG)
### 3. 求Ra1和Ra2的并集：
![](https://github.com/JohnZhangninesun/bigdata_homework4/blob/master/screenshot/union.PNG)  
### 4. 求Ra1和Ra2的交集：
![](https://github.com/JohnZhangninesun/bigdata_homework4/blob/master/screenshot/Intersection.PNG)
### 5. 求Ra1-Ra2：
![](https://github.com/JohnZhangninesun/bigdata_homework4/blob/master/screenshot/difference.PNG)
### 6: Ra和Rb在属性id上进行自然连接：
![](https://github.com/JohnZhangninesun/bigdata_homework4/blob/master/screenshot/nujoin.PNG)

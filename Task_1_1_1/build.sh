javac -d ./build/classes/ ./lib/src/main/java/sys/pro/*.java 
java -classpath ./build/classes/ sys.pro.HeapSort
javadoc -quiet -d ./docs -sourcepath ./lib/src/main/java sys.pro

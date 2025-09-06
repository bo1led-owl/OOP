javadoc -quiet -d ./docs -sourcepath ./lib/src/main/java sys.pro
javac -d ./build/classes/ ./lib/src/main/java/sys/pro/*.java
jar -c -f ./build/HeapSort.jar -e sys.pro.HeapSort -C ./build/classes .
java -jar ./build/HeapSort.jar

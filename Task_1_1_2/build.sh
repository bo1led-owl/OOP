javac -d ./build/classes/ ./src/main/java/sys/pro/*.java ./src/main/java/sys/pro/*/*.java
jar -c -f ./build/Blackjack.jar -e sys.pro.Game -C ./build/classes .
java -jar ./build/Blackjack.jar

echo "compiling to .out and executing"
javac -d ./out src/uk/ac/cam/cl/gfxintro/ym346/tick1/*.java
java -classpath ./out uk.ac.cam.cl.gfxintro.ym346.tick1.Tick1 --input tick1.xml --output output.png --bounces 8


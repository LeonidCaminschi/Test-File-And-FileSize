In order to run this in Linux i used the following commands
```
javac Main.java ls.java
jar cvmf META-INF/MANIFEST.MF app.jar Main.class ls.class
java -jar app.jar "<directory>"
```
In case any changes have been dome be sure to check in with the current written MANIFEST
Also for convinience sake for Linux was made a bash script script.bs and inside it
```
#!/bin/bash

javac Main.java ls.java
jar cvmf META-INF/MANIFEST.MF app.jar Main.class ls.class
java -jar app.jar "/home/leonidas"
```
to run it just type
```
./script.bs
```
and all the steps will be executed automatically.

P.S. I used the "B" because the .size() function from Path returns data in bytes and not in bits.

javac -d out src/code/*.java
jar cvfm Asteroids.jar manifest.txt -C out . -C src/Fonts . -C src/SoundEffects .
rm -r out

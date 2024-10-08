# JFrameAsteroids
Copy of the game Asteroids in the uselessy complicated JFrame library.
#### Requirements
- JDK > 8.0
- A functioning brain
#### How to run
```bash
git clone https://github.com/fantasyAlgo/JFrameAsteroids.git
```
**There is now the .jar file!, now you just need to double click the Asteroids.jar file**<br>
If you want instead to compile it yourself, then if you're linux/macos you can either use the ./deploy.sh file, or by typing:
```bash
javac -d out src/code/*.java
jar cvfm Asteroids.jar manifest.txt -C out . -C src/Fonts . -C src/SoundEffects .
rm -r out
```
On windows instead you can compile using this
```bash
javac -d out src/code/*.java
jar cvfm Asteroids.jar manifest.txt -C out . -C src/Fonts . -C src/SoundEffects .
rmdir /s /q out
```
Then you can run the .jar on the terminal/CMD using
```bash
java -jar Asteroids.jar
```

### Features
- This game uses no textures, it is all procedurally generated (starting from basic vertices).
- Because it is procedurally generated, the asteroids are all different
- You can play it using the arrow keys to go up, rotating to the left and rotating to the right (you can change this into wasd), you can shoot using the space key (or k)
- The goal of this game is to have the highest score (the bigger the asteroids is, the higher the reward). Good luck! 


### TODO:
- [ ] Refactor the code in a "java like" way
- [x] Particle system for exploding asteroids
- [x] Death animation for player
- [x] Animation for when player is moving
- [x] Sound effects and background music
- [x] Some UI/GUI
- [ ] Fullscreen * I tried implementing it, but for some reason was really buggy and unplayable, so i'll save it for future (me)s
- [x] Alien spaceship AI
- [x] Sound music * Half done, the Clip class is really bad for this sort of things, so i need to rewrite the whole sound stuff using SourceDataLine.
- [x] Ability to change the keybindings
- [ ] Dunno, i'm open to suggestions

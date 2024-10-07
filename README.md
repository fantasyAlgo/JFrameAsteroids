# JFrameAsteroids
Copy of the game Asteroids in the uselessy complicated JFrame library.
#### Requirements
- JDK
- A functioning brain
#### How to run
```bash
git clone https://github.com/fantasyAlgo/JFrameAsteroids.git
```
Now, if you're on linux/macos, then you just need to run the run.sh script, if you're on windows, then you can compile and run using the following:
```bash
javac *.java
java Main
```
### Features
- This game uses no textures, it is all procedurally generated (starting from basic vertices).
- Because it is procedurally generated, the asteroids are all different
- You can play it using the arrow keys to go up, rotating to the left and rotating to the right, you can shoot using the space key
- The goal of this game is to have the highest score, so good luck sigmas of github!



### TODO:
- [ ] Refactor the code in a "java like" way
- [x] Particle system for exploding asteroids
- [x] Death animation for player
- [x] Animation for when player is moving
- [x] Sound effects and background music
- [x] Some UI/GUI * Option button is still not implemented
- [ ] Fullscreen * I tried implementing it, but for some reason was really buggy and unplayable, so i'll save it for future (me)s
- [x] Alien spaceship AI
- [x] Sound music * Half done, the Clip class is really bad for this sort of things, so i need to rewrite the whole sound stuff using SourceDataLine.
- [ ] Ability to change the keybindings
- [ ] Dunno, i'm open to suggestions

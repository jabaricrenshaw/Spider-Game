# Spider-Game
A game made in Java using CodenameOne

# Starting the Game
1. Run the game by executing the A3Prj.jar JAR file with this command:

Mac/Linux:<br>
java -classpath JavaSE.jar:dist/A3Prj.jar com.codename1.impl.javase.Simulator com.mycompany.a3
Windows:<br>
java -classpath JavaSE.jar;dist\A3Prj.jar com.codename1.impl.javase.Simulator com.mycompany.a3

2. Once the game window appears, from the top toolbar navigate to:<br>
Skins -> More..., the find and download the Ipad III skin.

3. Then, from the top toolbar navigate to:<br>
Simulator -> Uncheck single window mode.

# Playing the Game
Icons
1. Your Player object in the game window appears as a pink circle. You control the movement of the Player.
2. Flags are the yellow triangles. Flags are ordered by the number in the center of each.
3. Food Stations are the blue boxes. The food level at each FoodStation is identified by the number in the center of each
4. Spiders are unfilled white triangles. Spiders move randomly across the game window.

Buttons
1. The "Accelerate" button speeds up your player.
2. The "Brake" button slows down your player.
3. The "Turn Left" and "Turn Right" buttons slowly adjust the angle at which your player moves.
4. The "Position" button allow you to rearrange Food Stations and Flags in the game window. Only functional when the game is "Paused"
5. The "Pause/Play" button pauses the game or resumes if it is "Paused".

# Help Section
Why doesn't the game continue upon starting?
The game may be experiencing a bug where the object do not move unless you click the Pause/Play button. Try clicking the button repeatedly until you get a message or the objects begin to move.

Available Keybindings for commands:
a -> Accelerate (Increases the speed of the Ant until maximum speed.)
b -> Brake (Reduces the speed of the Ant until minimum speed.)
l -> Turn Left
r -> Turn Right

How do I use the "Position" button?
1. Make sure the game is "Paused" by clicking the "Pause" button.
2. Select a Flag or FoodStation that you would like to move. It is selected when the object becomes unfilled.
3. Click the "Position" button.
4. Click somewhere in the game window to move the selected object.

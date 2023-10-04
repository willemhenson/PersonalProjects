######################### SETUP #########################

from time import time, sleep # import only relevant objects and methods to avoid namespace errors
from random import randint, choice
from tkinter import Tk, Canvas, Label, Button, PhotoImage, Entry, StringVar, NW, SW

__doc__ = """
--------------------
Welcome to Run the Gauntlet! A Python Tkinter Game By Will Henson.
COMP16321 Coursework 2
Game Resolution : 1280x720
How To Play : Jump Through the Gates To Earn Score!
(Please See Credits File In Program Directory)
--------------------
"""

windowSize = [1280, 720]  # should NOT be altered - constant

#list of all cheat codes:
#1.'noclip' - turns off collision to gates
#2.'flappy' - turns game into flappy bird inspired gameplay
#3.'score' - adds 1 to score
#4.'wide' - turns all gaps wide
#5.'slow' - slows down scroll rate
#6.'ghost' - adds ghost sprite that chases you!


print(__doc__)  # welcome message
input("Please Press <Enter> To Start Game")
print("Loading...")
root = Tk()  # initialise tk
root.resizable(False, False)  # cannot resize window
window = Canvas(
    root, width=windowSize[0], height=windowSize[1], bg="black", highlightthickness=0
)
window.pack()
backgroundImages = {
    "start": PhotoImage(file="images/start_image.png"),
    "grey": PhotoImage(file="images/grey_image.png"),
    "green": PhotoImage(file="images/green_image.png"),
    "blue": PhotoImage(file="images/blue_image.png"),
    "purple": PhotoImage(file="images/purple_image.png"),
    "gold": PhotoImage(file="images/gold_image.png"),
}  # loads all necessary .png files from image folder
bossImage = PhotoImage(file="images/boss.png")
ghostImage = PhotoImage(file="images/ghost.png")

root.bind("<Escape>", lambda a: toggleMenu())  # key bindings necessary for the game
root.bind("<Right>", lambda a: bossKey())
root.bind("<Up>", lambda a: jump())
root.bind("<Down>", lambda a: drop())


class GlobalVariables:  # creates a global class that can be used to store global variables
    pass


globalVariables = GlobalVariables()  # initialise alll global variables
globalVariables.userName = "anon"
globalVariables.score = 0
globalVariables.level = 1
globalVariables.play = False
globalVariables.alive = False
globalVariables.quit = False
globalVariables.boss = False
globalVariables.log = "navigate the gauntlet to earn score!"
globalVariables.acc = 0
globalVariables.pipeCheat = False
globalVariables.noclip = False
globalVariables.scrollCheat = False
globalVariables.font = "Courier"
globalVariables.ghost = False
globalVariables.vel = 0
globalVariables.overrule = None
globalVariables.entry1 = (
    StringVar()
)  # sets up tk stringvar() for getting entries' values
globalVariables.entry2 = StringVar()
globalVariables.backgrounds = [
    window.create_image(0, 0, image=backgroundImages["start"], anchor=NW),
    window.create_image(windowSize[0], 0, image=backgroundImages["start"], anchor=NW),
]  # creates the two scrolling backdrops

######################### GAME CLASS OBJECTS #########################


class UI:  # class of widgets that will be permanently shown (HUD)
    items = []

    def __init__(
        self, pos0=[0, 0], text0="", show0=True, font0=(globalVariables.font, 16)
    ):
        UI.items.append(self)
        super().__init__()
        self._tk = Label(
            root, text=text0, font=font0, bg="gray"
        )  # assigns tk widget to object created
        self._pos = pos0
        if show0:
            self.show()

    def show(self):
        self._tk.place(x=self._pos[0], y=self._pos[1])

    def hide(self):
        self._tk.place_forget()


class MenuButton:  # class of menu buttons, grouping them will allow easy way to show and hide menu
    def __init__(
        self,
        parent,
        pos0=[0, 0],
        text0="button",
        show0=True,
        command0=None,
        font0=(globalVariables.font, 16),
    ):
        parent._items.append(self)
        self._tk = Button(
            root,
            cursor="hand2",
            text=text0,
            command=command0,
            font=font0,
            bg="gray",
            activebackground="black",
            activeforeground="white",
        )
        self._pos = pos0
        if show0:
            self.show()

    def show(self):
        self._tk.place(x=self._pos[0], y=self._pos[1])

    def hide(self):
        self._tk.place_forget()


class MenuLabel:  # similar to 'MenuButton' but with text-based label widgets
    def __init__(
        self,
        parent,
        pos0=[0, 0],
        text0="label",
        show0=True,
        font0=(globalVariables.font, 16),
    ):
        parent._items.append(self)
        self._tk = Label(root, text=text0, font=font0, bg="gray", highlightthickness=2)
        self._pos = pos0
        if show0:
            self.show()

    def show(self):
        self._tk.place(x=self._pos[0], y=self._pos[1])

    def hide(self):
        self._tk.place_forget()


class MenuInput:  # input boxes for codes and name
    def __init__(
        self,
        parent,
        pos0=[0, 0],
        show0=True,
        font0=(globalVariables.font, 16),
        textVar0=None,
    ):
        parent._items.append(self)
        self._tk = Entry(root, font=font0, textvariable=textVar0, width=10)
        self._pos = pos0
        if show0:
            self.show()

    def show(self):
        self._tk.place(x=self._pos[0], y=self._pos[1])

    def hide(self):
        self._tk.place_forget()


class LeaderBoard:  # leaderboard object holds canvaas that stores all scores
    def __init__(
        self,
        parent,
        pos0=[0, 0],
        text0="",
        show0=True,
        font0=(globalVariables.font, 16),
    ):
        parent._items.append(self)
        self._tk = Canvas(root, bg="gray", width=250, height=300)
        self._font = font0
        self._pos = pos0
        if show0:
            self.show()

    def update(
        self,
    ):  # can be called to update current standings - so no need to restart program
        LB = self._tk
        LB.delete("all")
        font0 = self._font
        LB.create_text(125, 20, text="== High Scores ==", font=font0)
        topScores = loadScores()
        for i in range(len(topScores)):
            if i < 10:
                if topScores[i][0] == "":
                    LB.create_text(
                        125,
                        50 + i * 25,
                        text="{}.    {}    {}".format(
                            str(i + 1), "ANON", topScores[i][1]
                        ),
                        font=(globalVariables.font, 11),
                    )
                else:
                    LB.create_text(
                        125,
                        50 + i * 25,
                        text="{}.    {}    {}".format(
                            str(i + 1), topScores[i][0].lower(), topScores[i][1]
                        ),
                        font=(globalVariables.font, 11),
                    )

    def show(self):
        self._tk.place(x=self._pos[0], y=self._pos[1])

    def hide(self):
        self._tk.place_forget()


class Menu:  # parent class of menu
    def __init__(
        self, pos0=[0, 0], text0="test", show0=True, font0=(globalVariables.font, 16)
    ):
        self._items = []
        self._tk = Label(root, text=text0, font=font0, bg="gray", highlightthickness=2)
        self._pos = pos0
        if show0:
            self.show()

    def show(self):
        self._tk.place(x=self._pos[0], y=self._pos[1])
        for item in self._items:
            item.show()

    def hide(self):
        self._tk.place_forget()
        for item in self._items:
            item.hide()


######################### UI METHODS #########################


def toggleMenu():  # either opens or closes menu depeding on whether it is alredy open or closed
    if globalVariables.play:
        globalVariables.play = False
        if globalVariables.alive:
            globalVariables.log = "paused"
        mainMenu.show()
    elif globalVariables.play == False and globalVariables.alive:
        globalVariables.play = True
        globalVariables.log = "escape the gauntlet!"
        mainMenu.hide()


def bossKey():  # universal method that can be called to instantly create work overlay on game
    if globalVariables.boss:
        globalVariables.boss = False
        bossScreen.place_forget()
    elif globalVariables.boss == False:
        globalVariables.boss = True
        globalVariables.play = False
        toggleMenu()
        toggleMenu()
        bossScreen.place(x=0, y=0)


def updateUI():  # continuously will run and update all ui elements as necessary
    try:
        ui_fps._tk.config(text="FPS:" + str(int(1 / globalVariables.frametime)))
    except BaseException:
        pass
    if not globalVariables.alive:
        menuButtonContinue._tk.configure(bg="#222222")
        menuButtonSave._tk.configure(bg="#222222")
    else:
        menuButtonContinue._tk.configure(bg="gray")
        menuButtonSave._tk.configure(bg="gray")
    menuLeaderBoard.update()
    ui_name._tk.config(
        text="Name:" + globalVariables.userName
    )  # updates elements with corresponding values
    ui_score._tk.config(text="Score:" + str(globalVariables.score))
    ui_level._tk.config(text="Level:" + str(globalVariables.level))
    ui_hint._tk.config(text=globalVariables.log)


######################### MENU OPTIONS #########################


def continueGame():  # attempts to unpause game if alive
    if globalVariables.alive:
        toggleMenu()
    elif globalVariables.alive == False:
        globalVariables.log = "no active game session"


def resetGame():  # resets all basic global variables
    if globalVariables.alive and globalVariables.overrule != "reset":
        globalVariables.log = "warning! new game will overwrite progress"
        globalVariables.overrule = "reset"
    else:
        globalVariables.overrule = None
        globalVariables.score = 0
        globalVariables.level = 1
        globalVariables.vel = 0
        globalVariables.alive = True
        resetGameObjects()
        continueGame()


def saveGame():  # writes level data and name to external file
    if globalVariables.overrule != "save":
        globalVariables.log = "warning! overwrite save progress?"
        globalVariables.overrule = "save"
    else:
        globalVariables.overrule = None
        if globalVariables.alive:  # only allows saving if player is alive
            with open("save.csv", "w") as file:
                file.write(str(globalVariables.level) + ",")
                file.write(str(globalVariables.userName) + ",")
                file.write(str(globalVariables.score))
            globalVariables.log = "progress saved"
        else:
            globalVariables.log = "no game in progress"


def loadGame():  # loads game data into current session
    if globalVariables.alive and globalVariables.overrule != "load":
        globalVariables.log = "warning! loading will overwrite current progress!"
        globalVariables.overrule = "load"
    else:
        globalVariables.overrule = None
        try:
            with open(
                "save.csv", "r"
            ) as file:  # splits the csv into a list which contains the 3 separate values
                lines = file.read().split(",")
                globalVariables.level = int(lines[0])
                globalVariables.userName = lines[1]
                globalVariables.score = int(lines[2])
                globalVariables.alive = True
                globalVariables.vel = 0
                globalVariables.log = "progress level loaded"
                resetGameObjects()
                levelSetup()
        except FileNotFoundError:  # if no save file found
            globalVariables.log = "no save found!"


def enterName():
    if "," not in globalVariables.entry1.get():
        if globalVariables.entry1.get() != "":
            globalVariables.userName = globalVariables.entry1.get()
            globalVariables.log = "welcome back, {}".format(globalVariables.userName)


def enterCode():  # method for assigning different global variables based on text entered
    if globalVariables.entry2.get() == "noclip":
        if globalVariables.noclip:
            globalVariables.noclip = False
            messageText = "code success: noclip off"
        else:
            globalVariables.noclip = True
            messageText = "code success: noclip on"
    elif globalVariables.entry2.get() == "score":
        globalVariables.score += 1
        messageText = "code success: added score"
    elif globalVariables.entry2.get() == "flappy":
        if globalVariables.acc == 0:
            globalVariables.acc = 2500
            messageText = "code success: flappy mode off"
        else:
            globalVariables.acc = 0
            messageText = "code success: flappy mode on"
    elif globalVariables.entry2.get() == "wide":
        if globalVariables.pipeCheat:
            globalVariables.pipeCheat = False
            messageText = "code success: wide gaps off"
        else:
            globalVariables.pipeCheat = True
            messageText = "code success: wide gaps on"
    elif globalVariables.entry2.get() == "slow":
        if globalVariables.scrollCheat:
            globalVariables.scrollCheat = False
            messageText = "code success: slow mode on"
        else:
            globalVariables.scrollCheat = True
            messageText = "code success: slow mode on"
    elif globalVariables.entry2.get() == "ghost":

        if globalVariables.ghost:
            globalVariables.ghost = False
            window.delete(globalVariables.ghostSprite)
            messageText = "code success: ghost turned off"
        else:
            globalVariables.ghost = True
            globalVariables.ghostSprite = window.create_image(50, 300, image=ghostImage)
            messageText = "code success: ahhhh ghost!!"
    else:
        messageText = "invalid code"
    globalVariables.log = messageText


def movementMouse():  # allows user to choose movement keys
    globalVariables.log = "controls set to left and right mouse"
    root.bind("<Button-1>", lambda a: jump())
    root.bind("<Button-3>", lambda a: drop())
    root.unbind("<Up>")
    root.unbind("<Down>")


def movementArrows():  # allows user to choose movement keys
    globalVariables.log = "controls set to up and down arrows"
    root.unbind("<Button-1>")
    root.unbind("<Button-3>")
    root.bind("<Up>", lambda a: jump())
    root.bind("<Down>", lambda a: drop())


def quitGame():  # will stop main loop from running and destroy (close) main window
    if globalVariables.overrule != "quit":
        globalVariables.log = "warning! unsaved progress will be lost!"
        globalVariables.overrule = "quit"
    else:
        globalVariables.overrule = None
        globalVariables.quit = True
        root.destroy()


######################### MOVEMENT KEYS #########################


def jump():  # when 'jump' key is pressed
    if globalVariables.play:
        globalVariables.vel = -900


def drop():  # when 'drop' key is pressed
    if globalVariables.play:
        globalVariables.vel = 900


######################### SAVING AND LOADING #########################


def loadScores():  # opens score file and loads csv data into a list which is then returned
    topScores = []
    try:
        with open("scores.csv", "r") as file:
            lines = file.readlines()
        for entry in lines:
            entry = entry.strip("\n")
            entry = entry.split(",")
            entry[1] = int(entry[1])
            topScores.append(entry)
        topScores = sorted(topScores, key=lambda x: x[1], reverse=True)
    except FileNotFoundError:
        pass
    return topScores


def saveScore():  # when player dies, will save score to external file to be used on the leaderboard
    if globalVariables.score > 0:
        name = globalVariables.entry1.get()
        with open("scores.csv", "a") as file:
            file.write(
                globalVariables.userName + "," + str(globalVariables.score) + "\n"
            )


######################### GAMEPLAY CONTROL #########################


def moveObjects():  # main function that is called when a play frame is needed
    if globalVariables.alive:
        if globalVariables.scrollCheat:
            scroll = 450
        else:
            scroll = 500 + 80 * globalVariables.level + 3*globalVariables.score

        levelSetup()
        # move pipes:
        for i in range(
            len(globalVariables.pipes)
        ):  # moves both pipe sections by scroll speed
            window.move(
                globalVariables.pipes[i][0], -scroll * globalVariables.frametime, 0
            )
            window.move(
                globalVariables.pipes[i][1], -scroll * globalVariables.frametime, 0
            )
            if window.coords(globalVariables.pipes[i][0])[0] <= 0:
                globalVariables.score += 1
                if globalVariables.score % 10 == 0:
                    if globalVariables.level < 5:  # tests if user reaches new level
                        globalVariables.level += 1
                        globalVariables.log = "level up!"
                elif globalVariables.level == 5:  # tests if user if golden level
                    globalVariables.log = "entered golden room! get bonus score!"
                window.delete(
                    globalVariables.pipes[i][0]
                )  # when pipe hits edge, will delete and be re-created at other side
                window.delete(globalVariables.pipes[i][1])
                globalVariables.pipes[i] = newPipe(windowSize[0])
        # move ball
        globalVariables.vel += (
            globalVariables.acc * globalVariables.frametime
        )  # differential equation to get velociy value by time elapsed
        window.move(
            globalVariables.ball1, 0, globalVariables.vel * globalVariables.frametime
        )
        # move bg
        for background in globalVariables.backgrounds:
            if window.coords(background)[0] <= -windowSize[0]:
                window.move(background, 2 * windowSize[0], 0)
            window.move(background, -scroll * globalVariables.frametime / 2, 0)
        # move enemy, if easter egg enabled
        if globalVariables.ghost:
            if (
                window.coords(globalVariables.ball1)[1]
                < window.coords(globalVariables.ghostSprite)[1]
            ):
                window.move(
                    globalVariables.ghostSprite, 0, -50 * globalVariables.frametime
                )
            elif (
                window.coords(globalVariables.ball1)[1]
                > window.coords(globalVariables.ghostSprite)[1]
            ):
                window.move(
                    globalVariables.ghostSprite, 0, 50 * globalVariables.frametime
                )
            myList = [0] * 5
            myList.append(1)
            myList.append(-1)
            window.move(globalVariables.ghostSprite, choice(myList), 0)

        # collision detection
        if (
            collision(globalVariables.ball1, globalVariables.pipes[0][0])
            or collision(globalVariables.ball1, globalVariables.pipes[0][1])
            or collision(globalVariables.ball1, globalVariables.pipes[1][0])
            or collision(globalVariables.ball1, globalVariables.pipes[1][1])
        ):  # treats objects as rectangular
            if not globalVariables.noclip:
                globalVariables.alive = False
        if (
            window.bbox(globalVariables.ball1)[1] < -1
            or window.bbox(globalVariables.ball1)[3] > 720
        ):
            globalVariables.alive = False
        if not globalVariables.alive:
            globalVariables.log = "game over! your score: {}".format(
                globalVariables.score
            )
            saveScore()
            toggleMenu()


def levelSetup():  # sets necessary backdrop for each level
    if globalVariables.level == 1:
        window.itemconfig(
            globalVariables.backgrounds[0], image=backgroundImages["grey"]
        )
        window.itemconfig(
            globalVariables.backgrounds[1], image=backgroundImages["grey"]
        )
    if globalVariables.level == 2:
        window.itemconfig(
            globalVariables.backgrounds[0], image=backgroundImages["green"]
        )
        window.itemconfig(
            globalVariables.backgrounds[1], image=backgroundImages["green"]
        )
    if globalVariables.level == 3:
        window.itemconfig(
            globalVariables.backgrounds[0], image=backgroundImages["blue"]
        )
        window.itemconfig(
            globalVariables.backgrounds[1], image=backgroundImages["blue"]
        )
    if globalVariables.level == 4:
        window.itemconfig(
            globalVariables.backgrounds[0], image=backgroundImages["purple"]
        )
        window.itemconfig(
            globalVariables.backgrounds[1], image=backgroundImages["purple"]
        )
    if globalVariables.level == 5:
        window.itemconfig(
            globalVariables.backgrounds[0], image=backgroundImages["gold"]
        )
        window.itemconfig(
            globalVariables.backgrounds[1], image=backgroundImages["gold"]
        )


def newPipe(startX):  # method for generating random new pipe
    gaps = [400, 350, 300, 275, 250]  # gaps for each level
    if globalVariables.pipeCheat:
        gap = 600
    else:
        gap = gaps[globalVariables.level - 1]
    colors = [
        "gray",
        "green",
        "#4169e1",
        "pink",
        "#FFC000",
    ]  # colours for each level
    color = colors[globalVariables.level - 1]
    gapTLCorner = randint(
        5, windowSize[1] - gap - 5
    )  # calculates where top-left corner of gap should be randomly
    pipe = [
        window.create_rectangle(startX, -10, startX + 50, gapTLCorner, fill=color),
        window.create_rectangle(
            startX, gapTLCorner + gap, startX + 50, windowSize[1] + 10, fill=color
        ),
    ]  # pipe is created as 2 separate rectangles that move in unison
    return pipe


######################### GENERATING MENU AND UI #########################

mainMenu = Menu(
    pos0=[200, 100], text0="Main Menu", font0=(globalVariables.font, 36)
)  # generates main menu parent object
# generate menu:
menuButtonContinue = MenuButton(
    parent=mainMenu, pos0=[200, 200], text0="Continue", command0=continueGame
)
menuButtonReset = MenuButton(
    parent=mainMenu, pos0=[200, 250], text0="New Game", command0=resetGame
)
menuButtonSave = MenuButton(
    parent=mainMenu, pos0=[200, 300], text0="Save Game", command0=saveGame
)
menuButtonLoad = MenuButton(
    parent=mainMenu, pos0=[200, 350], text0="Load Game", command0=loadGame
)
menuButtonName = MenuButton(
    parent=mainMenu, pos0=[200, 400], text0="Enter Name", command0=enterName
)
menuButtonCode = MenuButton(
    parent=mainMenu, pos0=[200, 450], text0="Enter Code", command0=enterCode
)
menuButtonExit = MenuButton(
    parent=mainMenu, pos0=[200, 550], text0="Quit", command0=quitGame
)

menuLeaderBoard = LeaderBoard(parent=mainMenu, pos0=[550, 200])

menuButtonArrows = MenuButton(
    parent=mainMenu, pos0=[600, 600], text0="Movement Arrows", command0=movementArrows
)
menuButtonMouse = MenuButton(
    parent=mainMenu, pos0=[900, 600], text0="Movement Mouse", command0=movementMouse
)


menuGameTitle = MenuLabel(
    parent=mainMenu,
    pos0=[900, 250],
    font0=(globalVariables.font, 48),
    text0="Run the\nGauntlet",
)

menuInputCode = MenuInput(
    parent=mainMenu, pos0=[360, 400], textVar0=globalVariables.entry1
)
menuInputCode = MenuInput(
    parent=mainMenu, pos0=[360, 450], textVar0=globalVariables.entry2
)
# generate ui elements
ui_fps = UI(pos0=[0, 30])
ui_name = UI(pos0=[0, 0])
ui_score = UI(pos0=[1150, 0])
ui_level = UI(pos0=[1050, 0])
ui_hint = UI(pos0=[0, 695])

bossScreen = Label(root, width=windowSize[0], height=windowSize[1], image=bossImage)


######################### GAME SPRITE CONTROL #########################


def resetGameObjects():
    window.delete("all")  # removes all sprites from window
    ballSize = 15
    # create sprites:
    globalVariables.backgrounds = [
        window.create_image(0, 0, image=backgroundImages["grey"], anchor=NW),
        window.create_image(
            windowSize[0], 0, image=backgroundImages["grey"], anchor=NW
        ),
    ]
    globalVariables.ball1 = window.create_oval(
        200, 300, 200 + ballSize, 300 + ballSize, fill="light gray"
    )
    globalVariables.pipes = [
        newPipe(windowSize[0] * 2.5),
        newPipe(windowSize[0] * 2),
    ]  # uses newPipe() method to create 2 pipes per frame
    if globalVariables.ghost:
        globalVariables.ghostSprite = window.create_image(50, 300, image=ghostImage)


def collision(ob1, ob2):
    leeway = 1
    obj1 = window.bbox(ob1)
    obj2 = window.bbox(ob2)
    # tests for overlap of TL+BR corners
    if (
        obj1[0] + leeway <= obj2[2] - leeway
        and obj1[1] + leeway <= obj2[3] - leeway
        and obj1[2] - leeway >= obj2[0] + leeway
        and obj1[3] - leeway >= obj2[1] + leeway
    ):
        return True
    #  tests for overlap of BL+TP corners
    if (
        obj1[2] - leeway >= obj2[0] + leeway
        and obj1[1] + leeway <= obj2[3] - leeway
        and obj1[0] + leeway <= obj2[2] - leeway
        and obj1[3] - leeway >= obj2[1] + leeway
    ):
        return True
    return False


######################### MAIN GAME LOOP (DRAW FRAME) #########################


def main():
    global startTime
    globalVariables.frametime = (
        time() - startTime
    )  # calculates time elapsed since last fame drawn
    startTime = (
        time() + 0
    )  # '+0' creates a copy of value, rather than pointer to time()
    updateUI()
    if (
        globalVariables.play and globalVariables.alive
    ):  # will play a play frame if alive and unpaused
        moveObjects()
    if not globalVariables.quit:  # tests if user has not quit
        root.after(1, main)


# start of main code run
if __name__ == "__main__":
    startTime = time()
    globalVariables.frametime = None
    main()
root.mainloop()


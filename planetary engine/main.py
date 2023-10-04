from turtle import Turtle, Screen
import math
import time


distance_scale=1000000000
time_scale=1
x_offset=0
y_offset=0
G=6.6743*10**-11

def warp_up():
    global time_scale
    time_scale*=1.5
def warp_down():
    global time_scale
    time_scale/=1.5
def zoom_in():
    global distance_scale
    distance_scale/=1.5
def zoom_out():
    global distance_scale
    distance_scale*=1.5

def get_angle(p1,p2):
    dif_x=p2[0]-p1[0]
    dif_y=p2[1]-p1[1]
    if dif_x > 0:
        return math.degrees(math.atan(dif_y/dif_x))
    if dif_x < 0:
        return math.degrees(math.atan(dif_y/dif_x))+180
    if dif_y > 0:
        return 90
    if dif_y < 0:
        return -90
    if dif_y == 0:
        return 0

def engine():
    for body1 in bodies:
        F=[0,0]
        for body2 in bodies:
            theta=get_angle(body1._s,body2._s)
            x_vector=body2._s[0]-body1._s[0]
            y_vector=body2._s[1]-body1._s[1]
            if not(x_vector ==0 and y_vector ==0):
                gravity_force=(body1._mass*body2._mass*G)/(x_vector**2+y_vector**2)
            if x_vector==0:
                x_force=0  
            else:
                x_force=gravity_force*math.cos(math.radians(theta))
            if y_vector==0:
                y_force=0
            else:
                y_force=gravity_force*math.sin(math.radians(theta))
            F[0]+=x_force
            F[1]+=y_force
        delta_t=(time.time()-body1._cur_time)*time_scale
        body1._cur_time=time.time()
        body1._a=[F[0]/body1._mass,F[1]/body1._mass]
        body1._v=[body1._v[0]+body1._a[0]*delta_t,body1._v[1]+body1._a[1]*delta_t]
        body1._s=[body1._s[0]+body1._v[0]*delta_t,body1._s[1]+body1._v[1]*delta_t]

def draw_frame(turtles):
    global x_offset
    global y_offset
    global distance_scale
    screen.update()
    for i in range(len(bodies)):
        turtles[i].goto(bodies[i]._s[0]/distance_scale+x_offset/distance_scale**1,bodies[i]._s[1]/distance_scale+y_offset/distance_scale**1)
    screen.update()

class Body():
    def __init__(self,name,mass,s,v,a):
        global turtles
        self._name=name
        self._mass=mass
        self._s=s
        self._v=v
        self._a=a
        self._cur_time=time.time()
        new_turtle = Turtle()
        new_turtle.shape("turtle")
        new_turtle.turtlesize(0.05)
        new_turtle.penup() 
        turtles.append(new_turtle)




def pan_up():
    global y_offset
    y_offset-=10*distance_scale
def pan_left():
    global x_offset
    x_offset+=10*distance_scale
def pan_down():
    global y_offset
    y_offset+=10*distance_scale
def pan_right():
    global x_offset
    x_offset-=10*distance_scale





        
#LOOP

turtles=[]
bodies=[Body("earth",5.9722*10**24,[1.51*10**11,0],[0,2.978*10**4],[0,0]), Body("moon",7.3477*10**22,[1.51348*10**11,0],[0,2.8757*10**4],[0,0]), Body("sun",1.989*10**30,[0,0],[0,0],[0,0])]



#1.989*10**30

screen = Screen()
screen.tracer(0)
screen.onkey(warp_up,"Right")
screen.onkey(warp_down,"Left")
screen.onkey(zoom_in,"Up")
screen.onkey(zoom_out,"Down")



screen.onkey(pan_up,"w")
screen.onkey(pan_left,"a")
screen.onkey(pan_down,"s")
screen.onkey(pan_right,"d")


while True:
    engine()
    draw_frame(turtles)
    screen.listen()














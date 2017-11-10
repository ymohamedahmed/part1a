from martypy import Marty
mymarty = Marty('socket://192.168.0.2')
mymarty.hello()
mymarty.play_sound(100, 1000, 50)

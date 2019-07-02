file = open("test.txt", "w")

import random

L_PROBABILITY = 5 # connect to 1 out of every 5 other vertices
M_PROBABILITY = 3 # connect to 1 out of every 3 other vertices
H_PROBABILITY = 1 # connect all other vertices

probability = L_PROBABILITY

size = 400

count = 0

# add vertexes
for vertex in range(1, size):
    file.write("AV " + str(vertex) + "\n")

# add edges
for srcVertex in range(1, size):
    for tarVertex in range(srcVertex, size):
        if (srcVertex != tarVertex):
            canConnect = (random.randint(1,probability) % probability == 0)
            if (canConnect):
                file.write("AE " + str(srcVertex) + " " + str(tarVertex) + " " + str(random.randint(1,9)) + "\n")
                count += 1

print("Added " + str(count) + " edges.")
                
file.close()


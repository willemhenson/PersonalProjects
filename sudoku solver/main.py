
from math import sqrt

def coord2Area(item_row,item_column):
    return (item_column//area_length+1+\
            area_length*(item_row//area_length))

def area2TLCoord(area_number):
    area_number-=1
    return [area_length*(area_number//area_length),\
            area_length*(area_number%area_length)]


log=input("log? ").lower()
if "y" in log:
    log=True
else:
    log=False


grid = [[7,0,0,0,3,4,8,0,0],
        [8,0,4,6,0,0,0,0,0],
        [0,3,9,0,5,0,0,0,0],
        [1,0,0,5,0,0,6,0,0],
        [0,4,0,7,0,9,0,3,0],
        [0,0,3,0,0,8,0,0,9],
        [0,0,0,0,7,0,3,2,0],
        [0,2,6,0,0,1,9,0,5],
        [0,0,7,9,2,0,0,0,4]]

size = len(grid)
area_length = int(sqrt(size))





used_numbers_in_each_area = []
for i in range(size):
    used_numbers_in_each_area.append([])
for r in range(size):
    for c in range(size):
        if grid[r][c] != 0:
            used_numbers_in_each_area[coord2Area(r,c)-1].append(grid[r][c])







if log:
    print("-------------- NEW INSTANCE -----------")
run = True



#STRAT1
while run:
    run = False
    for i in range(size):
        if log:
            print("\n    checking new area: {}".format(i+1))
        first_cell_in_area=area2TLCoord(i+1)
        for current_number_checking in range(1,size+1):
            if log:
                print("        checking new number: {}".format(current_number_checking))
            found_current_number_in_area=False
            for r in range(first_cell_in_area[0],first_cell_in_area[0]+area_length):
                for c in range(first_cell_in_area[1],first_cell_in_area[1]+area_length):
                    if log:
                        print("            checking new cell: {}".format([r,c]))
                    if grid[r][c] == current_number_checking:
                        found_current_number_in_area=True
                        if log:
                            print("                *found '{}' at {}".format(current_number_checking,[r,c]))
            if found_current_number_in_area == True:
                if log:
                    print("        '{}' is already in area {},skipping".format(current_number_checking,i+1))



            elif found_current_number_in_area == False:
                if log:
                    print("        '{}' is not in area {},continuing".format(current_number_checking,i+1))
                possible_positions = []
                for r in range(first_cell_in_area[0],first_cell_in_area[0]+area_length):
                    for c in range(first_cell_in_area[1],first_cell_in_area[1]+area_length):
                        if grid[r][c] == 0:
                            found_current_number_in_rc=False
                            for incrementer in range(size):
                                if grid[r][incrementer] == current_number_checking:
                                    found_current_number_in_rc=True
                                
                                elif grid[incrementer][c] == current_number_checking:
                                    found_current_number_in_rc=True
                            if found_current_number_in_rc == False:
                                possible_positions.append([r,c])
                if log:
                    print("        possible positions for '{}' in area {}: {}".format(current_number_checking,i+1,possible_positions))
                if len(possible_positions) == 1:
                    run=True
                    set_posx = possible_positions[0][0]
                    set_posy = possible_positions[0][1]
                    grid[set_posx][set_posy]=current_number_checking
                    used_numbers_in_each_area[i].append(current_number_checking)
                    if log:
                        print("ADDED '{}' to {}".format(current_number_checking,[set_posx,set_posy]))
    if run == False:
        if log:
            print("NO NEW CHANGES, MOVING ON")



#STRAT2
run = True
while run:
    run=False
    for r in range(size):
        for c in range(size):
            if grid[r][c] == 0:
                current_area=coord2Area(r,c)
                possible_entries=[]
                for num in range(1,size+1):
                    if num in used_numbers_in_each_area[current_area-1]:
                        pass
                    else:
                        possible_entries.append(num)
                for p in range(size):
                    if r == 1 and c == 7:
                        if log:
                            print(possible_entries)
                    if grid[p][c] in possible_entries:
                        del possible_entries[possible_entries.index(grid[p][c])]
                    if grid[r][p] in possible_entries:
                        del possible_entries[possible_entries.index(grid[r][p])]
                if len(possible_entries) == 1:
                    run=True
                    grid[r][c]=possible_entries[0]
                    used_numbers_in_each_area[current_area-1].append(possible_entries[0])
                    if log:
                        print("ADDED '{}' to {}".format(possible_entries[0],[r,c]))
    if run == False:
        if log:
            print("NO NEW CHANGES, MOVING TO END")

print("\nFINAL GRID:")
print("\n".join(map(str, grid)))
        

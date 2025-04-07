import os
from awpy import Demo

print(os.getcwd())

dem = Demo(r"src\main\resources\dem\the-mongolz-vs-g2-m1-ancient.dem", verbose=True)
dem.parse()

for event_name, event in dem.events.items():
    print(f"{event_name}: {event.shape[0]} rows x {event.shape[1]} columns")

data_categories = {
    "header": dem.header,
    "rounds": dem.rounds,
    "kills": dem.kills.head(n=1000),
    "damages": dem.damages.head(n=1000),
    "shots": dem.shots.head(n=1000),
    "bomb": dem.bomb.head(n=1000),
    "smokes": dem.smokes.head(n=1000),
    "infernos": dem.infernos.head(n=1000),
    "grenades": dem.grenades.head(n=1000),
    "footsteps": dem.footsteps.head(n=1000),
    "ticks": dem.ticks.head(n=1000),
}

# Zapisywanie plików w folderze analyzed
for category, data in data_categories.items():
    file_path = os.path.join(r"src\main\resources\analyzed", f"{category}.txt")
    with open(file_path, "w", encoding="utf-8") as file:
        file.write(str(data))

print("File saved")

dem.ticks.to_pandas()
import os
import json
import sys
from awpy import Demo

def parseDemo(fileName):
    #value = fr"src\main\resources\dem\{fileName}"
    value = fr"..\dem\{fileName}"
    #value = fr"C:\Users\kacpe\IdeaProjects\DemoAnalyzer\src\main\resources\dem\the-mongolz-vs-g2-m2-dust2.dem"
    dem = Demo(value, verbose=True)
    dem.parse()


    data_categories = {
        "header": dem.header,
        "rounds": dem.rounds,
        "kills": dem.kills,
        "DamagesDeserializer": dem.damages,
        "shots": dem.shots,
        "bomb": dem.bomb,
        "smokes": dem.smokes,
        "infernos": dem.infernos,
        "grenades": dem.grenades,
        "footsteps": dem.footsteps,
        "ticks": dem.ticks,
    }

    #output_dir = r"src\main\resources\analyzed"
    output_dir = r"C:\Users\kacpe\IdeaProjects\DemoAnalyzer\src\main\resources\analyzed"
    os.makedirs(output_dir, exist_ok=True)

    for category, data in data_categories.items():
        try:
            if category == "header":
                file_path = os.path.join(output_dir, f"{category}.json")
                with open(file_path, "w", encoding="utf-8") as f:
                    json.dump(data, f, indent=4, ensure_ascii=False)
            else:
                file_path = os.path.join(output_dir, f"{category}.csv")
                data.to_pandas().to_csv(file_path, index=False)

            print(f"Category data '{category}' saved to: {file_path}")
        except Exception as e:
            print(f"Error while saving category '{category}': {e}")

    print("All data has been saved")

print('test')
path = sys.argv[1]
print('python reviced path: ', path)
parseDemo(path)
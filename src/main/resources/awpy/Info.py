import os
import json
from awpy import Demo

dem = Demo(r"src\main\resources\dem\the-mongolz-vs-g2-m1-ancient.dem", verbose=True)
dem.parse()

data_categories = {
    "header": dem.header,
    "rounds": dem.rounds,
    "kills": dem.kills,
    "damages": dem.damages,
    "shots": dem.shots,
    "bomb": dem.bomb,
    "smokes": dem.smokes,
    "infernos": dem.infernos,
    "grenades": dem.grenades,
    "footsteps": dem.footsteps,
    "ticks": dem.ticks,
}

output_dir = r"src\main\resources\analyzed"
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

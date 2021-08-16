import os
import random

FILE_COUNT = 10
CLASSMATES_COUNT = 10000
FIRST_NAME_FILE = "names/FirstNames.txt"
LAST_NAME_FILE = "names/LastNames.txt"

if __name__ == '__main__':
    def get_file_path(name):
        return os.path.join(dir_path, name)


    subjects = ["chinese", "mathematics", "english", "physics", "chemistry"]
    dir_path = os.path.dirname(os.path.realpath(__file__))
    with open(get_file_path(FIRST_NAME_FILE), "r", encoding="UTF-8") as firstNameFile, \
            open(get_file_path(LAST_NAME_FILE), "r", encoding="UTF-8") as lastNameFile:
        first_names = [x.strip() for x in firstNameFile.readlines()]
        last_names = [x.strip() for x in lastNameFile.readlines()]

        for i in range(FILE_COUNT):
            with open(get_file_path(f"scores-{i}.csv"), "w", encoding="UTF-8") as output:
                for i in range(CLASSMATES_COUNT):
                    name = f"{random.choice(first_names)} {random.choice(last_names)}"
                    output.writelines([f"{name},{x},{random.randint(0,100)},\n" for x in subjects])

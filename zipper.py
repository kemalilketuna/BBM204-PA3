
# zip all java files in the current directory
import zipfile
import os

def zip_files():
    # get all files in the current directory
    files = os.listdir(os.getcwd())
    # filter out only java files
    java_files = [f for f in files if f.endswith('.java')]
    # create a zip file with zero compression
    with zipfile.ZipFile('b2220356127.zip', 'w', zipfile.ZIP_STORED) as z:
        for f in java_files:
            z.write(f)

if __name__ == '__main__':
    zip_files()



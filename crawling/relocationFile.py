import shutil
import os


#다운로드 파일 위치
file_source = 'C:/Users/유희석/Downloads'
file_destination = './lecture_files'

get_files = os.listdir(file_source)

count = 0
for g in get_files:
    if('강의시간표' in g):
        shutil.move(file_source+'/' + g, file_destination)
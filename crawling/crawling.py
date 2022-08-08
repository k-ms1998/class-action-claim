import pymysql
import pandas as pd
import warnings
from datetime import datetime
warnings.simplefilter("ignore")

def readEXCEL(index):
    fileName = './lecture_files/강의시간표_'+datetime.today().strftime("%Y-%m-%d")
    if(index > 0):
        fileName += ' ({}).xlsx'.format(index)
    else:
        fileName += '.xlsx'
    df = pd.read_excel(fileName)
    return df

def getScheduleName(df):
    return df["Unnamed: 1"][2]

def createListTB(name):
    conn = pymysql.connect(
        user="root",
        password="8398",
        host="localhost",
        db='dtdb',
        charset='utf8'
    )

    cur = conn.cursor()

    create_tb = "CREATE OR REPLACE TABLE "+name+"_tb(department VARCHAR(50), course_id VARCHAR(50) NOT NULL, class VARCHAR(50) NOT NULL, course_name VARCHAR(100),course_type VARCHAR(50), credit VARCHAR(50), professor_name VARCHAR(50), constraint PRIMARY KEY(course_id, class))"
    cur.execute(create_tb)

    conn.commit()
    conn.close()

def createTimeTB(name):
    conn = pymysql.connect(
        user="root",
        password="8398",
        host="localhost",
        db='dtdb',
        charset='utf8'
    )

    cur = conn.cursor()

    create_tb = "CREATE OR REPLACE TABLE "+name+"_Time_tb(course_id VARCHAR(50), class VARCHAR(50), day VARCHAR(50),start VARCHAR(50),end VARCHAR(50))"
    cur.execute(create_tb)

    conn.commit()
    conn.close()

def createTotalTB(name):
    conn = pymysql.connect(
        user="root",
        password="8398",
        host="localhost",
        db='dtdb',
        charset='utf8'
    )

    cur = conn.cursor()

    create_tb = "CREATE OR REPLACE TABLE "+name+"_Total_tb(department VARCHAR(50), course_id VARCHAR(50), class VARCHAR(50), course_name VARCHAR(100),course_type VARCHAR(50), credit VARCHAR(50), professor_name VARCHAR(50),day VARCHAR(50),start VARCHAR(50),end VARCHAR(50))"
    cur.execute(create_tb)

    conn.commit()
    conn.close()


def setCredit(df):  
    temp = df["credit"].tolist()
    credit_list = []
    for element in temp:
        credit_list.append(float((element.split('/'))[0]))
    return credit_list

def convertDay(ch):
    match ch:
        case "월":
            return "Mon"
        case "화":
            return "Tue"
        case "수":
            return "Wed"
        case "목":
            return "Thu"
        case "금":
            return "Fri"


def seperateTime(df):
    id_list = []
    class_list = []
    day_list = []
    start_list = []
    end_list = []

    for i in range(0,len(df.index)):
        temp = df["time"].iloc[i]
        if(type(temp) != type("str")):
            df["time"].iloc[i] = "null"
        df["time"].iloc[i] = (df["time"].iloc[i]).split(',')

   
    for i in range(0,len(df.index)):
        flag = 0

        for time in df["time"].iloc[i]:
            j = 0
            for j in range(0,len(time)):
                if(time == "null"):
                    flag = 1
                    break
                if(time[j].isdigit()):
                    break
                temp = convertDay(time[j])
                day_list.append(temp)
                id_list.append(df["courseId"].iloc[i])
                class_list.append(df["class"].iloc[i])
            if (flag == 1):
                continue
            start,end = (time[j:]).split("-")
            for k in range(0,j):
                start_list.append(start)
                end_list.append(end)
            
    check_df = pd.DataFrame({
        'courseId': id_list,
        'class': class_list,
        'dayOfWeek': day_list,
        'startTime': start_list,
        'endTime': end_list
    })

    return check_df

        

def getCourseSchedule(df):
    schedule_df = df[["Unnamed: 2","Unnamed: 3","Unnamed: 13"]][2:].copy()
    schedule_df.columns=["courseId","class","time"]
    schedule_df.reset_index(drop=True,inplace=True)

    result_df = seperateTime(schedule_df)

    return result_df

def getTotalDataFrame(df):
    department_list = []
    id_list = []
    class_list = []
    courseName_list = []
    courseType_list = []
    credit_list = []
    professorName_list = []
    day_list = []
    start_list = []
    end_list = []
# 교수명 Nan 변형
    for i in range(0,len(df.index)):
        temp = df["professorName"].iloc[i]
        if(type(temp) != type("str")):
            df["professorName"].iloc[i] = "null"

# 시간 Nan 값 변형, 시간대 분할
    for i in range(0,len(df.index)):
        temp = df["time"].iloc[i]
        if(type(temp) != type("str")):
            df["time"].iloc[i] = "null"
        df["time"].iloc[i] = (df["time"].iloc[i]).split(',')

   
    for i in range(0,len(df.index)):
        flag = 1

        for time in df["time"].iloc[i]:
            j = 0
            for j in range(0,len(time)):
                if(time == "null"):
                    flag *= 2
                if(df["professorName"].iloc[i] == 'null'):
                    flag *= 3
                if(time[j].isdigit()):
                    break
                
                id_list.append(df["courseId"].iloc[i])
                class_list.append(df["class"].iloc[i])
                department_list.append(df["department"].iloc[i])
                courseName_list.append(df["courseName"].iloc[i])
                courseType_list.append(df["courseType"].iloc[i])
                credit_list.append(df["credit"].iloc[i])

                # 날짜가 없는 경우
                if(flag % 2 == 0):
                    day_list.append('null')
                    start_list.append('null')
                    end_list.append('null')
                    if(flag % 3 == 0):
                        professorName_list.append('null')
                    else:
                        professorName_list.append(df["professorName"].iloc[i])
                    break

                
                temp = convertDay(time[j])
                day_list.append(temp)

                # 교수가 없는 경우
                if(flag % 3 == 0):
                    professorName_list.append('null')
                else:
                    professorName_list.append(df["professorName"].iloc[i])
            if(flag % 2 == 0):
                continue
            start,end = (time[j:]).split("-")
            for k in range(0,j):
                start_list.append(start)
                end_list.append(end)
            
    result_df = pd.DataFrame({
        'department': department_list,
        'courseId': id_list,
        'class': class_list,
        'courseName': courseName_list,
        'courseType': courseType_list,
        'credit': credit_list,
        'professorName':professorName_list,
        'dayOfWeek': day_list,
        'startTime': start_list,
        'endTime': end_list
    })


    return result_df


def insertListData(course_df, departmentName):
    conn = pymysql.connect(
        user="root",
        password="8398",
        host="localhost",
        charset='utf8',
        db="dtdb"
    )
    cur = conn.cursor()
    
    result_df = course_df[["Unnamed: 1","Unnamed: 2","Unnamed: 3","Unnamed: 4","Unnamed: 6","Unnamed: 8","Unnamed: 12"]][2:].copy()
    result_df.columns=["department","courseId","class","courseName","courseType","credit","professorName"]
    result_df.reset_index(drop=True,inplace=True)

    credit_list = setCredit(result_df)
    result_df["credit"] = credit_list

    insert_query = "INSERT INTO "+departmentName+"_tb VALUES (%s,%s,%s,%s,%s,%s,%s)"
    cur.executemany(insert_query,result_df.values.tolist())
            
    conn.commit()

    conn.close()

def insertTimeData(course_df,departmentName):

    conn = pymysql.connect(
        user="root",
        password="8398",
        host="localhost",
        charset='utf8',
        db="dtdb"
    )
    cur = conn.cursor()

    result_df = getCourseSchedule(course_df)

    insert_query = "INSERT INTO "+departmentName+"_Time_tb VALUES (%s,%s,%s,%s,%s)"
    cur.executemany(insert_query,result_df.values.tolist())

    conn.commit()
    conn.close()

def insertTotalData(course_df,departmentName):

    conn = pymysql.connect(
        user="root",
        password="8398",
        host="localhost",
        charset='utf8',
        db="dtdb"
    )
    cur = conn.cursor()

    temp_df = course_df[["Unnamed: 1","Unnamed: 2","Unnamed: 3","Unnamed: 4","Unnamed: 6","Unnamed: 8","Unnamed: 12","Unnamed: 13"]][2:].copy()
    temp_df.columns=["department","courseId","class","courseName","courseType","credit","professorName","time"]
    temp_df.reset_index(drop=True,inplace=True)

    credit_list = setCredit(temp_df)
    temp_df["credit"] = credit_list

    print(temp_df)

    result_df = getTotalDataFrame(temp_df)

    insert_query = "INSERT INTO "+departmentName+"_Total_tb VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
    cur.executemany(insert_query,result_df.values.tolist())

    conn.commit()
    conn.close()

def checkDB():
    conn = pymysql.connect(
        user="root",
        password="8398",
        host="localhost",
        charset='utf8',
        db="dtdb"
    )

    cur = conn.cursor()

    show_tables = "SHOW TABLES"
    cur.execute(show_tables)

    conn.close()

# course_df = readEXCEL()
# departmentName = getScheduleName(course_df)
# createListTB(departmentName)
# insertListData(course_df, departmentName)

# createTimeTB(departmentName)
# insertTimeData(course_df,departmentName)

# print(datetime.today().strftime("%Y-%m-%d"))

departmentName = "schedule"

createTotalTB(departmentName)

for i in range(0,83):
    course_df = readEXCEL(i)
    insertTotalData(course_df,departmentName)

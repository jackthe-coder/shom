#Team sqlinjectors
#Sho Masuda, Chandula Kodituwakku, Samuel Tsokwa, Tony Chan, Andrew Yung
#2019/11/23

import sys
import csv
import psycopg2


psql_user = 'shom'
psql_db = 'sqlinjectors'
psql_password = 'V00877001'
psql_server = 'studentdb1.csc.uvic.ca'
psql_port= 5432
conn = psycopg2.connect(
        dbname = psql_db,user=psql_user,
        password = psql_password,
        host = psql_server,
        port = psql_port)

cur = conn.cursor()
cur.execute("Select Worksheet_id, username, grade  from Users U, Worksheets W , Student S where U.user_id = S.user_id and S.student_id = W.student_id")
rows = cur.fetchall()
for i in rows:
        print(i)
cur.execute("UPDATE Worksheets W SET grade = 76.4  where W.worksheet_id = 'W090009' ")
print("--------Change--below----------")
cur.execute("Select Worksheet_id,username,grade   from Users U, Worksheets W , Student S where U.user_id = S.user_id and S.student_id = W.student_id")
row = cur.fetchall()
for j in row:
        print(j)
        
        
cur.close()
conn.close()

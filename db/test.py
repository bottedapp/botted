import openpyxl as xl
import sqlite3 as sql

file = "test.xlsx"

wb = xl.load_workbook(file)
ws = wb.active

conn = sql.connect('bots_dataset.db')
cur = conn.cursor()
query = '''INSERT INTO comments(botId,comment1,comment2,comment3,comment4,comment5,
        comment6,comment7,comment8,comment9,comment10,comment11,comment12,comment13,
        comment14,comment15,comment16,comment17,comment18,comment19,comment20)
        VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)'''

record = []
recordList = []

for row in ws.rows:
    for cell in row:
        record.append(cell.value)
    recordList.append(record)
    record = []


with conn:
    cur.executemany(query, recordList)




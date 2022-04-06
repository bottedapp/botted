import openpyxl as xl
import praw
import sqlite3 as sql

reddit = praw.Reddit(
    client_id="GgPNctP2KQdth-iX6aMGUQ",
    client_secret="6zov1gDWJ8Ij60yH3L7q6N_LnPUZHA",
    user_agent="botted 0.0.1",
)

with open('botList.txt') as f:
        usernames = f.readlines()
f.close()

toInsert = {}
commentList = []

for name in usernames:
    for item in reddit.redditor(name).comments.new(limit=20):
            commentList.append(item.body)

    toInsert[name] = commentList
    commentList = []

wb = xl.Workbook()
ws = wb.active

conn = sql.connect('bots_dataset.db')
cur = conn.cursor()

for k,v in toInsert.items():
    bot = k.strip("\n")
    cur.execute('SELECT id FROM bots WHERE user=?',(bot,))
    id = cur.fetchone()
    v.insert(0,id[0])
    ws.append(v)

wb.save("test.xlsx")


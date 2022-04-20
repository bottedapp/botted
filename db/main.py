import openpyxl as xl
import praw
import sqlite3 as sql
import psycopg2 as psy
from sim import Sim




def isbot(sims):
    if (sims > 0.5):
        bot = True
    return bot

reddit = praw.Reddit(
    client_id="GgPNctP2KQdth-iX6aMGUQ",
    client_secret="6zov1gDWJ8Ij60yH3L7q6N_LnPUZHA",
    user_agent="botted 0.0.1",
)

try:
    print("Connecting to Heroku DB")
    conn = psy.connect(
        host = "ec2-34-194-158-176.compute-1.amazonaws.com",
        database = "da2g0o7m136sp5",
        user = "fzbeyehwmqhuxn",
        password = "7b04e1735374fcb6ba8f984fdcbcaaf5bada71f4d85df12c0e62cab2ca2b4022")

    cur = conn.cursor()

    print("DB version is: ")
    cur.execute('SELECT version()')
    db_version = cur.fetchone()
    print(db_version)


except (Exception, psy.DatabaseError) as error:
    print(error)

with open('botList.txt') as f:
        usernames = f.readlines()
f.close()



for acct in usernames:
    comments = reddit.redditor(acct).comments.new(limit=20)
    account = Sim(acct)
    similarities = account.similarities()
    query = '''INSERT INTO comments (bot_name, bot_comment, bot_score)
            VALUES (%s,%s,%s)'''
    for comment in comments:
        cur = conn.cursor()
        record = (acct, comment.body, similarities)
        cur.execute(query,record)
    conn.commit()
    print(f"Comments for {acct} have been inserted.")

conn.close()

    






'''with open('botList.txt') as f:
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

wb.save("test.xlsx")'''


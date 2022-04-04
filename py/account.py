#!/usr/bin/env python
import praw
import difflib
import sys

# initiate Reddit API connection using PRAW
reddit = praw.Reddit(
    client_id="GgPNctP2KQdth-iX6aMGUQ",
    client_secret="6zov1gDWJ8Ij60yH3L7q6N_LnPUZHA",
    user_agent="botted 0.0.1",
)
# declare reddit username
redditor_name = "spez"
# instantiating the Redditor class
redditor = reddit.redditor(redditor_name)

print("username:", redditor.name)
print("id:", redditor.id)
print("avatar:", redditor.icon_img)
print("created:", redditor.created_utc)
print("employee:", redditor.is_employee)
print("mod:", redditor.is_mod)
print("premium:", redditor.is_gold)
print("verified email:", redditor.has_verified_email)
print("comment karma:", redditor.comment_karma)
print("link karma:", redditor.link_karma)
print("\n")

# print("suspended:", redditor.is_suspended)
# print("friend:", redditor.is_friend)
# DEPRECIATED
# print("subreddit:", str(redditor.subreddit))
# print("subreddit banner:", redditor.subreddit["banner_img"])
# print("subreddit name:", redditor.subreddit["name"])
# print("subreddit NSFW:", redditor.subreddit["over_18"])
# print("subreddit description:", redditor.subreddit["public_description"])
# print("subreddit subscribers:", redditor.subreddit["subscribers"])
# print("subreddit title:", redditor.subreddit["title"])



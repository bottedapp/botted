#!/usr/bin/env python
import praw
import difflib
import sys


Reddit = praw.Reddit(
    client_id="GgPNctP2KQdth-iX6aMGUQ",
    client_secret="6zov1gDWJ8Ij60yH3L7q6N_LnPUZHA",
    user_agent="botted 0.0.1",
)
username = sys.argv[1]
print(username,"\n")
i = 1
for item in Reddit.redditor(username).comments.new(limit=10):
    print(f"comment {i}:", item.body, "<br>")
    i += 1

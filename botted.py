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
print(username,"comments<br>")
i = 1
for item in Reddit.redditor(username).comments.new(limit=10):
    print(f"comment {i}:", item.body, "<br>")
    i += 1

# Credit https://pastebin.com/wiAByySP
Similarities = {}
for Index, Comment in enumerate(Reddit.redditor(username).comments.new(limit=10)):
    for CheckIndex, CheckComment in enumerate(Reddit.redditor(username).comments.new(limit=10)):
        if not Comment.subreddit.display_name == CheckComment.subreddit.display_name:
            continue
        if Index != CheckIndex:
            if Comment.subreddit.display_name in Similarities:
                List = Similarities[Comment.subreddit.display_name]
                List.append(difflib.SequenceMatcher(None, Comment.body, CheckComment.body).ratio())
                Similarities[Comment.subreddit.display_name] = List
                continue
            Similarities[Comment.subreddit.display_name] = [difflib.SequenceMatcher(None, Comment.body, CheckComment.body).ratio()]
SimilaritiesValues = list(Similarities.values())
TotalSimilarities = []
for Index, Item in enumerate(Similarities):
    TotalSimilarities.append(sum(SimilaritiesValues[Index]) / len(SimilaritiesValues[Index]))
try:
    print(sum(TotalSimilarities) / len(TotalSimilarities))
except ZeroDivisionError:
    print("score:",0.0)

import praw

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

for comment in redditor.comments.new(limit=3):
    print("author:", comment.author)
    print("comment:", comment.id)
    print("body:", comment.body)
    print("OP:", comment.is_submitter)
    print("submission:", comment.submission)
    print("subreddit:", comment.subreddit)
    print("subreddit id:", comment.subreddit_id)
    print("created:", comment.created_utc)
    print("edited:", str(comment.edited))
    print("replies:", comment.replies)
    print("upvotes:", comment.score)
    print("saved:", comment.saved)
    print("distinguished:", comment.distinguished)
    print("link id:", comment.link_id)
    print("parent id:", comment.parent_id)
    print("permalink:", comment.permalink)
    print("\n")

    # print("body:", comment.body_html)

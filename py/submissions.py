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

for submission in redditor.submissions.new(limit=1):
    print("author:", submission.author)
    print("clicked:", submission.clicked)
    print("created:", submission.created_utc)
    print("distinguished:", submission.distinguished)
    print("edited:", submission.edited)
    print("id:", submission.id)
    print("original:", submission.is_original_content)
    print("selfpost:", submission.is_self)
    print("locked:", submission.locked)
    print("name:", submission.name)
    print("# comments:", submission.num_comments)
    print("NSFW:", submission.over_18)
    print("permalink:", submission.permalink)
    print("saved:", submission.saved)
    print("upvotes:", submission.score)
    print("selftext:", submission.selftext)
    print("spoiler:", submission.spoiler)
    print("stickied:", submission.stickied)
    print("subreddit:", submission.subreddit)
    print("title:", submission.title)
    print("upvote ratio:", submission.upvote_ratio)
    print("url:", submission.url)
    print("\n")

    # print("comments:", submission.comments)
    # print("link flair id:", submission.link_flair_template_id)
    # print("link flair text:", submission.link_flair_text)
    # print("poll:", submission.poll_data)

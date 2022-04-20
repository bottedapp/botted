import praw
import difflib


class Sim:
    def __init__(self, username):
        self.reddit = praw.Reddit(
            client_id="GgPNctP2KQdth-iX6aMGUQ",
            client_secret="6zov1gDWJ8Ij60yH3L7q6N_LnPUZHA",
            user_agent="botted 0.0.1",
    )
        self.user = self.reddit.redditor(username)
    
    def similarities(self):
        Similarities = {}
        for Index, Comment in enumerate(self.user.comments.new(limit=20)):
            for CheckIndex, CheckComment in enumerate(self.user.comments.new(limit=20)):
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
            score = sum(TotalSimilarities) / len(TotalSimilarities)
        except ZeroDivisionError:
            score = 0.0
        return score



'''reddit = praw.Reddit(
    client_id="GgPNctP2KQdth-iX6aMGUQ",
    client_secret="6zov1gDWJ8Ij60yH3L7q6N_LnPUZHA",
    user_agent="botted 0.0.1",
)'''


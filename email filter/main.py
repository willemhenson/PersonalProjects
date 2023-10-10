import pandas as pd

df = pd.read_csv("spam.csv",usecols=["v1","v2"])

df = df.rename(columns={"v1": "Category", "v2": "Message"})
df.head(20)


clean = pd.DataFrame()
for column in df.columns:
  clean[column] = df[column].str.replace("[^a-zA-Z]+", ' ', regex=True)
  clean[column] = clean[column].str.lower()
clean.head(5)

train_data = clean.sample(frac = 0.75).copy()
test_data = clean.drop(train_data.index).copy()


all_words = {}
for message in train_data['Message']:
  words = message.split()
  for word in words:
    all_words[word] = 0

total_ham_num = 0
total_spam_num = 0
spam_count = all_words.copy()
ham_count = all_words.copy()
for index, message in enumerate(train_data['Message']):
  category = train_data.iloc[index]["Category"]
  words = message.split()
  used_words_per_message = []
  if category == "ham":
    total_ham_num += 1
    for word in words:
      if word not in used_words_per_message:
        ham_count[word] += 1
        used_words_per_message.append(word)
  if category == "spam":
    total_spam_num += 1
    for word in words:
      if word not in used_words_per_message:
        spam_count[word] += 1
        used_words_per_message.append(word)


word_freq = pd.DataFrame(columns=["Word","#Spam","#Ham"])
word_freq["Word"] = all_words.keys()
word_freq["#Spam"] = spam_count.values()
word_freq["#Ham"] = ham_count.values()

word_freq

from wordcloud import WordCloud
import matplotlib.pyplot as plt

wordcloud = WordCloud(width=800, height=800, background_color='white')
wordcloud.generate_from_frequencies(spam_count)
plt.figure(figsize=(8, 8), facecolor=None)
plt.imshow(wordcloud)
plt.axis("off")
plt.show()

word_prob = pd.DataFrame(columns=["Word","P(E|S)","P(E|¬S)"],)
k = 0.5
word_prob["Word"] = all_words.keys()
for index, row in word_prob.iterrows():
  row["P(E|S)"] = (spam_count[row["Word"]]+k)/(total_spam_num+2*k)
  row["P(E|¬S)"] = (ham_count[row["Word"]]+k)/(total_ham_num+2*k)
word_prob.head(5)


#assume P(S) = 0.5, P(¬S) = 0.5
prob_s = 0.5
prob_not_s = 1- prob_s
#word = "ghnvghndassdsdddsadasdasdsvh"
word = "winner"
#word = "free"
#word= "win"

prob_e_given_s = None
prob_e_given_not_s = None
for index, row in word_prob.iterrows():
  if row["Word"] == word:
    prob_e_given_s = row["P(E|S)"]
    prob_e_given_not_s = row["P(E|¬S)"]
    break

if prob_e_given_s:
  prob_s_given_e = (prob_e_given_s*prob_s)/(prob_e_given_s*prob_s+prob_e_given_not_s*prob_not_s)
else:
  prob_s_given_e = 0
  print("word not found in data set")
print("prob = {}".format(prob_s_given_e))

#assume P(S) = 0.4, P(¬S) = 0.6
prob_s = 0.5
prob_not_s = 1 - prob_s

several_words = "hello well done winner prize"
words = several_words.split()

prob_pi_s = 1
prob_pi_not_s = 1
for word in words:
  if word in word_prob["Word"].values:
    for index, row in word_prob.iterrows():
      if row["Word"] == word:
        prob_pi_s*=row["P(E|S)"]
        prob_pi_not_s*=row["P(E|¬S)"]
        break

prob_s_given_words = (prob_pi_s*prob_s)/(prob_pi_s*prob_s+prob_pi_not_s*prob_not_s)
prob_not_s_given_words = (prob_pi_not_s*prob_s)/(prob_pi_not_s*prob_s+prob_pi_s*prob_not_s)
print(prob_s_given_words)
print(prob_not_s_given_words)
if prob_s_given_words > prob_not_s_given_words:
  print("likely spam")
else:
  print("likely not spam")


word_prob = word_prob.set_index("Word")


import numpy as np

def isSpam(several_words):

  #assume P(S) = 0.4, P(¬S) = 0.6
  prob_s = 0.5
  prob_not_s = 1 - prob_s

  words = several_words.split()

  log_sum_s = np.log(prob_s)
  log_sum_not_s = np.log(prob_not_s)
  for word in words:
    try:
      log_sum_s += np.log(word_prob.loc[word]["P(E|S)"])
      log_sum_not_s += np.log(word_prob.loc[word]["P(E|¬S)"])
    except:
      pass
  if log_sum_s > log_sum_not_s:
    #print("likely spam")
    return True
  else:
    #print("likely not spam")
    return False    

words = "hello well done winner prize"

if isSpam(words):
  print("likely spam")
else:
  print("likely not spam")


match_spam = 0
match_ham = 0
thought_ham_is_spam = 0
thought_spam_is_ham = 0


for index, row in test_data.iterrows():
  guess = isSpam(row["Message"])
  if row["Category"] == "spam":
    #print("thought spam")
    if guess == True:
      match_spam+=1
    else:
      thought_ham_is_spam+=1
  elif row["Category"] == "ham":
    #print("thought ham")
    if guess == False:
      match_ham+=1
    else:
      thought_spam_is_ham+=1

total = match_spam+match_ham+thought_ham_is_spam+thought_spam_is_ham
print("match_spam {}".format(match_spam))
print("match_ham {}".format(match_ham))
print("thought_ham_is_spam {}".format(thought_ham_is_spam))
print("thought_spam_is_ham {}".format(thought_spam_is_ham))
print("Accuracy: {}".format((match_spam+match_ham)/total))
import tensorflow as tf 
import numpy as np 
from keras.models import Sequential
from keras.layers.core import Dense, Activation, Dropout
from keras.layers.recurrent import LSTM, SimpleRNN
from keras.layers.wrappers import TimeDistributed
from tqdm import tqdm
import sys

data = []
unique = []
ignore = ['\n', '®' , '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '/']
punctuation = ['.', '!', '?']

filename = sys.argv[1]

with open(filename + '.txt', 'r') as file:
    for line in file:
        for c in line:
            if c not in ignore:
                data.append(c)

unique = sorted(set(data))
print(unique)

low = -1
high = -1
for index, letter in enumerate(unique):
    if letter == 'A':
        low = index
    elif letter == 'a':
        high = index - 1


index_to_char = {index:char for index, char in enumerate(unique)}
char_to_index = {char:index for index, char in enumerate(unique)}
UNIQUE_SIZE = len(unique)
TIMESTEPS = 50
DATA_LEN = len(data)
DATA_NUM = int(DATA_LEN/TIMESTEPS)
HIDDEN_UNITS = 500
LAYER_NUM = 2
BATCH_SIZE = 50
GENERATE_LEN = 100
num_epochs = 0




X = np.zeros((DATA_NUM, TIMESTEPS, UNIQUE_SIZE))
Y = np.zeros((DATA_NUM, TIMESTEPS, UNIQUE_SIZE))

for i in range(0, DATA_NUM):
    X_sequence = data[i*TIMESTEPS: (i+1) * TIMESTEPS]
    X_sequence_index = [char_to_index[c] for c in X_sequence]
    input_sequence = np.zeros((TIMESTEPS, UNIQUE_SIZE))

    for t in range(TIMESTEPS):
        input_sequence[t][X_sequence_index[t]] = 1
    X[t] = input_sequence

    Y_sequence = data[i*TIMESTEPS+1: (i+1)*TIMESTEPS+1]
    Y_sequence_index = [char_to_index[value] for value in Y_sequence]
    target_sequence = np.zeros((TIMESTEPS, UNIQUE_SIZE))
    for t in range(TIMESTEPS):
        target_sequence[t][Y_sequence_index[t]] = 1
    Y[i] = target_sequence


model = Sequential()
model.add(LSTM(HIDDEN_UNITS, input_shape=(None, UNIQUE_SIZE), return_sequences=True))
for i in range(LAYER_NUM - 1):
    model.add(LSTM(HIDDEN_UNITS, return_sequences=True))
model.add(TimeDistributed(Dense(UNIQUE_SIZE)))
model.add(Activation('softmax'))
model.compile(loss="categorical_crossentropy", optimizer="rmsprop")


def generate_text(model, length):
    index = [np.random.randint(UNIQUE_SIZE)]
    #index = [int(np.random.uniform(low, high))]
    sentence = [index_to_char[index[-1]]]
    X = np.zeros((1, length, UNIQUE_SIZE))
    for i in tqdm(range(length)):
        X[0, i, :][index[-1]] = 1
        print(index_to_char[index[-1]], end="")
        index=np.argmax(model.predict(X[:, :i+1, :])[0], 1)
        sentence.append(index_to_char[index[-1]])
        #if index_to_char[index[-1]] in punctuation:
        #    break
    sentence.append('.')
    return ('').join(sentence)

if len(sys.argv) == 3:
    model.load_weights('Archive/' + sys.argv[1] + '_epoch_' + sys.argv[2] + '.hdf5')
    num_epochs = int(sys.argv[2])

while True:
    print('\n\n')
    model.fit(X, Y, batch_size=BATCH_SIZE, verbose=1, epochs=1)
    num_epochs += 1
    generate_text(model, GENERATE_LEN)
    if num_epochs % 10 == 0:
        model.save_weights('Archive/{}_epoch_{}.hdf5'.format(filename, num_epochs))

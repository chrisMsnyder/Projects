############################################
#
# Chris Snyder
# lok139
# vegas_classification.py
#
############################################
import tensorflow as tf
import numpy as np
import os
import pandas as pd
from tqdm import tqdm

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

learning_rate = 0.1
training_epochs = 1000

n_nodes = 100

# Runs the classifier
def run(df):
    # clean te data
    df.to_csv("testdata.csv")
    df_x = df.drop(['Hotel name', 'Score'], axis=1)
    train_x = df_x.values.tolist()
    df_y = df['Hotel name'].to_frame()
    df_y = df_y.join(pd.get_dummies(df_y['Hotel name']))
    df_y = df_y.drop(['Hotel name'], axis=1)
    train_y = df_y.values.tolist()
    test_x = [ [ 1, 0, 0, 0, 1, 1, 5, 0, 0, 1, 0, 0, 0, 0, 1, 0 ], [0,0,0,1,0,1,5,1,0,0,0,0,1,0,0,0]  ]
    test_y = [ [0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0] ]
    
    # initialize nodes
    n_input = 16
    n_hidden = 30
    n_output = 21
    
    #initialize placeholders
    X = tf.placeholder('float', [None, n_input])
    Y = tf.placeholder('float', [None, n_output])    

    # initialize weights and biases
    weight = { 'hidden': tf.Variable(tf.random_normal([n_input, n_hidden])),
                'output': tf.Variable(tf.random_normal([n_hidden, n_output]))}
    bias = { 'hidden': tf.Variable(tf.random_normal([n_hidden])),
             'output': tf.Variable(tf.random_normal([n_output]))}
    
    #initialize the neural network
    def neural_network(X, weight, bias):
        #layer one
        input_layer = tf.add(tf.matmul(X, weight['hidden']), bias['hidden'])
        input_layer = tf.nn.relu(input_layer)
        output_layer = tf.add(tf.matmul(input_layer, weight['output']), bias['output'])
        return output_layer
    #initialize cost, prediction and optimization procedures
    prediction = neural_network(X, weight, bias)
    cost = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits(labels=Y, logits=prediction))
    optimizer = tf.train.AdamOptimizer(learning_rate).minimize(cost)

    #Begin the session
    with tf.Session() as sess:
        sess.run(tf.global_variables_initializer())

        #loop thorugh the epochs
        for epoch in tqdm(range(training_epochs)):
                _, c = sess.run([optimizer, cost], feed_dict={X:train_x, Y:train_y})
        #determine the predictions
        correct_pred = sess.run(prediction, feed_dict={X:test_x})
        hotel_predict = sess.run(tf.argmax(prediction, 1), feed_dict={X:test_x})
        
        #list the answers to the questions
        for index, x in enumerate(hotel_predict):
            print('Question #' + str(index + 3) + ' = ' + str(df_y.columns[x]))

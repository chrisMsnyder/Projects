##############################################
#
# Chris Snyder
# lok139
# vegas_regression.py
#
#############################################

import tensorflow as tf
import numpy as np
import os
import pandas as pd
from tqdm import tqdm

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

learning_rate = 0.1
training_epochs = 200

#Run the regressor
def run(df):
    #clean the data
    #df.to_csv("testdata.csv")
    train_x_l = []
    for col in df:
        if col != 'Score':
            train_x_l.append(df[col])
    train_y = df['Score']
    #####################################################stay###################type
    test_x = [[0,1],[1,1],[0,0],[0,1],[1,1],[1,1],[3,5],[0,1],[0,0],[0,0],[1,0],[0,0],[0,0],[1,0],[0,0],[0,1]]
    test_y = [4]
    n_samples = 504

    #initialize placeholders
    X_in_list = []
    for x in train_x_l:
        X_in_list.append(tf.placeholder(tf.float32)) 
    Y = tf.placeholder(tf.float32, name = 'p_y')

    #Initilaize weights and biases
    W_list = []
    for x in train_x_l:
        W_list.append(tf.Variable(np.random.randn()))
    b = tf.Variable(np.random.randn())

    #dot product
    sum_list = []
    for (x, w) in zip(X_in_list, W_list):
        sum_list.append(tf.multiply(x, w))

    # initialize prediction, cost and optimization procedures
    pred = tf.add_n(sum_list) + b
    cost = tf.reduce_mean(tf.pow(Y - pred, 2))/(2*n_samples)
    optimizer = tf.train.GradientDescentOptimizer(learning_rate).minimize(cost)

    init = tf.global_variables_initializer()

    #Begin the session
    with tf.Session() as sess:
        sess.run(init)
        #Begin learning
        print("Learning...")
        train_with_y = train_x_l
        train_with_y.append(train_y)
        X_with_Y = X_in_list
        X_with_Y.append(Y)
        trainlist = list(zip(*train_with_y))
        for epoch in tqdm(range(training_epochs)):
            for xlist in trainlist:
                feeddict = buildDict(X_in_list, xlist)
                sess.run(optimizer, feed_dict=feeddict)

        #Begin testing
        print("Testing...")
        
        test_xy = test_x
        test_xy.append(test_y)
        feeddict = buildDict(X_in_list, test_xy)
        #final_cost = sess.run(cost, feed_dict = feeddict)
        feedict = buildDict(X_in_list, test_x)
        pred_values = sess.run(pred, feed_dict = feeddict)
        for i, value in enumerate(pred_values):
            print("Question #" + str(i+1) + " = " + str(round(value, 1)))

#Build a dictionary to feed
def buildDict(X_in_list, train_x):
    newDict = dict(zip(X_in_list, train_x))
    return newDict

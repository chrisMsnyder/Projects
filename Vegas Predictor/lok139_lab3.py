################################################
#
# Chris Snyder
# lok139
# lok139_lab3.py
#
###############################################
import tensorflow as tf
import numpy as np
import pandas as pd
import os
import csv
import pandas as pd
import vegas_regression as vr
import vegas_classification as vc

os.environ["TF_CPP_MIN_LOG_LEVEL"] = '2'
#import data
df = pd.read_csv('vegas2.csv')

#clean data
df = df.join(pd.get_dummies(df['Period of stay']))
df = df.join(pd.get_dummies(df['Traveler type']))
#df = df.join(pd.get_dummies(df['Hotel name']))
df = df.drop(['Nr. reviews', 'Nr. hotel reviews', 'Period of stay', 'Traveler type','Review weekday', 'Review month', 'Member years', 'User continent', 'Nr. rooms', 'Helpful votes', 'Nr. reviews', 'User country'], axis=1)
df = df.replace('YES', 1)
df = df.replace('NO', 0)
df_r = df.drop(['Hotel name'], axis=1)
vr.run(df_r)
vc.run(df)

# fashion_testing_6.py : Tensorflow 모델 불러오기 예제1 (만들어진 모델 가져오기)
# fashion_testing_3.py : Tensorflow 모델 불러오기 예제1 (만들어진 모델 가져오기)
# fashion_testing_3_image.py : Tensorflow 모델 불러오기 이미지 하나 parse로 전달해야함. (만들어진 모델 가져오기)
#                         python fashion_testing_3_image.py --image test.jpg 로 사용  

import tensorflow as tf
from PIL import Image
import glob
import numpy as np
from numpy import array
import os
import time
import argparse

def upper_deep(folder, img_name): # folder="D:\\spring_kkr\\", img_name="test.jpg"
    image_width = 64
    image_height = 64
    image_label = []
    All_Img = []
    image_file_list = ["hood_T", "long_T", "shirt", "short_T", "sleeveless", "vest"]
    image_num = len(image_file_list)

    label_dict = {
      0: 'hood_T',
     1: 'long_T',
     2: 'shirt',
     3: 'short_T',
     4: 'sleeveless',
     5: 'vest'
    }


    # 모델 정의
    X=tf.compat.v1.placeholder(tf.float32, shape = [None, image_width, image_height, 3], name='X')
    Y=tf.compat.v1.placeholder(tf.float32, shape = [None,image_num], name='Y')
    keep_prob = tf.compat.v1.placeholder(tf.float32)

    W1 = tf.Variable(tf.random.normal([3, 3, 3, 128], stddev=0.01), name='W1')
    L1 = tf.nn.conv2d(X, W1, strides=[1, 1, 1, 1], padding='SAME')
    L1 = tf.nn.relu(L1)
    L1 = tf.nn.max_pool2d(L1, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')

    W2 = tf.Variable(tf.random.normal([3, 3, 128, 64], stddev=0.01), name='W2')
    L2 = tf.nn.conv2d(L1, W2, strides=[1, 1, 1, 1], padding='SAME')
    L2 = tf.nn.relu(L2)
    L2 = tf.nn.max_pool2d(L2, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')

    W3 = tf.Variable(tf.random.normal([3, 3, 64, 32], stddev=0.01), name='W3')
    L3 = tf.nn.conv2d(L2, W3, strides=[1, 1, 1, 1], padding='SAME')
    L3 = tf.nn.relu(L3)
    L3 = tf.nn.max_pool2d(L3, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')

    W4 = tf.Variable(tf.random.normal([ 8 * 8 * 32, 32], stddev=0.01), name='W4')
    L4 = tf.reshape(L3, [ -1, 8 * 8 * 32])
    L4 = tf.matmul(L4, W4)
    L4 = tf.nn.relu(L4)
    L4 = tf.nn.dropout(L4, rate=1-keep_prob)  # rate=dropout할 확률(x가 삭제될 확률 0.3정도가 적당)

    W5 = tf.Variable(tf.random.normal([32, image_num], stddev=0.01), name='W5')
    model = tf.matmul(L4, W5)

    # saver 객체 생성
    sess = tf.compat.v1.Session()
    saver = tf.compat.v1.train.Saver()
    
    sess.run(tf.compat.v1.global_variables_initializer())
    #saver.restore(sess, './my_model/my_fashion_model_0819_00001_0819_L5-1000') # testdata 정확도 : 0.71, cost : 0.16
    saver.restore(sess, './my_model/my_fashion_model_1027_000001_1027upper_L5-100')
    
    #Test
    image_label = []
    All_Img = []

    image_name = folder+img_name
    image = Image.open(image_name)
    image = image.resize((image_width, image_height))
    All_Img.append(array(image))
    real_value = 0 # real_value = 이미지의 진짜 결과값
    image_label.append(real_value)

    label=np.eye(image_num)[image_label]


    #print("====Test====")
    predict = tf.argmax(model, 1)
    original = tf.argmax(Y, 1)
    is_correct = tf.equal(predict, original)

    accuracy = tf.reduce_mean(tf.cast(is_correct, tf.float32))

    #이미지 결과 보내기
    result = label_dict[sess.run(predict, feed_dict={X: All_Img, keep_prob: 1})[0]]
    sess.close()
    tf.compat.v1.reset_default_graph()
    return result


def lower_deep(folder, img_name):
    image_width = 64
    image_height = 64
    image_label = []
    All_Img = []
    #image_file_list = ["leggings", "long_pants", "long_skirt", "mini_skirt", "short_pants"]
    image_file_list = ["long_pants", "long_skirt", "mini_skirt", "short_pants"]
    image_num = len(image_file_list)
    
    label_dict = {
     0: 'long_pants',
     1: 'long_skirt',
     2: 'mini_skirt',
     3: 'short_pants'
    }

    # 모델 정의
    X=tf.compat.v1.placeholder(tf.float32, shape = [None, image_width, image_height, 3], name='X')
    Y=tf.compat.v1.placeholder(tf.float32, shape = [None,image_num], name='Y')
    keep_prob = tf.compat.v1.placeholder(tf.float32)

    W1 = tf.Variable(tf.random.normal([3, 3, 3, 128], stddev=0.01), name='W1')
    L1 = tf.nn.conv2d(X, W1, strides=[1, 1, 1, 1], padding='SAME')
    L1 = tf.nn.relu(L1)
    L1 = tf.nn.max_pool2d(L1, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')

    W2 = tf.Variable(tf.random.normal([3, 3, 128, 64], stddev=0.01), name='W2')
    L2 = tf.nn.conv2d(L1, W2, strides=[1, 1, 1, 1], padding='SAME')
    L2 = tf.nn.relu(L2)
    L2 = tf.nn.max_pool2d(L2, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')

    W3 = tf.Variable(tf.random.normal([3, 3, 64, 32], stddev=0.01), name='W3')
    L3 = tf.nn.conv2d(L2, W3, strides=[1, 1, 1, 1], padding='SAME')
    L3 = tf.nn.relu(L3)
    L3 = tf.nn.max_pool2d(L3, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')

    W4 = tf.Variable(tf.random.normal([ 8 * 8 * 32, 32], stddev=0.01), name='W4')
    L4 = tf.reshape(L3, [ -1, 8 * 8 * 32])
    L4 = tf.matmul(L4, W4)
    L4 = tf.nn.relu(L4)
    L4 = tf.nn.dropout(L4, rate=1-keep_prob)  # rate=dropout할 확률(x가 삭제될 확률 0.3정도가 적당)

    W5 = tf.Variable(tf.random.normal([32, image_num], stddev=0.01), name='W5')
    model = tf.matmul(L4, W5)

    # saver 객체 생성
    sess = tf.compat.v1.Session()
    saver = tf.compat.v1.train.Saver()

    sess.run(tf.compat.v1.global_variables_initializer())
    #saver.restore(sess, './my_model/my_fashion_model_0928_00005_0928lower_L5-1000') # testdata 정확도 : 0.71, cost : 0.16
    #saver.restore(sess, './my_model/my_fashion_model_1101_00001_1101lower_L5-1000') # testdata 정확도 : 1.0, cost : 0.01
    saver.restore(sess, './my_model/my_fashion_model_1101_00001_1101lower_L5-100') # testdata 정확도 : 1.0, cost : 0.01

    #Test
    image_label = []
    All_Img = []

    image_name = folder+img_name
    image = Image.open(image_name)
    image = image.resize((image_width, image_height))
    All_Img.append(array(image))
    real_value = 0 # real_value = 이미지의 진짜 결과값
    image_label.append(real_value)

    label=np.eye(image_num)[image_label]


    #print("====Test====")
    predict = tf.argmax(model, 1)
    original = tf.argmax(Y, 1)
    is_correct = tf.equal(predict, original)

    accuracy = tf.reduce_mean(tf.cast(is_correct, tf.float32))

    #이미지 결과 보내기
    result = label_dict[sess.run(predict, feed_dict={X: All_Img, keep_prob: 1})[0]]
    sess.close()
    tf.compat.v1.reset_default_graph()
    return result

def outer_deep(folder, img_name):
    image_width = 64
    image_height = 64
    image_label = []
    All_Img = []
    image_file_list = ["cardigan", "coat", "hood_zipup", "jacket", "jumper", "padding"]
    image_num = len(image_file_list)

    label_dict = {
      0: 'cardigan',
     1: 'coat',
     2: 'hood_zipup',
     3: 'jacket',
     4: 'jumper',
     5: 'padding'
    }

    # 모델 정의
    X=tf.compat.v1.placeholder(tf.float32, shape = [None, image_width, image_height, 3], name='X')
    Y=tf.compat.v1.placeholder(tf.float32, shape = [None,image_num], name='Y')
    keep_prob = tf.compat.v1.placeholder(tf.float32)

    W1 = tf.Variable(tf.random.normal([3, 3, 3, 128], stddev=0.01), name='W1')
    L1 = tf.nn.conv2d(X, W1, strides=[1, 1, 1, 1], padding='SAME')
    L1 = tf.nn.relu(L1)
    L1 = tf.nn.max_pool2d(L1, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')

    W2 = tf.Variable(tf.random.normal([3, 3, 128, 64], stddev=0.01), name='W2')
    L2 = tf.nn.conv2d(L1, W2, strides=[1, 1, 1, 1], padding='SAME')
    L2 = tf.nn.relu(L2)
    L2 = tf.nn.max_pool2d(L2, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')

    W3 = tf.Variable(tf.random.normal([3, 3, 64, 32], stddev=0.01), name='W3')
    L3 = tf.nn.conv2d(L2, W3, strides=[1, 1, 1, 1], padding='SAME')
    L3 = tf.nn.relu(L3)
    L3 = tf.nn.max_pool2d(L3, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')

    W4 = tf.Variable(tf.random.normal([ 8 * 8 * 32, 32], stddev=0.01), name='W4')
    L4 = tf.reshape(L3, [ -1, 8 * 8 * 32])
    L4 = tf.matmul(L4, W4)
    L4 = tf.nn.relu(L4)
    L4 = tf.nn.dropout(L4, rate=1-keep_prob)  # rate=dropout할 확률(x가 삭제될 확률 0.3정도가 적당)

    W5 = tf.Variable(tf.random.normal([32, image_num], stddev=0.01), name='W5')
    model = tf.matmul(L4, W5)

    # saver 객체 생성
    sess = tf.compat.v1.Session()
    saver = tf.compat.v1.train.Saver()

    sess.run(tf.compat.v1.global_variables_initializer())
    saver.restore(sess, './my_model/my_fashion_model_0927_00005_0927padding_L5-1000') # testdata 정확도 : 0.71, cost : 0.16

    #Test
    image_label = []
    All_Img = []

    image_name = folder+img_name
    image = Image.open(image_name)
    image = image.resize((image_width, image_height))
    All_Img.append(array(image))
    real_value = 0 # real_value = 이미지의 진짜 결과값
    image_label.append(real_value)

    label=np.eye(image_num)[image_label]


    #print("====Test====")
    predict = tf.argmax(model, 1)
    original = tf.argmax(Y, 1)
    is_correct = tf.equal(predict, original)

    accuracy = tf.reduce_mean(tf.cast(is_correct, tf.float32))

    #이미지 결과 보내기
    result = label_dict[sess.run(predict, feed_dict={X: All_Img, keep_prob: 1})[0]]
    sess.close()
    tf.compat.v1.reset_default_graph()
    return result


def color_deep(folder, img_name):
    image_width = 64
    image_height = 64
    image_label = []
    All_Img = []
    image_file_list = ["beige", "black", "blue", "brown", "fluorescent", "gray", "green", "light_blue", "orange", "pink", "purple", "red", "white", "yellow"]
    image_num = len(image_file_list)

    label_dict = {
        0: 'beige',
        1: 'black',
        2: 'blue',
        3: 'brown',
        4: 'fluorescent',
        5: 'gray',
        6: 'green',
        7: 'light_blue',
        8: 'orange',
        9: 'pink',
        10: 'purple',
        11: 'red',
        12: 'white',
        13: 'yellow'
    }

    # 모델 정의
    X=tf.compat.v1.placeholder(tf.float32, shape = [None, image_width, image_height, 3], name='X')
    Y=tf.compat.v1.placeholder(tf.float32, shape = [None,image_num], name='Y')
    keep_prob = tf.compat.v1.placeholder(tf.float32)

    W1 = tf.Variable(tf.random.normal([3, 3, 3, 128], stddev=0.01), name='W1')
    L1 = tf.nn.conv2d(X, W1, strides=[1, 1, 1, 1], padding='SAME')
    L1 = tf.nn.relu(L1)
    L1 = tf.nn.max_pool2d(L1, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')

    W2 = tf.Variable(tf.random.normal([3, 3, 128, 64], stddev=0.01), name='W2')
    L2 = tf.nn.conv2d(L1, W2, strides=[1, 1, 1, 1], padding='SAME')
    L2 = tf.nn.relu(L2)
    L2 = tf.nn.max_pool2d(L2, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')

    W3 = tf.Variable(tf.random.normal([3, 3, 64, 32], stddev=0.01), name='W3')
    L3 = tf.nn.conv2d(L2, W3, strides=[1, 1, 1, 1], padding='SAME')
    L3 = tf.nn.relu(L3)
    L3 = tf.nn.max_pool2d(L3, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')

    W4 = tf.Variable(tf.random.normal([ 8 * 8 * 32, 32], stddev=0.01), name='W4')
    L4 = tf.reshape(L3, [ -1, 8 * 8 * 32])
    L4 = tf.matmul(L4, W4)
    L4 = tf.nn.relu(L4)
    L4 = tf.nn.dropout(L4, rate=1-keep_prob)  # rate=dropout할 확률(x가 삭제될 확률 0.3정도가 적당)

    W5 = tf.Variable(tf.random.normal([32, image_num], stddev=0.01), name='W5')
    model = tf.matmul(L4, W5)

    # saver 객체 생성
    sess = tf.compat.v1.Session()
    saver = tf.compat.v1.train.Saver()

    sess.run(tf.compat.v1.global_variables_initializer())
    saver.restore(sess, './my_model/my_fashion_model_0929_00001_0929color_L5-1000')

    #Test
    image_label = []
    All_Img = []


    image_name = folder+img_name
    image = Image.open(image_name)
    image = image.resize((image_width, image_height))
    All_Img.append(array(image))
    real_value = 0 # real_value = 이미지의 진짜 결과값
    image_label.append(real_value)

    label=np.eye(image_num)[image_label]


    #print("====Test====")
    predict = tf.argmax(model, 1)
    original = tf.argmax(Y, 1)
    is_correct = tf.equal(predict, original)

    accuracy = tf.reduce_mean(tf.cast(is_correct, tf.float32))

    #이미지 결과 보내기
    result = label_dict[sess.run(predict, feed_dict={X: All_Img, keep_prob: 1})[0]]
    sess.close()
    tf.compat.v1.reset_default_graph()
    return result


def pattern_deep(folder, img_name):
    image_width = 64
    image_height = 64
    image_label = []
    All_Img = []
    image_file_list = ["check", "no", "pattern", "stripe"]
    image_num = len(image_file_list)

    label_dict = {
      0: 'check',
     1: 'no',
     2: 'pattern',
     3: 'stripe'
    }

    # 모델 정의
    X=tf.compat.v1.placeholder(tf.float32, shape = [None, image_width, image_height, 3], name='X')
    Y=tf.compat.v1.placeholder(tf.float32, shape = [None,image_num], name='Y')
    keep_prob = tf.compat.v1.placeholder(tf.float32)

    W1 = tf.Variable(tf.random.normal([3, 3, 3, 128], stddev=0.01), name='W1')
    L1 = tf.nn.conv2d(X, W1, strides=[1, 1, 1, 1], padding='SAME')
    L1 = tf.nn.relu(L1)
    L1 = tf.nn.max_pool2d(L1, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')

    W2 = tf.Variable(tf.random.normal([3, 3, 128, 64], stddev=0.01), name='W2')
    L2 = tf.nn.conv2d(L1, W2, strides=[1, 1, 1, 1], padding='SAME')
    L2 = tf.nn.relu(L2)
    L2 = tf.nn.max_pool2d(L2, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')

    W3 = tf.Variable(tf.random.normal([3, 3, 64, 32], stddev=0.01), name='W3')
    L3 = tf.nn.conv2d(L2, W3, strides=[1, 1, 1, 1], padding='SAME')
    L3 = tf.nn.relu(L3)
    L3 = tf.nn.max_pool2d(L3, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')

    W4 = tf.Variable(tf.random.normal([ 8 * 8 * 32, 32], stddev=0.01), name='W4')
    L4 = tf.reshape(L3, [ -1, 8 * 8 * 32])
    L4 = tf.matmul(L4, W4)
    L4 = tf.nn.relu(L4)
    L4 = tf.nn.dropout(L4, rate=1-keep_prob)  # rate=dropout할 확률(x가 삭제될 확률 0.3정도가 적당)

    W5 = tf.Variable(tf.random.normal([32, image_num], stddev=0.01), name='W5')
    model = tf.matmul(L4, W5)


    # saver 객체 생성
    sess = tf.compat.v1.Session()
    saver = tf.compat.v1.train.Saver()

    sess.run(tf.compat.v1.global_variables_initializer())
    saver.restore(sess, './my_model/my_fashion_model_1002_00005_1002pattern_L5-1000') # testdata 정확도 : 0.71, cost : 0.16

    #Test
    image_label = []
    All_Img = []

    image_name = folder+img_name
    image = Image.open(image_name)
    image = image.resize((image_width, image_height))
    All_Img.append(array(image))
    real_value = 0 # real_value = 이미지의 진짜 결과값
    image_label.append(real_value)

    label=np.eye(image_num)[image_label]


    #print("====Test====")
    predict = tf.argmax(model, 1)
    original = tf.argmax(Y, 1)
    is_correct = tf.equal(predict, original)

    accuracy = tf.reduce_mean(tf.cast(is_correct, tf.float32))

    #이미지 결과 보내기
    result = label_dict[sess.run(predict, feed_dict={X: All_Img, keep_prob: 1})[0]]
    sess.close()
    tf.compat.v1.reset_default_graph()
    return result


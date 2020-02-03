# image_padding = 하나의 이미지 주변에 padding을 넣고 300*300으로 변환, 저장하기.(배경흰색)
# python image_padding_one_3.py --image test.jpg
# 
import cv2
import numpy as np
import fashion_pose_image2 as fashion_pose

# test.jpg 이미지에 패딩입혀 spring_kkr 밑에 저장.
def image_padding(folder, image): # folder = "D:\\spring_kkr\\", image = "test.jpg"
    # 이미지 path 설정
    filename = folder+image     # spring_kkr\\test.jpg
    save_filename = folder+image
    img = cv2.imread(filename)

    cv2.imshow("img_original", img)
    # 이미지에서 옷 뽑아내기

    # 이미지의 x, y가 300이 넘을 경우 작게해주기
    percent = 1
    if(img.shape[1] > img.shape[0]) :       # 이미지의 가로가 세보다 크면 가로를 300으로 맞추고 세로를 비율에 맞춰서
        percent = 300/img.shape[1]
    else :
        percent = 300/img.shape[0]

    img = cv2.resize(img, dsize=(0, 0), fx=percent, fy=percent, interpolation=cv2.INTER_LINEAR)

    # 이미지 범위 지정
    y,x,h,w = (0,0,img.shape[0], img.shape[1])

    # 그림 주변에 흰색으로 칠하기
    w_x = (300-(w-x))/2  # w_x = (300 - 그림)을 뺀 나머지 영역 크기 [ 그림나머지/2 [그림] 그림나머지/2 ]
    h_y = (300-(h-y))/2

    if(w_x < 0):         # 크기가 -면 0으로 지정.
        w_x = 0
    elif(h_y < 0):
        h_y = 0

    M = np.float32([[1,0,w_x], [0,1,h_y]])  #(2*3 이차원 행렬)
    img_re = cv2.warpAffine(img, M, (300, 300), borderValue=(255, 255, 255))
    cv2.imshow("img_re", img_re)

    # 이미지 저장하기
    cv2.imwrite(save_filename, img_re)

    print("save new test.jpg with padding")
    #cv2.waitKey(0)
    cv2.destroyAllWindows()
    
# test.jpg 이미지 상하의 나눠 spring_kkr 밑에 upper.jpg, lower.jpg로 저장
def image_divide(folder, image):
    fashion_pose.divide_upper_lower(folder, image)

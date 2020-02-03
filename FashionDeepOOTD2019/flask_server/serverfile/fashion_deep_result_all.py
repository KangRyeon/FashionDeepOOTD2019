# 사진이 들어오면 upper인지 lower인지 outer인지 보고 딥러닝 결과 가져옴
# color, pattern 딥러닝 결과 가져옴
# upper : upper, color, pattern 세개 해야함.
# python fashion_deep_result.py --category upper --image test.jpg


# 사진 들어오면 upper인지 lower인지 outer인지 보고 딥러닝 결과만 가져옴
import fashion_pose_image2 as fp   # 내가만든거
import fashion_deep as deep        # 내가만든거
import os
import argparse
import threading


# 딥러닝 후 hood_T,yellow,no 반환
def fashion_deep_result_all(category, folder, filename):
    # category = upper / lower / outer
    # folder = "D:\\spring_kkr\\"
    # image = "test.jpg"

    low_category=""
    color=""
    pattern=""

    # Low Category 딥러닝
    if(category == "upper"):
        low_category = deep.upper_deep(folder, filename)
    elif(category == "outer"):
        low_category = deep.outer_deep(folder, filename)
    elif(category == "lower"):
        low_category = deep.lower_deep(folder, filename)

    # 색 딥러닝
    color = deep.color_deep(folder, filename)

    # 패턴 딥러닝
    pattern = deep.pattern_deep(folder, filename)

    # result = hood_T,white,no
    result = low_category + "," + color + "," + pattern

    print("result = ", low_category)
    return result

#if __name__ == '__main__':
#    fashion_deep_result_all("upper", "test.jpg")

from flask import Flask, render_template, request
import fashion_deep_result_all as fashion_deep
import image_processing as image_proc
import fashion_retrain as fashion_retrain
app = Flask(__name__)
app.debug = True

# fashion_deep_result_all, fashion_deep, image_processing, fashion_pose_image2 필요
@app.route('/')
def index():
    return render_template('index.html')

@app.route('/info')
def info():
    return render_template('info.html')


# test.jpg 이미지에 패딩입혀 다시저장
@app.route('/image_padding')
def image_padding():
    # category = upper / lower / outer
    # image = "test.jpg"
    folder = request.args.get("folder") # "D:\\spring_kkr\\"
    image = request.args.get('image')   # "test.jpg"
    
    image_proc.image_padding(folder,image) # test.jpg 이미지에 패딩입혀 spring_kkr 밑에 저장.
    return "ok"


# test.jpg 이미지 패딩입혀 저장, 상하의 나눠 그것대해 딥러닝 돌려 보냄.
@app.route('/image_divide_deep')
def image_divide_deep():
    # category = upper / lower / outer
    # image = "test.jpg"
    folder = request.args.get("folder") # "D:\\spring_kkr\\"
    image = request.args.get('image')   # "test.jpg"
    
    image_proc.image_padding(folder, image) # test.jpg 이미지에 패딩입혀 spring_kkr 밑에 저장.

    image_proc.image_divide(folder, image) # test.jpg 이미지를 상하의 나눠 spring_kkr 밑에 upper.jpg, lower.jpg로 저장

    # hood_T,yellow,no,long_pants,black,no 반환
    result = fashion_deep.fashion_deep_result_all("upper", folder, "upper.jpg")+","+fashion_deep.fashion_deep_result_all("lower", folder, "lower.jpg")
    return result



# 받은 이미지를 딥러닝 돌려 결과반환
@app.route('/getDeepResult')
def getDeepResult():
    # category = upper / lower / outer
    # image = "test.jpg"
    category = request.args.get("category") # upper
    folder = request.args.get("folder")     # D:\\spring_kkr\\
    image = request.args.get('image')       # test.jpg
    
    result = fashion_deep.fashion_deep_result_all(category, folder, image) # hood_T,yellow,no 반환
    return result


# 받은 이미지를 딥러닝 돌려 결과반환
@app.route('/retrainModel')
def retrainModel():
    # category = upper / lower / outer
    # image = "test.jpg"
    category = request.args.get("category") # upper
    folder = request.args.get("folder")     # D:\\spring_kkr\\
    image = request.args.get('image')       # test.jpg
    real_value = request.args.get('real_value')       # real_value

    result = ""
    if(category == "upper"):
        result = fashion_retrain.upper_retrain(folder, image, real_value)
    elif(category == "lower"):
        result = fashion_retrain.lower_retrain(folder, image, real_value)
        
    return result # "ok" 반환


@app.route('/python2', methods=['POST', 'GET'])
def python2():
    return "hi i'm python"

if __name__ == '__main__':
   app.run(debug = True, threaded=True)

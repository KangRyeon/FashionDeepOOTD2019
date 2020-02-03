package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class PythonExec {

	private static String python_folder = null;
	private static String server_folder = null;

	public PythonExec(String folder, String s_folder) {
		python_folder = folder; // python 폴더 경로 : "D:\\python_D\\fashion_project\\serverfile\\"
		server_folder = s_folder; // server 폴더 경로 : "D:\\spring_kkr\\"
	}

	// 패딩입힌 이미지를 D:\\spring_kkr\\에 저장
	// python image_padding_one_3.py --image test.jpg : test.jpg에 padding입힘
	static void savePaddingImage(String image) { // image = "test.jpg"
		URL url = null;
		URLConnection urlConn = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		String resData = null;

		String rs = null;
		System.out.println("실행");
		try {
			// category, image 파일 이름을 보냄. (D:\\spring_kkr\\에 있는 test.jpg대해 패딩입힐 것.)
			url = new URL("http://127.0.0.1:5000/image_padding?folder=" + server_folder + "&image=" + image);
			urlConn = url.openConnection();
			urlConn.setDoOutput(true);

			br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			System.out.println("실행3");
			// 예측받아온 값을 읽어줌.
			while ((resData = br.readLine()) != null) {
				rs = resData;
			}
			System.out.println("받아온 값 : " + rs);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// python divide_upper_lower.py : test.jpg를 상의,하의로 나눠서 padding 입혀 저장
	static String divideUpperLowerDeep(String image) { // image = "test.jpg"
		URL url = null;
		URLConnection urlConn = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		String resData = null;

		String rs = null;
		System.out.println("실행");
		try {
			// category, image 파일 이름을 보냄.
			url = new URL("http://127.0.0.1:5000/image_divide_deep?folder=" + server_folder + "&image=" + image);
			urlConn = url.openConnection();
			urlConn.setDoOutput(true);

			br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			System.out.println("실행3");
			// 예측받아온 값을 읽어줌.
			while ((resData = br.readLine()) != null) {
				rs = resData;
			}
			System.out.println("받아온 값 : " + rs);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// python 파일 내에서 spring_kkr 밑에 사진 저장함.
	// python fashion_deep_result.py --category upper --image upper.jpg
	static String getDeepResult(String category, String image) { // category=outer, image=test.jpg

		URL url = null;
		URLConnection urlConn = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		String resData = null;

		String rs = null;

		if (category == null) {
			category = "upper";
		}
		System.out.println("실행");
		try {
			// category, image 파일 이름을 보냄.
			url = new URL("http://127.0.0.1:5000/getDeepResult?category=" + category + "&folder=" + server_folder + "&image=" + image);
			// url = new URL("http://127.0.0.1:5000/python2");
			urlConn = url.openConnection();
			urlConn.setDoOutput(true);

			br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			System.out.println("실행3");
			// 예측받아온 값을 읽어줌.
			while ((resData = br.readLine()) != null) {
				rs = resData;
			}
			System.out.println("받아온 값 : " + rs);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rs;
	}
	
	static String retrainModel(String category, String folder, String image, String real_value) {
		URL url = null;
		URLConnection urlConn = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		String resData = null;
		String rs = null;


		System.out.println("실행");
		try {
			// category, image 파일 이름을 보냄.
			url = new URL("http://127.0.0.1:5000/retrainModel?category=" + category + "&folder=" + server_folder + "&image=" + image + "&real_value="+real_value);
			// url = new URL("http://127.0.0.1:5000/python2");
			urlConn = url.openConnection();
			urlConn.setDoOutput(true);

			br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			System.out.println("실행3");
			// 예측받아온 값을 읽어줌.
			while ((resData = br.readLine()) != null) {
				rs = resData;
			}
			System.out.println("받아온 값 : " + rs);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rs;
		
	}
}

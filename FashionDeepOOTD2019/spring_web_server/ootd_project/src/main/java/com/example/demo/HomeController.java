package com.example.demo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import dao.ClothesDao;
import dao.FashionSetDao;
import dao.MemberDao;
import dto.Clothes;
import dto.FashionSet;
import dto.Member;

@Controller
public class HomeController {
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private ClothesDao clothesDao;
	@Autowired
	private FashionSetDao fashionSetDao;
	@Autowired
	private PythonExec pythonExec;

	private String server_folder = "D:\\spring_kkr\\"; // test.jpg 저장될 폴더.
	// test용
	@RequestMapping(value = "/home")
	public String home(Model model, @RequestParam(value = "id", required = false) String id) {
		model.addAttribute("id", id);
		System.out.println("받아온 id = " + id);
		return "home";
	}

	// test용 id와 category에 따른 low category 개수 가져옴(후드티, 롱티, 폴라티, 셔츠티, ...)
	@RequestMapping(value = "/dbtest")
	public String dbtest(Model model) {

		String category = "upper";
		String id = "test";

		String[] outer = { "cardigan", "jacket", "padding", "coat", "jumper", "hood_zipup" };
		String[] upper = { "hood_T", "long_T", "shirt", "short_T", "sleeveless", "vest" };
		String[] lower = { "long_pants", "short_pants", "mini_skirt", "long_skirt" };
		String[] onepeace = { "long_arm_mini_onepeace", "long_arm_long_onepeace", "short_arm_mini_onepeace",
				"short_arm_long_onepeace" };
		String[] etc = { "bag", "cap", "shoes", "accessory" };

		String[] low_category = new String[10];
		if (category.equals("outer")) {
			low_category = outer;
		} else if (category.equals("upper")) {
			low_category = upper;
		} else if (category.equals("lower")) {
			low_category = lower;
		} else if (category.equals("etc")) {
			low_category = etc;
		}

		System.out.println("id와 category에 따른 low_category들의 옷 개수 가져옴");
		int result[] = clothesDao.selectLowCategoryTotalById(id, category);

		System.out.println("JSON으로 보냄: hood_T " + result[0] + "개, long_T " + result[1] + "개, shirt = " + result[2]
				+ "개, short_T " + result[3]);

		return "home";
	}

	@ResponseBody // 리턴되는 값이 View(jsp파일 등)를 통해 출력이 아닌 http body에 직접 쓰여지게 됨
	@RequestMapping(value = "/flaskTest") // json.do // 객체로
	public JSONObject flaskTest() throws IOException {

		// 예측받기.
		String result = pythonExec.getDeepResult("upper", "test.jpg");

		// 넘어오는게 hood_T,yellow,no 이므로 split
		String[] results = result.split(",");
		String low_category, color, pattern;
		low_category = results[0];
		color = results[1];
		pattern = results[2];

		// 결과값 JSON으로 보내기
		JSONObject jsonMain = new JSONObject(); // json 객체 [{변수명:값, 변수명:값}
		JSONArray jArray = new JSONArray(); // json 배열

		JSONObject row = new JSONObject();
		row.put("low_category", low_category);
		row.put("color", color);
		row.put("pattern", pattern);

		jArray.add(0, row);
		jsonMain.put("deep_result", jArray);
		return jsonMain;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	// 넘겨받은 id, pw따라 로그인 체크 후 맞으면 ok, 틀리면 no 전송
	@SuppressWarnings({ "unchecked", "null" })
	@ResponseBody // 리턴되는 값이 View(jsp파일 등)를 통해 출력이 아닌 http body에 직접 쓰여지게 됨
	@RequestMapping(value = "/loginCheck", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public JSONObject loginCheck(Model model, @RequestParam(value = "id") String id,
			@RequestParam(value = "password") String pw) {

		System.out.println("loginCheck 실행");

		// id따른 Member 정보 받아옴
		Member result = memberDao.selectMemberById(id);

		// 결과값 JSON으로 보내기
		JSONObject jsonMain = new JSONObject(); // json 객체 [{변수명:값, 변수명:값}
		JSONArray jArray = new JSONArray(); // json 배열

		JSONObject row = new JSONObject();

		if (pw.equals(result.getPassword())) {
			System.out.println("로그인 잘됨");
			row.put("result", "ok");
		} else {
			System.out.println("로그인 잘못됨");
			row.put("result", "no");
		}
		jArray.add(0, row);
		jsonMain.put("login_result", jArray);
		return jsonMain;
	}

	// 넘겨받은 id, pw, nickname 따라 insert 후 맞으면 ok, 틀리면 no 전송
	@SuppressWarnings({ "unchecked", "null" })
	@ResponseBody
	@RequestMapping(value = "/insertToMembers", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public JSONObject insertToMembers(Model model, @RequestParam(value = "id") String id,
			@RequestParam(value = "password") String pw, @RequestParam(value = "nickname") String nickname) {

		System.out.println("insertToMembers 실행");

		// member 추가하기
		int result = memberDao.insertToMember(id, pw, nickname);

		// 결과값 JSON으로 보내기
		JSONObject jsonMain = new JSONObject(); // json 객체 [{변수명:값, 변수명:값}
		JSONArray jArray = new JSONArray(); // json 배열

		JSONObject row = new JSONObject();

		if (result > 0) {
			System.out.println("member 추가함");
			row.put("result", "ok");
		} else {
			System.out.println("member 추가못함");
			row.put("result", "no");
		}

		jArray.add(0, row);
		jsonMain.put("join_result", jArray);
		return jsonMain;
	}

	// id따라서 카테고리(상의,하의,아우터,기타) 대해 전체개수 리턴
	@SuppressWarnings({ "unchecked", "null" })
	@ResponseBody
	@RequestMapping(value = "/selectCategoryTotalById", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public JSONObject selectCategoryTotalById(Model model, @RequestParam(value = "id") String id) {

		System.out.println("selectCategoryTotalById 실행");

		// id에 따른 상의, 하의, 아우터, 기타 개수 DB에서 가져옴
		int result[] = clothesDao.selectCategoryTotalById(id);

		// 결과값 JSON으로 보내기
		JSONObject jsonMain = new JSONObject(); // json 객체 [{변수명:값, 변수명:값}
		JSONArray jArray = new JSONArray(); // json 배열

		JSONObject row = new JSONObject();
		row.put("upper", result[0]);
		row.put("lower", result[1]);
		row.put("outer", result[2]);
		row.put("etc", result[3]);

		jArray.add(0, row);
		jsonMain.put("total_results", jArray);

		System.out.println("JSON으로 보냄: upper_total " + result[0] + "개, lower " + result[1] + "개, outer " + result[2]
				+ "개, etc " + result[3]);

		return jsonMain;
	}

	// id와 category에 따른 low category 개수 가져옴(후드티, 롱티, 폴라티, 셔츠티, ...)
	@SuppressWarnings({ "unchecked", "null" })
	@ResponseBody
	@RequestMapping(value = "/selectLowCategoryTotalById", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public JSONObject selectLowCategoryTotalById(Model model, @RequestParam(value = "id") String id,
			@RequestParam(value = "category") String category) {

		System.out.println("selectLowCategoryTotalById 실행");

		String[] outer = { "cardigan", "jacket", "padding", "coat", "jumper", "hood_zipup" };
		String[] upper = { "hood_T", "long_T", "shirt", "short_T", "sleeveless", "vest" };
		String[] lower = { "long_pants", "short_pants", "mini_skirt", "long_skirt" };
		String[] etc = { "bag", "cap", "shoes", "accessory" };

		// 받아온 카테고리에 따라 Low_category 이름이 뭐뭐있는지 정해줌
		String[] low_category = new String[10];
		if (category.equals("outer")) {
			low_category = outer;
		} else if (category.equals("upper")) {
			low_category = upper;
		} else if (category.equals("lower")) {
			low_category = lower;
		} else if (category.equals("etc")) {
			low_category = etc;
		}

		// id와 category에 따른 low_category들의 옷 개수 가져옴
		int result[] = clothesDao.selectLowCategoryTotalById(id, category);

		// 결과값 JSON으로 보내기
		JSONObject jsonMain = new JSONObject(); // json 객체 [{변수명:값, 변수명:값}
		JSONArray jArray = new JSONArray(); // json 배열

		JSONObject row = new JSONObject();

		for (int i = 0; i < low_category.length; i++) {
			row.put(low_category[i], result[i]); // row.put("cardigan", "1"); ...
		}

		jArray.add(0, row);
		jsonMain.put("total_results", jArray);

		System.out.println("JSON으로 보냄: " + result[0] + "개, " + result[1] + "개, " + result[2] + "개, " + result[3]);

		return jsonMain;
	}

	// 옷 정보를 가져옴.
	@SuppressWarnings({ "unchecked", "null" })
	@ResponseBody
	@RequestMapping(value = "/loadCloth", produces = "application/json; charset=utf-8", method = RequestMethod.POST) // json.do
	public JSONObject loadCloth(Model model, @RequestParam(value = "id") String id,
			@RequestParam(value = "dress_number") String dress_num) throws IOException {

		System.out.println("loadCloth 실행");

		// id, dressnum에 따른 옷 가져옴
		Clothes clothe = clothesDao.selectByIdNDress_num(id, dress_num);

		// 결과값 JSON으로 보내기
		JSONObject jsonMain = new JSONObject(); // json 객체 [{변수명:값, 변수명:값}]
		JSONArray jArray = new JSONArray(); // json 배열

		JSONObject row = new JSONObject();
		row.put("dress_number", clothe.getDress_number());
		row.put("category1", clothe.getCategory());
		row.put("category2", clothe.getLow_category());
		row.put("color", clothe.getColor());
		row.put("pattern", clothe.getPattern());
		row.put("length", clothe.getLength());
		jArray.add(0, row);

		jsonMain.put("cloth_result", jArray);
		System.out.println(jsonMain);

		return jsonMain;
	}

	// 옷 정보 업데이트
	@SuppressWarnings({ "unchecked", "null" })
	@ResponseBody
	@RequestMapping(value = "/updateClothDB", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public JSONObject updateClothDB(@RequestParam(value = "id") String id,
			@RequestParam(value = "dress_number") String dress_number,
			@RequestParam(value = "category") String category,
			@RequestParam(value = "low_category") String low_category, @RequestParam(value = "color") String color,
			@RequestParam(value = "pattern") String pattern, @RequestParam(value = "length") String length)
			throws IOException {

		System.out.println("updateClothDB 실행");

		// 저거 위에서 받은거대로 db 업데이트
		int result = clothesDao.updateClothes(id, dress_number, category, low_category, color, pattern, length);

		// 결과값 JSON으로 보내기
		JSONObject jsonMain = new JSONObject(); // json 객체 [{변수명:값, 변수명:값}
		JSONArray jArray = new JSONArray(); // json 배열

		JSONObject row = new JSONObject();
		if (result > 0) {
			System.out.println("cloth 정보 변경함");
			row.put("result", "ok");
		} else {
			System.out.println("cloth 정보 변경못함");
			row.put("result", "no");
		}
		jArray.add(0, row);

		jsonMain.put("cloth_result", jArray);
		return jsonMain;
	}

	// 옷 정보 삭제
	@SuppressWarnings({ "unchecked", "null" })
	@ResponseBody
	@RequestMapping(value = "/deleteClothDB", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public JSONObject deleteClothDB(@RequestParam(value = "id") String id,
			@RequestParam(value = "dress_number") String dress_number) throws IOException {

		System.out.println("deleteClothDB 실행");
		// 저거 위에서 받은거대로 db 업데이트
		int result = clothesDao.deleteClothes(id, dress_number);

		// 결과값 JSON으로 보내기
		JSONObject jsonMain = new JSONObject(); // json 객체 [{변수명:값, 변수명:값}
		JSONArray jArray = new JSONArray(); // json 배열

		JSONObject row = new JSONObject();
		if (result > 0) {
			System.out.println("cloth 삭제함");
			row.put("result", "ok");
		} else {
			System.out.println("cloth 삭제못함");
			row.put("result", "no");
		}
		jArray.add(0, row);

		jsonMain.put("delete_result", jArray);
		return jsonMain;
	}

	// 딥러닝 돌려서 결과값 받고, id,dress_num(이름), upper(카테고리), 딥결과, 패턴결과, length(딥결과따라)
	// 옷 table에 저장
	@SuppressWarnings({ "unchecked", "null" })
	@ResponseBody
	@RequestMapping(value = "/uploadImage", produces = "application/json; charset=utf-8", method = RequestMethod.POST) // json.do
	public JSONObject uploadImage5(@RequestParam("new_file") MultipartFile file, ModelMap modelMap,
			@RequestParam(value = "id") String id, @RequestParam(value = "category") String category)
			throws IOException {

		System.out.println("uploadImage실행");
		File testfile = new File(server_folder+"test.jpg");
		if(testfile.exists()) {
			if(testfile.delete()==true)
				System.out.println("기존의 test.jpg 파일을 지웁니다.");
			else
				System.out.println("기존의 test.jpg 파일을 못지웁니다.");
		}
		
		// 안드로이드에서 받은 이미지 파일 저장
		file.transferTo(new File(server_folder+"test.jpg"));
		System.out.println(server_folder+"test.jpg 으로 저장되었습니다.");

		// 저장한 이미지파일을 불러와 이미지 패딩입혀 다시 저장하기
		pythonExec.savePaddingImage("test.jpg"); // image_processing.py 안의 image_padding(folder, image)함수 실행

		// 딥러닝돌려서 옷 결과값 받기 : hood_T,yellow,no 받을 것.
		// category = outer, upper, lower
		String deep_result = pythonExec.getDeepResult(category, "test.jpg");

		// dress_num 여기서 생성해서 안드로이드로 보내주기
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date time = new Date();
		String time2 = format.format(time); // 20190923114305

		// String[] outer = {"cardigan", "jacket", "padding", "coat", "jumper",
		// "hood_zipup"};
		// String[] upper = {"hood_T", "long_T", "shirt", "short_T",
		// "sleeveless", "vest"};
		// String[] lower = {"long_pants", "short_pants", "Leggings", "mini_skirt",
		// "long_skirt"};

		// 여기서 딥려닝 결과들 가져와야함. String dress_num = time2; // 20190923114305 String
		String dress_num = time2; // 20190923114305
		
		// 넘어오는게 hood_T,yellow,no 이므로 split
		String[] results = deep_result.split(",");
		String low_category = results[0]; // "hood_T";
		String color = results[1]; // "white"; // String color = "white";
		String pattern = results[2]; // "no";
		String lengthC = ""; // 아래에서 바뀜

		// 아래 배열 제외한것은 다 long
		String[] short_length = { "short_T", "sleeveless", "vest", "short_pants", "mini_skirt" };
		Arrays.sort(short_length);
		int inShort = Arrays.binarySearch(short_length, low_category);
		System.out.println("길이는 뭐냥"+inShort);
		if (inShort > -1) {
			System.out.println("현재 이미지는 short임.");
			lengthC = "short"; // short = short_T, sleeveless, vest, short_pants, mini_skirt
		} else {
			System.out.println("현재 이미지는 long임.");
			lengthC = "long"; // short = short_T, sleeveless, vest, short_pants, mini_skirt
		}

		// 옷을 DB에 추가
		int result = clothesDao.insertToClothes(id, dress_num, category, low_category, color, pattern, lengthC);
		if (result > 0)
			System.out.println("옷 추가함");
		else
			System.out.println("옷 추가못함");

		// 결과값 JSON으로 보내기
		JSONObject jsonMain = new JSONObject(); // json 객체 [{변수명:값, 변수명:값}]
		JSONArray jArray = new JSONArray(); // json 배열

		JSONObject row = new JSONObject();
		row.put("result", deep_result);
		row.put("dress_num", dress_num);
		row.put("category1", category);
		row.put("category2", low_category);
		row.put("color", color);
		row.put("pattern", pattern);
		row.put("length", lengthC);
		jArray.add(0, row);

		jsonMain.put("deep_result", jArray); // jsonMain = members, books, items 같은거 여러개 가능

		System.out.println("JSON으로 보냄");
		System.out.println(jsonMain);
		// return "home";

		return jsonMain;
	}

	// 받아온 결과대로 DB 업데이트, 원하면 모델 재학습
	@SuppressWarnings({ "unchecked", "null" })
	@ResponseBody
	@RequestMapping(value = "/updateDBdownloadImage", produces = MediaType.IMAGE_JPEG_VALUE) // json.do 라는 객체로
	public byte[] updateDBdownloadImage(@RequestParam(value = "id") String id,
			@RequestParam(value = "dress_number") String dress_number,
			@RequestParam(value = "category") String category,
			@RequestParam(value = "low_category") String low_category, @RequestParam(value = "color") String color,
			@RequestParam(value = "pattern") String pattern, @RequestParam(value = "length") String length)
			throws IOException {

		// 원래 값 가져옴, 하위카테고리 값이 같지 않다면
		Clothes cloth = clothesDao.selectByIdNDress_num(id, dress_number);
		
		// 변경되는 값이 있으면 model을 업데이트함.
		String isOk;
		if(cloth.getLow_category().equals(low_category) == false) {
			isOk = pythonExec.retrainModel(category, server_folder, "test.jpg", low_category); // ok받음
			System.out.println("모델을 재학습시켰습니다. "+cloth.getLow_category()+" to "+low_category);
		}
				
		// 저거 위에서 받은거대로 db 업데이트
		int result = clothesDao.updateClothes(id, dress_number, category, low_category, color, pattern, length);
		System.out.println("옷 업데이트 결과 : " + result);

		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(server_folder+"test.jpg"));
			return IOUtils.toByteArray(in);
		} finally {
			in.close();
		}
	}

	// fashion set 저장
	@SuppressWarnings({ "unchecked", "null" })
	@ResponseBody
	@RequestMapping(value = "/saveSet", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public JSONObject saveSet(Model model, @RequestParam(value = "id") String id,
			@RequestParam(value = "set_name") String set_name, @RequestParam(value = "outer") String outer,
			@RequestParam(value = "upper") String upper, @RequestParam(value = "lower") String lower,
			@RequestParam(value = "cap", required = false) String cap,
			@RequestParam(value = "bag", required = false) String bag,
			@RequestParam(value = "shoes", required = false) String shoes,
			@RequestParam(value = "accessory1", required = false) String accessory1,
			@RequestParam(value = "accessory2", required = false) String accessory2,
			@RequestParam(value = "accessory3", required = false) String accessory3) {

		// fashion set 저장함.
		int result = fashionSetDao.insertToFashionSet(id, set_name, outer, upper, lower, cap, bag, shoes, accessory1,
				accessory2, accessory3);

		JSONObject jsonMain = new JSONObject(); // json 객체 [{변수명:값, 변수명:값}
		JSONArray jArray = new JSONArray(); // json 배열

		JSONObject row = new JSONObject();
		if (result > 0) {
			System.out.println("패션세트 추가함");
			row.put("result", "ok");
		} else {
			System.out.println("패션세트 추가못함");
			row.put("result", "no");
		}
		jArray.add(0, row);

		jsonMain.put("result", jArray); // jsonMain = members, books, items 같은거 여러개 가능
		System.out.println(jsonMain);

		return jsonMain;
	}

	// fashion set 전체 가져옴
	@SuppressWarnings({ "unchecked", "null" })
	@ResponseBody
	@RequestMapping(value = "/loadFashionSetList", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public JSONObject loadFashionSetList(@RequestParam(value = "id") String id) throws IOException {

		// id로 fashion set를 가져옴.
		List<FashionSet> sets = fashionSetDao.selectById(id);
		System.out.println("losdFashionSetList() id따른 set개수 : " + sets.size() + "개");

		// 결과값 JSON으로 보내기
		JSONObject jsonMain = new JSONObject(); // json 객체 [{변수명:값, 변수명:값}
		JSONArray jArray = new JSONArray(); // json 배열
		if (sets.size() != 0) {
			for (int i = 0; i < sets.size(); i++) {
				JSONObject row = new JSONObject();
				FashionSet set = sets.get(i);
				System.out.println(set.getSet_name() + " 패션세트 가져옴");

				// 결과값 JSON으로 보내기
				row.put("result", "" + sets.size());
				row.put("id", set.getId());
				row.put("set_name", set.getSet_name());
				row.put("outer", set.getOuter());
				row.put("upper", set.getUpper());
				row.put("lower", set.getLower());
				row.put("cap", set.getCap());
				row.put("bag", set.getBag());
				row.put("shoes", set.getShoes());
				row.put("accessory1", set.getAccessory1());
				row.put("accessory2", set.getAccessory2());
				row.put("accessory3", set.getAccessory3());

				jArray.add(i, row);
			}
		} else {
			JSONObject row = new JSONObject();
			row.put("result", "" + sets.size());
			jArray.add(0, row);
		}
		jsonMain.put("fashionset_result", jArray); // jsonMain = members, books, items 같은거 여러개 가능

		System.out.println(jsonMain);
		return jsonMain;
	}
	
	// 딥러닝 돌려서 결과값 받고, id,dress_num(이름), upper(카테고리), 딥결과, 패턴결과, length(딥결과따라)
	// 옷table에 저장
	@SuppressWarnings({ "unchecked", "null" })
	@ResponseBody
	@RequestMapping(value = "/recommendSet", produces = "application/json; charset=utf-8", method = RequestMethod.POST) // json.do
	public JSONObject recommendSet(@RequestParam("new_file") MultipartFile file, ModelMap modelMap, @RequestParam(value = "id") String id) throws IOException {

		System.out.println("recommendSet실행");
		modelMap.addAttribute("file", file);

		File testfile = new File(server_folder+"test.jpg");
		if(testfile.exists()) {
			if(testfile.delete()==true)
				System.out.println("기존의 test.jpg 파일을 지웁니다.");
			else
				System.out.println("기존의 test.jpg 파일을 못지웁니다.");
		}
		
		file.transferTo(new File(server_folder+"test.jpg"));
		System.out.println(server_folder+"test.jpg 으로 저장되었습니다.");
		
		// 저장한 이미지를 불러와 상의,하의로 나누고 각각 300x300으로 패딩입혀 저장
		String deep_result = pythonExec.divideUpperLowerDeep("test.jpg");
		
		// 넘어오는게 hood_T,yellow,no,long_pants,black,no 이므로 split
		String[] results = deep_result.split(",");

		// category
		String category[] = new String[2];
		category[0] = "upper";
		category[1] = "lower";

		// Low Category 딥러닝 결과
		String low_category[] = new String[2];
		low_category[0] = results[0];
		low_category[1] = results[3];
		
		// Color 딥러닝 결과
		String color[] = new String[2];
		color[0] = results[1];
		color[1] = results[4];
		
		// Pattern 딥러닝 결과
		String pattern[] = new String[2];
		pattern[0] = results[2];
		pattern[1] = results[5];

		// length 지정
		String length[] = new String[2];
		for (int i = 0; i < 2; i++) { 	// low_category[0], [1]
			String[] short_length = { "short_T", "sleeveless", "vest", "short_pants", "mini_skirt" };
			Arrays.sort(short_length);
			int inShort = Arrays.binarySearch(short_length, low_category[i]);
			if (inShort >= 0) {
				System.out.println("현재 이미지는 short임.");
				length[i] = "short";
			} else {
				System.out.println("현재 이미지는 long임.");
				length[i] = "long";
			}
		}

		// 옷이름 아무거나 지정. 저장할 것 아니라 상관 없음
		String dressnum[] = {"1","2"};
		
		// 결과값 JSON으로 보내기
		System.out.println("이제 JSON으로 보낼것");
		JSONObject jsonMain = new JSONObject(); // json 객체 [{변수명:값, 변수명:값}]
		JSONArray jArray = new JSONArray(); // json 배열

		for (int i = 0; i < 2; i++) {
			JSONObject row = new JSONObject();
			row.put("result", low_category[i]);
			row.put("dress_num", dressnum[i]);
			row.put("category1", category[i]);
			row.put("category2", low_category[i]);
			row.put("color", color[i]);
			row.put("pattern", pattern[i]);
			row.put("length", length[i]);
			jArray.add(i, row);
		}
		jsonMain.put("deep_result", jArray);

		System.out.println("JSON으로 보냄");
		System.out.println(jsonMain);

		return jsonMain;
	}
	
	@SuppressWarnings({ "unchecked", "null" })
	@ResponseBody
	@RequestMapping(value = "/selectClothesbyCategoryNColor", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public JSONObject selectClothesbyCategoryNColor(Model model, @RequestParam(value = "id") String id,
			@RequestParam(value = "upper_category") String upper_category,
			@RequestParam(value = "upper_color") String upper_color,
			@RequestParam(value = "lower_category") String lower_category,
			@RequestParam(value = "lower_color") String lower_color) {

		// String color[] = {"beige", "black", "blue", "brown", "fluorescent", "gray", "green", "light_blue", "orange", "pink", "purple", "red", "white", "yellow"}
		// beige : brown, white
		// black : gray
		// blue : light_blue
		// brown : red
		// fluorescent : green, yellow
		// gray : black, white
		// green : fluorescent, yellow
		// light_blue : blue, white
		// orange : yellow
		// pink : red, brown, purple
		// red : brown, pink, purple
		// white : gray, beige
		// yellow : fluorescent
		
		// 결과값 JSON으로 보내기
		JSONObject jsonMain = new JSONObject(); // json 객체 [{변수명:값, 변수명:값}
		JSONArray jArray = new JSONArray(); // json 배열

		System.out.println("옷 카테고리, 색에따라 db에서 가져옴.");
		List<Clothes> upper_clothes = clothesDao.selectByIdNCategoryNColor(id, upper_category, upper_color);
		List<Clothes> lower_clothes = clothesDao.selectByIdNCategoryNColor(id, lower_category, lower_color);

		for (int j = 0; j < upper_clothes.size(); j++) { // upper 가져온것대해 clothes 정보 보냄
			JSONObject row = new JSONObject();
			row.put("dress_num", upper_clothes.get(j).getDress_number());
			row.put("category1", upper_clothes.get(j).getCategory());
			row.put("category2", upper_clothes.get(j).getLow_category());
			row.put("color", upper_clothes.get(j).getColor());
			row.put("pattern", upper_clothes.get(j).getPattern());
			row.put("length", upper_clothes.get(j).getLength());
			jArray.add(j, row);
		}
		jsonMain.put("upper_result", jArray);

		jArray = new JSONArray(); // json 배열
		for (int j = 0; j < lower_clothes.size(); j++) { // upper 가져온것대해 clothes 정보 보냄
			JSONObject row = new JSONObject();
			row.put("dress_num", lower_clothes.get(j).getDress_number());
			row.put("category1", lower_clothes.get(j).getCategory());
			row.put("category2", lower_clothes.get(j).getLow_category());
			row.put("color", lower_clothes.get(j).getColor());
			row.put("pattern", lower_clothes.get(j).getPattern());
			row.put("length", lower_clothes.get(j).getLength());
			jArray.add(j, row);
		}
		jsonMain.put("lower_result", jArray); // jsonMain = members, books, items 같은거 여러개 가능

		System.out.println("JSON으로 보냄: upper " + upper_clothes.size() + "개, lower " + lower_clothes.size() + "개");
		System.out.println(jsonMain);

		return jsonMain;
	}

	@SuppressWarnings({ "unchecked", "null" })
	@ResponseBody
	@RequestMapping(value = "/selectClothesbyCategoryNPattern", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public JSONObject selectClothesbyCategoryNPattern(Model model, @RequestParam(value = "id") String id,
			@RequestParam(value = "upper_category") String upper_category,
			@RequestParam(value = "upper_pattern") String upper_pattern,
			@RequestParam(value = "lower_category") String lower_category,
			@RequestParam(value = "lower_pattern") String lower_pattern) {

		// 결과값 JSON으로 보내기
		JSONObject jsonMain = new JSONObject(); // json 객체 [{변수명:값, 변수명:값}
		JSONArray jArray = new JSONArray(); // json 배열

		System.out.println("옷 카테고리, 패턴에따라 db에서 가져옴.");
		List<Clothes> upper_clothes = clothesDao.selectByIdNCategoryNPattern(id, upper_category, upper_pattern);
		List<Clothes> lower_clothes = clothesDao.selectByIdNCategoryNPattern(id, lower_category, lower_pattern);

		for (int j = 0; j < upper_clothes.size(); j++) { // upper 가져온것대해 clothes 정보 보냄
			JSONObject row = new JSONObject();
			row.put("dress_num", upper_clothes.get(j).getDress_number());
			row.put("category1", upper_clothes.get(j).getCategory());
			row.put("category2", upper_clothes.get(j).getLow_category());
			row.put("color", upper_clothes.get(j).getColor());
			row.put("pattern", upper_clothes.get(j).getPattern());
			row.put("length", upper_clothes.get(j).getLength());
			jArray.add(j, row);
		}
		jsonMain.put("upper_result", jArray);

		jArray = new JSONArray(); // json 배열
		for (int j = 0; j < lower_clothes.size(); j++) { // upper 가져온것대해 clothes 정보 보냄
			JSONObject row = new JSONObject();
			row.put("dress_num", lower_clothes.get(j).getDress_number());
			row.put("category1", lower_clothes.get(j).getCategory());
			row.put("category2", lower_clothes.get(j).getLow_category());
			row.put("color", lower_clothes.get(j).getColor());
			row.put("pattern", lower_clothes.get(j).getPattern());
			row.put("length", lower_clothes.get(j).getLength());
			jArray.add(j, row);
		}
		jsonMain.put("lower_result", jArray); // jsonMain = members, books, items 같은거 여러개 가능

		System.out.println("JSON으로 보냄: upper " + upper_clothes.size() + "개, lower " + lower_clothes.size() + "개");
		System.out.println(jsonMain);

		return jsonMain;
	}
	
	@SuppressWarnings({ "unchecked", "null" })
	@ResponseBody
	@RequestMapping(value = "/selectClothesbyCategoryNLength", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
	public JSONObject selectClothesbyCategoryNLength(Model model, @RequestParam(value = "id") String id,
			@RequestParam(value = "upper_category") String upper_category,
			@RequestParam(value = "upper_length") String upper_length,
			@RequestParam(value = "lower_category") String lower_category,
			@RequestParam(value = "lower_length") String lower_length) {

		// 결과값 JSON으로 보내기
		JSONObject jsonMain = new JSONObject(); // json 객체 [{변수명:값, 변수명:값}
		JSONArray jArray = new JSONArray(); // json 배열

		System.out.println("옷 카테고리, 패턴에따라 db에서 가져옴.");
		List<Clothes> upper_clothes = clothesDao.selectByIdNCategoryNLength(id, upper_category, upper_length);
		List<Clothes> lower_clothes = clothesDao.selectByIdNCategoryNLength(id, lower_category, lower_length);

		for (int j = 0; j < upper_clothes.size(); j++) { // upper 가져온것대해 clothes 정보 보냄
			JSONObject row = new JSONObject();
			row.put("dress_num", upper_clothes.get(j).getDress_number());
			row.put("category1", upper_clothes.get(j).getCategory());
			row.put("category2", upper_clothes.get(j).getLow_category());
			row.put("color", upper_clothes.get(j).getColor());
			row.put("pattern", upper_clothes.get(j).getPattern());
			row.put("length", upper_clothes.get(j).getLength());
			jArray.add(j, row);
		}
		jsonMain.put("upper_result", jArray);

		jArray = new JSONArray(); // json 배열
		for (int j = 0; j < lower_clothes.size(); j++) { // upper 가져온것대해 clothes 정보 보냄
			JSONObject row = new JSONObject();
			row.put("dress_num", lower_clothes.get(j).getDress_number());
			row.put("category1", lower_clothes.get(j).getCategory());
			row.put("category2", lower_clothes.get(j).getLow_category());
			row.put("color", lower_clothes.get(j).getColor());
			row.put("pattern", lower_clothes.get(j).getPattern());
			row.put("length", lower_clothes.get(j).getLength());
			jArray.add(j, row);
		}
		jsonMain.put("lower_result", jArray); // jsonMain = members, books, items 같은거 여러개 가능

		System.out.println("JSON으로 보냄: upper " + upper_clothes.size() + "개, lower " + lower_clothes.size() + "개");
		System.out.println(jsonMain);

		return jsonMain;
	}
}

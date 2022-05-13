영어만가능. 대문자 넣지마세요. 검색기능 안됨.영어만가능.


SearchingGallery.java에서 쓰이는 파일들
	row_items.xml		//list뷰에서 각 리스트에 사용될 행파일
	searching_gallery.xml	//검색view와 list뷰를 갖고있음
	GalleryInfro.java	//갤러리정보를 담고있음

SearchingGallery.java에서
27~29번째줄
 int images[] = {R.drawable.project_gallery,R.drawable.project_gallery,R.drawable.project_gallery,R.drawable.project_gallery,R.drawable.project_gallery};     //이미지 가져와서 동적으로 넣어줘야함
    String names[]={"apple","banana","kiwi","watermelon","orange"};      //여기나중에 동적으로 끌어서 넣어줘야함  ,일단 소문자만 넣어야함
    String desc[]={"This is  apple","This is Banana","This is Kiwi","This is Watermelon","This is orange"};  //여기도 동적으로 넣어줘야함
이부분 서버에서 데이터 끌어올때 image,name,desc(description)순서대로 잘 넣기. 순서대로 들어가서 화면에 표시됨.



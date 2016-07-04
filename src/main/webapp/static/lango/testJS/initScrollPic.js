function scrollPic() { 
var sp = new ScrollPic();
	  sp.scrollContId   = "FS_Cont_01"; //内容容器ID
	  sp.arrLeftId      = "FS_arr_left_01";//左箭头ID
	  sp.arrRightId     = "FS_arr_right_01"; //右箭头ID
	  sp.dotListId      = "FS_numList_01";//点列表ID
	  sp.dotClassName   = "";//点className
	  sp.dotOnClassName	= "current";//当前点className
	  sp.listType		= "";//列表类型(number:数字，其它为空)
	  sp.listEvent      = "onmousedown"; //切换事件
	  sp.frameWidth     = 900;//显示框宽度
	  sp.pageWidth      = 900; //翻页宽度
	  sp.upright        = false; //垂直滚动
	  sp.speed          = 30; //移动速度(单位毫秒，越小越快)
	  sp.space          = 60; //每次移动像素(单位px，越大越快)
	  sp.autoPlay       = true; //自动播放
	  sp.autoPlayTime   = 5; //自动播放间隔时间(秒)
	  sp.circularly     = true;
	  sp.initialize(); //初始化
}